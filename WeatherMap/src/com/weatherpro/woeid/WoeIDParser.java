package com.weatherpro.woeid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.weatherpro.weatherReport.Constant;
import com.weatherpro.weatherReport.ForeCast;
import com.weatherpro.weatherReport.YahooWeather;



public class WoeIDParser 
{
	public static XmlPullParser parser =null;
	public static List<CityResult> parse(String format) 
	{
		ArrayList<CityResult> cityResult = new ArrayList<CityResult>();
		try {


			String queryIntput =  Constant.YAHOO_PLACE_QUERY_URL 
					+ format
					+ ");count=MAX_RESULT?appid="
					+ Constant.appID;
			parser = sendGet(queryIntput);


			int event = parser.getEventType();

			CityResult cty = null;
			String tagName = null;
			String currentTag = null;

			// We start parsing the XML
			while (event != XmlPullParser.END_DOCUMENT) {
				tagName = parser.getName();

				if (event == XmlPullParser.START_TAG) {
					if (tagName.equals("place")) {
						// place Tag Found so we create a new CityResult
						cty = new CityResult();
						Log.d("mausam", "New City found");
					}
					currentTag = tagName;
					Log.d("mausam", "Tag ["+tagName+"]");
				}
				else if (event == XmlPullParser.TEXT) {
					// We found some text. let's see the tagName to know the tag related to the text
					if ("woeid".equals(currentTag))
						cty.setWoeid(Long.parseLong(parser.getText()));
					else if ("name".equals(currentTag))
						cty.setCityName(parser.getText());
					else if ("country".equals(currentTag))
						cty.setCountry(parser.getText());

					Log.d("mausam", cty.getCityName());
					// We don't want to analyze other tag at the moment
				} else if (event == XmlPullParser.END_TAG) {
					if ("place".equals(tagName))
						cityResult.add(cty);
				}

				event = parser.next();
			}
		} catch (Exception e) {
			Log.d("mausam", e.toString());
		}

		return cityResult;
	}




	
	public static  XmlPullParser sendGet(String query) throws Exception 
	{

		//String url = "http://where.yahooapis.com/v1/places.q("+cityName+");count=1000?appid=3hgdY0bV34GAmksWqZzpDWma.hz1QQVpIb2DMbkXwZjH9D5G5x_Y6EIjsXcJzSIZhaOOR5tV";
		//String url = "http://weather.yahooapis.com/forecastrss?w=2295411";

		//query="http://google.com";
		URL obj = new URL(query);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", null);


		//int responseCode = con.getResponseCode();
		/*System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);*/


		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		InputStreamReader is = new InputStreamReader(con.getInputStream());
		BufferedReader buff = new BufferedReader(is);
		String databuf = null;
		StringBuffer buf = new StringBuffer();
		while((databuf = buff.readLine()) != null) {
			buf.append(databuf);
		}
		
		parser.setInput(is);
		return parser;
	}




