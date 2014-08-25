package com.weatherpro.woeid;


public class CityResult 
{
	//@XmlElement(name="woeid")
	public long   woeid;
	
	//@XmlElement(name="name")
	public String cityName;
	
	//@XmlElement(name="country")
	public String country;

	public long getWoeid() {
		return woeid;
	}

	public void setWoeid(long woeid) {
		this.woeid = woeid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	@Override
	public String toString()
	{
		return cityName+", "+country;
	}
	
}
