package com.weatherpro.weatherReport;


public class Location {

	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	

	private double longitude = 0.0;
	
	private double latitude  = 0.0;
	public Location() 
	{
		
	}

}
