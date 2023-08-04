package com.digit.CRSApp.databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.CRSApp.CourseChangeResponse;
import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.services.Services;

public class StudentDBOperations {

	public static void deleteStudentCourseChangeRequest(String userName, int studentID, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "DELETE FROM student_course_change_requests WHERE userName = ? AND studentID = ? AND newCourseID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setInt(2, studentID);
			ps.setInt(3, newCourseID);
			
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateStudentCourseInTable(Student curStudent, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "UPDATE Student SET courseEnrolled = ? WHERE studentID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newCourseID);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<CourseChangeResponse> getAllCourseChangeReqOfStudents() {
		ArrayList<CourseChangeResponse> allRequests = new ArrayList<CourseChangeResponse>();

		Connection conn = Services.getConnectionInstance();
		String query = "SELECT * FROM student_course_change_requests";

		int studentID, newCourseID;
		String userName;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				userName = rs.getString("userName");
				studentID = rs.getInt("studentID");
				newCourseID = rs.getInt("newCourseID");

				CourseChangeResponse ccr = new CourseChangeResponse();
				ccr.setRequestedCourseID(newCourseID);

				Student curStudent = StudentDBOperations.getStudentFromStudentTableUsingID(studentID);
				ccr.setCurObject(curStudent);

				allRequests.add(ccr);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return allRequests;
	}

	public static void deRegisterStudentGrades(Student curStudent) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT SET marksSecured = -1 WHERE studentID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, curStudent.getStudentID());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateStudentMarksOfStudent(Student curStudent, int marks) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT SET marksSecured = ? WHERE studentID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, marks);
			ps.setInt(2, curStudent.getStudentID());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateCourseIDForStudentInPending(Student curStudent, int newCourse) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT_NEW_REQ SET courseEnrolled = ? WHERE userName = ? AND studentID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newCourse);
			ps.setString(2, curStudent.getUserName());
			ps.setInt(3, curStudent.getStudentID());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void addCourseChangeRequestToCourseChangeRequestsTable(Student curStudent, int newCourseID) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO STUDENT_COURSE_CHANGE_REQUESTS VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curStudent.getUserName());
			ps.setInt(2, curStudent.getStudentID());
			ps.setInt(3, newCourseID);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeStudentNameInTable(Student curStudent, String newName) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT SET name = ? WHERE studentID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newName);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeStudentAgeInTable(Student curStudent, int newAge) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT SET age = ? WHERE studentID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newAge);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeStudentCityInTable(Student curStudent, String newCity) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE STUDENT SET city = ? WHERE studentID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newCity);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Student getStudentFromStudentTableUsingCredentials(String userName, String password) {
		Connection conn = Services.getConnectionInstance();
		Student curStudent = null;

		String query = "SELECT * FROM Student WHERE userName = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curStudent = new Student();
				curStudent.setStudentID(rs.getInt("studentID"));
				curStudent.setName(rs.getString("name"));
				curStudent.setAge(rs.getInt("age"));
				curStudent.setCity(rs.getString("city"));
				curStudent.setCourseEnrolled(rs.getInt("courseEnrolled"));
				curStudent.setMarksSecured(rs.getInt("marksSecured"));
				curStudent.setUserName(rs.getString("userName"));
				curStudent.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curStudent;
	}

	public static Student getStudentFromStudentRequestTableUsingCredentials(String userName, String password) {
		Connection conn = Services.getConnectionInstance();
		Student curStudent = null;

		String query = "SELECT * FROM STUDENT_NEW_REQ WHERE userName = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curStudent = new Student();
				curStudent.setStudentID(rs.getInt("studentID"));
				curStudent.setName(rs.getString("name"));
				curStudent.setAge(rs.getInt("age"));
				curStudent.setCity(rs.getString("city"));
				curStudent.setCourseEnrolled(rs.getInt("courseEnrolled"));
				curStudent.setMarksSecured(rs.getInt("marksSecured"));
				curStudent.setUserName(rs.getString("userName"));
				curStudent.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static ArrayList<Student> getAllStudentsList() {
		Connection conn = Services.getConnectionInstance();

		ArrayList<Student> allStudentsList = new ArrayList<Student>();
		String query = "SELECT * FROM Student";

		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Student curStudent = new Student();
				curStudent.setStudentID(rs.getInt("StudentID"));
				curStudent.setName(rs.getString("name"));
				curStudent.setAge(rs.getInt("age"));
				curStudent.setCity(rs.getString("city"));
				curStudent.setCourseEnrolled(rs.getInt("courseEnrolled"));
				curStudent.setMarksSecured(rs.getInt("marksSecured"));
				curStudent.setUserName(rs.getString("userName"));
				curStudent.setPassword(rs.getString("password"));

				allStudentsList.add(curStudent);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return allStudentsList;
	}

	public static void deleteStudentFromStudentTableUsingID(int studentID) {
		Connection conn = Services.getConnectionInstance();
		String query = "DELETE FROM Student WHERE studentID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, studentID);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Student getStudentFromStudentTableUsingID(int studentID) {
		Connection conn = Services.getConnectionInstance();
		Student curStudent = null;

		String query = "SELECT * FROM student WHERE studentID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, studentID);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				curStudent = new Student();
				curStudent.setStudentID(rs.getInt("StudentID"));
				curStudent.setName(rs.getString("name"));
				curStudent.setAge(rs.getInt("age"));
				curStudent.setCity(rs.getString("city"));
				curStudent.setCourseEnrolled(rs.getInt("courseEnrolled"));
				curStudent.setMarksSecured(rs.getInt("marksSecured"));
				curStudent.setUserName(rs.getString("userName"));
				curStudent.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static boolean isValidStudentID(int studentID) {
		Connection conn = Services.getConnectionInstance();

		String query = "SELECT * FROM Student WHERE studentID = ?";
		try {

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, studentID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void addStudentToStudentTable(Student curStudent) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO STUDENT(name, age, city, courseEnrolled, "
				+ "userName, password) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curStudent.getName());
			ps.setInt(2, curStudent.getAge());
			ps.setString(3, curStudent.getCity());
			ps.setInt(4, curStudent.getCourseEnrolled());
			ps.setString(5, curStudent.getUserName());
			ps.setString(6, curStudent.getPassword());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addStudentToStudentRequestTable(Student curStudent) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO STUDENT_NEW_REQ(name, age, city, courseEnrolled,"
				+ "userName, password) VALUES(?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curStudent.getName());
			ps.setInt(2, curStudent.getAge());
			ps.setString(3, curStudent.getCity());
			ps.setInt(4, curStudent.getCourseEnrolled());
			ps.setString(5, curStudent.getUserName());
			ps.setString(6, curStudent.getPassword());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteStudentFromStudentRequestsTable(Student curStudent) {
		Connection conn = Services.getConnectionInstance();
		String query = "DELETE FROM STUDENT_NEW_REQ WHERE userName = ? AND studentID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curStudent.getUserName());
			ps.setInt(2, curStudent.getStudentID());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isUserNameExists(String userName) {
		Connection conn = Services.getConnectionInstance();
		try {
			String query = "SELECT * FROM STUDENT WHERE userName = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			ps.clearBatch();

			query = "SELECT * FROM STUDENT_NEW_REQ WHERE userName = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void changeStudentUserName(Student curStudent, String newUserName, String password) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Student SET userName = ? WHERE studentID = ? AND userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newUserName);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());
			ps.setString(4, password);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeStudentPassword(Student curStudent, String newPassword, String password) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Student SET password = ? WHERE studentID = ? AND userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newPassword);
			ps.setInt(2, curStudent.getStudentID());
			ps.setString(3, curStudent.getUserName());
			ps.setString(4, password);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
