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

import com.iCo6.iConomy;
import com.iCo6.system.Accounts;

public class IconomyMoney implements MoneyPlugin {
	
	private boolean isActive;
	private Accounts accounts;
	
	public IconomyMoney(){
		isActive = initIcon();
	}

	@Override
	public double getMoneyOfPlayer(String player) {
		if(!isActive) return 0;
		
		if(!accounts.exists(player)) return 0;
		return accounts.get(player).getHoldings().getBalance();
		
	}

	@Override
	public boolean addMoney(String player, double amount) {
		if(!isActive) return false;

		if(!accounts.exists(player)) return false;
		
		accounts.get(player).getHoldings().add(amount);
		return true;
	}

	@Override
	public boolean transferMoney(String from, String to, double amount) {
		if(!isActive) return false;
		
		if(!accounts.exists(from)) return false;
		if(!accounts.exists(to)) return false;
		
		if(!accounts.get(from).getHoldings().hasEnough(amount)) return false;
		
		accounts.get(from).getHoldings().subtract(amount);
		accounts.get(to).getHoldings().add(amount);
		
		return true;
	}

	@Override
	public boolean removeMoney(String player, double amount) {
		if(!isActive) return false;
		if(getMoneyOfPlayer(player) < amount) return false;
		
		accounts.get(player).getHoldings().subtract(amount);
		return false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	private boolean initIcon(){
		try{
			iConomy tempPlugin = (iConomy) Bukkit.getPluginManager().getPlugin("iConomy");
			if(tempPlugin == null) return false;
			if(!tempPlugin.isEnabled()) return false;
			
			accounts = new Accounts();
			
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "iConomy6";
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
