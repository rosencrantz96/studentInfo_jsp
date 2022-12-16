package ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
	// 필드
	Connection conn = null; // Connection: 데이터베이스와 연결해주는 역할을 하는 인터페이스! 
	PreparedStatement pstmt; // PreparedStatement: 쿼리문의 실행을 담당하는 인터페이스! (실행시켜준다고 ㅇㅇ)
	// sql문을 이 인터페이스에 주면 sql이 컴파일이 된다 (컴퓨터가 알아들을 수 있는 말로 변한다는 뜻) 그 다음에... 어쩌구 저쩌구...
	
	
	// jdbc 뜻: 자바하고 DB를 연결해주는 api -> 오라클은 ojbcd6.jar
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	// 오라클에서 게정 정보를 통해 호스트 이름, 포트 번호, SID 꼭 확인! @localhost: 여기 뒤에 내 포트 번호랑 SID 써줘야 함

	
	// DB 연결 메소드
	public void open() {
		try {
			Class.forName(JDBC_DRIVER); // 드라이버 로드
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234"); // DB연결 (내 계정 아이디, 비번)  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// DB 닫는 메소드
	public void close() {
		try {
			// ★pstmt, conn은 리소스(데이터를 읽고 쓰는 객체)이므로 사용 후 반드시 닫아줘야 한다.
			// 계속 열려있는 상태는 메모리를 잡아먹으니깐... 꼭 닫아줘야 한다! 
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 학생 정보를 다 불러온다. (select)
	public ArrayList<Student> getAll() {
		open(); // 1. db오픈
		// 2. 쿼리문 실행
		ArrayList<Student> students = new ArrayList<>(); // 2-1. student 객체를 담을 리스트 준비
		
		try {
			// 2-2. prepareStatement 이 안에 쿼리문을 쓰면 된다! 
			// 쿼리문 작성 
			pstmt = conn.prepareStatement("select * from student");
			// 2-3. 쿼리문 실행 (executeQuery(): select문 사용 시 사용)
			ResultSet rs = pstmt.executeQuery(); // ResultSet: 데이터를 받는 역할을 하는 인터페이스 
			
			// next(): 한 행 씩 값이 있는지 없는지 판단한 후 출력 (api 문서 확인)
			while(rs.next()) {
				// 그리고 이제 값을 넣어줘야 하니까...! 
				Student s = new Student(); // 객체를 생성
				s.setId(rs.getInt("id"));
				s.setUsername(rs.getString("username"));
				s.setUniv(rs.getString("univ"));
				s.setBirth(rs.getDate("birth"));
				s.setEmail(rs.getString("email"));
				
				students.add(s);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(); // 리소스를 열었다면 닫아주기도 해야 한다! 
		}
		
		return students; // ArrayList에 담긴 학생 데이터를 리턴 
	}
	
	// 학생 정보를 입력. (insert)
	public void insert(Student s) {
		// 1. 먼저 db 오픈
		open(); 
		// 2. 쿼리문을 미리 작성해둔다. 
		String sql = "insert into student values(id_seq.nextval, ?, ?, ?, ?) ";
		// ?: 어떤 데이터가 들어올지 모른다.
		
		// 아래는 비슷한 흐름! 
		try {
			pstmt = conn.prepareStatement(sql); // 실행할 쿼리문 입력
			// setString: ? 안에 데이터를 넣어준다! 
			// 오라클의 데이터 타입으로 변환을 해준다. 
			pstmt.setString(1, s.getUsername()); // pstmt.setString(값을 넣어줄 위치, 넣어줄 데이터);
			pstmt.setString(2, s.getUniv());
			pstmt.setDate(3, s.getBirth());
			pstmt.setString(4, s.getEmail());
			
			// 값 세팅이 끝난 후 데이터를 받아준다.
			pstmt.executeUpdate(); //insert, delete, update 실행 시 사용하는 메소드 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 리소스를 열었으니 반드시 닫아줘야! 
			close();
		}
	}
	
}
