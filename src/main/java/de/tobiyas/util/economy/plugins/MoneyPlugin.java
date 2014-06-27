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
package de.tobiyas.util.economy.plugins;

import org.bukkit.OfflinePlayer;


public interface MoneyPlugin {
	
	public boolean isActive();
	
	//Player Stuff
	public double getMoneyOfPlayer(String playerName);
	
	public boolean addMoney(String playerName, double amount);
	
	public boolean transferMoney(String from, String to, double amount);
	
	public boolean removeMoney(String playerName, double amount);
	

	//Player Stuff
	public double getMoneyOfPlayer(OfflinePlayer player);
	
	public boolean addMoney(OfflinePlayer playerName, double amount);
	
	public boolean transferMoney(OfflinePlayer from, OfflinePlayer to, double amount);
	
	public boolean removeMoney(OfflinePlayer playerName, double amount);
	
	
	//Bank stuff
	public void createBankAccount(String name, String owner);
	
	public void removeBankAccount(String name);
	
	public boolean addToBankAccount(String name, double amount);
	
	public boolean withdrawFromBankAccount(String name, double amount);
	
	public double getBankBalance(String bankName);
	
	public boolean hasBankSupport();
	
	public String getName();
	
	//Bank stuff / Player
	public void createBankAccount(String name, OfflinePlayer owner);
	
}
