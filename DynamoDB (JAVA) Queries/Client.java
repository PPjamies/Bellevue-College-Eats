package bc_eats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Client implements Runnable {
	 final BufferedReader in; 
	 final PrintWriter out;

	 Socket s; 
	    
	 // constructor 
	 public Client(Socket s, BufferedReader in, PrintWriter out) {  
	     this.in = in; 
	     this.out = out; 
	     this.s = s; 
	 }


	 @Override
	 public void run() {

	     String received="";
	     while (true)  {
	         try {

	        	 String msg = null;
	        
		        try {
		        
					received = this.in.readLine();
					System.out.println("received : "+received);        
		                    
		        } catch (IOException e) {
		        	e.printStackTrace();

	            }
	        
	           if(received==null) {
	        	   break;
	           }
	           else if(received.startsWith("@@"))
 	           {
 	        	  String[] newCredentials = received.split("@@"); //@@newUserEmail@@newUserPassword
		          int newNumber = Integer.parseInt(newCredentials[1]);
		          String newPassword = newCredentials[2];
		          System.out.println("New Email: " + newNumber + " New Password: " + newPassword);
		          
		          if(!Server.isRegisteredUser(newNumber)) 
		          {
		        	  Server.createNewUser(newNumber, newPassword);
		        	  sendMessageToMobile("@@0");
		          }
		          else
		          {
		        	  sendMessageToMobile("@@1"); //email is in use
		          }
 	           }
 	           
 	           else if(received.startsWith("%%"))
 	           {
 	        	  String[] credentials = received.split("%%"); //%%email%%password
		          int number = Integer.parseInt(credentials[1]);
		          String password = credentials[2];
		          System.out.println("User Email: " + number + " User Password: " + password);
		          
		          if(Server.isRegisteredUser(number) && Server.isCorrectPassword(number, password))
		          {
		        	  sendMessageToMobile("%%0");
		          }
		          else
		          {
		        	  sendMessageToMobile("%%1"); //invalid credentials
		          }
 	           }
	                 
	           else if(received.startsWith("fruit"))
	           {

		          
	           }
	           else if(received.startsWith("flower"))
	           {

	           }
	           else if(received.startsWith("animal"))
	           {

	           }
	           else if(received.startsWith("="))
	           {  
	        	   String userAnswer = received.substring(1);
				   if (answer.equalsIgnoreCase(userAnswer))
				   {
					   
					   sendMessageToMobile("#0"); // correct
				   }
				   else
				   {
					   sendMessageToMobile("#1");  // wrong
				   }
			   }
	           else
	           {
			            
			   }
		
			   } catch (Exception e) {          
			             e.printStackTrace();
			        }
			          
			     }
			    
		     try
		     {
		         // closing resources
		         this.in.close();
		         this.out.close();
		         this.s.close();
		          
		     }catch(IOException e){
		         e.printStackTrace();
		     }
	 }
	
	public void sendMessageToMobile(final String str) {

		new Thread(new Runnable() {

		         @Override
		         public void run() {
		        	 System.out.println("attempting to send message to mobile");
		         try {       
		         PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "utf-8"), true);
		                
		           if (!str.isEmpty()){
		              out.println(str);
		              out.flush();
		              System.out.println("sent to mobile: "+ str);  // see log file
		           }                          
		          }
		          catch (IOException e) {   
		        	  System.out.println("Where is my program crashing: sending message to mobile");
		         }
		 
		        }
		     }).start();
		 }
		 
}
