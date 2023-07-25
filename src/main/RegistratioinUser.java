package main;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import config.DBConnectionMgr;

public class RegistratioinUser extends JFrame {

	private JPanel contentPane;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistratioinUser frame = new RegistratioinUser();
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
	public RegistratioinUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(10, 37, 200, 26);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setBounds(12, 12, 57, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호");
		lblNewLabel_1.setBounds(222, 12, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(222, 37, 200, 26);
		contentPane.add(passwordTextField);
		
		JButton addUserButton = new JButton("추가");
		addUserButton.setBounds(10, 73, 410, 26);
		contentPane.add(addUserButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 109, 412, 142);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(getUserTableModel());			
		scrollPane.setViewportView(table);
	}
	
	public DefaultTableModel getUserTableModel() {
		String[] header = new String[] {"user_id", "user_name", "password"};
		List<List<Object>> userListAll = getUserListAll();
		
		Object[][] userModelArray = new Object[userListAll.size()][userListAll.get(0).size()];
		for(int i = 0; i < userListAll.size(); i++) {
			for(int j = 0; j < userListAll.get(i).size(); j++) {
				userModelArray[i][j] = userListAll.get(i).get(j);
			}
		}
		return new DefaultTableModel(userModelArray, header);
	}
	
	public List<List<Object>> getUserListAll() {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<List<Object>> mstList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			String sql = "select user_id, user_name, password from user_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				List<Object> dtList = new ArrayList<>();
				dtList.add(rs.getInt(1));
				dtList.add(rs.getString(2));
				dtList.add(rs.getString(3));
				mstList.add(dtList);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return mstList;
	
		
	}
		
	
}
