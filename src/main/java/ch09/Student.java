package ch09;

import java.sql.Date;

// 엔티티 클래스는 데이터베이스와 대응된다. 컬렴명 = 속성 
public class Student {
	// 속성
	private int id; // 숫자는 크기에 따라서 다양하게 맞춰주면 된다 (long이나 int)
	private String username;
	private String univ;
	private Date birth; // Date는 임포트 2개임! 여기서는 java.sql을 import 해야 한다! (db에서 가져온 걸 넣을거니까)  
	private String email;

	// 단순하게 데이터를 받아서 보내주는 역할을 하기 때문에
	// getter, setter 메소드 생성 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
