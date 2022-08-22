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
		System.out.print(getPlaceId("Sheridan College"));


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

}
