
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class BasicAccessing {

	private static HttpURLConnection connection;

	public static void main(String[] args) throws Exception{

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://api.github.com/users/octocat/repos");
			connection = (HttpURLConnection) url.openConnection();

			// Request Setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			//System.out.println(status);

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}

			//System.out.println(responseContent.toString());
		parse(responseContent.toString());
		
		
		
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Parse JSON to readable format. 
	public static String parse (String responseBody) {
		
		JSONArray info = new JSONArray (responseBody);
		for (int i=0; i<info.length(); i++) {
			JSONObject information = info.getJSONObject(i);
			String name = information.getString("name");
			int issues = information.getInt("open_issues_count");
			String url = information.getString("url");
			System.out.println("Repository Name : "+name+"  URL :  "+url+"  Total Issues : "+issues);	
			System.out.println();
		}
		return null;	
	}

}