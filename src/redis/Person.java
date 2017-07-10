package redis;

import java.io.Serializable;

public class Person implements Serializable {  
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;  
	private String name;  
	  
	public Person(int id, String name) {  
	  this.id = id;  
	  this.name = name;  
	}  
	  
	public int getId() {  
	  return id;  
	}  
	  
	public String getName() {  
	  return name;  
	}  
  
}  
