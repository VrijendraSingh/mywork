package com.weatherpro.weatherReport;

import java.util.*;

public class WeatherChannel 
{
	private String title = null;
	private String url = null;
	private String langauage = null;
	private Date LastBuildDate = null;
	private int ttl = -1;
	private YahooWeather yahooW = null;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLangauage() {
		return langauage;
	}
	public void setLangauage(String langauage) {
		this.langauage = langauage;
	}
	public Date getLastBuildDate() {
		return LastBuildDate;
	}
	public void setLastBuildDate(Date lastBuildDate) {
		LastBuildDate = lastBuildDate;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public YahooWeather getYahooW() {
		return yahooW;
	}
	public void setYahooW(YahooWeather yahooW) {
		this.yahooW = yahooW;
	}
}

