/*package woeid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="places")
public class places 
{
	
	@XmlAttribute(name="start", namespace=Constant.YWEATHER_URI)
	public String str;
	
	@XmlAttribute(name="count", namespace=Constant.YWEATHER_URI)
	public String count;
	
	@XmlElement(name="place")
	public place c;
}
*/


package com.weatherpro.woeid;

import java.util.List;


public class Places 
{
	public List<CityResult> c;
	

	public Places()
	{
		//System.out.println("Raja");
	}
}
