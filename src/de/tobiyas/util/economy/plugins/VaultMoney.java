package de.tobiyas.util.economy.plugins;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultMoney implements MoneyPlugin {
	
	private boolean isActive;
	private Economy vaultEconomy;
	
	public VaultMoney(){
		isActive = initVault();
	}

	@Override
	public double getMoneyOfPlayer(Player player) {
		if(!isActive) return 0; 
		return vaultEconomy.getBalance(player.getName());
	}

	@Override
	public boolean addMoney(Player player, double amount) {
		if(!isActive) return false;
		if(!vaultEconomy.hasAccount(player.getName())) return false;
		vaultEconomy.depositPlayer(player.getName(), amount);
		return true;
	}

	@Override
	public boolean transferMoney(Player from, Player to, double amount) {
		if(!isActive) return false; 
		if(!vaultEconomy.hasAccount(from.getName())) return false;
		if(!vaultEconomy.hasAccount(to.getName())) return false;
		
		if(!vaultEconomy.has(from.getName(), amount)) return false;
		
		vaultEconomy.withdrawPlayer(from.getName(), amount);
		vaultEconomy.depositPlayer(to.getName(), amount);
		return true;
	}

	@Override
	public double removeMoney(Player player, double amount) {
		if(!isActive) return 0;
		return vaultEconomy.withdrawPlayer(player.getName(), amount).balance;
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
	public void addToBankAccount(String name, double amount) {
		vaultEconomy.bankDeposit(name, amount);
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
