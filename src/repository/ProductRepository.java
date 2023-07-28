package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnectionMgr;
import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;

public class ProductRepository {
	
	private DBConnectionMgr pool;
	private static ProductRepository instance;
	
	private ProductRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepository();
		}
		return instance;
	}
	
	public Product findProductId(int productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			con = pool.getConnection();
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "	pt.product_name,\r\n"
					+ "	pt.product_price,\r\n"
					+ "    \r\n"
					+ "	pt.product_color_id,\r\n"
					+ "	pcot.product_color_name,\r\n"
					+ "	\r\n"
					+ " pt.product_category_id,\r\n"
					+ "	pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						.productCategoryID(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryID(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return product;
	}
	
	
	public Product findProductName(String productName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			con = pool.getConnection();
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "	pt.product_name,\r\n"
					+ "	pt.product_price,\r\n"
					+ "    \r\n"
					+ "	pt.product_color_id,\r\n"
					+ "	pcot.product_color_name,\r\n"
					+ "	\r\n"
					+ " pt.product_category_id,\r\n"
					+ "	pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						.productCategoryID(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryID(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return product;
	}
	
	public int saveProduct(Product product) {
		Connection con = null;
		// 프로시저를 호출할때는 pstmt 대신 cstmt를 사용?
		CallableStatement cstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_insert_product(?, ?, ?, ?) }";
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, product.getProductName());
			cstmt.setInt(2, product.getProductPrice());
			cstmt.setString(3, product.getProductColor().getProductColorName());
			cstmt.setString(4, product.getProductCategory().getProductCategoryName());
			successCount = cstmt.executeUpdate();
//			executeUpdate <-> execute 차이점 ??
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			pool.freeConnection(con);
		}
		return successCount;
		
	}
	
	public List<Product> getSearchProductList(String searchOption, String searchValue) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList = null;
		
		try {
			con = pool.getConnection();
			//아래를 프로시저로 변경해보기
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "	pt.product_name,\r\n"
					+ "	pt.product_price,\r\n"
					+ "    \r\n"
					+ "	pt.product_color_id,\r\n"
					+ "	pcot.product_color_name,\r\n"
					+ "	\r\n"
					+ "    pt.product_category_id,\r\n"
					+ "	pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	1 = 1 ";
			
			if(searchValue != null) {
				if(!searchValue.isBlank()) {
					String whereSql = null;
					switch (searchOption) {
						case "전체":
							whereSql = "and (pt.product_name like concat('%', ?, '%') "
									+ "or pcot.product_color_name like concat('%', ?, '%') "
									+ "or pcat.product_category_name like concat('%', ?, '%'))";
							break;
						
						case "상품명":
							whereSql = "and pt.product_name like concat('%', ?, '%')";
							break;
						
						case "색상":
							whereSql = "and pcot.product_color_name like concat('%', ?, '%')";
							break;
						
						case "카테고리":
							whereSql = "and or pcat.product_category_name like concat('%', ?, '%')";
							break;
							
						default:
							break;
					}
					sql += whereSql;
					
				}
			}
			
			pstmt = con.prepareStatement(sql);
			
			//null체크는 따로 해줘야한다 if(searchValue != null and !searchValue.isBlank()) 불가
			if(searchValue != null) {
				if(!searchValue.isBlank()) {
					if(searchOption.equals("전체")) {
						pstmt.setString(1, searchValue);
						pstmt.setString(2, searchValue);
						pstmt.setString(3, searchValue);
					}else {
						pstmt.setString(1, searchValue);
					}				
				}				
			}
			
			rs = pstmt.executeQuery();
			
			productList = new ArrayList<>();			
			while (rs.next()) {
				Product product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						.productCategoryID(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryID(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
				productList.add(product);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return productList;	
	}
	
	public int deleteProduct(int productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int successeCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "delete from product_tb where product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			successeCount = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		return successeCount;
	}
	
	public int updateProduct(Product product) {
		Connection con = null;
		CallableStatement cstmt = null;
		int successeCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_update_product(?, ?, ?, ?, ? ) }";
			cstmt.setInt(1, product.getProductId());
			cstmt.setString(2, product.getProductName());
			cstmt.setInt(3, product.getProductPrice());
			cstmt.setString(4, product.getProductColor().getProductColorName());
			cstmt.setString(5, product.getProductCategory().getProductCategoryName());
			successeCount = cstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con);
		}
		return successeCount;
	}
}
