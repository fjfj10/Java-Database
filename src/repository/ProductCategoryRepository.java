package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import config.DBConnectionMgr;
import entity.ProductCategory;

public class ProductCategoryRepository {
	
	private DBConnectionMgr pool;
	private static  ProductCategoryRepository instance;
	
	
	private ProductCategoryRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductCategoryRepository getInstance() {
		if(instance == null) {
			instance = new ProductCategoryRepository();
		}
		return instance;		
	}
	
	
	public List<ProductCategory> getProductCategoryListAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductCategory> productCategoryList = null;
		
		try {
			con = pool.getConnection();
			//쓸 때 띄어쓰기 주의 안띄우면 예외뜸
			String sql = "select "
					+ "product_category_id, "
					+ "product_category_name "
					+ "from "
					+ "product_category_tb "
					+ "order by "
					+ "product_category_name";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			productCategoryList = new ArrayList<>();
			
			while (rs.next()) {
				ProductCategory productCategory = ProductCategory.builder()
						.productCategoryID(rs.getInt(1))
						.productCategoryName(rs.getString(2))
						.build();
				
				productCategoryList.add(productCategory);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productCategoryList;	
	}
	
	public ProductCategory findProductCategorybyProductCategoryName(String productCategoryName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductCategory productCategory = null;
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_category_id, "
					+ "product_category_name "
					+ "from "
					+ "product_category_tb "
					+ "where "
					+ "product_category_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productCategoryName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				productCategory = ProductCategory.builder()
						.productCategoryID(rs.getInt(1))
						.productCategoryName(rs.getString(2)).
						build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productCategory;
	}
	//매개변수는 객체 형태로 받는것이 좋다 = 입력받는 매개변수가 많을수록 코드가 늘어나기 때문에 객체를 사용하여 간소화
	public int saveProductCategory(ProductCategory productCategory) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int sussessCount = 0;
		
		try {
			//연결
			con = pool.getConnection();
			//쿼리문 작성
			String sql = "insert into product_Category_tb values (0, ?)";
			// (0, ?) 채우기
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productCategory.getProductCategoryName());
			//결과 받기
			sussessCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		return sussessCount;
	}
	
	
	
}
