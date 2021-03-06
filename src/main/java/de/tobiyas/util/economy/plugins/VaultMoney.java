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

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultMoney extends AbstractMoneyPlugin {
	
	private boolean isActive;
	private Economy vaultEconomy;
	
	public VaultMoney(){
		isActive = initVault();
	}

	@Override
	public double getMoneyOfPlayer(OfflinePlayer player) {
		if(!isActive) return 0; 
		return vaultEconomy.getBalance(player);
	}

	@Override
	public boolean addMoney(OfflinePlayer player, double amount) {
		if(!isActive) return false;
		if(!vaultEconomy.hasAccount(player)) return false;
		vaultEconomy.depositPlayer(player, amount);
		return true;
	}

	@Override
	public boolean transferMoney(OfflinePlayer from, OfflinePlayer to, double amount) {
		if(!isActive) return false; 
		if(!vaultEconomy.hasAccount(from)) return false;
		if(!vaultEconomy.hasAccount(to)) return false;
		
		if(!vaultEconomy.has(from, amount)) return false;
		
		vaultEconomy.withdrawPlayer(from, amount);
		vaultEconomy.depositPlayer(to, amount);
		return true;
	}

	@Override
	public boolean removeMoney(OfflinePlayer player, double amount) {
		if(!isActive) return false;
		
		vaultEconomy.withdrawPlayer(player, amount);
		return true;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	private boolean initVault(){
		try{
	        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            vaultEconomy = economyProvider.getProvider();
	        }
	
	        return (vaultEconomy != null);
		}catch(NoClassDefFoundError e){
			return false;
		}
  }

	@Override
	public String getName() {
		return "Vault";
	}

	@Override
	public void createBankAccount(String name, OfflinePlayer owner) {
		vaultEconomy.createBank(name, owner);
	}

	@Override
	public void removeBankAccount(String name) {
		vaultEconomy.deleteBank(name);
	}

	@Override
	public boolean addToBankAccount(String name, double amount) {
		if(!isActive) return false;
		
		vaultEconomy.bankDeposit(name, amount);
		return true;
	}

	@Override
	public boolean withdrawFromBankAccount(String name, double amount) {
		if(getBankBalance(name) < amount) return false;
		
		vaultEconomy.bankWithdraw(name, amount);
		return true;
	}

	@Override
	public double getBankBalance(String bankName) {
		return vaultEconomy.bankBalance(bankName).balance;
	}

	@Override
	public boolean hasBankSupport() {
		return vaultEconomy.hasBankSupport();
	}

}
