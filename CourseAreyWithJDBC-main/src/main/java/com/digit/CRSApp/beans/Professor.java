package com.digit.CRSApp.beans;

public class Professor {
	private int professorID;
	private String name;
	private int age;
	private String city;
	private int yearsOfExpreience;
	private int courseID;
	private String userName;
	private String password;

	public Professor(int professorID, String name, int age, String city, int yearsOfExpreience, int courseID,
			String userName, String password) {
		super();
		this.professorID = professorID;
		this.name = name;
		this.age = age;
		this.city = city;
		this.yearsOfExpreience = yearsOfExpreience;
		this.courseID = courseID;
		this.userName = userName;
		this.password = password;
	}

	public Professor() {
	}

	public int getProfessorID() {
		return professorID;
	}

	public void setProfessorID(int professorID) {
		this.professorID = professorID;
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

	public int getYearsOfExpreience() {
		return yearsOfExpreience;
	}

	public void setYearsOfExpreience(int yearsOfExpreience) {
		this.yearsOfExpreience = yearsOfExpreience;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
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
