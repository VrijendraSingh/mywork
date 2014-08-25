package com.weatherpro.weatherReport;


public class ForeCast extends WeatherCondition
{
	/*forecast day="Fri" date="8 Aug 2014" low="70" high="78" text="Showers" code="11" />*/
	

	private String dayofweek;

	private String date;

	private String minTemp;

	private String maxTemp;

	private String textComment;

	private int code;
	
	
	public String getDayofweek() {
		return dayofweek;
	}


	public void setDayofweek(String dayofweek) {
		this.dayofweek = dayofweek;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getMinTemp() {
		return minTemp;
	}


	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}


	public String getMaxTemp() {
		return maxTemp;
	}


	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}


	public String getTextComment() {
		return textComment;
	}


	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}


	public ForeCast() 
	{
	
	}

}
