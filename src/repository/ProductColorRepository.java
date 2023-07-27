package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnectionMgr;
import entity.ProductColor;

public class ProductColorRepository {
	
	private DBConnectionMgr pool;
	private static  ProductColorRepository instance;
	
	
	private ProductColorRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductColorRepository getInstance() {
		if(instance == null) {
			instance = new ProductColorRepository();
		}
		return instance;		
	}
	
	public List<ProductColor> getProductColorListAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductColor> productColorList = null;
		
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_color_id, "
					+ "product_color_name "
					+ "from "
					+ "product_color_tb "
					+ "order by "
					+ "product_color_name";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			productColorList = new ArrayList<>();
			
			while (rs.next()) {
				ProductColor productColor = ProductColor.builder()
						.productColorId(rs.getInt(1))
						.productColorName(rs.getString(2))
						.build();
				
				productColorList.add(productColor);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return productColorList;
	}
	
	public ProductColor findProductColorbyProductColorName(String productColorName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductColor productColor = null;
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_color_id, "
					+ "product_color_name "
					+ "from "
					+ "product_color_tb "
					+ "where "
					+ "product_color_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productColorName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				productColor = ProductColor.builder()
						.productColorId(rs.getInt(1))
						.productColorName(rs.getString(2)).
						build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productColor;
	}
	//매개변수는 객체 현태로 
	public int saveProductColor(ProductColor productColor) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int sussessCount = 0;
		
		try {
			//연결
			con = pool.getConnection();
			//쿼리문 작성
			String sql = "insert into product_Color_tb values (0, ?)";
			// ? 채우기
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productColor.getProductColorName());
			//결과 받기
			sussessCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		return sussessCount;
	}
	
	
	
}
