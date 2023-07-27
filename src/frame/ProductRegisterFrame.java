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

public class ProductRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductRegisterFrame frame = new ProductRegisterFrame();
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
	public ProductRegisterFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 41);
		contentPane.add(titleLabel);
		
		JLabel productNameLabel = new JLabel("상품명");
		productNameLabel.setBounds(12, 71, 57, 21);
		contentPane.add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(72, 71, 350, 21);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productPriceLabel = new JLabel("가격");
		productPriceLabel.setBounds(12, 102, 57, 21);
		contentPane.add(productPriceLabel);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(72, 102, 350, 21);
		contentPane.add(productPriceTextField);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setBounds(12, 133, 57, 21);
		contentPane.add(productColorLabel);

		JComboBox colorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setCombmBoxModel(colorComboBox, ProductColorService.getInstance().getProductColorNameList());
		colorComboBox.setBounds(72, 133, 350, 21);
		contentPane.add(colorComboBox);
		
		JLabel productCategoryLabel = new JLabel("카테고리");
		productCategoryLabel.setBounds(12, 164, 57, 21);
		contentPane.add(productCategoryLabel);
		
		JComboBox categoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setCombmBoxModel(categoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		categoryComboBox.setBounds(72, 163, 350, 21);
		contentPane.add(categoryComboBox);
		
		JButton registerSubmitButton = new JButton("등록하기");
		registerSubmitButton.addMouseListener(new MouseAdapter() {
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
				
				Product product = Product.builder()
						.productName(productName)
						// Integer.parseInt(): 문자열을 숫자로
						.productPrice(Integer.parseInt(productPrice))
						// 바로 가지고 올 수 없다 = 생성하여 이름만 넣어준다
						.productColor(ProductColor.builder().productColorName(productColorName).build())
						.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
						.build();
				
				if(!ProductService.getInstance().registerProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품등록 중 오류가 발생했습니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "새로운 상품을 등록했습니다.", "등록성공", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productNameTextField);
				CustomSwingTextUtil.clearTextField(productPriceTextField);
				colorComboBox.setSelectedIndex(0);
				categoryComboBox.setSelectedIndex(0);
				
			}
		});
		registerSubmitButton.setBounds(12, 199, 410, 41);
		contentPane.add(registerSubmitButton);
		
		
	}
}
