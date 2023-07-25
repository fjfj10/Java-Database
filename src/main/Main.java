package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	
	public static void main(String[] args) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		String sql  = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			//자바와 DB를 연결함
			con = pool.getConnection();
			
			//실행할 쿼리문 작성
			sql = "select * from user_tb";
			
			//작성한 쿼리문을 가공
			pstmt = con.prepareStatement(sql);
			
			//가공된 쿼리문 실행 -> 결과를 ResultSet으로 변환
			rs = pstmt.executeQuery();
			
			//rs는 리스트 형태, rs.next() = 행 하나를 통채로 들고옴 => 결과값이 없으면 rs.next()= false로 반복 중단
			//결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			
			while (rs.next()) {
				// .getString(2) 2번째 열에 있는걸 들고온다
				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3));
//				System.out.println(rs.getString(2));
				//.getInt() -> 정수
				//.getString() -> 문자열
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//freeConnection: 연결을 끊을때 객체 소멸 시켜줌
			//생성된 rs, pstmt, con 객체 소멸(데이터베이스 연결 해제)
			//실행되는 중간에 터져도 무조건 한번 실행되야함 -> finally에 있어야함.
		} finally {
		pool.freeConnection(con, pstmt, rs);
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////select
		try {
			//데이터 베이스 연결
			con = pool.getConnection();

			//쿼리문 작성
			sql = "insert into user_tb values(0, ?, ?)";
			
			//쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			
			//쿼리문 가공
			pstmt.setString(1, "ttt");
			pstmt.setString(2, "1234");
			
			//쿼리문 실행
			int successCount = pstmt.executeUpdate();
			System.out.println("insert 성공횟수: " + successCount);
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////insert
		try {
			//자바와 DB를 연결함
			con = pool.getConnection();
			
			//실행할 쿼리문 작성
			sql = "select * from user_tb";
			
			//작성한 쿼리문을 가공
			pstmt = con.prepareStatement(sql);
			
			//가공된 쿼리문 실행 -> 결과를 ResultSet으로 변환
			rs = pstmt.executeQuery();
			
			//rs는 리스트 형태, rs.next() = 행 하나를 통채로 들고옴 => 결과값이 없으면 rs.next()= false로 반복 중단
			//결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			
			while (rs.next()) {
				// .getString(2) 2번째 열에 있는걸 들고온다
				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3));
//				System.out.println(rs.getString(2));
				//.getInt() -> 정수
				//.getString() -> 문자열
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//freeConnection: 연결을 끊을때 객체 소멸 시켜줌
			//생성된 rs, pstmt, con 객체 소멸(데이터베이스 연결 해제)
			//실행되는 중간에 터져도 무조건 한번 실행되야함 -> finally에 있어야함.
		} finally {
		pool.freeConnection(con, pstmt, rs);
		}
	}
	
}
