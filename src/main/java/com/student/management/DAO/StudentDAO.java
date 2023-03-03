package com.student.management.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.management.model.Student;

/*This class contains all the methods 
 * required to perform the CRUD operations
 * with the help of Database connection using 
 * the model object*/

public class StudentDAO {

	private  String URL = "jdbc:mysql://localhost:3306/student_DB";
	private  String USERNAME = "root";
	private  String PASSWORD = "Nikhil_2127";
	private  String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static final String insertQuery = "INSERT INTO students(name, email, country) VALUES(?, ?, ?);";
	private static final String selectAll = "SELECT * FROM students;";
	private static final String selectStudent = "SELECT * FROM students WHERE Id = ?;";
	private static final String deleteQuery = "DELETE FROM students WHERE Id = ?";
	private static final String updateQuery = "UPDATE students SET name = ?, email = ?, country = ? where Id = ?;";
	
	public StudentDAO() {
		
	}
	
	protected Connection initialize() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return connection;
	}
	
	// Insert
	public void insert(Student student) {
		Connection connection = initialize();
		try {
			PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setString(1, student.getName());
			statement.setString(2, student.getEmail());
			statement.setString(3, student.getCountry());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Display a student by Id
	public Student displayStudent(int id) {
		Student student = null;
		Connection connection = initialize();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(selectStudent);
			statement.setInt(1, student.getId());
			ResultSet set = statement.executeQuery();
			
			while (set.next()) {
				String name = set.getString("name");
				String email = set.getString("email");
				String country = set.getString("country");
				
				student = new Student(name, email, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	// Select all students
	public List<Student> displayAll() {
		
		List<Student> students = new ArrayList<>();
		try {
			Connection connection = initialize();
			PreparedStatement statement = connection.prepareStatement(selectAll);
			ResultSet set = statement.executeQuery();
			
			while (set.next()) {
				int id = set.getInt("id");
				String name = set.getString("name");
				String email = set.getString("email");
				String country = set.getString("country");
				
				students.add(new Student(id, name, email, country));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
	
	// Update
	public boolean update(Student student) {
		boolean updated = false;
		Connection connection = initialize();
		try {
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getEmail());
			statement.setString(4, student.getCountry());
			
			statement.executeUpdate();
			updated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updated;
	}
	
	// Delete by Id
	public boolean delete(int Id) {
		boolean deleted = false;
		Connection connection = initialize();
		try {
			PreparedStatement statement = connection.prepareStatement(deleteQuery);
			statement.setInt(1, Id);
			statement.executeUpdate();
			deleted = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleted;
	}
}
