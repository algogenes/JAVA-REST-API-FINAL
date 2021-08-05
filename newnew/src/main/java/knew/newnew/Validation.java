package knew.newnew;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Validation {
	String uname = "username";
	String pword = "password";
	boolean isUserValid = false;
	Set<String> catchDuplicates = new HashSet<>();
	ArrayList<String> duplicates = new ArrayList<String>();
	String[] expectedNames = {"username", "password", "name", "sport", "age", "id"};
	ArrayList<String> badData = new ArrayList<String>();
	List<String> listOfExpectedNames = Arrays.asList(expectedNames);
	
	public  boolean isJSONValid(String jsonInString ) {
	    try {
	       final ObjectMapper mapper = new ObjectMapper();
	       mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }

}
	
	public boolean isUserValid(String unm, String pwd) {
		boolean isUsernamecorrect = (unm == uname);
		System.out.println("unm is :" + unm);
		System.out.println("pwd is :" + pwd);
		System.out.println("isUsernamecorrect is :" + isUsernamecorrect);
		if(unm.equals(uname)) {
			isUserValid = true;
			System.out.println("Username is valid ");
			
			if(pwd.equals(pword)) {
				isUserValid = true;
				System.out.println("Password is valid ");
			}
			else {
				isUserValid = false;
				System.out.println("Password is wrong thus invalid user");
			}
			
		}
		else {
			isUserValid = false;
			System.out.println("Username is unknown thus invalid user");
			System.out.println("Username being checked is : " + unm );
			System.out.println("The correct username is  : " + uname );
			
		}
		
		return isUserValid;
	}
	
	public void isDataValid(ArrayList<String> jsonKeys) {
		badData.clear();
		
		System.out.println("The key names below");
		for(int a = 0; a < jsonKeys.size(); a++){
			System.out.println(jsonKeys);
		}
		
		for(int b = 0; b < jsonKeys.size(); b++) {
			if(!(listOfExpectedNames.contains(jsonKeys.get(b)))){
				badData.add(jsonKeys.get(b));
				System.out.println(jsonKeys.get(b) + " Is NOT a valid key name");
				
	        }
			else {
				System.out.println(jsonKeys.get(b) + " Is a valid key name");
			}
		}
		
		boolean checkBadData = badData.isEmpty();
		System.out.println("Bad data is: " + badData);
		System.out.println("checkBadData is: " + checkBadData);
		
		//return checkBadData;
	}
	
	public void checkDuplicates(String[] jsonKEYS) {
		System.out.println("Checking for duplicates");
		 for (String named : jsonKEYS) {
	           if (catchDuplicates.add(named) == false) {
	               System.out.println("found a duplicate element in array : "
	                       + named);
	               duplicates.add(named);
	           }
	           else {
	           	 System.out.println("There were no duplicates found in json keys");
	           }
	       }
		 
		 /* for(int a = 0; a < jsonKeys.length; a++){
			 boolean ansOfAddDuplicates = true;
			 ansOfAddDuplicates = catchDuplicates.add(jsonKeys[a]);
			 System.out.println(ansOfAddDuplicates);
			 if(ansOfAddDuplicates == false) {
				 duplicates.add(jsonKeys[a]);
			 }
			}*/
		 
		 System.out.println("Checking for errors 3");
		 if(!(duplicates.isEmpty())) {
				System.out.println("Client has duplicates");
				System.out.println("duplicates are: " + duplicates);
			}
			else {
				System.out.println("Client has no duplicates");
			}
		
		
	}
	
	
	 
	 
	
}
