package com.vrij.weathermap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.weatherpro.woeid.CityResult;
import com.weatherpro.woeid.WoeIDParser;

public class CitySelectorActivity extends ActionBarActivity {

	public static final String EXTRA_MESSAGE = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_city_selector);
		AutoCompleteTextView dyCityOpt = (AutoCompleteTextView)this.findViewById(R.id.autoSel_city);
		CityAdapter adpt = new CityAdapter(this, null);
		dyCityOpt.setAdapter(adpt);
		dyCityOpt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				CityResult selCity =(CityResult) parent.getItemAtPosition(position);
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(CitySelectorActivity.this);
				SharedPreferences.Editor edit = sharedPref.edit();
				edit.putString("cityname", selCity.getCityName());
				edit.putLong("woeid", selCity.getWoeid());
				edit.putString("country", selCity.getCountry());
				edit.putString("unit", "c");
				edit.commit();
				Intent i = null;
				refreshData();
			}
		});	

	}

	private void refreshData()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (prefs == null)
			return ;


		long woeid1 = prefs.getLong("woeid", 0);//("woeid", null);
		if (woeid1 == 0) {
			return;
		}
		String woeid = ""+woeid1;
		//Log.d("SwA", "WOEID ["+woeid+"]");
		String query_str = null;
		if (woeid != null) {
			String loc = prefs.getString("cityname", null) + "," + prefs.getString("country", null);
			String unit = prefs.getString("unit", null);
			if (unit == null)
				unit = "c";
			query_str = WoeIDParser.makeWeatherURL(woeid, unit);
		}

		Intent it = new Intent(getApplicationContext(), WeatherDisplayActivity.class);
		it.putExtra(EXTRA_MESSAGE, query_str);
		startActivity(it);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_selector, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private class CityAdapter extends ArrayAdapter<CityResult> implements Filterable {

		private Context ctx;
		private List<CityResult> cityList = new ArrayList<CityResult>();

		public CityAdapter(Context ctx, List<CityResult> cityList) {
			super(ctx, R.id.result_layout, cityList);
			this.cityList = cityList;
			this.ctx = ctx;
		}


		@Override
		public CityResult getItem(int position) {
			if (cityList != null)
				return cityList.get(position);

			return null;
		}

		@Override
		public int getCount() {
			if (cityList != null)
				return cityList.size();

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View result = convertView;
			if (result == null) {
				LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				result = inf.inflate(R.layout.result_city, parent, false);
			}
			TextView  tv=  (TextView)result.findViewById(R.id.txtCityName);
			Log.d("mausam", tv != null?"Not Null":"Null TextView");
			Log.d("WEATHERPRO", cityList.get(position).getCityName() + "," + cityList.get(position).getCountry());
			tv.setText(cityList.get(position).getCityName() + "," + cityList.get(position).getCountry());
			System.out.println(cityList.get(position).getCityName() + "," + cityList.get(position).getCountry());

			return result;
		}

		@Override
		public long getItemId(int position) {
			if (cityList != null)
				return cityList.get(position).hashCode();
			return 0;
		}

		@Override
		public Filter getFilter() {
			Filter cityFilter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults results = new FilterResults();
					if (constraint == null || constraint.length() < 2)
						return results;

					List<CityResult> cityResultList= new ArrayList<CityResult>();
					CityResult[]  city = new CityResult[3];
					city[0] = new CityResult();
					city[0].cityName = "Kushinagar";
					city[0].woeid = 12586773;
					city[0].country = "India";
					cityResultList.add(city[0]);
					try {
						//cityResultList = WoeIDParser.parse(constraint.toString());
					} catch (Exception e) {
						Log.d("[WeatherMap]", e.toString());
					}
					results.values = cityResultList;
					results.count = cityResultList.size();
					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					cityList = (List) results.values;
					notifyDataSetChanged();
				}
			};

			return cityFilter;
		}
	}
}
