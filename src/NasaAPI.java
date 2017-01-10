import java.io.*;
import java.io.BufferedReader;
import java.util.*;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class NasaAPI {
  final static String apiKey = "9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute";
  public static void main(String[] args) throws Exception {
    flyby(43.078154,-79.075891);
    
  }
  private static void flyby (double latitude, double longitude) throws Exception
  {
    URL nasa = new URL("https://api.nasa.gov/planetary/earth/assets?lon=" + longitude + "&lat="+ latitude + "&begin=2014-02-01&api_key=" + apiKey);
    BufferedReader in = new BufferedReader(new InputStreamReader(nasa.openStream()));
    String inputLine;
    inputLine = in.readLine();
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(inputLine);
    JSONArray arr = (JSONArray)json.get("results");
     @SuppressWarnings("unchecked")
    Iterator<JSONObject> i = arr.iterator();
    long sum = 0;
    long amount = 0;
    Date second=null;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    JSONObject image1 = i.next();
    String largest = (String)image1.get("date");
    Date latest = format.parse(largest);
    
    while (i.hasNext()) {
          
      JSONObject image2 = i.next();
      String date1 = (String)image1.get("date");
      String date2 = (String)image2.get("date"); 
      Date first = format.parse(date1);
      second = format.parse(date2);
      long difference = second.getTime()-first.getTime();
      amount++;
      sum+=difference;
      if (i.hasNext())
        image1 = image2;
      if (second.getTime()>latest.getTime())
        latest = second;
      
    }
    
    long average = sum/amount;
    long finalDateTime = latest.getTime() + average;
    Date answer = new Date(finalDateTime);
    System.out.println("Next time: " + answer.toString());
    
  }
}