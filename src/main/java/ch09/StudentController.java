package ch09;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class StudentController
 */

// StudentController s = new StudentController(); 서블릿 객체 생성은 톰캣에서 해준다.  
@WebServlet("/studentControll")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDAO dao; // 객체 생성 
	
	// config -> 필드 / init(): 필드를 가져와서 초기화 (api문서 확인해보면 다 나와요)
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config); // 서블릿 초기화하는 역할 
		dao = new StudentDAO(); // init()은 최초에 한 번만 실행되기 때문에 메모리 내에서 객체가 한 번만 만들어진다. 
		// StudentDAO 객체가 딱 한 번만 만들어진다 → 공유해서 쓸 수 있다. 
	}

	// service(): 추상 메소드
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// view에서 넘어오는 한글 데이터 깨짐 방지
		request.setCharacterEncoding("utf-8");
		
		// request: 뷰에서 넘어온 데이터, 정보가 들어있다.
		String action = request.getParameter("action"); // "insert"를 가져옴 
		String view = ""; 
		
		view = insert(request, response); // request와 response 객체를 매개변수로 넘겨준다. 
		
		// getServletContext(): ServletContext를 얻어옴 
		// getRequestDispatcher(이동할 페이지): 이동할 페이지의 경로 지정 
		// forward(): 페이지를 이동시킨다. 내부에서 이동이 되기 때문에 주소가 변하지 않는다. 
		getServletContext().getRequestDispatcher("/ch09/" + view).forward(request, response);
	}
	
	// request 데이터 받아옴 -> DAO에 있는 insert 실행 (DB에 insert가 됨) -> 페이지명 (studentInfo.jsp) 리턴
	public String insert(HttpServletRequest request, HttpServletResponse response) {
		Student s = new Student();
		
		try {
			BeanUtils.populate(s, request.getParameterMap());

			/*
			BeanUtils.populate(s, request.getParameterMap()); -> 아래 코드의 역할을 대신 해준다.
			
			s.setUsername(request.getParameter("username"));
			s.setEmail(request.getParameter("email"));
			s.setUniv(request.getParameter("univ"));
			Date date = (Date)formatter.parse(request.getParameter("birth");
			s.setBirth(request.getParameter("birth"));
			 */
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		dao.insert(s); // 컨트롤러는 DAO한테 있는 메소드를 사용한다. DAO한테 데이터 베이스 관련 요청을 해야 한다. 
		return "studentInfo.jsp";
	} 

}
