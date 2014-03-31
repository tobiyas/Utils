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

import org.bukkit.Bukkit;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EssentialsEcoMoney implements MoneyPlugin {

	boolean isActive;
	
	public EssentialsEcoMoney(){
		isActive = initEssentials();
	}
	
	@Override
	public double getMoneyOfPlayer(String playerName) {
		try {
			return Economy.getMoney(playerName);
		} catch (UserDoesNotExistException e) {
			return Double.MIN_VALUE;
		}
	}

	@Override
	public boolean addMoney(String player, double amount) {
		try {
			Economy.add(player, amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean transferMoney(String from, String to, double amount) {
		if(getMoneyOfPlayer(from) < amount) return false;
		if(getMoneyOfPlayer(to) != Double.MIN_VALUE) return false;
		try {
			if(!Economy.hasEnough(from, amount)) return false;
		} catch (UserDoesNotExistException e) {
			return false;
		}
		
		removeMoney(from, amount);
		addMoney(to, amount);
		
		return true;
	}

	@Override
	public boolean removeMoney(String player, double amount) {
		if(!Economy.playerExists(player)) return false;
		try {
			if(!Economy.hasEnough(player, amount)) return false;
		} catch (UserDoesNotExistException e) {
			return false;
		}
		
		try {
			Economy.subtract(player, amount);
		} catch (Exception e) {
			return false;
		}
		
		return true;		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	private boolean initEssentials(){
		try{
			Essentials tempPlugin = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
			if(tempPlugin == null) return false;
			if(!tempPlugin.isEnabled()) return false;
		}catch(NoClassDefFoundError e){
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "EssentialsECO";
	}

	@Override
	public void createBankAccount(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBankAccount(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addToBankAccount(String name, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean withdrawFromBankAccount(String name, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBankBalance(String bankName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

}
