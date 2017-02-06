package service;

import java.util.List;

import model.Data;

public interface DataService {
	void saveData(Data data);
	int updateData(Data data);
	int deleteDataById(String id);
	List<Data> getAllData();
}
