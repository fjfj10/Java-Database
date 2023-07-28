package frame;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import entity.Product;
import service.ProductService;
import utils.CustomSwingTableUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductSearchFrame extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	private JTextField searchTextField;
	private DefaultTableModel searchProductTableModel;
	private JTable productTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductSearchFrame frame = new ProductSearchFrame();
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
	public ProductSearchFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품조회");
		titleLabel.setBounds(12, 10, 128, 25);
		contentPane.add(titleLabel);
		
		JButton producModifyButton = new JButton("수정");
		producModifyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!producModifyButton.isEnabled()) { return; }
				
				int productId = Integer.parseInt((String)searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0));
				System.out.println(productId);
				ProductModifyFrame productModifyFrame = new ProductModifyFrame(productId);
				productModifyFrame.setVisible(true);
				
			}
		});
		producModifyButton.setBounds(766, 11, 97, 23);
		producModifyButton.setEnabled(false);
		contentPane.add(producModifyButton);
		
		JButton productRemoveButton = new JButton("삭제");
		productRemoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!productRemoveButton.isEnabled()) { return; }
				
				int productId = Integer.parseInt((String) searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0));
				
				if(!ProductService.getInstance().removeProduct(productId)) {
					JOptionPane.showMessageDialog(contentPane, "상품을 삭제 중 오류가 발생했습니다.", "삭제오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "선택한 상품을 삭제했습니다.", "삭제성공", JOptionPane.PLAIN_MESSAGE);
				setSearchProductTableModel();
				productRemoveButton.setEnabled(false);
				producModifyButton.setEnabled(false);
			}
		});
		productRemoveButton.setBounds(875, 11, 97, 23);
		productRemoveButton.setEnabled(false);
		contentPane.add(productRemoveButton);
		
		JLabel searchLabel = new JLabel("검색");
		searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLabel.setBounds(446, 48, 55, 25);
		contentPane.add(searchLabel);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "상품명", "색상", "카테고리"}));
		comboBox.setBounds(506, 49, 97, 23);
		contentPane.add(comboBox);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setSearchProductTableModel();
					productRemoveButton.setEnabled(false);
					producModifyButton.setEnabled(false);
				}
			}
			
		});
		searchTextField.setBounds(605, 48, 367, 25);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 80, 960, 371);
		contentPane.add(scrollPane);
		
		productTable = new JTable();
		setSearchProductTableModel();
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productRemoveButton.setEnabled(true);
				producModifyButton.setEnabled(true);
				
			}
		});
		scrollPane.setViewportView(productTable);
		
	}
	
	
	private void setSearchProductTableModel() {
		String searchOption = (String) comboBox.getSelectedItem();
		String searchValue = searchTextField.getText();
		
		List<Product> searchProductList = ProductService.getInstance().searchProduct(searchOption, searchValue);
		String [][] searchProductModelArray = CustomSwingTableUtil.serchProductListToArray(searchProductList);
		
		searchProductTableModel = new DefaultTableModel(
				searchProductModelArray,
			new String[] {
				"product_id", "product_name", "product_price", "product_color_id", "product_color_name", "product__category_id", "product_ctegory_name"
			}
		);
		productTable.setModel(searchProductTableModel);
	}
}
