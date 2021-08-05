package knew.newnew;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

//import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class CrunchifyRESTService {
	
	@POST
	@Path("/Service")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) throws ClassNotFoundException {
		StringBuilder crunchifyBuilder = new StringBuilder();
		String data;
		String newResponse = "r";
		String name = "";
		String sport = "";
		String username = "";
		String password = "";

		int age = 0;
		int id = 0;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		data = crunchifyBuilder.toString();
		
		Validation val = new Validation();
		Boolean checker = val.isJSONValid(data);
		
		if(checker==true){
			System.out.println("String is valid JSON");
			
			//JSON OBJECT
			JSONObject jo = new JSONObject(data);
			
			String stringOfReceivedKeys = "";
			for (String key: jo.keySet()){
			    System.out.println(key);
			    stringOfReceivedKeys = stringOfReceivedKeys.concat("," + key);
			}
			
			System.out.println("Value of stringOfReceivedKeys is :" +stringOfReceivedKeys);
			
			System.out.println("Number of keys is :" + jo.length());
			
			String[] jsonKeys = stringOfReceivedKeys.split(",");
			//String[] receivedKeys = new String[6];//parameters we are expecting
			ArrayList<String> receivedKeys = new ArrayList<String>();
			for(String x:jsonKeys) {
				System.out.println("json keys are:" + x);
			}
			
			for(int a = 0, b = 1; b < jsonKeys.length; b++) {
				receivedKeys.add(jsonKeys[b]);
				a++;
			}
			
			for(String y:receivedKeys) {
				System.out.println(y);
			}
			
			val.isDataValid(receivedKeys);
			//boolean hasNoBadData = val.isDataValid(receivedKeys);
			//System.out.println("Value of hasBadData is: " + hasNoBadData);
			if(val.badData.isEmpty() == false) {
				System.out.println("Client has bad data" + val.badData.isEmpty());
				System.out.println("Bad data is: " + val.badData);
				newResponse = String.join(", ", val.badData);
				newResponse = newResponse.concat(" are invalid");
				
			}
			else {
				System.out.println("Client has no bad data");
				
				try {
					 username = jo.getString("username");
				}
				catch(Exception e)
				{			
					newResponse = " username isnt a string";
					System.out.println(newResponse);
				}
				
				try {
					 password = jo.getString("password");
				}
				catch(Exception e)
				{			
					newResponse = " password isnt a string";
					System.out.println(newResponse);
				}
				
				boolean isUserValidValue = val.isUserValid(username, password);
				System.out.println("Value of  username entered is:" +username);
				System.out.println("Value of  password entered is:" +password);
				if(isUserValidValue == true){
					System.out.println("Authentication passed ");
					
					try {
						name = jo.getString("name");
					}
					catch(Exception e)
					{			
						newResponse = " name isnt a string";
						System.out.println(newResponse);
					}
					
					
					
					try {
						sport = jo.getString("sport");
					}
					catch(Exception e)
					{			
						newResponse = newResponse.concat(" sport isnt a string");
						System.out.println(newResponse);
					}
					
					try {
						age = jo.getInt("age");
					}
					catch(Exception e)
					{			
						newResponse = newResponse.concat(" age isnt an int");
						System.out.println(newResponse);
					}
					
					try {
						 id = jo.getInt("id");
					}
					catch(Exception e)
					{			
						newResponse = newResponse.concat(" id isnt an int");
						System.out.println(newResponse);
					}
					
					System.out.println("response is after checking params " + newResponse);
					
			        System.out.println("name is :" + name);
			        System.out.println("sport is :" + sport);
			        System.out.println("age is :" + age);
			        System.out.println("id is :" + id);

			        if(newResponse == "r") {
			        	
			        	AccessDB myObj = new AccessDB();
				        //myObj.getPerson(); 
				        
				        myObj.addPerson(name, sport, age, id);
				        
				        //sometimes TOMCAT wont be able to use the connector driver and db operations wont work so 
				        //Copy the JDBC-driver JAR file (mine was ojdbc6) into $JAVA_HOME/jre/lib/ext (for me it was C:\Program Files\Java\jdk-11.0.10\lib but it can also be C:\Program Files\Java\jdk1.7.0_80\jre\lib\ext).
				        //just
				        
				        String newName = name + " new name";
				        String newsport = sport + " new sport";
				        int newAge = age + 1;
				        int newId = id + 1;
				        
				        JSONObject ju = new JSONObject();
				        ju.put("newName", newName);
				        ju.put("newsport", newsport);
				        ju.put("newAge", newAge);
				        ju.put("newId", newId);
				        
				        newResponse = ju.toString();
			        }
					
				}
				else {
					System.out.println("Authentication failed");
					newResponse = " E16 ";
				}
				
				
				
			}
			
			
			
		}
		else {
			System.out.println("String is not valid JSON");
			newResponse = "Bad request format contact dev for right format";
		}
		
        return Response.status(200).entity(newResponse).build();
        
		// return HTTP response 200 in case of success
		//return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "CrunchifyRESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
		
		@GET
		@Path("/ver")
		//@Produces(MediaType.TEXT_PLAIN)
		public Response verResponse(@QueryParam("id") int id) throws ClassNotFoundException {
			String result = "Hi " + id;// testing to see if the value of id was identified uniquely
			System.out.println(id); // testing to see if the value of id was identified uniquely
			
			AccessDB myObj1 = new AccessDB();
			result = myObj1.getPersonB(id);
			int newLastId = myObj1.getLastId();
			System.out.println(newLastId);
			// return HTTP response 200 in case of success
			return Response.status(200).entity(result).build();
	}

}
