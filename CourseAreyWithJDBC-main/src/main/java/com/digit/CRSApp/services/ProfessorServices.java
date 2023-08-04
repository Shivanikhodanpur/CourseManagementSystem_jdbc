package com.digit.CRSApp.services;

import java.util.ArrayList;
import java.util.Scanner;

import com.CRSApp.LoginResponse;
import com.digit.CRSApp.beans.Course;
import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.databaseOperations.CourseDBOperations;
import com.digit.CRSApp.databaseOperations.ProfessorDBOperations;
import com.digit.CRSApp.databaseOperations.StudentDBOperations;

public class ProfessorServices {

	public static boolean withDrawApplicationRequestOfProfessor(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("You have Initiated Withdrawl Of Application Request!");
			System.out.println("\nDo You Wish to Withdraw Application: (Y/N): ");
			System.out.println("Enter Your Choice: ");
			String ch = sc.nextLine();

			if (ch.equalsIgnoreCase("Y")) {
				ProfessorDBOperations.deleteProfessorFromProfessorApplicationsTable(curProfessor);
				return true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static Professor changePersonalDetailsOfProfessorInRequest(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (!(curProfessor.getPassword().equals(password) && curProfessor.getUserName().equals(userName))) {
				System.out.println("Invalid Credentials!");
				return curProfessor;
			}

			DetailsChangeLoop: while (true) {
				System.out.println("- Change Details -");
				System.out.println("1. Name\n2. Age\n3. City");
				System.out.println("Enter your choice: ");

				int ch = Integer.parseInt(sc.nextLine());
				switch (ch) {
				case 1: {
					System.out.println("Enter New Name to Update: ");
					String newName = sc.nextLine();
					ProfessorDBOperations.changeProfessorInRequestNameInTable(curProfessor, newName);
					curProfessor.setName(newName);
					break DetailsChangeLoop;
				}
				case 2: {
					System.out.println("Enter New Age Value to Update: ");
					int newAge = Integer.parseInt(sc.nextLine());
					ProfessorDBOperations.changeProfessorInRequestAgeInTable(curProfessor, newAge);
					curProfessor.setAge(newAge);
					break DetailsChangeLoop;
				}
				case 3: {
					System.out.println("Enter New City Value to Update: ");
					String newCity = sc.nextLine();
					ProfessorDBOperations.changeProfessorInRequestCityInTable(curProfessor, newCity);
					curProfessor.setCity(newCity);
					break DetailsChangeLoop;
				}
				default:
					System.out.println("Unexpected value: " + ch);
					continue;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return curProfessor;
	}

	public static Professor changePersonalDetailsOfProfessor(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (!(curProfessor.getPassword().equals(password) && curProfessor.getUserName().equals(userName))) {
				System.out.println("Invalid Credentials!");
				return curProfessor;
			}

			DetailsChangeLoop: while (true) {
				System.out.println("- Change Details -");
				System.out.println("1. Name\n2. Age\n3. City");
				System.out.println("Enter your choice: ");

				int ch = Integer.parseInt(sc.nextLine());
				switch (ch) {
				case 1: {
					System.out.println("Enter New Name to Update: ");
					String newName = sc.nextLine();
					ProfessorDBOperations.changeProfessorNameInTable(curProfessor, newName);
					curProfessor.setName(newName);
					break DetailsChangeLoop;
				}
				case 2: {
					System.out.println("Enter New Age Value to Update: ");
					int newAge = Integer.parseInt(sc.nextLine());
					ProfessorDBOperations.changeProfessorAgeInTable(curProfessor, newAge);
					curProfessor.setAge(newAge);
					break DetailsChangeLoop;
				}
				case 3: {
					System.out.println("Enter New City Value to Update: ");
					String newCity = sc.nextLine();
					ProfessorDBOperations.changeProfessorCityInTable(curProfessor, newCity);
					curProfessor.setCity(newCity);
					break DetailsChangeLoop;
				}
				default:
					System.out.println("Unexpected value: " + ch);
					continue;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return curProfessor;
	}

	public static Professor changePasswordOfProfessor(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (curProfessor.getPassword().equals(password) && curProfessor.getUserName().equals(userName)) {
				System.out.println("Enter New Password: ");
				String newPassword = sc.nextLine();
				System.out.println("Re-Enter New Password: ");
				String reEnterPassword = sc.nextLine();
				if (newPassword.equals(reEnterPassword)) {
					System.out.println("Are you sure that you like to change your Password? (Enter Y/N): ");
					String ch = sc.nextLine();
					if (ch.equalsIgnoreCase("Y")) {
						ProfessorDBOperations.changeProfessorPassword(curProfessor, newPassword, password);
						curProfessor.setPassword(newPassword);
					}
				} else {
					System.out.println("Passwords doesn't match!");
				}
			} else {
				System.out.println("Invalid Credentials!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return curProfessor;
	}

	public static Professor changeUserNameOfProfessor(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (curProfessor.getPassword().equals(password) && curProfessor.getUserName().equals(userName)) {
				System.out.println("Enter New UserName: ");
				String newUserName = sc.nextLine();
				boolean isUserExists = ProfessorDBOperations.isUserNameExists(newUserName);
				if (isUserExists) {
					System.out.println("User with UserName \"" + newUserName + "\" already exists!");
				} else {
					System.out.println("Would you like to Change your UserName to " + newUserName + " ? (Enter Y/N): ");
					String ch = sc.nextLine();
					if (ch.equalsIgnoreCase("Y")) {
						ProfessorDBOperations.changeProfessorUserName(curProfessor, newUserName, password);
						curProfessor.setUserName(newUserName);
					}
				}
			} else {
				System.out.println("Invalid Credentials!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curProfessor;
	}

	public static void changeProfessorInRequestCourse(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Select From Courses");
			CourseServices.printAllCourseIDsAndNames();
			System.out.println("Enter New Course ID you wish to Change To: ");
			int courseID = Integer.parseInt(sc.nextLine());

			boolean isValidCourseID = CourseDBOperations.isValidCourseID(courseID);
			if (!isValidCourseID) {
				System.out.println("Invalid Course ID!");
				return;
			}

			ProfessorDBOperations.changeCourseForProfessorInRequest(curProfessor, courseID);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void requestChangeProfessorCourseTeach(Professor curProfessor) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Select From Courses");
			CourseServices.printAllCourseIDsAndNames();
			System.out.println("Enter New Course ID you wish to Change To: ");
			int courseID = Integer.parseInt(sc.nextLine());

			boolean isValidCourseID = CourseDBOperations.isValidCourseID(courseID);
			if (!isValidCourseID) {
				System.out.println("Invalid Course ID!");
				return;
			}

			ProfessorDBOperations.addCourseChangeRequestToProfCourseReqTable(curProfessor, courseID);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void printProfessorTeachingCourseDetails(Professor curProfessor) {
		Course curCourse = CourseDBOperations.getCourseFromCourseTableUsingID(curProfessor.getCourseID());
		if (curCourse == null) {
			System.out.println("Invalid Course or Course No Longer Exists!");
			System.out.println("Contact Admin!");
			return;
		}
		CourseServices.printCourseDetails(curCourse);
	}

	public static void viewAllStudentsUnderCurProfessor(Professor curProfessor) {
		ArrayList<Student> allStudentsOfProfessor = ProfessorDBOperations.getAllStudentsUnderProfessor(curProfessor);
		for (Student curStudent : allStudentsOfProfessor) {
			StudentServices.printStudentDetails(curStudent, "Student Details: ");
		}
	}

	public static void gradeStudentsUnderCurProfessor(Professor curProfessor) {
		try {
			ArrayList<Student> allStudentsOfProfessor = ProfessorDBOperations
					.getAllStudentsUnderProfessor(curProfessor);
			Scanner sc = new Scanner(System.in);
			for (Student curStudent : allStudentsOfProfessor) {
				if (curStudent.getMarksSecured() != -1) {
					continue;
				}
				StudentServices.printStudentDetails(curStudent, "Student Details: ");
				System.out.println("Enter marks to Grade: (Enter \"S\" to Skip): ");
				String inp = sc.nextLine();
				if (inp.equalsIgnoreCase("S")) {
					System.out.println("Current Student Skipped");
					continue;
				}
				int marks = Integer.parseInt(inp);
				StudentDBOperations.updateStudentMarksOfStudent(curStudent, marks);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void printProfessorDetails(Professor curProf, String msg) {
		System.out.println(msg);
		System.out.println("\tProfessor ID: " + curProf.getCourseID());
		System.out.println("\tProfessor Name: " + curProf.getName());
		System.out.println("\tProfessor Age: " + curProf.getAge());
		System.out.println("\tProfessor City: " + curProf.getCity());
		System.out.println("\tProfessor's Year's of Experience: " + curProf.getYearsOfExpreience());
		System.out.println("\tProfessor Teach: " + curProf.getCourseID());
		System.out.println("\tProfessor UserName: " + curProf.getUserName());
		System.out.println();
	}

	public static LoginResponse professorLogin() {
		Scanner sc = new Scanner(System.in);
		Professor curProessor = null;
		LoginResponse curLoginResponse = new LoginResponse();

		try {
			System.out.println("\n-- Login Page --\n");

			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			curProessor = ProfessorDBOperations.getProfessorFromProfessorTableUsingCredentials(userName, password);
			if (curProessor == null) {
				curProessor = ProfessorDBOperations.getProfessorFromProfessorRequestsTableUsingCredentials(userName,
						password);
				if (curProessor == null) {
					return null;
				}
				curLoginResponse.setCurUserObject(curProessor);
				curLoginResponse.setResponseCode(0);
				return curLoginResponse;
			}
			curLoginResponse.setCurUserObject(curProessor);
			curLoginResponse.setResponseCode(1);
			return curLoginResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curLoginResponse;
	}

	public static void professorSignUp() {
		try {
			Scanner sc = new Scanner(System.in);

			String userName, password;

			System.out.println("- Credentials SetUp -");
			while (true) {
				System.out.println("Enter UserName: ");
				userName = sc.nextLine();

				boolean isUserNameExists = ProfessorDBOperations.isUserNameExists(userName);
				if (isUserNameExists) {
					System.out.println("User with UserName \"" + userName + "\" already exists!");
					continue;
				}
				System.out.println("Enter Password: ");
				password = sc.nextLine();
				System.out.println("Re-Enter Password: ");
				String reEnteredPassword = sc.nextLine();

				if (!password.equals(reEnteredPassword)) {
					System.out.println("Passwords doesn't match!");
					continue;
				}
				break;
			}

			System.out.println("- Personal Details -");
			System.out.println("Enter your Name: ");
			String name = sc.nextLine();
			System.out.println("Enter you Age: ");
			int age = Integer.parseInt(sc.nextLine());
			System.out.println("Enter your City: ");
			String city = sc.nextLine();
			System.out.println("Enter Years of Experience: ");
			int yearsOfExperience = Integer.parseInt(sc.nextLine());
			int courseID = 0;
			while (true) {
				System.out.println("Choose your Choice of Course to Enroll: ");
				CourseServices.printAllCourseIDsAndNames();
				courseID = Integer.parseInt(sc.nextLine());
				if (!CourseDBOperations.isValidCourseID(courseID)) {
					System.out.println("Invalid Course ");
					continue;
				}
				break;
			}

			Professor curProfessor = new Professor(-1, name, age, city, yearsOfExperience, courseID, userName,
					password);
			ProfessorDBOperations.addProfessorToProfessorRequestTable(curProfessor);

			System.out.println("SignUp Successful!");
			System.out.println("Wait for Admin to Approve your Request!\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
