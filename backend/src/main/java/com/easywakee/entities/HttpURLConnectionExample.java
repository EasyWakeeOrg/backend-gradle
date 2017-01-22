package com.easywakee.entities;
import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";
	private String url = "";

	// HTTP GET request
	public String sendGet() throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		JSONObject jObject  = new JSONObject(response.toString());
		JSONArray rows = jObject.getJSONArray("rows");
		JSONArray arrayObject = ((JSONObject)rows.getJSONObject(0)).getJSONArray("elements");
		JSONObject durationArray = (JSONObject)arrayObject.getJSONObject(0);
		JSONObject durationJSON = (JSONObject)durationArray.getJSONObject("duration");
		String duration = (String) durationJSON.get("text");
		System.out.println(duration);

		return duration;


	}
	
	public void setUrl(String newUrl){
		this.url = newUrl;
	}
	
	public String getUrl(){
		return this.url;
	}
}