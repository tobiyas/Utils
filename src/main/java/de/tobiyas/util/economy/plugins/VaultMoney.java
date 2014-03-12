package de.tobiyas.util.economy.plugins;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultMoney implements MoneyPlugin {
	
	private boolean isActive;
	private Economy vaultEconomy;
	
	public VaultMoney(){
		isActive = initVault();
	}

	@Override
	public double getMoneyOfPlayer(String playerName) {
		if(!isActive) return 0; 
		return vaultEconomy.getBalance(playerName);
	}

	@Override
	public boolean addMoney(String playerName, double amount) {
		if(!isActive) return false;
		if(!vaultEconomy.hasAccount(playerName)) return false;
		vaultEconomy.depositPlayer(playerName, amount);
		return true;
	}

	@Override
	public boolean transferMoney(String from, String to, double amount) {
		if(!isActive) return false; 
		if(!vaultEconomy.hasAccount(from)) return false;
		if(!vaultEconomy.hasAccount(to)) return false;
		
		if(!vaultEconomy.has(from, amount)) return false;
		
		vaultEconomy.withdrawPlayer(from, amount);
		vaultEconomy.depositPlayer(to, amount);
		return true;
	}

	@Override
	public boolean removeMoney(String playerName, double amount) {
		if(!isActive) return false;
		
		vaultEconomy.withdrawPlayer(playerName, amount);
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
	public void createBankAccount(String name) {
		vaultEconomy.createBank(name, null);
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
