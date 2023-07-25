package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DBConnectionMgr;

public class Main2 {
//	public static void main(String[] args) {
//		DBConnectionMgr pool = DBConnectionMgr.getInstance();
//		
//		Connection con = null;
//		String sql  = null;
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			//자바와 DB를 연결함
//			con = pool.getConnection();
//			
//			//실행할 쿼리문 작성
//			sql = "select * from product_tb";
//			
//			//작성한 쿼리문을 가공
//			pstmt = con.prepareStatement(sql);
//			
//			//가공된 쿼리문 실행 -> 결과를 ResultSet으로 변환
//			rs = pstmt.executeQuery();
//			
//			//결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
//			System.out.println("product_code\t|\tproduct_Name");
//			
//			while (rs.next()) {
//				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2));
//
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		pool.freeConnection(con, pstmt, rs);
//		}
//		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////select
//		try {
//			//데이터 베이스 연결
//			con = pool.getConnection();
//
//			//쿼리문 작성
//			sql = "insert into product_tb values(?, ?)";
//			
//			//쿼리문 가공 준비
//			pstmt = con.prepareStatement(sql);
//			
//			//쿼리문 가공
//			pstmt.setInt(1, 20230706);
//			pstmt.setString(2, "상품6");
//			
//			//쿼리문 실행
//			int successCount = pstmt.executeUpdate();
//			System.out.println("insert 성공횟수: " + successCount);
//		
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			pool.freeConnection(con, pstmt, rs);
//		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//		try {
//			//자바와 DB를 연결함
//			con = pool.getConnection();
//			
//			//실행할 쿼리문 작성
//			sql = "select * from product_tb";
//			
//			//작성한 쿼리문을 가공
//			pstmt = con.prepareStatement(sql);
//			
//			//가공된 쿼리문 실행 -> 결과를 ResultSet으로 변환
//			rs = pstmt.executeQuery();
//			
//			//결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
//			System.out.println("product_code\t|\tproduct_Name");
//			
//			while (rs.next()) {
//				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2));
//
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		pool.freeConnection(con, pstmt, rs);
//		}
//	}
	public static void main(String[] args) {
		System.out.println(getProductByProductCode(20230701));
		insertProduct(20230707, "상품7");
		System.out.println(getProductByProductCode(20230707));
		
	}
	public static void insertProduct(int productCode, String productName) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = pool.getConnection();
			
			String sql = "insert into product_tb values(?, ?)";			
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, productCode);
			pstmt.setString(2, productName);
			
			pstmt.executeLargeUpdate();
			//성공 횟수에 대한 표시일 뿐
			System.out.println("Sussessful!!");
			
		} catch (Exception e) {
		}finally {
			pool.freeConnection(con, pstmt);
			}
	}
	public static Map<String, Object> getProductByProductCode(int productCode) {
		Map<String, Object> resultMap = new HashMap();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			String sql = "select product_code, product_name from product_tb where product_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productCode);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultMap.put("product_code", rs.getInt(1));
				resultMap.put("product_name", rs.getString(2));
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return resultMap;
		
	}
}
