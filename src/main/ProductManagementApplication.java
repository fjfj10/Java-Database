package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import frame.ProductCategoryRegisterFrame;
import frame.ProductColorRegisterFrame;
import frame.ProductRegisterFrame;
import frame.ProductSearchFrame;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductManagementApplication extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManagementApplication frame = new ProductManagementApplication();
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
	public ProductManagementApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton productSerchFrameOpenButton = new JButton("상품조회");
		productSerchFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductSearchFrame productSearchFrame = new ProductSearchFrame();
				productSearchFrame.setVisible(true);
			}
		});
		productSerchFrameOpenButton.setBounds(12, 25, 410, 44);
		contentPane.add(productSerchFrameOpenButton);
		
		JButton productRegisterFrameOpenButton = new JButton("상품등록");
		productRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//상품등록 누를때 열리는 Jframe 추가
				ProductRegisterFrame productRegisterFrame = new ProductRegisterFrame();
				productRegisterFrame.setVisible(true);
			}
		});
		productRegisterFrameOpenButton.setBounds(12, 79, 410, 44);
		contentPane.add(productRegisterFrameOpenButton);
		
		
		JButton productColorRegisterFrameOpenButton = new JButton("상품색상등록");
		productColorRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductColorRegisterFrame productColorRegisterFrame = new ProductColorRegisterFrame();
				productColorRegisterFrame.setVisible(true);
			}
		});
		productColorRegisterFrameOpenButton.setBounds(12, 133, 410, 44);
		contentPane.add(productColorRegisterFrameOpenButton);
		
		
		JButton productCategoryRegisterFrameOpenButton = new JButton("상품카테고리등록");
		productCategoryRegisterFrameOpenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductCategoryRegisterFrame productCategoryRegisterFrame = new ProductCategoryRegisterFrame();
				productCategoryRegisterFrame.setVisible(true);
			}
		});
		productCategoryRegisterFrameOpenButton.setBounds(12, 187, 410, 44);
		contentPane.add(productCategoryRegisterFrameOpenButton);
	}
}
