package service;

import static org.junit.Assert.*;
import model.Data;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceImplTest {	
	
	@Mock
	ConnectionFactory connectionFactory;
	
	@InjectMocks
	DataServiceImpl dataServiceImpl;

	@Test
	public void testSaveDataValid(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table= Mockito.mock(DBCollection.class);
		BasicDBObject document = new BasicDBObject();
		Data data = new Data("1","content");
		document.put("content",data.getContent());
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);		
		//testing		
		dataServiceImpl.saveData(data);
		Mockito.verify(table,Mockito.times(1)).insert(document);
	}
	
	@Test
	public void testSaveDataInvalid(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table= Mockito.mock(DBCollection.class);
		BasicDBObject document = new BasicDBObject();
		Data data = null;
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);		
		//testing		
		dataServiceImpl.saveData(data);
		Mockito.verify(table,Mockito.times(0)).insert(document);
	}
	
	@Test
	public void testUpdateDataValid(){
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		WriteResult res = Mockito.mock(WriteResult.class);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);	
		Data data = new Data("111111111111111111111111","content");
		BasicDBObject query = new BasicDBObject();
		query.put("_id",new ObjectId(data.getId()));
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("content", data.getContent());
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		Mockito.when(table.update(query,updateObj)).thenReturn(res);
		Mockito.when(res.getN()).thenReturn(1);		
		//testing
		int returnCode = dataServiceImpl.updateData(data);
		Mockito.verify(table,Mockito.times(1)).update(query,updateObj);
		assertEquals(returnCode, 1);
	}
	
	@Test
	public void testUpdateDataInvalid(){
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		WriteResult res = Mockito.mock(WriteResult.class);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);	
		Data data = null;
		BasicDBObject query = new BasicDBObject();		
		//testing
		int returnCode = dataServiceImpl.updateData(data);
		Mockito.verify(table,Mockito.times(0)).update(query,new BasicDBObject());
		assertEquals(returnCode, -1);
	}
	
	@Test
	public void testGetAllDataNoData(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		DBCursor cursor = Mockito.mock(DBCursor.class);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(table.find()).thenReturn(cursor);
		Mockito.when(cursor.hasNext()).thenReturn(false);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);	
		//testing
		dataServiceImpl.getAllData();
		Mockito.verify(table,Mockito.times(1)).find();
		Mockito.verify(cursor,Mockito.times(0)).next();
	}
	
	@Test
	public void testGetAllDataHasData(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		DBCursor cursor = Mockito.mock(DBCursor.class);
		DBObject entry = Mockito.mock(DBObject.class);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		Mockito.when(table.find()).thenReturn(cursor);
		Mockito.when(cursor.hasNext()).thenReturn(true).thenReturn(false);		
		Mockito.when(cursor.next()).thenReturn(entry);
		Mockito.when(entry.get("_id")).thenReturn("111111111111111111111111");
		Mockito.when(entry.get("content")).thenReturn("content");
		Mockito.when(connectionFactory.getClient()).thenReturn(client);	
		//testing
		dataServiceImpl.getAllData();
		Mockito.verify(table,Mockito.times(1)).find();
		Mockito.verify(cursor,Mockito.atLeast(1)).next();
	}
	
	@Test
	public void testDeleteDataByIdValid(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		WriteResult res = Mockito.mock(WriteResult.class);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", new ObjectId("111111111111111111111111"));
		Mockito.when(table.remove(searchQuery)).thenReturn(res);
		Mockito.when(res.getN()).thenReturn(1);
		//testing
		int returnCode =dataServiceImpl.deleteDataById("111111111111111111111111");
		Mockito.verify(res,Mockito.times(1)).getN();
		assertEquals(1,returnCode);
	}
	
	@Test
	public void testDeleteDataByIdInvalid(){
		//setting up mock behaviour
		MongoClient client = Mockito.mock(MongoClient.class);
		DB db = Mockito.mock(DB.class);
		DBCollection table = Mockito.mock(DBCollection.class);
		WriteResult res = Mockito.mock(WriteResult.class);
		Mockito.when(connectionFactory.getClient()).thenReturn(client);
		Mockito.when(client.getDB(Mockito.anyString())).thenReturn(db);
		Mockito.when(db.getCollection(Mockito.anyString())).thenReturn(table);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", new ObjectId("111111111111111111111111"));
		Mockito.when(table.remove(searchQuery)).thenReturn(res);
		Mockito.when(res.getN()).thenReturn(1);
		//testing
		int returnCode =dataServiceImpl.deleteDataById(null);
		Mockito.verify(connectionFactory,Mockito.times(0)).getClient();
		assertEquals(-1,returnCode);
	}
}
