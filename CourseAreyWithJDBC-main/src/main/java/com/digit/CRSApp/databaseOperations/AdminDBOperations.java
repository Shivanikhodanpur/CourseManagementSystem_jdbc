package com.digit.CRSApp.databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.services.ProfessorServices;
import com.digit.CRSApp.services.Services;
import com.digit.CRSApp.services.StudentServices;

public class AdminDBOperations {

	public static boolean authAdminFromTable(String userName, String password) {
		Connection conn = Services.getConnectionInstance();

		String query = "SELECT * FROM Admin WHERE userName = ? AND password = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
