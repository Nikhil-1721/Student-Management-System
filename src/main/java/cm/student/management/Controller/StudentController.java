package cm.student.management.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.student.management.DAO.StudentDAO;
import com.student.management.model.Student;

@WebServlet("/")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO objDao;

	public StudentController() {
		super();

	}

	public void init() throws ServletException {
		objDao = new StudentDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;

		case "/insert":
			insertStudent(request, response);
			break;

		case "/delete":
			deleteStudent(request, response);
			break;

		case "/edit":
			showEditForm(request, response);
			break;
			
		case "/update":
			updateStudent(request, response);
			break;
		default:
			listStudents(request, response);
			break;
		}

	}

	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
		dispatcher.forward(request, response);
		
	}
	private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		int id = Integer.parseInt(request.getParameter("Id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		Student student = new Student(name, email, country);
		
		
		objDao.insert(student);
		response.sendRedirect("student-list");
		
	}
	

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
	
		objDao.delete(id);
		response.sendRedirect("sudent-list.jsp");
	}
	
	

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
		Student existingStudent;
		int id = Integer.parseInt(request.getParameter("Id"));
		
		try {
			existingStudent = objDao.displayStudent(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
			request.setAttribute("student", existingStudent);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			
		}
		
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("Id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		Student student = new Student(id, name, email, country);
		objDao.update(student);
		response.sendRedirect("student-list.jsp");
	}
	
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Student> students = objDao.displayAll();
		request.setAttribute("students", students);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-list.jsp");
		dispatcher.forward(request, response);
	}
}
