package de.tobiyas.util.economy.defaults.banks;

public class BankAccount {
	
	private double balance;
	private String name;
	
	public BankAccount(String name){
		this.name = name;
		this.balance = 0;
	}
	
	public BankAccount(String name, double balance){
		this.name = name;
		this.balance = balance;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public double reduceBalance(double amount){
		balance -= amount;
		return balance;
	}
	
	public double increaseBalance(double amount){
		balance += amount;
		return balance;
	}
	
	public void setBalance(double balance){
		this.balance = balance;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean equals(String name){
		return this.name.equals(name);
	}

}
