package com.digit.CRSApp.services;

import java.util.ArrayList;
import java.util.Scanner;

import com.CRSApp.CourseChangeResponse;
import com.digit.CRSApp.beans.Course;
import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.databaseOperations.AdminDBOperations;
import com.digit.CRSApp.databaseOperations.CourseDBOperations;
import com.digit.CRSApp.databaseOperations.DBOperations;
import com.digit.CRSApp.databaseOperations.ProfessorDBOperations;
import com.digit.CRSApp.databaseOperations.StudentDBOperations;

public class AdminServices {

	public static void deRegisterStudentGrades() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Student Grade De-Register --");

			System.out.println("Enter Student ID: ");
			int studentID = Integer.parseInt(sc.nextLine());

			boolean isValidStudent = StudentDBOperations.isValidStudentID(studentID);
			if (!isValidStudent) {
				System.out.println("Invalid Student ID! Student with ID doesn't exists.");
				return;
			}

			Student curStudent = StudentDBOperations.getStudentFromStudentTableUsingID(studentID);
			if (curStudent.getMarksSecured() == -1) {
				System.out.println("It has not been graded yet.");
				return;
			}

			StudentDBOperations.deRegisterStudentGrades(curStudent);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void approveProfessorCourseChangeRequest() {
		try {
			Scanner sc = new Scanner(System.in);

			ArrayList<CourseChangeResponse> allRequests = ProfessorDBOperations.getAllCourseChangeReqOfProfessors();
			Professor curProfessor;
			Course newCourse;
			int newCourseID;

			for (CourseChangeResponse curReq : allRequests) {
				curProfessor = (Professor) curReq.getCurObject();
				newCourseID = curReq.getRequestedCourseID();

				ProfessorServices.printProfessorDetails(curProfessor, "Current Professor Details");
				newCourse = CourseDBOperations.getCourseFromCourseTableUsingID(newCourseID);

				System.out.println("\nRequesting to Change To: ");
				CourseServices.printCourseDetails(newCourse);

				System.out.println("- Manage Professor Application -");
				System.out.println("1. Approve\n2. Reject\n3. Skip");
				System.out.println("Choose an Operation: ");
				int ch = Integer.parseInt(sc.nextLine());

				ManageProfessorLoop: while (true) {
					if (ch == 1) {
						ProfessorDBOperations.updateProfessorCourseInTable(curProfessor, newCourseID);
						ProfessorDBOperations.deleteProfessorCourseChangeRequest(curProfessor.getProfessorID(),
								newCourseID);
						break;
					} else if (ch == 2) {
						ProfessorDBOperations.deleteProfessorCourseChangeRequest(curProfessor.getProfessorID(),
								newCourseID);
						break;
					} else if (ch == 3) {
						System.out.println("Request skipped!");
						break ManageProfessorLoop;
					} else {
						System.out.println("Choose from Options!");
						continue ManageProfessorLoop;
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void approveStudentCourseChangeRequest() {
		try {
			Scanner sc = new Scanner(System.in);

			ArrayList<CourseChangeResponse> allRequests = StudentDBOperations.getAllCourseChangeReqOfStudents();

			Student curStudent;
			Course newCourse;
			int newCourseID;

			for (CourseChangeResponse curResp : allRequests) {
				curStudent = (Student) curResp.getCurObject();
				newCourseID = curResp.getRequestedCourseID();

				if (curStudent == null) {
					System.out.println("Null");
					continue;
				}

				StudentServices.printStudentDetails(curStudent, "Current Student Details");
				newCourse = CourseDBOperations.getCourseFromCourseTableUsingID(newCourseID);

				System.out.println("\nRequesting to Change To: ");
				CourseServices.printCourseDetails(newCourse);

				System.out.println("- Manage Professor Application -");
				System.out.println("1. Approve\n2. Reject\n3. Skip");
				System.out.println("Choose an Operation: ");
				int ch = Integer.parseInt(sc.nextLine());

				ManageStudentLoop: while (true) {
					if (ch == 1) {
						StudentDBOperations.updateStudentCourseInTable(curStudent, newCourseID);
						StudentDBOperations.deleteStudentCourseChangeRequest(curStudent.getUserName(),
								curStudent.getStudentID(), newCourseID);
						break;
					} else if (ch == 2) {
						StudentDBOperations.deleteStudentCourseChangeRequest(curStudent.getUserName(),
								curStudent.getStudentID(), newCourseID);
						break;
					} else if (ch == 3) {
						System.out.println("Request skipped!");
						break ManageStudentLoop;
					} else {
						System.out.println("Choose from Options!");
						continue ManageStudentLoop;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void printAllCoursesDetails() {
		ArrayList<Course> allCoursesList = CourseDBOperations.getAllCoursesList();

		for (Course curCourse : allCoursesList) {
			CourseServices.printCourseDetails(curCourse);
		}
	}

	public static void printAllStudentDetails() {
		ArrayList<Student> allStudentsList = StudentDBOperations.getAllStudentsList();

		for (Student curStudent : allStudentsList) {
			StudentServices.printStudentDetails(curStudent, "Student Details:");
		}
	}

	public static void printAllProfessorsDetails() {
		ArrayList<Professor> allProfessorsList = ProfessorDBOperations.getAllProfessorsList();

		for (Professor curProfessor : allProfessorsList) {
			ProfessorServices.printProfessorDetails(curProfessor, "Professor Details: ");
		}
	}

	public static void removeProfessorFromStudentTable() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Professor Removal Page --");

			System.out.println("Enter Professor ID to Remove: ");
			int professorID = Integer.parseInt(sc.nextLine());

			boolean isValidProfessor = ProfessorDBOperations.isValidProfessorID(professorID);
			if (!isValidProfessor) {
				System.out.println("Invalid Professor ID! Professor with ID doesn't exists.");
				return;
			}

			Professor curProfessor = ProfessorDBOperations.getProfessorFromProfessorTableUsingID(professorID);
			ProfessorServices.printProfessorDetails(curProfessor, "Professor Details: ");

			System.out.println("Do you really want to Remove Professor - " + curProfessor.getName() + " ? (Y/N): ");
			String ch = sc.nextLine();
			if (ch.equalsIgnoreCase("Y")) {
				ProfessorDBOperations.deleteProfessorFromProfessorTableUsingID(professorID);
				System.out.println("\nProfessor Removed Successfully!\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void removeStudentFromStudentTable() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Student Removal Page --");

			System.out.println("Enter Student ID to Remove: ");
			int studentID = Integer.parseInt(sc.nextLine());

			boolean isValidStudent = StudentDBOperations.isValidStudentID(studentID);
			if (!isValidStudent) {
				System.out.println("Invalid Student ID! Student with ID doesn't exists.");
				return;
			}

			Student curStudent = StudentDBOperations.getStudentFromStudentTableUsingID(studentID);
			StudentServices.printStudentDetails(curStudent, "Student Details: ");

			System.out.println("Do you really want to Remove Student - " + curStudent.getName() + " ? (Y/N): ");
			String ch = sc.nextLine();
			if (ch.equalsIgnoreCase("Y")) {
				StudentDBOperations.deleteStudentFromStudentTableUsingID(studentID);
				System.out.println("\nStudent Removed Successfully!\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static boolean authenticateAdmin() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Admin Authentication --");

			System.out.println("Enter UserName: ");
			String userName = sc.nextLine();
			System.out.println("Enter Password: ");
			String password = sc.nextLine();

			boolean isAuthenticated = AdminDBOperations.authAdminFromTable(userName, password);
			if (isAuthenticated) {
				System.out.println("\nWelcome Admin!\n");
				return true;
			} else {
				System.err.println("Invalid Credentials. You are not authenticated!\n");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static void addCourseIntoCourseTable() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Course Creation Page --");

			System.out.println("Enter Course Name: ");
			String curCourseName = sc.nextLine();
			System.out.println("Enter Course Fee: ");
			int curCourseFee = Integer.parseInt(sc.nextLine());
			System.out.println("Enter Course Duration In Hrs: ");
			int curCourseDuration = Integer.parseInt(sc.nextLine());

			Course curCourse = new Course(-1, curCourseName, curCourseFee, curCourseDuration);
			CourseDBOperations.addCourseToCourseTable(curCourse);

			System.out.println("\nCourse Created Successfully!\n");
			curCourse = CourseDBOperations.getCourseUsingNameFeeAndDuration(curCourseName, curCourseFee,
					curCourseDuration);
			CourseServices.printCourseDetails(curCourse);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void removeCourseFromCourseTable() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("-- Course Deletion Page --");

			CourseServices.printAllCoursesDetails();

			System.out.println("Enter Course ID you wish to Delete: ");
			int curCourseID = Integer.parseInt(sc.nextLine());

			boolean isValidCourse = CourseDBOperations.isValidCourseID(curCourseID);
			if (!isValidCourse) {
				System.out.println("Invalid CourseID! Course with that ID doesn't exists.");
				return;
			}

			Course curCourse = CourseDBOperations.getCourseFromCourseTableUsingID(curCourseID);
			CourseServices.printCourseDetails(curCourse);

			System.out.println("Do you really want to Delete Course - " + curCourse.getName() + " ? (Y/N): ");
			String ch = sc.nextLine();
			if (ch.equalsIgnoreCase("Y")) {
				CourseDBOperations.deleteCourseFromCourseTableUsingID(curCourseID);
				System.out.println("\nCourse Deleted Successfully!\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void approveStudentSignInRequests() {
		try {
			Scanner sc = new Scanner(System.in);

			ArrayList<Student> allStudentRequestsList = DBOperations.getAllStudentRequestsList();

			try {
				for (Student curStudent : allStudentRequestsList) {
					StudentServices.printStudentDetails(curStudent, "\nStudent Details: ");
					System.out.println("- Manage Student Request -");
					System.out.println("1. Approve\n2. Reject\n3. Skip");
					System.out.println("Choose an Operation: ");
					int ch = Integer.parseInt(sc.nextLine());

					ManageStudentLoop: while (true) {
						if (ch == 1) {
							StudentDBOperations.deleteStudentFromStudentRequestsTable(curStudent);
							StudentDBOperations.addStudentToStudentTable(curStudent);
							break;
						} else if (ch == 2) {
							StudentDBOperations.deleteStudentFromStudentRequestsTable(curStudent);
							break;
						} else if (ch == 3) {
							System.out.println("Student skipped!");
							break ManageStudentLoop;
						} else {
							System.out.println("Choose from Options!");
							continue ManageStudentLoop;
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("All Requests are Reviewed!\n");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void approveProfessorApplicationRequests() {
		Scanner sc = new Scanner(System.in);

		ArrayList<Professor> allProfessorApplicationsList = DBOperations.getAllProfessorApplicationsList();

		try {

			for (Professor curProfessor : allProfessorApplicationsList) {
				ProfessorServices.printProfessorDetails(curProfessor, "\nProfessor Details: ");
				System.out.println("- Manage Professor Application -");
				System.out.println("1. Approve\n2. Reject\n3. Skip");
				System.out.println("Choose an Operation: ");
				int ch = Integer.parseInt(sc.nextLine());

				ManageProfessorLoop: while (true) {
					if (ch == 1) {
						ProfessorDBOperations.deleteProfessorFromProfessorApplicationsTable(curProfessor);
						ProfessorDBOperations.addProfessorToProfessorTable(curProfessor);
						break;
					} else if (ch == 2) {
						ProfessorDBOperations.deleteProfessorFromProfessorApplicationsTable(curProfessor);
						break;
					} else if (ch == 3) {
						System.out.println("Professor skipped!");
						break ManageProfessorLoop;
					} else {
						System.out.println("Choose from Options!");
						continue ManageProfessorLoop;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("All Applications are Reviewed!\n");
	}
}
