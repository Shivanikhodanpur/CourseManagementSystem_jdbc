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

public class StudentServices {

	public static boolean withDrawApplicationRequestOfStudent(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("You have Initiated Withdrawl Of Application Request!");
			System.out.println("\nDo You Wish to Withdraw Application: (Y/N): ");
			System.out.println("Enter Your Choice: ");
			String ch = sc.nextLine();

			if (ch.equalsIgnoreCase("Y")) {
				StudentDBOperations.deleteStudentFromStudentRequestsTable(curStudent);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static void viewAppliedCourseDetailsOfStudent(Student curStudent) {
		try {
			Course curCourse = CourseDBOperations.getCourseFromCourseTableUsingID(curStudent.getCourseEnrolled());
			if (curCourse == null) {
				System.out.println("Invalid Course or Course No Longer Exists!");
				System.out.println("Contact Admin to Re-Enroll or Enroll to other Course!");
				return;
			}
			CourseServices.printCourseDetails(curCourse);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void viewCurrentStudentProfessorDetails(Student curStudent) {
		try {
			ArrayList<Professor> allProfessorsList = ProfessorDBOperations.getAllProfessorsList();

			for (Professor curProfessor : allProfessorsList) {
				if (curProfessor.getCourseID() == curStudent.getCourseEnrolled()) {
					ProfessorServices.printProfessorDetails(curProfessor, "Professor Details: ");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void changeCourseEnrolled(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);
			ArrayList<Course> allCoursesList = CourseDBOperations.getAllCoursesList();

			System.out.println("- List of All Courses -");
			for (Course curCourse : allCoursesList) {
				System.out.println("Course ID: " + curCourse.getCourseID() + "\tCourse Name: " + curCourse.getName());
			}
			System.out.println("Choose Course you wish to Enroll: (Enter Course ID): ");
			int courseID = Integer.parseInt(sc.nextLine());

			boolean isValid = CourseDBOperations.isValidCourseID(courseID);
			if (!isValid) {
				System.out.println("Invalid CourseID. Select from Existing Courses!");
				return;
			}

			StudentDBOperations.addCourseChangeRequestToCourseChangeRequestsTable(curStudent, courseID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Student changeCourseEnrolledForStudentInPending(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);
			ArrayList<Course> allCoursesList = CourseDBOperations.getAllCoursesList();

			System.out.println("- List of All Courses -");
			for (Course curCourse : allCoursesList) {
				System.out.println("Course ID: " + curCourse.getCourseID() + "\tCourse Name: " + curCourse.getName());
			}
			System.out.println("Choose Course you wish to Enroll: (Enter Course ID): ");
			int courseID = Integer.parseInt(sc.nextLine());

			boolean isValid = CourseDBOperations.isValidCourseID(courseID);
			if (!isValid) {
				System.out.println("Invalid CourseID. Select from Existing Courses!");
				return curStudent;
			}

			StudentDBOperations.updateCourseIDForStudentInPending(curStudent, courseID);
			curStudent.setCourseEnrolled(courseID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static Student changePersonalDetailsOfStudent(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (!(curStudent.getPassword().equals(password) && curStudent.getUserName().equals(userName))) {
				System.out.println("Invalid Credentials!");
				return curStudent;
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
					StudentDBOperations.changeStudentNameInTable(curStudent, newName);
					curStudent.setName(newName);
					break DetailsChangeLoop;
				}
				case 2: {
					System.out.println("Enter New Age Value to Update: ");
					int newAge = Integer.parseInt(sc.nextLine());
					StudentDBOperations.changeStudentAgeInTable(curStudent, newAge);
					curStudent.setAge(newAge);
					break DetailsChangeLoop;
				}
				case 3: {
					System.out.println("Enter New City Value to Update: ");
					String newCity = sc.nextLine();
					StudentDBOperations.changeStudentCityInTable(curStudent, newCity);
					curStudent.setCity(newCity);
					break DetailsChangeLoop;
				}
				default:
					System.out.println("Unexpected value: " + ch);
					continue;
				}
			} 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static LoginResponse studentLogin() {
		Scanner sc = new Scanner(System.in);
		Student curStudent;
		LoginResponse curLoginResponse = new LoginResponse();

		try {
			System.out.println("\n-- Login Page --\n");

			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			curStudent = StudentDBOperations.getStudentFromStudentTableUsingCredentials(userName, password);
			if (curStudent == null) {
				curStudent = StudentDBOperations.getStudentFromStudentRequestTableUsingCredentials(userName, password);
				if (curStudent == null) {
					return null;
				}
				curLoginResponse.setCurUserObject(curStudent);
				curLoginResponse.setResponseCode(0);
				return curLoginResponse;
			}
			curLoginResponse.setCurUserObject(curStudent);
			curLoginResponse.setResponseCode(1);
			return curLoginResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curLoginResponse;
	}

	public static void printStudentDetails(Student curStudent, String msg) {
		System.out.println(msg);
		System.out.println("\tStudent Name: " + curStudent.getName());
		System.out.println("\tStudent Age: " + curStudent.getAge());
		System.out.println("\tStudent City: " + curStudent.getCity());
		System.out.println("\tCourse Enrolled: " + curStudent.getCourseEnrolled());
		System.out.println("\tStudent UserName: " + curStudent.getUserName());

		System.out.println();
	}

	public static void printStudentMarks(Student curStudent) {
		try {
			if (curStudent.getMarksSecured() != -1) {
				System.out.println("- Marks Details -");
				System.out.println("You Got: " + curStudent.getMarksSecured() + " marks!");
			} else {
				System.out.println("It is yet to be graded. Please check later!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void printStudentMarksCard(Student curStudent) {
		if (curStudent.getMarksSecured() == -1) {
			System.out.println("It is yet to be graded. Please check later!");
			return;
		}
		System.out.println("\n------------ Marks Card ------------ \n");
		System.out.println("Student ID       : " + curStudent.getStudentID());
		System.out.println("Student Name     : " + curStudent.getName());
		System.out.println("Student Age      : " + curStudent.getAge());
		System.out.println("Student City     : " + curStudent.getCity());
		System.out.println("Course Enrolled  : " + curStudent.getCourseEnrolled());
		System.out.println("Course Name      : " + curStudent.getCourseEnrolled());
		System.out.println("Score out of 100 : " + curStudent.getMarksSecured());
		System.out.println("----------------------------------");
	}

	public static void printStudentEnrolledCourseDetails(Student curStudent) {
		try {
			Course curCourse = CourseDBOperations.getCourseFromCourseTableUsingID(curStudent.getCourseEnrolled());
			if (curCourse == null) {
				System.out.println("Invalid Course or Course No Longer Exists!");
				System.out.println("Contact Admin to Re-Enroll or Enroll to other Course!");
				return;
			}
			CourseServices.printCourseDetails(curCourse);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Student changeUserNameOfStudent(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (curStudent.getPassword().equals(password) && curStudent.getUserName().equals(userName)) {
				System.out.println("Enter New UserName: ");
				String newUserName = sc.nextLine();
				boolean isUserNameExists = StudentDBOperations.isUserNameExists(newUserName);
				if (isUserNameExists) {
					System.out.println("User with Username \"" + newUserName + "\" already exists!");
				} else {
					System.out.println("Would you like to Change your UserName to " + newUserName + "? (Enter Y/N): ");
					String ch = sc.nextLine();
					if (ch.equalsIgnoreCase("Y")) {
						StudentDBOperations.changeStudentUserName(curStudent, newUserName, password);
						curStudent.setUserName(newUserName);
					}
				}
			} else {
				System.out.println("Invalid Credentials!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static Student changePasswordOfStudent(Student curStudent) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("- Verify User -");
			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (curStudent.getPassword().equals(password) && curStudent.getUserName().equals(userName)) {
				System.out.println("Enter New Password: ");
				String newPassword = sc.nextLine();
				System.out.println("Re-Enter New Password: ");
				String reEnterPassword = sc.nextLine();
				if (newPassword.equals(reEnterPassword)) {
					System.out.println("Are you sure that you like to change your Password? (Enter Y/N): ");
					String ch = sc.nextLine();
					if (ch.equalsIgnoreCase("Y")) {
						StudentDBOperations.changeStudentPassword(curStudent, newPassword, password);
						curStudent.setPassword(newPassword);
					}
				} else {
					System.out.println("Passwords doesn't match!");
				}
			} else {
				System.out.println("Invalid Credentials!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return curStudent;
	}

	public static void studentSignUp() {
		try {
			Scanner sc = new Scanner(System.in);

			String userName, password;

			System.out.println("- Credentials SetUp -");
			while (true) {
				System.out.println("Enter UserName: ");
				userName = sc.nextLine();

				boolean isUserNameExists = StudentDBOperations.isUserNameExists(userName);
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

			Student curstudent = new Student(-1, name, age, city, courseID, -1, userName, password);
			StudentDBOperations.addStudentToStudentRequestTable(curstudent);

			System.out.println("SignUp Successful!");
			System.out.println("Wait for Admin to Approve your Request!\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
