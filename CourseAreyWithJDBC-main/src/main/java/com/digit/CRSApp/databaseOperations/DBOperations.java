package com.digit.CRSApp.databaseOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.services.Services;

public class DBOperations {

	public static ArrayList<Professor> getAllProfessorApplicationsList() {
		Connection conn = Services.getConnectionInstance();

		ArrayList<Professor> allProfessorApplicationsList = new ArrayList<Professor>();
		String query = "SELECT * FROM PROFESSOR_NEW_REQ";

		try {
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);
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

				allProfessorApplicationsList.add(curProfessor);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return allProfessorApplicationsList;
	}

	public static ArrayList<Student> getAllStudentRequestsList() {
		Connection conn = Services.getConnectionInstance();

		ArrayList<Student> allStudentRequestsList = new ArrayList<Student>();
		String query = "SELECT * FROM STUDENT_NEW_REQ";

		try {
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);
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

				allStudentRequestsList.add(curStudent);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return allStudentRequestsList;
	}

}
