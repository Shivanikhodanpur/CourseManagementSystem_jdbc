package com.digit.CRSApp.services;

import java.util.ArrayList;

import com.digit.CRSApp.beans.Course;
import com.digit.CRSApp.databaseOperations.CourseDBOperations;

public class CourseServices {

	public static void printCourseDetails(Course curCourse) {
		System.out.println("Course Details:");
		System.out.println("\tCourse ID: " + curCourse.getCourseID());
		System.out.println("\tCourse Name: " + curCourse.getName());
		System.out.println("\tCourse Fee: " + curCourse.getFee());
		System.out.println("\tCourse Duration: " + curCourse.getDurationInHrs());
		System.out.println();
	}
	
	public static void printAllCourseIDsAndNames() {
		ArrayList<Course> allCoursesList = CourseDBOperations.getAllCoursesList();
		for (Course curCourse : allCoursesList) {
			System.out.println("\tCourse ID: " + curCourse.getCourseID() + "\tCourse Name: " + curCourse.getName());
		}
	}
	
	public static void printAllCoursesDetails() {
		ArrayList<Course> allCoursesList = CourseDBOperations.getAllCoursesList();
		for (Course curCourse : allCoursesList) {
			printCourseDetails(curCourse);
		}
	}
}
