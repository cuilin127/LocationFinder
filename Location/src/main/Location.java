package main;

import java.io.IOException;

import org.json.JSONObject;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Location {

	public static void main(String[] args) {
		
		Place place = getPlace("");
		
		System.out.print(place);


	}
	public static String getPlaceId(String input) {
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("text/plain");
				@SuppressWarnings("deprecation")
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
				MediaType mediaType = MediaType.parse("text/plain");
				
				Request request = new Request.Builder()
				  .url("https://maps.googleapis.com/maps/api/place/details/json?place_id="+id+"&fields=name%2Cformatted_address%2Cformatted_phone_number%2Cwebsite&key=AIzaSyDx6zg1YLtCfG0_0VHy3woS0nX-m29Qd4A")
				  .method("GET", null)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					//System.out.print(response.body().string());
					JSONObject myObject = new JSONObject(response.body().string());
					place.id = id;
					place.name = myObject.getJSONObject("result").getString("name");
					place.address = myObject.getJSONObject("result").getString("formatted_address");
					place.phoneNumber = myObject.getJSONObject("result").getString("formatted_phone_number");
					place.website = myObject.getJSONObject("result").getString("website");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					return place;
				}
		
	}

}
