package com.CRSApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.digit.CRSApp.beans.Professor;
import com.digit.CRSApp.beans.Student;
import com.digit.CRSApp.databaseOperations.CourseDBOperations;
import com.digit.CRSApp.databaseOperations.ProfessorDBOperations;
import com.digit.CRSApp.services.AdminServices;
import com.digit.CRSApp.services.ProfessorServices;
import com.digit.CRSApp.services.Services;
import com.digit.CRSApp.services.StudentServices;

public class CRSApp {
	public static void main(String[] args) {
		Connection conn = Services.getConnectionInstance();
		if (conn == null) {
			System.out.println("Unable to connect to CourseArey!\nTry Again Later!");
			return;
		}

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to CourseArey!");
		System.out.println("\t\tEducation Made Simpler!\n");

		MainExecLoop: while (true) {
			try {

				System.out.println("\n--------- Main Menu ---------\n");
				System.out.println("-- Select Portal to Access --");
				System.out.println("\t1. Admin\n\t2. Professor\n\t3. Student\n\t4. Quit\n");
				System.out.println("Enter your Choice: ");

				int ch = Integer.parseInt(sc.nextLine());

				if (ch == 1) {
					// ADMIN
					boolean isAdminAuthenticated = AdminServices.authenticateAdmin();
					if (!isAdminAuthenticated) {
						continue MainExecLoop;
					}

					AdminPortalLoop: while (true) {
						System.out.println("\n--- Admin Portal ---\n");
						System.out.println("\t1.  Approve Student\n\t2.  Approve Professor\n\t"
								+ "3.  Add Course\n\t4.  Remove Course\n\t5.  Remove Student\n\t6.  Remove Professor\n\t"
								+ "7.  View All Courses\n\t8.  View All Students\n\t"
								+ "9.  View All Professors\n\t10. Approve Student Course Change Requests\n\t"
								+ "11. Approve Professor Course Change Requests\n\t"
								+ "12. De-Register Student Grades\n\t" + "13.  Back to Main Menu\n");
						System.out.println("Enter your Choice: ");

						int curCh = Integer.parseInt(sc.nextLine());
						if (curCh == 1) {
							AdminServices.approveStudentSignInRequests();
						} else if (curCh == 2) {
							AdminServices.approveProfessorApplicationRequests();
						} else if (curCh == 3) {
							AdminServices.addCourseIntoCourseTable();
						} else if (curCh == 4) {
							AdminServices.removeCourseFromCourseTable();
						} else if (curCh == 5) {
							AdminServices.removeStudentFromStudentTable();
						} else if (curCh == 6) {
							AdminServices.removeProfessorFromStudentTable();
						} else if (curCh == 7) {
							AdminServices.printAllCoursesDetails();
						} else if (curCh == 8) {
							AdminServices.printAllStudentDetails();
						} else if (curCh == 9) {
							AdminServices.printAllProfessorsDetails();
						} else if (curCh == 10) {
							AdminServices.approveStudentCourseChangeRequest();
						} else if (curCh == 11) {
							AdminServices.approveProfessorCourseChangeRequest();
						} else if (curCh == 12) {
							AdminServices.deRegisterStudentGrades();
						} else if (curCh == 13) {
							break AdminPortalLoop;
						} else {
							System.out.println("Choose from Options!");
							continue AdminPortalLoop;
						}
					}
				} else if (ch == 2) {
					// PROFESSOR
					ProfessorMain: while (true) {
						System.out.println("- Professor Portal -\n");
						System.out.println("\t1. Login\n\t2. SignUp\n\t3. Back To Main Menu\n");

						int curCh = Integer.parseInt(sc.nextLine());
						if (curCh == 1) {
							// LOGIN
							LoginResponse curLoginResponse = ProfessorServices.professorLogin();
							if (curLoginResponse == null) {
								System.out.println("Invalid Credentials!");
								continue MainExecLoop;
							}
							Professor curProfessor = (Professor) curLoginResponse.getCurUserObject();

							int loginResponseCode = curLoginResponse.getResponseCode();
							if (loginResponseCode == 1) {
								// PROFESSOR EXISTING
								ProfessorOperations: while (true) {
									System.out.println("Welcome " + curProfessor.getName());
									System.out.println("\t1. Grade Students\n\t2. View Details\n\t"
											+ "3. View All Students You Teach\n\t4. View Course Details\n\t"
											+ "5. Change Course you Teach\n\t6. Change UserName\n\t"
											+ "7. Change Password\n\t8. Change Personal Details\n\t"
											+ "9. Back to Main\n");
									System.out.println("Enter your Choice: ");
									int inCurCh = Integer.parseInt(sc.nextLine());

									if (inCurCh == 1) {
										ProfessorServices.gradeStudentsUnderCurProfessor(curProfessor);
									} else if (inCurCh == 2) {
										ProfessorServices.printProfessorDetails(curProfessor, "Your Details: ");
									} else if (inCurCh == 3) {
										ProfessorServices.viewAllStudentsUnderCurProfessor(curProfessor);
									} else if (inCurCh == 4) {
										ProfessorServices.printProfessorTeachingCourseDetails(curProfessor);
									} else if (inCurCh == 5) {
										ProfessorServices.requestChangeProfessorCourseTeach(curProfessor);
									} else if (inCurCh == 6) {
										curProfessor = ProfessorServices.changeUserNameOfProfessor(curProfessor);
									} else if (inCurCh == 7) {
										curProfessor = ProfessorServices.changePasswordOfProfessor(curProfessor);
									} else if (inCurCh == 8) {
										curProfessor = ProfessorServices.changePersonalDetailsOfProfessor(curProfessor);
									} else if (inCurCh == 9) {
										break ProfessorOperations;
									} else {
										System.out.println("Choose from Options!");
										continue ProfessorOperations;
									}
								}
							} else if (loginResponseCode == 0) {
								// PROFESSOR IN PENDING
								ProfessorOperations: while (true) {
									System.out.println("Welcome " + curProfessor.getName());

									System.out.println("Your Application is yet to be Approved by Admin!");
									System.out.println("Please Check Later.\n");
									System.out.println("\nChoose Operation: ");

									System.out.println("1. View Application Details\n2. View Applied Course Details\n"
											+ "3. Change Course Applied\n4. Edit Details\n"
											+ "5. WithDraw Request\n6. Back to Main Menu");

									System.out.println("Enter your Choice: ");
									int inCurCh = Integer.parseInt(sc.nextLine());

									if (inCurCh == 1) {
										ProfessorServices.printProfessorDetails(curProfessor,
												"Your Application Details");
									} else if (inCurCh == 2) {
										ProfessorServices.printProfessorTeachingCourseDetails(curProfessor);
									} else if (inCurCh == 3) {
										ProfessorServices.changeProfessorInRequestCourse(curProfessor);
									} else if (inCurCh == 4) {
										ProfessorServices.changePersonalDetailsOfProfessorInRequest(curProfessor);
									} else if (inCurCh == 5) {
										boolean isWithdrawn = ProfessorServices
												.withDrawApplicationRequestOfProfessor(curProfessor);
										if (isWithdrawn) {
											continue MainExecLoop;
										}
									} else if (inCurCh == 6) {
										break ProfessorOperations;
									} else {
										System.out.println("Choose from Options!");
									}
								}
							} else {
								System.out.println("Something went wrong!");
								continue ProfessorMain;
							}
						} else if (curCh == 2) {
							// SIGNUP
							ProfessorServices.professorSignUp();
						} else if (curCh == 3) {
							break ProfessorMain;
						} else {
							System.out.println("Choose From Options!");
							continue ProfessorMain;
						}
					}
				} else if (ch == 3) {
					// STUDENT

					StudentMain: while (true) {
						System.out.println("- Student Portal -\n");
						System.out.println("\t1. Login\n\t2. SignUp\n\t3. Back To Main Menu\n");
						int curCh = Integer.parseInt(sc.nextLine());
						if (curCh == 1) {
							// LOGIN
							LoginResponse curLoginResponse = StudentServices.studentLogin();
							if (curLoginResponse == null) {
								System.out.println("Invalid Credentials!");
								continue MainExecLoop;
							}

							Student curStudent = (Student) curLoginResponse.getCurUserObject();

							int loginResponseCode = curLoginResponse.getResponseCode();
							if (loginResponseCode == 1) {
								// STUDENT IN EXISTENCE
								System.out.println("Welcome " + curStudent.getName());
								StudentOperations: while (true) {
									System.out.println("1. Get Scores\n2. Get Marks Card\n" + "3. View Profile\n"
											+ "4. View Course Details\n5. Change UserName\n6. Change Password\n"
											+ "7. Change Personal Details\n8. Change Course\n"
											+ "9. View Professor Details\10. Back to Main Menu\n");
									System.out.println("Enter your Choice: ");
									int inCurCh = Integer.parseInt(sc.nextLine());

									if (inCurCh == 1) {
										StudentServices.printStudentMarks(curStudent);
									} else if (inCurCh == 2) {
										StudentServices.printStudentMarksCard(curStudent);
									} else if (inCurCh == 3) {
										StudentServices.printStudentDetails(curStudent, "Your Details: ");
									} else if (inCurCh == 4) {
										StudentServices.printStudentEnrolledCourseDetails(curStudent);
									} else if (inCurCh == 5) {
										curStudent = StudentServices.changeUserNameOfStudent(curStudent);
									} else if (inCurCh == 6) {
										curStudent = StudentServices.changePasswordOfStudent(curStudent);
									} else if (inCurCh == 7) {
										curStudent = StudentServices.changePersonalDetailsOfStudent(curStudent);
									} else if (inCurCh == 8) {
										StudentServices.changeCourseEnrolled(curStudent);
									} else if (inCurCh == 9) {
										StudentServices.viewCurrentStudentProfessorDetails(curStudent);
									} else if (inCurCh == 10) {
										break StudentOperations;
									} else {
										System.out.println("Choose from Options!");
									}
								}
							} else if (loginResponseCode == 0) {
								// STUDENT IN REQUEST
								System.out.println("Welcome " + curStudent.getName());
								StudentOperations: while (true) {
									System.out.println("\nYour Application is yet to be Approved by Admin!");
									System.out.println("Please Check Later.\n");

									System.out.println("\nChoose Operation: ");

									System.out.println("1. View Application Details\n2. View Applied Course Details\n"
											+ "3. Change Course Applied\n4. Edit Details\n"
											+ "5. WithDraw Request\n6. Back to Main Menu");

									System.out.println("Enter your Choice: ");
									curCh = Integer.parseInt(sc.nextLine());
									if (curCh == 1) {
										StudentServices.printStudentDetails(curStudent, "Your Details");
									} else if (curCh == 2) {
										StudentServices.viewAppliedCourseDetailsOfStudent(curStudent);
									} else if (curCh == 3) {
										curStudent = StudentServices
												.changeCourseEnrolledForStudentInPending(curStudent);
									} else if (curCh == 4) {
										curStudent = StudentServices.changePersonalDetailsOfStudent(curStudent);
									} else if (curCh == 5) {
										boolean isWithdrawn = StudentServices
												.withDrawApplicationRequestOfStudent(curStudent);
										if (isWithdrawn) {
											continue MainExecLoop;
										}
									} else if (curCh == 6) {
										break StudentOperations;
									} else {
										continue StudentOperations;
									}
								}
							} else {
								System.out.println("Something went wrong!");
								continue StudentMain;
							}
						} else if (curCh == 2) {
							// SIGNUP
							StudentServices.studentSignUp();
						} else if (curCh == 3) {
							// BACK TO MAIN
							break StudentMain;
						} else {
							System.out.println("Choose From Options");
							continue StudentMain;
						}
					}
				} else if (ch == 4) {
					break MainExecLoop;
				} else {
					System.out.println("Choose from Options!");
					continue;
				}

			} catch (NumberFormatException | InputMismatchException e) {
				e.printStackTrace();
				System.err.println("Invalid Input! Please Enter Valid Input.");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}

		}
	}
}
