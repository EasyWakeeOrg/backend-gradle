package com.easywakee.service;
import com.easywakee.entities.Address;
import com.easywakee.entities.HttpURLConnectionExample;
import com.easywakee.entities.Time;
import com.easywakee.entities.User;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


public class Quickstart {
	private static final String API_KEY = "AIzaSyDtDCtkU7HxMQArZYXTOYM9hGCuWV8cjmY";

	   /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

	public static Time firstEvent(User u) throws IOException{
		Time firstEventTime = new Time(-1,-1);
		//Google API
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
            getCalendarService();

        // List the next 10 events from the primary calendar.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        DateTime now = new DateTime(cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);        
        DateTime tomorrow = new DateTime(cal.getTime());   
        Events events = service.events().list("primary")
            .setTimeMin(now)
            .setTimeMax(tomorrow)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {            
	    	Event event = items.get(0);
	        DateTime start = event.getStart().getDateTime();
	        if (start == null) {
	            start = event.getStart().getDate();
	        }
	        String[] startTimeVect = start.toString().split("T");
	        if(startTimeVect == null){
	        	System.out.println("No start time?");
	        }
	        else{
	        	String startTime = startTimeVect[1];
	            String[] hourMin = startTime.split(":");
	            int h = Integer.parseInt(hourMin[0]);
	            int m = Integer.parseInt(hourMin[1]);
	            firstEventTime.setHour(h);
	            firstEventTime.setMinute(m);
	        }
	    }
		return firstEventTime;
	}

	//Computes the route of the user from home to school. Return the time needed
	//to perform the travel
	public static int computeRoute(User u) throws Exception{
    	String destination="";
    	String origin="";

    	destination += u.getAddress().getNb() + ",";
    	for(String field : u.getAddress().getStreet().split(" ")){
    		destination += field;
    		destination += ",";
    	}
    	destination += u.getAddress().getPostalCode() + ",";
    	String[] fields = u.getAddress().getCity().split(" ");
    	for(int i = 0 ; i< fields.length - 1;i++){
    		destination += fields[i];
    		destination += ",";
    	}
    	destination += fields[fields.length - 1];
    	
    	origin += u.getSchoolPlace().getNb() + ",";
    	for(String field : u.getSchoolPlace().getStreet().split(" ")){
    		origin += field;
    		origin += ",";
    	}
    	origin += u.getSchoolPlace().getPostalCode() + ",";
    	String[] schoolCity = u.getSchoolPlace().getCity().split(" ");
    	for(int i = 0 ; i< schoolCity.length - 1;i++){
    		origin += schoolCity[i];
    		origin += ",";
    	}
    	origin += schoolCity[schoolCity.length - 1];

		HttpURLConnectionExample http = new HttpURLConnectionExample();
		http.setUrl("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=");
		String url = http.getUrl();
		url += origin;
		url += "&destinations=";
		url += destination;
		url += "&key=";
		url += getAPI_KEY();
		http.setUrl(url);
		

		System.out.println("Testing 1 - Send Http GET request");
		String getResult = http.sendGet();
		String[] time = getResult.split(" ");
		int i=0;
		int travelDuration = 0;
		while(i<time.length){
			if(time[i+1].equals("hours") || time[i+1].equals("hour")){
				int nbHour = Integer.parseInt(time[i]);
				travelDuration += (nbHour*60);
				i = i + 2;
			}
			else if(time[i+1].equals("mins") || time[i+1].equals("min")){
				travelDuration += Integer.parseInt(time[i]);
				i = i + 2;
			}
		}
		return travelDuration;
	}
	
	//Send the alarm time to the alarm clock device
	public static Time setAlarmTime(User u) throws Exception{
		//Computes the alarm time
		Time alarmTime = firstEvent(u);
		alarmTime.substract(u.getTime() + computeRoute(u));
		return alarmTime;
		//Write code to send the info to the device
		//Other possibility: write the info somewhere and the device
		//will send a request to read it
	}

    public static void main(String[] args) throws Exception {
    	Address home = new Address(68, "Cours Fauriel", 42100, "Saint-etienne");
    	Address school = new Address(5,"chemin des hautes Bastides",06650,"Le Rouret");
    	ArrayList<String> transport = new ArrayList<String>();
    	transport.add("Car");
    	User u = new User("Pierre", "ptipito","test",30,home,transport,school,"id");

    	while(true){
        	SimpleDateFormat h = new SimpleDateFormat ("HH:mm");
        	Date currentTime_1 = new Date();
        	String heureString = h.format(currentTime_1);
        	//Get tomorrow's date
        	Calendar calendar = Calendar.getInstance();
        	calendar.add(Calendar.DAY_OF_YEAR, 1);
        	String date="";
        	date+=calendar.get(Calendar.DAY_OF_MONTH);
        	date+="/";
        	int month =(calendar.get(Calendar.MONTH)+1);
        	if(month<10)
        		date+="0";
        	date+=month;
        	date+="/";
        	date+=calendar.get(Calendar.YEAR);
        	
    		if(heureString.equals("11:08")){
			
		    	Time first = firstEvent(u);
		    	if(first.getHour()==-1 || first.getMinute() == -1){
		    		try {
						BufferedWriter out = new BufferedWriter(new FileWriter("~\\EasyWakeeAlarm\\AlarmTime.txt"));
					    out.write("NoEvt"); 
					    out.close();
			    	}
			    	catch (IOException e)
			    	{
			    	    System.out.println("Exception ");
			    	}
			    	finally{
			    		break;
			    	}
		    	}
		    	else{
			    	Time alarm = setAlarmTime(u);
			    	System.out.println("First event time : " + first);
			    	System.out.println("Alarm time : " + alarm);
			    	try {
						BufferedWriter out = new BufferedWriter(new FileWriter("~\\EasyWakeeAlarm\\AlarmTime.txt"));
					    out.write(alarm.toString()); 
					    out.close();
					    BufferedWriter out2 = new BufferedWriter(new FileWriter("~\\EasyWakeeAlarm\\AlarmDay.txt"));
					    out2.write(date); 
					    out2.close();
			    	}
			    	catch (IOException e)
			    	{
			    	    System.out.println("Exception ");
			    	}
			    	finally{
			    		break;
			    	}
		    	}
    		}
    	}
    }
    
    public static String getAPI_KEY(){
    	return API_KEY;
    }

}
