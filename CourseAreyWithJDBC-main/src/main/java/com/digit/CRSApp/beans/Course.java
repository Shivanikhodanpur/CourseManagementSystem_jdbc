package com.digit.CRSApp.beans;

public class Course {
	private int courseID;
	private String name;
	private int fee;
	private int durationInHrs;

	public Course(int courseID, String name, int fee, int durationInHrs) {
		super();
		this.courseID = courseID;
		this.name = name;
		this.fee = fee;
		this.durationInHrs = durationInHrs;
	}

	public Course() {

	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getDurationInHrs() {
		return durationInHrs;
	}

	public void setDurationInHrs(int durationInHrs) {
		this.durationInHrs = durationInHrs;
	}
}
