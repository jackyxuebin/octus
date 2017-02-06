package service;

import java.net.UnknownHostException;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import com.mongodb.MongoClient;

@Repository
public class ConnectionFactory {
	private MongoClient client = null;
		
	public MongoClient getClient(){
		return this.client;
	}
	
	@PostConstruct
	public void init(){
		try {
			 client = new MongoClient("localhost");//replace with your own host
			 System.out.println("connected to localhost mongod");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