	public static YahooWeather parseWeather (String wQ) 
	{

		YahooWeather result = new YahooWeather();
		// Log.d("SwA", "Response ["+resp+"]");
		try {
			XmlPullParser parser = sendGet(wQ);

			String tagName = null;
			String currentTag = null;

			int event = parser.getEventType();
			boolean isFirstDayForecast = true;
			while (event != XmlPullParser.END_DOCUMENT) {
				tagName = parser.getName();

				if (event == XmlPullParser.START_TAG) {
					if (tagName.equals("yweather:wind")) {
						// Log.d("mausam", "Tag [Wind]");
						result.wind.setChill(Integer.parseInt(parser.getAttributeValue(null, "chill")));
						result.wind.setDirection(Integer.parseInt(parser.getAttributeValue(null, "direction")));
						result.wind.setSpeed(Float.parseFloat(parser.getAttributeValue(null, "speed")));
					}
					else if (tagName.equals("yweather:atmosphere")) {
						// Log.d("mausam", "Tag [Atmos]");
						result.atmosphere.setHumidity(parser.getAttributeValue(null, "humidity"));
						result.atmosphere.setVisibility(parser.getAttributeValue(null, "visibility"));
						result.atmosphere.setPressure(parser.getAttributeValue(null, "pressure"));
						result.atmosphere.setRising(parser.getAttributeValue(null, "rising"));
					}
					else if (tagName.equals("yweather:forecast")) {
						//  Log.d("mausam", "Tag [Fore]");
						ForeCast forecast = new ForeCast();
						if (forecast!=null) {
							forecast.setCode(Integer.parseInt(parser.getAttributeValue(null, "code")));
							forecast.setMinTemp(parser.getAttributeValue(null, "low"));
							forecast.setMaxTemp(parser.getAttributeValue(null, "high"));
							forecast.setDay(parser.getAttributeValue(null,"day"));
							forecast.setDate(parser.getAttributeValue(null,"date"));
							result.weatherForeCast.add(forecast);
						}
					}
					else if (tagName.equals("yweather:condition")) {
						//  Log.d("mausam", "Tag [Condition]");
						result.condition.setCode(Integer.parseInt(parser.getAttributeValue(null, "code")));
						result.condition.setDescription(parser.getAttributeValue(null, "text"));
						result.condition.setCurrTemp(Integer.parseInt(parser.getAttributeValue(null, "temp")));
						result.condition.setDate(parser.getAttributeValue(null, "date"));
						result.condition.setDate(parser.getAttributeValue(null, "day"));
					}
					else if (tagName.equals("yweather:units")) {
						//   Log.d("mausam", "Tag [units]");
						result.units.temp = "°" + parser.getAttributeValue(null, "temperature");
						result.units.pressure = parser.getAttributeValue(null, "pressure");
						result.units.distance = parser.getAttributeValue(null, "distance");
						result.units.windSpeed = parser.getAttributeValue(null, "speed");
					}
					else if (tagName.equals("yweather:location")) {
						result.loc.setCityName(parser.getAttributeValue(null, "city"));
						result.loc.setRegion( parser.getAttributeValue(null, "region"));
						result.loc.setCountryName(parser.getAttributeValue(null, "country"));
					}
					else if (tagName.equals("image"))
						currentTag = "image";
					else if (tagName.equals("url")) {
						if (currentTag == null) {
							// result.imageUrl = parser.getAttributeValue(null, "src");
						}
					}
					else if (tagName.equals("lastBuildDate")) {
						currentTag="update";
					}
					else if (tagName.equals("yweather:astronomy")) {
						result.astronomy.setSunrise( parser.getAttributeValue(null, "sunrise"));
						result.astronomy.setSunset(parser.getAttributeValue(null, "sunset"));
					}

				}
				else if (event == XmlPullParser.END_TAG) {
					if ("image".equals(currentTag)) {
						currentTag = null;
					}
				}
				else if (event == XmlPullParser.TEXT) {
					if ("update".equals(currentTag)){}
					//result.lastUpdate = parser.getText();
				}
				event = parser.next();
			}


		}
		catch(Throwable t) {
			t.printStackTrace();
		}

		return result;
	}





	public static YahooWeather getWeather(String cityWoeid, String unit)
	{  
		YahooWeather yahooW = new YahooWeather();
		String str = makeWeatherURL(cityWoeid, unit);
		yahooW = parseWeather(str);
		return yahooW;
	}

