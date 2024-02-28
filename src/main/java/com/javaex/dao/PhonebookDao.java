package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhonebookDao {

		
//mybitis없을때!
	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	//pool 설치후 지우기 -> 이 역할을 하는걸 설치해줬으니 쓸모없어짐.
//	private String driver = "com.mysql.cj.jdbc.Driver";
//	private String url = "jdbc:mysql://localhost:3306/phone_db";
//	private String id = "phone";
//	private String pw = "phone";
	
	@Autowired
	private DataSource dataSource;//여기안에 메소드가 만들어져있는거임

	// 생성자
	// 메소드-gs

	// 메소드-일반

	// 연결
	public void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩 -> 라이브러리가 불러올거임.
			//Class.forName(driver);

			// 2. Connection 얻어오기
//			conn = DriverManager.getConnection(url, id, pw);
			conn = dataSource.getConnection();//비어있는거 알아서 연결해주는 역할
		}
//		 catch (ClassNotFoundException e) {
//			System.out.println("error: 드라이버 로딩 실패 - " + e);
//
//		} 이것도 커넥션 관련이라 지워주기
		catch (SQLException e) {
			System.out.println("error:" + e);

		}
	}

	// 종료
	public void close() {//Dao와 DataSource는 같은 공간에 있고 연결이 끊겼다 연결됨. DB는 외부에 있는거고 진짜로 끊어지진않음.
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 전체가져오기
	public List<PersonVo> personSelect() {

		this.getConnection();

		List<PersonVo> personList = new ArrayList<PersonVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select person_id, ";
			query += "	      name, ";
			query += "        hp, ";
			query += "	      company ";
			query += " from person ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {// 반복
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				// db에서 가져온 데이터 vo로 묶기
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				// 리스트에 주소 추가
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return personList;
	}

	// 등록
	public int personInsert(PersonVo personVo) {
		int count = -1;

		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into person ";
			query += " values(null, ?, ?, ?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();

		return count;
	}

	// 삭제
	public int personDelete(int no) {
		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " delete from person ";
			query += " where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}

	// 1개 가져오기
	public PersonVo personSelectOne(int no) {

		this.getConnection();

		PersonVo personVo = null;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select person_id, ";
			query += "	      name, ";
			query += "        hp, ";
			query += "	      company ";
			query += " from person ";
			query += " where person_id=? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {// 반복
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				// db에서 가져온 데이터 vo로 묶기
				personVo = new PersonVo(personId, name, hp, company);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return personVo;
	}
	
	// 수정
	public int personUpdate(PersonVo personVo) {
		int count = -1;

		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update person ";
			query += " set name=?, ";
			query += " 	   hp=?, ";
			query += "     company=? ";
			query += " where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());
			
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();

		return count;
	}
}