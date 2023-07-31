package com.employee.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.employee.dao.EmployeeDao;
import com.employee.model.Employee;


@WebServlet("/")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDao empdao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeController() {
		this.empdao = new EmployeeDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath(); 
		
		switch (action) {
		case "/new":
			try {
				showNewForm(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "/insert":
			try {
				insertEmployee(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteEmployee(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateEmployee(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			try {
				listemp(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	private void listemp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Employee> listemp = empdao.ListAllEmployee();
		request.setAttribute("listemp", listemp);
		System.out.println("hii " +listemp);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/emp-list.jsp");
		rd.forward(request, response);
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ParseException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		int salary = Integer.parseInt(request.getParameter("salary"));
		String joiningdate = request.getParameter("joiningdate");
		String skills = request.getParameter("skills");
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(joiningdate);  
		Employee emp = new Employee(id, name, age, salary, date1,skills);
		empdao.updateEmployee(emp);

		response.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee existingemp = empdao.selectEmployee(id);
		System.out.println("exist " + existingemp);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/employee-update.jsp");
		request.setAttribute("emp", existingemp);
		rd.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/employee-form.jsp");
		rd.forward(request, response);
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		empdao.deleteEmployee(id);
		response.sendRedirect("list");
	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ParseException {

		String name = request.getParameter("name");
		String skills = request.getParameter("skills");
		int age = Integer.parseInt(request.getParameter("age"));
		int salary = Integer.parseInt(request.getParameter("salary"));
		String joiningdate = request.getParameter("joiningdate");
		System.out.println(joiningdate + request.getParameter("joiningdate"));
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(joiningdate);  
		Employee emp = new Employee(name, age, salary, date1,skills);

		System.out.println("details of employee while insert " + emp);
		empdao.insertEmp(emp);

		response.sendRedirect("list");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
