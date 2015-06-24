package de.tobiyas.util.collections;

public class Bi <T,S> {

	private T key;
	
	private S value;
	
	
	public Bi(T key, S value) {
		this.key = key;
		this.value = value;
	}
	
	
	public T getKey() {
		return key;
	}
	
	
	public S getValue() {
		return value;
	}
	
	
	public void setKey(T key) {
		this.key = key;
	}
	
	
	public void setValue(S value) {
		this.value = value;
	}

}
