package com.digit.CRSApp.beans;

public class Student {
	private int studentID;
	private String name;
	private int age;
	private String city;
	private int courseEnrolled;
	private int marksSecured;
	private String userName;
	private String password;

	public Student(int studentID, String name, int age, String city, int courseEnrolled, int marksSecured,
			String userName, String password) {
		super();
		this.studentID = studentID;
		this.name = name;
		this.age = age;
		this.city = city;
		this.courseEnrolled = courseEnrolled;
		this.marksSecured = marksSecured;
		this.userName = userName;
		this.password = password;
	}

	public Student() {
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCourseEnrolled() {
		return courseEnrolled;
	}

	public void setCourseEnrolled(int courseEnrolled) {
		this.courseEnrolled = courseEnrolled;
	}

	public int getMarksSecured() {
		return marksSecured;
	}

	public void setMarksSecured(int marksSecured) {
		this.marksSecured = marksSecured;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
