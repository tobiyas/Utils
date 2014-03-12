package de.tobiyas.util.economy;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.economy.plugins.EssentialsEcoMoney;
import de.tobiyas.util.economy.plugins.IconomyMoney;
import de.tobiyas.util.economy.plugins.MoneyPlugin;
import de.tobiyas.util.economy.plugins.VaultMoney;

public class MoneyManager implements MoneyPlugin{
	
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
	
	
	public boolean transferPlayerToBank(String player, String bankName, double amount){
		if(!isActive) return false;
		
		double playerAmount = moneyPlugin.getMoneyOfPlayer(player);
		if(playerAmount < amount) return false;
		
		moneyPlugin.removeMoney(player, amount);
		moneyPlugin.addToBankAccount(bankName, amount);
		return true;
	}
	
	
	public boolean transferBankToPlayer(String player, String bankName, double amount){
		if(!isActive) return false;
		
		double bankAmount = moneyPlugin.getBankBalance(bankName);
		if(bankAmount < amount) return false;
		
		moneyPlugin.withdrawFromBankAccount(bankName, amount);
		moneyPlugin.addMoney(player, amount);
		return true;
	}
	
	
	public String getActiveEcoName(){
		if(!isActive) return "NONE";
		return moneyPlugin.getName();
	}


	@Override
	public void createBankAccount(String clanName){
		if(!isActive) return;
		moneyPlugin.createBankAccount(clanName);
	}

	@Override
	public double getBankBalance(String clanName){
		if(!isActive) return 0;
		return moneyPlugin.getBankBalance(clanName);
	}

	@Override
	public void removeBankAccount(String clanName){
		if(!isActive) return;
		moneyPlugin.removeBankAccount(clanName);
	}
	
	@Override
	public boolean addToBankAccount(String clanName, double amount){
		if(!isActive) return false;
		
		return moneyPlugin.addToBankAccount(clanName, amount);
	}
	
	
	@Override
	public boolean withdrawFromBankAccount(String clanName, double amount){
		if(!isActive) return false;
		
		return moneyPlugin.withdrawFromBankAccount(clanName, amount);
	}

	/**
	 * Gives the Player the money passed.
	 * 
	 * @param player to give
	 * @param amount to give
	 * 
	 * @return true if worked, false if not
	 */
	@Override
	public boolean addMoney(String player, double amount) {
		if(!isActive) return false;
		
		
		if(moneyPlugin.getMoneyOfPlayer(player) == Double.MIN_VALUE) return false;
		return moneyPlugin.addMoney(player, amount);
	}

	/**
	 * Removes the Player the money passed.
	 * 
	 * @param player to remove
	 * @param amount to remove
	 * 
	 * @return true if worked, false if not
	 */
	@Override
	public boolean removeMoney(String player, double amount) {
		if(!isActive) return false;
		
		if(moneyPlugin.getMoneyOfPlayer(player) < amount) return false;

		if(moneyPlugin.getMoneyOfPlayer(player) == Double.MIN_VALUE) return false;
		moneyPlugin.removeMoney(player, amount);
		return true;
	}

	/**
	 * Gets the money the player has.
	 * 
	 * @param player to get from
	 * 
	 * @return the money of the player.
	 */
	@Override
	public double getMoneyOfPlayer(String player) {
		if(!isActive) return 0;
		
		return moneyPlugin.getMoneyOfPlayer(player);
	}

	@Override
	public boolean isActive() {
		return moneyPlugin.isActive();
	}

	@Override
	public boolean transferMoney(String from, String to, double amount) {
		if(!isActive) return false;
		
		return moneyPlugin.transferMoney(from, to, amount);
	}

	@Override
	public boolean hasBankSupport() {
		if(!isActive) return false;
		
		return moneyPlugin.hasBankSupport();
	}

	@Override
	public String getName() {
		if(!isActive) return "NONE";
		
		return moneyPlugin.getName();
	}

}
