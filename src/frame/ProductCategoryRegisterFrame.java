package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ProductCategoryRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPrice;
	private JTextField productColorTextField;
	private JTextField productCategoryTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductCategoryRegisterFrame frame = new ProductCategoryRegisterFrame();
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
	public ProductCategoryRegisterFrame() {
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
		
		productPrice = new JTextField();
		productPrice.setColumns(10);
		productPrice.setBounds(72, 102, 350, 21);
		contentPane.add(productPrice);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setBounds(12, 133, 57, 21);
		contentPane.add(productColorLabel);
		
		productColorTextField = new JTextField();
		productColorTextField.setColumns(10);
		productColorTextField.setBounds(72, 133, 350, 21);
		contentPane.add(productColorTextField);
		
		JLabel productCategoryLabel = new JLabel("분류");
		productCategoryLabel.setBounds(12, 164, 57, 21);
		contentPane.add(productCategoryLabel);
		
		productCategoryTextField = new JTextField();
		productCategoryTextField.setColumns(10);
		productCategoryTextField.setBounds(72, 164, 350, 21);
		contentPane.add(productCategoryTextField);
		
		JButton registerSubmitButton = new JButton("등록하기");
		registerSubmitButton.setBounds(12, 199, 410, 41);
		contentPane.add(registerSubmitButton);
	}
}
