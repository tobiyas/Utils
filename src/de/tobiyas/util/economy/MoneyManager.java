package de.tobiyas.util.economy;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.economy.plugins.*;

public class MoneyManager{
	
	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	
	private MoneyPlugin moneyPlugin;
	private boolean isActive;
	
	public MoneyManager(JavaPlugin plugin){
		this.plugin = plugin;
		isActive = initMoneyPlugin();
		
		if(!isActive){
			plugin.getLogger().log(Level.WARNING, "No Economy System found.");
		}
	}
	
	private boolean initMoneyPlugin(){
		MoneyPlugin tempPlugin;
		
		tempPlugin = new VaultMoney();
		if(tempPlugin.isActive()){
			moneyPlugin = tempPlugin;
			return true;
		}
		
		tempPlugin = new IconomyMoney();
		if(tempPlugin.isActive()){
			moneyPlugin = tempPlugin;
			return true;
		}
		
		try{
			tempPlugin = new EssentialsEcoMoney();
			if(tempPlugin.isActive()){
				moneyPlugin = tempPlugin;
				return true;
			}
		}catch(NoClassDefFoundError e)
		{}
		
		return false;
	}
	
	public boolean transferPlayerToBank(Player player, String bankName, double amount){
		if(!isActive) return false;
		
		double playerAmount = moneyPlugin.getMoneyOfPlayer(player);
		if(playerAmount < amount) return false;
		
		moneyPlugin.addToBankAccount(bankName, amount);
		return true;
	}
	
	public boolean transferBankToPlayer(Player player, String bankName, double amount){
		if(!isActive) return false;
		
		double bankAmount = moneyPlugin.getBankBalance(bankName);
		if(bankAmount < amount) return false;
		
		moneyPlugin.addMoney(player, amount);
		return true;
	}
	
	public String getActiveEcoName(){
		if(!isActive) return "NONE";
		return moneyPlugin.getName();
	}

	public void createBank(String clanName){
		if(!isActive) return;
		moneyPlugin.createBankAccount(clanName);
	}
	
	public double getBankBalance(String clanName){
		if(!isActive) return 0;
		return moneyPlugin.getBankBalance(clanName);
	}
	
	public void deleteBank(String clanName){
		if(!isActive) return;
		moneyPlugin.removeBankAccount(clanName);
	}
	
	public boolean transferToBank(String clanName, double amount){
		if(!isActive) return false;
		moneyPlugin.addToBankAccount(clanName, amount);
		return true;
	}
	
	public boolean withdrawFromBank(String clanName, double amount){
		if(!isActive) return false;
		return moneyPlugin.withdrawFromBankAccount(clanName, amount);
	}

	public boolean givePlayer(Player player, double amount) {
		if(!isActive) return false;
		if(moneyPlugin.getMoneyOfPlayer(player) == Double.MIN_VALUE) return false;
		return moneyPlugin.addMoney(player, amount);
	}

}
