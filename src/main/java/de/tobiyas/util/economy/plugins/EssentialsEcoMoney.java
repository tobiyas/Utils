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

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EssentialsEcoMoney extends AbstractMoneyPlugin {

	boolean isActive;
	
	public EssentialsEcoMoney(){
		isActive = initEssentials();
	}
	
	@Override
	public double getMoneyOfPlayer(OfflinePlayer player) {
		try {
			return Economy.getMoneyExact(player.getName()).doubleValue();
		} catch (UserDoesNotExistException e) {
			return Double.MIN_VALUE;
		}
	}

	@Override
	public boolean addMoney(OfflinePlayer player, double amount) {
		if(!Economy.playerExists(player.getName())) return false;
		try {
			Economy.add(player.getName(), new BigDecimal(amount));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean transferMoney(OfflinePlayer from, OfflinePlayer to, double amount) {
		if(getMoneyOfPlayer(from) < amount) return false;
		if(getMoneyOfPlayer(to) != Double.MIN_VALUE) return false;
		try {
			if(!Economy.hasEnough(from.getName(), new BigDecimal(amount))) return false;
		} catch (UserDoesNotExistException e) {
			return false;
		}
		
		removeMoney(from, amount);
		addMoney(to, amount);
		
		return true;
	}

	@Override
	public boolean removeMoney(OfflinePlayer player, double amount) {
		if(!Economy.playerExists(player.getName())) return false;
		try {
			if(!Economy.hasEnough(player.getName(), new BigDecimal(amount))) return false;
		} catch (UserDoesNotExistException e) {
			return false;
		}
		
		try {
			Economy.substract(player.getName(), new BigDecimal(amount));
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
	public void createBankAccount(String name, OfflinePlayer owner) {
	}

	@Override
	public void removeBankAccount(String name) {
	}

	@Override
	public boolean addToBankAccount(String name, double amount) {
		return false;
	}

	@Override
	public boolean withdrawFromBankAccount(String name, double amount) {
		return false;
	}

	@Override
	public double getBankBalance(String bankName) {
		return 0;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

}
