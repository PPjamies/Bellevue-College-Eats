package bc_eats;

import java.util.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static AmazonDynamoDB aws_dynamodb;
	static AmazonDynamoDB aws_client;
	public static BufferedReader in;
    public static PrintWriter out;
	public static HashMap<Integer,String> users = new HashMap<>();
    
    
	private static void init() throws Exception{
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAUR2SZKZYBH7O26FK","Yd29Y+irfmHlu4s0cut5s1xkRfrfnBMt8n5XBcHw");
		
		aws_dynamodb = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion("us-west-2") 
				.build();
	}
	
	public static void main(String[]args) throws Exception {				
		
		init();
		
		
		/* createBCEatsTable(); table created, do not run this code again */
		
		//testing queries (works)
		//createNewUser(1234567890,"1234"); //query works

		//System.out.println(isRegisteredUser(987654321)); 
		//System.out.println(isRegisteredUser(0));
		//System.out.println(isCorrectPassword(987654321,"123456"));
		
		//updatePassword(987654321, "my_chickens");
		//System.out.println(isCorrectPassword(987654321,"my_chickens"));
		


//		ServerSocket ss = new ServerSocket(8000);      
//	    Socket s; 
//	    
//		while (true)  {     
//				System.out.println("hiiiii");
//	         s = ss.accept(); // Accept the incoming request 
//	         System.out.println("incoming request accepted");
//	         
//	         in = new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));
//	         out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));                     
//	         System.out.println("in and out are made");
//	         
//	         
//	          // Create a new handler object for handling this request. 
//	          Client mtch = new Client(s, in, out); 
//	          System.out.println("Client made");
//	          
//	          // Create a new Thread with this object. 
//	          Thread t = new Thread(mtch); 
//	          System.out.println("Thread was made");        
//	          
//	          // start the thread. 
//	          t.start();        
//	      }
	}
	
