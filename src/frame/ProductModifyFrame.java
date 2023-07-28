package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;
import service.ProductCategoryService;
import service.ProductColorService;
import service.ProductService;
import utils.CustomSwingComboBoxUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductModifyFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;
	private JTextField productIdTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductModifyFrame frame = new ProductModifyFrame(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductModifyFrame(int productId) {
		//DISPOSE_ON_CLOSE : main창을 닫지 않는 이상 개별적으로 창을 끌 수 있음
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 수정");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 41);
		contentPane.add(titleLabel);
		
		JLabel productIdLabel = new JLabel("상품번호");
		productIdLabel.setBounds(12, 61, 57, 21);
		contentPane.add(productIdLabel);
		
		productIdTextField = new JTextField();
		productIdTextField.setColumns(10);
		productIdTextField.setBounds(72, 61, 350, 21);
		productIdTextField.setEnabled(false);
		contentPane.add(productIdTextField);
		
		JLabel productNameLabel = new JLabel("상품명");
		productNameLabel.setBounds(12, 90, 57, 21);
		contentPane.add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(72, 90, 350, 21);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productPriceLabel = new JLabel("가격");
		productPriceLabel.setBounds(12, 121, 57, 21);
		contentPane.add(productPriceLabel);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(72, 121, 350, 21);
		contentPane.add(productPriceTextField);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setBounds(12, 152, 57, 21);
		contentPane.add(productColorLabel);

		JComboBox colorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setCombmBoxModel(colorComboBox, ProductColorService.getInstance().getProductColorNameList());
		colorComboBox.setBounds(72, 152, 350, 21);
		contentPane.add(colorComboBox);
		
		JLabel productCategoryLabel = new JLabel("카테고리");
		productCategoryLabel.setBounds(12, 183, 57, 21);
		contentPane.add(productCategoryLabel);
		
		JComboBox categoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setCombmBoxModel(categoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		categoryComboBox.setBounds(72, 182, 350, 21);
		contentPane.add(categoryComboBox);
		
		JButton modifySubmitButton = new JButton("수정하기");
		modifySubmitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String productName = productNameTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productName)) { return; }
				
				String productPrice = productPriceTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productPrice)) { return; }
				
				String productColorName = (String) colorComboBox.getSelectedItem();
				// DB에서 못가져 왔을때
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productColorName)) { return; }
				
				String productCategoryName = (String) categoryComboBox.getSelectedItem();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productCategoryName)) { return; }
				
				if(ProductService.getInstance().isProductNameDuplicated(productName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 상품입니다.", "중복오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/*수정된 프로덕트 생성?*/
				Product product = Product.builder()
						.productId(productId)
						.productName(productName)
						// Integer.parseInt(): 문자열을 숫자로
						.productPrice(Integer.parseInt(productPrice))
						// 바로 가지고 올 수 없다 = 생성하여 이름만 넣어준다
						.productColor(ProductColor.builder().productColorName(productColorName).build())
						.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
						.build();				
				
				if(!ProductService.getInstance().modifyProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품수정 중 오류가 발생했습니다.", "수정오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "상품을 수정했습니다.", "수정성공", JOptionPane.PLAIN_MESSAGE);
				ProductSearchFrame.getInstance().setSearchProductTableModel();
				//수정창을 닫음
				dispose();
				
			}
		});
		modifySubmitButton.setBounds(12, 218, 410, 41);
		contentPane.add(modifySubmitButton);
		
		//해당 상품을 수정하는 버튼을 누르면 상품의 정보를 담은 창이 뜬다 -> 메소드로 빼도된다
		Product product = ProductService.getInstance().getProductByProductId(productId);
		
		if(product != null) {
		productIdTextField.setText(Integer.toString(product.getProductId()));
		productNameTextField.setText(product.getProductName());
		productPriceTextField.setText(Integer.toString(product.getProductPrice()));
		colorComboBox.setSelectedItem(product.getProductColor().getProductColorName());
		categoryComboBox.setSelectedItem(product.getProductCategory().getProductCategoryName());
		}
		
	}
}
