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

public class ProfessorDBOperations {

	public static void updateProfessorCourseInTable(Professor curProfessor, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "UPDATE Professor SET courseID = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newCourseID);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteProfessorCourseChangeRequest(int professorID, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "DELETE FROM professor_course_change_request WHERE professorID = ? AND newCourseID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, professorID);
			ps.setInt(2, newCourseID);

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<CourseChangeResponse> getAllCourseChangeReqOfProfessors() {
		ArrayList<CourseChangeResponse> allRequests = new ArrayList<CourseChangeResponse>();

		Connection conn = Services.getConnectionInstance();
		String query = "SELECT * FROM professor_course_change_request";

		int professorID, courseID;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				professorID = rs.getInt("professorID");
				courseID = rs.getInt("newCourseID");

				CourseChangeResponse pccr = new CourseChangeResponse();
				pccr.setRequestedCourseID(courseID);

				Professor curProfessor = ProfessorDBOperations.getProfessorFromProfessorTableUsingID(professorID);
				pccr.setCurObject(curProfessor);
				
				allRequests.add(pccr);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allRequests;
	}

	public static void changeProfessorAgeInTable(Professor curProfessor, int newAge) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Professor SET age = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newAge);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorInRequestAgeInTable(Professor curProfessor, int newAge) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE professor_new_req SET age = ? WHERE studentID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newAge);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorCityInTable(Professor curProfessor, String newCity) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Professor SET city = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newCity);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorInRequestCityInTable(Professor curProfessor, String newCity) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE professor_new_req SET city = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newCity);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorInRequestNameInTable(Professor curProfessor, String newName) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE professor_new_req SET name = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newName);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorNameInTable(Professor curProfessor, String newName) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Professor SET name = ? WHERE professorID = ? AND userName = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newName);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isUserNameExists(String userName) {
		Connection conn = Services.getConnectionInstance();
		try {
			String query = "SELECT * FROM Professor WHERE userName = ?";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			ps.clearBatch();

			query = "SELECT * FROM professor_new_req WHERE userName = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void changeProfessorPassword(Professor curProfessor, String newPassword, String password) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Professor SET password = ? WHERE professorID = ? AND userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newPassword);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());
			ps.setString(4, password);

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeProfessorUserName(Professor curProfessor, String newUserName, String password) {
		Connection conn = Services.getConnectionInstance();
		String query = "UPDATE Professor SET userName = ? WHERE professorID = ? AND userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newUserName);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());
			ps.setString(4, password);

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeCourseForProfessorInRequest(Professor curProfessor, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "UPDATE  professor_new_req SET courseID = ? WHERE professorID = ? AND userName = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newCourseID);
			ps.setInt(2, curProfessor.getProfessorID());
			ps.setString(3, curProfessor.getUserName());

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addCourseChangeRequestToProfCourseReqTable(Professor curProfessor, int newCourseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "INSERT INTO professor_course_change_request VALUES (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, curProfessor.getCourseID());
			ps.setInt(2, newCourseID);

			ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<Student> getAllStudentsUnderProfessor(Professor curProfessor) {
		Connection conn = Services.getConnectionInstance();
		ArrayList<Student> allStudentsUnderProfessor = new ArrayList<Student>();

		String query = "SELECT * FROM Student WHERE courseEnrolled = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, curProfessor.getCourseID());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Student curStudent = new Student();
				curStudent.setStudentID(rs.getInt("studentID"));
				curStudent.setName(rs.getString("name"));
				curStudent.setAge(rs.getInt("age"));
				curStudent.setCity(rs.getString("city"));
				curStudent.setCourseEnrolled(rs.getInt("courseEnrolled"));
				curStudent.setMarksSecured(rs.getInt("marksSecured"));
				curStudent.setUserName(rs.getString("userName"));
				curStudent.setPassword(rs.getString("password"));
				allStudentsUnderProfessor.add(curStudent);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allStudentsUnderProfessor;
	}

	public static Professor getProfessorFromProfessorTableUsingCredentials(String userName, String password) {
		Connection conn = Services.getConnectionInstance();
		Professor curProfessor = null;

		String query = "SELECT * FROM Professor WHERE userName = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curProfessor = new Professor();
				curProfessor.setProfessorID(rs.getInt("professorID"));
				curProfessor.setName(rs.getString("name"));
				curProfessor.setAge(rs.getInt("age"));
				curProfessor.setCity(rs.getString("city"));
				curProfessor.setYearsOfExpreience(rs.getInt("yearsOfExperience"));
				curProfessor.setCourseID(rs.getInt("courseID"));
				curProfessor.setUserName(rs.getString("userName"));
				curProfessor.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curProfessor;
	}

	public static Professor getProfessorFromProfessorRequestsTableUsingCredentials(String userName, String password) {
		Connection conn = Services.getConnectionInstance();
		Professor curProfessor = null;

		String query = "SELECT * FROM PROFESSOR_NEW_REQ WHERE userName = ? AND password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curProfessor = new Professor();
				curProfessor.setProfessorID(rs.getInt("professorID"));
				curProfessor.setName(rs.getString("name"));
				curProfessor.setAge(rs.getInt("age"));
				curProfessor.setCity(rs.getString("city"));
				curProfessor.setYearsOfExpreience(rs.getInt("yearsOfExperience"));
				curProfessor.setCourseID(rs.getInt("courseID"));
				curProfessor.setUserName(rs.getString("userName"));
				curProfessor.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curProfessor;
	}

	public static ArrayList<Professor> getAllProfessorsList() {
		Connection conn = Services.getConnectionInstance();

		ArrayList<Professor> allProfessorsList = new ArrayList<Professor>();
		String query = "SELECT * FROM Professor";

		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Professor curProfessor = new Professor();
				curProfessor.setProfessorID(rs.getInt("professorID"));
				curProfessor.setName(rs.getString("name"));
				curProfessor.setAge(rs.getInt("age"));
				curProfessor.setCity(rs.getString("city"));
				curProfessor.setYearsOfExpreience(rs.getInt("yearsOfExperience"));
				curProfessor.setCourseID(rs.getInt("courseID"));
				curProfessor.setUserName(rs.getString("userName"));
				curProfessor.setPassword(rs.getString("password"));

				allProfessorsList.add(curProfessor);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return allProfessorsList;
	}

	public static void deleteProfessorFromProfessorTableUsingID(int professorID) {
		Connection conn = Services.getConnectionInstance();
		String query = "DELETE FROM Professor WHERE professorID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, professorID);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addProfessorToProfessorTable(Professor curProfessor) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO Professor(name, age, city, yearsOfExperience, courseID, "
				+ "userName, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curProfessor.getName());
			ps.setInt(2, curProfessor.getAge());
			ps.setString(3, curProfessor.getCity());
			ps.setInt(4, curProfessor.getYearsOfExpreience());
			ps.setInt(5, curProfessor.getCourseID());
			ps.setString(6, curProfessor.getUserName());
			ps.setString(7, curProfessor.getPassword());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addProfessorToProfessorRequestTable(Professor curProfessor) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO professor_new_req(name, age, city, yearsOfExperience, courseID, "
				+ "userName, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curProfessor.getName());
			ps.setInt(2, curProfessor.getAge());
			ps.setString(3, curProfessor.getCity());
			ps.setInt(4, curProfessor.getYearsOfExpreience());
			ps.setInt(5, curProfessor.getCourseID());
			ps.setString(6, curProfessor.getUserName());
			ps.setString(7, curProfessor.getPassword());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isValidProfessorID(int professorID) {
		Connection conn = Services.getConnectionInstance();

		String query = "SELECT * FROM Professor WHERE professorID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, professorID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static Professor getProfessorFromProfessorTableUsingID(int professorID) {
		Connection conn = Services.getConnectionInstance();
		Professor curProf = null;
		
		String query = "SELECT * FROM Professor WHERE professorID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, professorID);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curProf =  new Professor();
				curProf.setProfessorID(rs.getInt("professorID"));
				curProf.setName(rs.getString("name"));
				curProf.setAge(rs.getInt("age"));
				curProf.setCity(rs.getString("city"));
				curProf.setYearsOfExpreience(rs.getInt("yearsOfExperience"));
				curProf.setCourseID(rs.getInt("courseID"));
				curProf.setUserName(rs.getString("userName"));
				curProf.setPassword(rs.getString("password"));
				return curProf;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return curProf;
	}

	public static void deleteProfessorFromProfessorApplicationsTable(Professor curProfessor) {
		Connection conn = Services.getConnectionInstance();
		String query = "DELETE FROM PROFESSOR_NEW_REQ WHERE userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curProfessor.getUserName());
			ps.setString(2, curProfessor.getPassword());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