	/*	public static void getWeather(String woeid, String unit, RequestQueue rq, final WeatherClientListener listener) {
		String url2Call = makeWeatherURL(woeid, unit);
		Log.d("FUCKING", "Weather URL ["+url2Call+"]");
		final YahooWeather result = new YahooWeather();
		StringRequest req = new StringRequest(Request.Method.GET, url2Call, new Response.Listener<String>() {
			@Override
			public void onResponse(String s) {
				try {
					parseResponse(s, result);
					listener.onWeatherResponse(result);
				} catch (XmlPullParserException e) {
					Log.d("parsing exception", "ocurs");
				} catch (IOException ex) {

				}
			}

			private void parseResponse(String resp, YahooWeather result) throws XmlPullParserException, IOException 
			{
				XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
				parser.setInput(new StringReader(resp));

				String tagName = null;
				String currentTag = null;

				int event = parser.getEventType();
				boolean isFirstDayForecast = true;
				while (event != XmlPullParser.END_DOCUMENT) {
					tagName = parser.getName();

					if (event == XmlPullParser.START_TAG) {
						if (tagName.equals("yweather:wind")) {
							// Log.d("mausam", "Tag [Wind]");
							result.wind.setChill(Integer.parseInt(parser.getAttributeValue(null, "chill")));
							result.wind.setDirection(Integer.parseInt(parser.getAttributeValue(null, "direction")));
							result.wind.setSpeed(Float.parseFloat(parser.getAttributeValue(null, "speed")));
						}
						else if (tagName.equals("yweather:atmosphere")) {
							// Log.d("mausam", "Tag [Atmos]");
							result.atmosphere.setHumidity(parser.getAttributeValue(null, "humidity"));
							result.atmosphere.setVisibility(parser.getAttributeValue(null, "visibility"));
							result.atmosphere.setPressure(parser.getAttributeValue(null, "pressure"));
							result.atmosphere.setRising(parser.getAttributeValue(null, "rising"));
						}
						else if (tagName.equals("yweather:forecast")) {
							//  Log.d("mausam", "Tag [Fore]");
							ForeCast forecast = new ForeCast();
							if (forecast!=null) {
								forecast.setCode(Integer.parseInt(parser.getAttributeValue(null, "code")));
								forecast.setMinTemp(parser.getAttributeValue(null, "low"));
								forecast.setMaxTemp(parser.getAttributeValue(null, "high"));
								forecast.setDay(parser.getAttributeValue(null,"day"));
								forecast.setDate(parser.getAttributeValue(null,"date"));
								result.weatherForeCast.add(forecast);
							}
						}
						else if (tagName.equals("yweather:condition")) {
							//  Log.d("mausam", "Tag [Condition]");
							result.condition.setCode(Integer.parseInt(parser.getAttributeValue(null, "code")));
							result.condition.setDescription(parser.getAttributeValue(null, "text"));
							result.condition.setCurrTemp(Integer.parseInt(parser.getAttributeValue(null, "temp")));
							result.condition.setDate(parser.getAttributeValue(null, "date"));
							result.condition.setDate(parser.getAttributeValue(null, "day"));
						}
						else if (tagName.equals("yweather:units")) {
							//   Log.d("mausam", "Tag [units]");
							result.units.temp = "°" + parser.getAttributeValue(null, "temperature");
							result.units.pressure = parser.getAttributeValue(null, "pressure");
							result.units.distance = parser.getAttributeValue(null, "distance");
							result.units.windSpeed = parser.getAttributeValue(null, "speed");
						}
						else if (tagName.equals("yweather:location")) {
							result.loc.setCityName(parser.getAttributeValue(null, "city"));
							result.loc.setRegion( parser.getAttributeValue(null, "region"));
							result.loc.setCountryName(parser.getAttributeValue(null, "country"));
						}
						else if (tagName.equals("image"))
							currentTag = "image";
						else if (tagName.equals("url")) {
							if (currentTag == null) {
								// result.imageUrl = parser.getAttributeValue(null, "src");
							}
						}
						else if (tagName.equals("lastBuildDate")) {
							currentTag="update";
						}
						else if (tagName.equals("yweather:astronomy")) {
							result.astronomy.setSunrise( parser.getAttributeValue(null, "sunrise"));
							result.astronomy.setSunset(parser.getAttributeValue(null, "sunset"));
						}

					}
					else if (event == XmlPullParser.END_TAG) {
						if ("image".equals(currentTag)) {
							currentTag = null;
						}
					}
					else if (event == XmlPullParser.TEXT) {
						if ("update".equals(currentTag)){}
						//result.lastUpdate = parser.getText();
					}
					event = parser.next();
				}



			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError volleyError) {

			}
		});

		rq.add(req);
	}

	 */
	public static String makeWeatherURL(String woeid, String unit) 
	{
		return  Constant.YAHOO_WEATHER_URL + "?w=" + woeid + "&u=" + unit;
	}


	public static interface WeatherClientListener {
		public void onWeatherResponse(YahooWeather weather);
	}
}
