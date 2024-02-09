package com.jda.snew.services.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jda.snew.config.SNEWCache;
import com.jda.snew.weather.api.DataPoint;
import com.jda.snew.weather.api.FIODaily;
import com.jda.snew.weather.api.FIODataPoint;
import com.jda.snew.weather.api.ForecastIO;
import com.jda.snew.weather.api.ForecastObject;

@Path("weather")
public class Weather {
	@Path("/forecast")
	@GET
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces({ MediaType.APPLICATION_JSON })
	public ForecastObject getForecast(/*@PathParam("latitude") String latitude,
			@PathParam("longitude") String longitude*/) {
		SNEWCache instance = SNEWCache.getInstance();
		ForecastObject fcObject = (ForecastObject) instance.getObject("weatherForecastObject");
		try{
		if (fcObject == null) {
			Properties props = (Properties) instance.getObject("properties");
			ForecastIO fio = new ForecastIO(props.getProperty("weatherApiKey"));
			fio.setUnits(ForecastIO.UNITS_AUTO);
			fio.setLang(ForecastIO.LANG_ENGLISH);
			fio.getForecast(props.getProperty("latitude"), props.getProperty("longitude"));
			System.out.println("Latitude: " + fio.getLatitude());
			System.out.println("Longitude: " + fio.getLongitude());
			System.out.println("Timezone: " + fio.getTimezone());
			System.out.println("Offset: " + fio.offset());
			FIODaily daily = new FIODaily(fio);
			// initialising Forecast Object
			fcObject = new ForecastObject();

			// In case there is no daily data available
			if (daily.days() < 0)
				System.out.println("No daily data.");
			else {
				FIODataPoint dp = null;
				List<DataPoint> datapoints = new ArrayList<DataPoint>();
				Map<String, String> weatherIconMap = new HashMap<String, String>();
				weatherIconMap.put("clear-day", "Clear day");
				weatherIconMap.put("clear-night", "Clear Night");
				weatherIconMap.put("rain", "Rain");
				weatherIconMap.put("snow", "Snow");
				weatherIconMap.put("sleet", "Sleet");
				weatherIconMap.put("wind", "Wind");
				weatherIconMap.put("fog", "Fog");
				weatherIconMap.put("cloudy", "Cloudy");
				weatherIconMap.put("partly-cloudy-day", "Partly Cloudy Day");
				weatherIconMap.put("partly-cloudy-night", "Partly Cloudy Night");
				weatherIconMap.put("hail", "Hail");
				weatherIconMap.put("thunderstorm", "Thunderstorm");
				weatherIconMap.put("tornado", "Tornado");
				// Print daily data
				for (int i = 0; i < daily.days(); i++) {
					DataPoint datapoint = new DataPoint();
					dp = daily.getDay(i);
					String[] h = dp.getFieldsArray();
					// System.out.println("Day #" + (i + 1));
					for (int j = 0; j < h.length; j++) {
						// System.out.println(h[j] + ": " +
						// daily.getDay(i).getByKey(h[j]));
						if (h[j].equals("summary"))
							datapoint.setSummary(dp.getByKey(h[j]).replace("\"", ""));
						if (h[j].equals("icon")) {
							datapoint.setIcon(dp.getByKey(h[j]).replace("\"", ""));
							datapoint.setIconText(weatherIconMap.get(datapoint.getIcon()));
						}
						if (h[j].equals("temperatureMax"))
							datapoint.setTemperatureMax(dp.getByKey(h[j]));
						if (h[j].equals("temperatureMin"))
							datapoint.setTemperatureMin(dp.getByKey(h[j]));
						if (h[j].equals("time")) {
							SimpleDateFormat inpdateFormat = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
							SimpleDateFormat outdateFormat = new SimpleDateFormat("EEE, d MMM yyyy ,h:mm a");
							SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
							try {
								// datapoint.setTime(dateFormat.parse(dp.getByKey(h[j])));
								@SuppressWarnings("deprecation")
								Date dt = inpdateFormat.parse(dp.getByKey(h[j]));
								datapoint.setTime(outdateFormat.format(dt));
								datapoint.setDay(dayFormat.format(dt));
								// System.out.println("Time::::"+dt);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (h[j].equals("windBearing"))
							datapoint.setWindBearing(dp.getByKey(h[j]));
						if (h[j].equals("windSpeed"))
							datapoint.setWindSpeed(dp.getByKey(h[j]));
						if (h[j].equals("pressure"))
							datapoint.setPressure(dp.getByKey(h[j]));
					}
					datapoints.add(datapoint);
				}
				fcObject.setData(datapoints);
				fcObject.setPlace(fio.getTimezone());
				// System.out.println(fio.toString());
			}
		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return fcObject;
	}

	@GET
	@Path("sample")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getSomeThing() {
		return "I am fine";
	}

}
