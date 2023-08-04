package com.digit.CRSApp.databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.digit.CRSApp.beans.Course;
import com.digit.CRSApp.services.Services;

public class CourseDBOperations {

	public static ArrayList<Course> getAllCoursesList() {
		Connection conn = Services.getConnectionInstance();

		ArrayList<Course> allCoursesList = new ArrayList<Course>();
		String query = "SELECT * FROM Course";

		try {
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Course curCourse = new Course();
				curCourse.setCourseID(rs.getInt("courseID"));
				curCourse.setName(rs.getString("name"));
				curCourse.setFee(rs.getInt("fee"));
				curCourse.setDurationInHrs(rs.getInt("durationInHrs"));

				allCoursesList.add(curCourse);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return allCoursesList;
	}

	public static boolean isValidCourseID(int courseID) {
		Connection conn = Services.getConnectionInstance();

		String query = "SELECT * FROM Course WHERE courseID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, courseID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void deleteCourseFromCourseTableUsingID(int courseID) {
		Connection conn = Services.getConnectionInstance();
		String query = "DELETE FROM COURSE WHERE courseID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, courseID);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Course getCourseFromCourseTableUsingID(int courseID) {
		Connection conn = Services.getConnectionInstance();
		Course curCourse = null;

		String query = "SELECT * FROM Course WHERE courseID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, courseID);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curCourse = new Course();
				curCourse.setCourseID(rs.getInt("courseID"));
				curCourse.setName(rs.getString("name"));
				curCourse.setFee(rs.getInt("fee"));
				curCourse.setDurationInHrs(rs.getInt("durationInHrs"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return curCourse;
	}

	public static void addCourseToCourseTable(Course curCourse) {
		Connection conn = Services.getConnectionInstance();
		String query = "INSERT INTO COURSE(name, fee, durationInHrs) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curCourse.getName());
			ps.setInt(2, curCourse.getFee());
			ps.setInt(3, curCourse.getDurationInHrs());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Course getCourseUsingNameFeeAndDuration(String name, int fee, int durationInHrs) {
		Connection conn = Services.getConnectionInstance();	
		Course curCourse = null;
		
		String query = "SELECT * FROM COURSE WHERE name = ? AND fee = ? AND durationInHrs = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, fee);
			ps.setInt(3, durationInHrs);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				curCourse = new Course();
				curCourse.setCourseID(rs.getInt("courseID"));
				curCourse.setName(rs.getString("name"));
				curCourse.setFee(rs.getInt("fee"));
				curCourse.setDurationInHrs(rs.getInt("durationInHrs"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return curCourse;
	}

}
