package savable;

import java.io.Serializable;


public enum Settings implements Serializable {
	/**
	 * true or false for autosave
	 */
	AUTOSAVE( "true"), 
	/**
	 * How many places to automaticly round to, -1 for off
	 */
	AUTOROUND("-1");
	private String value;
	
	private Settings(String value){
		this.value = value;
	}
	public void set(String val){
		value = val;
	}
	public String get(){
		return value;
	}
	
}
