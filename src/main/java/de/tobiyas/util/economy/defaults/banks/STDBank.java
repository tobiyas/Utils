package de.tobiyas.util.economy.defaults.banks;

import java.io.File;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;


public class STDBank {
	
	private List<BankAccount> accounts;
	
	public STDBank(JavaPlugin plugin){
		accounts = BankStore.loadBanks(plugin.getDataFolder().toString() + File.separator + "accounts.yml");
	}
	
	public void saveBanks(){
		BankStore.saveBanks(accounts);
	}
	
	
	public void createBankAccount(String name){
		BankAccount account = findAccount(name);
		if(account != null) return;
		
		accounts.add(new BankAccount(name));
	}
		
	public void removeBankAccount(String name){
		BankAccount account = findAccount(name);
		if(account != null) return;
		
		accounts.remove(account);
	}
		
	public void addToBankAccount(String name, double amount){
		BankAccount account = findAccount(name);
		if(account == null) return;
		
		account.increaseBalance(amount);
	}
		
	public boolean withdrawFromBankAccount(String name, double amount){
		BankAccount account = findAccount(name);
		if(account == null) return false;
		
		if(account.getBalance() < amount) return false;
		
		account.reduceBalance(amount);
		return true;
	}
		
	public double getBankBalance(String bankName){
		BankAccount account = findAccount(bankName);
		if(account == null) return 0;
		
		return account.getBalance();
	}
	
	private BankAccount findAccount(String name){
		for(BankAccount account : accounts)
			if(account.getName().equalsIgnoreCase(name))
				return account;
		
		return null;
	}
	
}
