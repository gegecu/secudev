package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
	private Map<String, String> info;
	private int id;
	
	public Post() {
		info = new HashMap<String, String>();
	}
	
	public String getInfo(String key) {
		return info.get(key);
	}
	
	public Map<String, String> getMap() {
		return info;
	}
	
	public void setInfo(String key, String value) {
		info.put(key, value);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
