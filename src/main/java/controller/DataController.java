package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import model.Data;
import service.DataService;


@Controller
public class DataController {
	@Autowired 
	private DataService dataService;
	static{
		System.out.println("initializing controller");
	}
	@RequestMapping(value="/data", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> saveData(@RequestBody Data data){
		System.out.println("Creating Data "+data.getContent());
		dataService.saveData(data);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/data", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Data>> listAllData(){
		System.out.println("retrieving all data");
		List<Data> dataList = dataService.getAllData();
		if(dataList.isEmpty()){
			return new ResponseEntity<List<Data>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Data>>(dataList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/data/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Data> updateData(@PathVariable("id") String id, @RequestBody Data data){
		System.out.println("Updating Data "+id);
		dataService.updateData(data);
		return new ResponseEntity<Data>(data, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Data> deleteData(@PathVariable("id") String id){
		System.out.println("Deleting data "+id);
		dataService.deleteDataById(id);
		return new ResponseEntity<Data>(HttpStatus.NO_CONTENT);
	}
}
