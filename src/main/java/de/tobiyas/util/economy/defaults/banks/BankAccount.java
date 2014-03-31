/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
