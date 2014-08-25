package com.weatherpro.weatherReport;



//@XmlAccessorType(XmlAccessType.NONE)
//@XmlRootElement
public class Atmosphere 
{
	//@XmlAttribute
	private String humidity;
	//@XmlAttribute
	private String visibility;
	//@XmlAttribute
	private String pressure;
	//@XmlAttribute
	private String rising;
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getRising() {
		return rising;
	}

	public void setRising(String rising) {
		this.rising = rising;
	}

	public Atmosphere() 
	{
	
	}

}
