package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;

public class Location {

	public static void main(String[] args) throws IOException {

		int j=0;
		System.out.println(j);
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/main/data.txt"));

		      String filename= "./src/main/fullData.txt";
		
			  FileWriter fw = new FileWriter(filename,true);
			  String line = br.readLine();
		      while(line != null) {
		    	System.out.println(j);
		        Place place = getPlace(line);
		        if(place.lng!=-1.1) {
		        	fw.write(place.id+"@@"+place.name+"@@"+place.address+"@@"+place.phoneNumber+"@@"+place.website+"@@"+place.lat+"@@"+place.lng+"\n");//appends the string to the file
				    j++;
		        }
			    place = null;
			    System.gc();
			    line = br.readLine();
		      }
		      br.close();
		      fw.close();
		    } catch (Exception e) {
		      System.out.println("An error occurred.");
		      System.out.println("Working Directory = " + System.getProperty("user.dir"));
		      e.printStackTrace();
		    }	
	}

	public static String getPlaceId(String input) {
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				//RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+input+"&inputtype=textquery&fields=place_id&key=AIzaSyDx6zg1YLtCfG0_0VHy3woS0nX-m29Qd4A")
				  .method("GET", null)
				  .build();
				String id = "Not Found!!";
				try {
					Response response = client.newCall(request).execute();
					JSONObject myObject = new JSONObject(response.body().string());
					id = myObject.getJSONArray("candidates").getJSONObject(0).getString("place_id");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					return id;
				}
					
				
	}
	public static Place getPlace(String input) {
		Place place = new Place();
		
		String id = getPlaceId(input);
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("https://maps.googleapis.com/maps/api/place/details/json?place_id="+id+"&fields=name%2Cformatted_address%2Cformatted_phone_number%2Cwebsite%2Cgeometry&key=AIzaSyDx6zg1YLtCfG0_0VHy3woS0nX-m29Qd4A")
				  .method("GET", null)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					//System.out.print(response.peekBody(2048).string());
					JSONObject myObject = new JSONObject(response.body().string());
					place.id = id;
					place.name = myObject.getJSONObject("result").getString("name");
					place.address = myObject.getJSONObject("result").getString("formatted_address");
					place.phoneNumber = myObject.getJSONObject("result").getString("formatted_phone_number");
					place.website = myObject.getJSONObject("result").getString("website");
					place.lat = myObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");
					place.lng = myObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					return place;
				}

		
	}

}
