package service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import model.Data;

@Service
public class DataServiceImpl implements DataService {
	@Autowired
	private ConnectionFactory connectionFactory;
	static{
		System.out.println("loading dataService bean");
	}
	
	public void saveData(Data data) {
		// TODO Auto-generated method stub
		if(data==null) return;
		MongoClient client = connectionFactory.getClient();
		DB db = client.getDB("test");//replace with your own db
		DBCollection table = db.getCollection("test");//replace with your own collection
		BasicDBObject document = new BasicDBObject();
		document.put("content",data.getContent());
		table.insert(document);
		
	}

	public int updateData(Data data) {
		// TODO Auto-gereturn null;
		if(data == null) return -1;
		MongoClient client = connectionFactory.getClient();
		DB db = client.getDB("test");//replace with your own db
		DBCollection table = db.getCollection("test");//replace with your own collection
		BasicDBObject query = new BasicDBObject();
		query.put("_id",new ObjectId(data.getId()));
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("content", data.getContent());
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		WriteResult rs = table.update(query, updateObj);
		return rs.getN();
	}

	public int deleteDataById(String id) {
		// TODO Auto-generated method stub
		if(id==null) return -1;
		MongoClient client = connectionFactory.getClient();
		DB db = client.getDB("test");//replace with your own db
		DBCollection table = db.getCollection("test");//replace with your own collection
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", new ObjectId(id));
		WriteResult rs = table.remove(searchQuery);	
		return rs.getN();
	}

	public List<Data> getAllData() {
		// TODO Auto-generated method stub
		List<Data> res = new ArrayList<Data>();
		MongoClient client = connectionFactory.getClient();
		DB db = client.getDB("test");//replace with your own db
		DBCollection table = db.getCollection("test");//replace with your own collection
		DBCursor cursor = table.find();
		while(cursor.hasNext()) {
			DBObject entry = cursor.next();
		    Data data = new Data(entry.get("_id").toString(),entry.get("content").toString());
		    res.add(data);
		}
		return res;
	}

	

}
