package com.weatherpro.weatherReport;

public class WeatherCondition 
{
	private String day= null;
	private String date= null; 
	private double currTemp = 0;
	private String comment = null;
	private int code = 0;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getCurrTemp() {
		return currTemp;
	}

	public void setCurrTemp(double currTemp) {
		this.currTemp = currTemp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public WeatherCondition() 
	{
	
	}

	public void setDescription(String attributeValue) {
		this.comment = attributeValue;
		
	}

}