/*INITIALIZING TABLE****************INITIALIZING TABLE*********************INITIALIZING TABLE**********************INITIALIZING TABLE**************INITIALIZING TABLE*/
	
	public static void createBCEatsTable() throws Exception {
		try {
			String tableName = "BCEats";
			
			List<KeySchemaElement> keySchema = new ArrayList<>();
			keySchema.add(new KeySchemaElement()
		             .withAttributeName("phoneNumber")
		             .withKeyType(KeyType.HASH)); 


			List<AttributeDefinition> attrDefinitions = new ArrayList<>();
			attrDefinitions.add(new AttributeDefinition()
		                  .withAttributeName("phoneNumber")
		                  .withAttributeType("N"));

		
			CreateTableRequest request = new CreateTableRequest()
		      .withTableName(tableName)
		      .withKeySchema(keySchema)
		      .withAttributeDefinitions(attrDefinitions)
		      .withProvisionedThroughput(new ProvisionedThroughput()
		           .withReadCapacityUnits(30L)
		           .withWriteCapacityUnits(10L));
			
			TableUtils.createTableIfNotExists(aws_dynamodb, request);
			TableUtils.waitUntilActive(aws_dynamodb, tableName);

			DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
			TableDescription tableDescription = aws_dynamodb.describeTable(describeTableRequest).getTable();
			System.out.println("Table Description: " + tableDescription); 	
		}
		catch(AmazonServiceException ase)
		{
			System.out.println("Caught an AmazonServiceException, which means your request made it " 
					+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		}
		catch(AmazonClientException ace)
		{
			System.out.println("Caught an AmazonClientException, which means the client encountered " 
					+ "a serious internal problem while trying to communicate with AWS, "      
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}		
		
	}

	
/*ACCOUNT QUERIES***************ACCOUNT QUERIES***************ACCOUNT QUERIES**************ACCOUNT QUERIES***************ACCOUNT QUERIES**********ACCOUNT QUERIES******/
	
	public static void createNewUser(int phoneNumber, String password) throws Exception {	
		try {
			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
					.withRegion("us-west-2")
					.build();
			DynamoDB dynamodb = new DynamoDB(client);
			Table table = dynamodb.getTable("BCEats");
			 	
		 	Item item1 = new Item()
		 			.withPrimaryKey("phoneNumber",phoneNumber)
		 			.withString("password",password);
		 	
		 	//put item in table
		 	PutItemOutcome outcome1 = table.putItem(item1);			 	
			System.out.println("Result: " + outcome1);	 	
			
			//put item into hash tables
			users.put(phoneNumber, password);
		}
		catch(AmazonServiceException ase)
		{
			System.out.println("Caught an AmazonServiceException, which means your request made it " 
					+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		}
		catch(AmazonClientException ace)
		{
			System.out.println("Caught an AmazonClientException, which means the client encountered " 
					+ "a serious internal problem while trying to communicate with AWS, "      
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}		
		
	}
	
	public static void updatePassword(int userCorrectPhoneNumber, String newPassword) {
		try {
			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
					.withRegion("us-west-2")
					.build();
			DynamoDB dynamodb = new DynamoDB(client);
			Table table = dynamodb.getTable("BCEats");
			
			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("phoneNumber", userCorrectPhoneNumber)
		            .withUpdateExpression("set password = :p")
		            .withValueMap(new ValueMap().withString(":p", newPassword))
		            .withReturnValues(ReturnValue.UPDATED_NEW);

			
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
            
            //update hashmap
            users.put(userCorrectPhoneNumber,newPassword);
		}
		catch (Exception e) {
	            System.err.println("Unable to update items");
	            System.err.println(e.getMessage());
	   }
	}

/*BOOLEANS**************BOOLEANS******************BOOLEANS**************BOOLEANS**************BOOLEANS*****************BOOLEANS*******************BOOLEANS*************/	
	
	public static boolean isRegisteredUser(int phoneNumber)
	{		
		//hashmap contains phone number, user exists
		if(users.containsKey(phoneNumber)) { 
			return true;
		}
		//hashmap does not contain phone number, check db and update cache hashmap
		else
		{		
			try {
				
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
						.withRegion("us-west-2")
						.build();
				DynamoDB dynamodb = new DynamoDB(client);
				Table table = dynamodb.getTable("BCEats");
				 	
				QuerySpec spec = new QuerySpec() 
				   .withKeyConditionExpression("phoneNumber = :nn")  
				   .withValueMap(new ValueMap()
				   .withNumber(":nn", phoneNumber));
				   
				ItemCollection<QueryOutcome> items = table.query(spec);  
				Iterator<Item> iterator = items.iterator(); 
	
				if (iterator.hasNext()) { //if user phone number exists then this data will be stored in hashmap
					Item item = iterator.next();
	                Integer number = item.getInt("phoneNumber");
	                String password = item.getString("password");
	                users.put(number, password);
				}
			}
			catch(AmazonServiceException ase)
			{
				System.out.println("Caught an AmazonServiceException, which means your request made it " 
						+ "to AWS, but was rejected with an error response for some reason.");
				System.out.println("Error Message:    " + ase.getMessage());
				System.out.println("HTTP Status Code: " + ase.getStatusCode());
				System.out.println("AWS Error Code:   " + ase.getErrorCode());
				System.out.println("Error Type:       " + ase.getErrorType());
				System.out.println("Request ID:       " + ase.getRequestId());
			}
			catch(AmazonClientException ace)
			{
				System.out.println("Caught an AmazonClientException, which means the client encountered " 
						+ "a serious internal problem while trying to communicate with AWS, "      
						+ "such as not being able to access the network.");
				System.out.println("Error Message: " + ace.getMessage());
			}
			
			return isRegisteredUser_HASHMAP(phoneNumber); //return what is in the hashmap
		}
	}
	
		private static boolean isRegisteredUser_HASHMAP(int phoneNumber) {
			for(int number: users.keySet())
				if (number == phoneNumber ){
					return true;
				}
			return false;
		}
	
	
	//user is checked in the db and exists, now check corresponding password
	public static boolean isCorrectPassword(int userCorrectPhoneNumber, String userPassword) {
		String correctPassword = users.get(userCorrectPhoneNumber);		
		return userPassword.equalsIgnoreCase(correctPassword);
	}
}		
/**END************END************END**********END************END********END********END******END************END******END***END*************END*********END*******END***/