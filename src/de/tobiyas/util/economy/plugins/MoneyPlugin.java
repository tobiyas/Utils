package de.tobiyas.util.economy.plugins;

import org.bukkit.entity.Player;

public interface MoneyPlugin {
	
	public boolean isActive();
	
	//Player Stuff
	public double getMoneyOfPlayer(Player player);
	
	public boolean addMoney(Player player, double amount);
	
	public boolean transferMoney(Player from, Player to, double amount);
	
	public double removeMoney(Player player, double amount);
	
	//Bank stuff
	public void createBankAccount(String name);
	
	public void removeBankAccount(String name);
	
	public void addToBankAccount(String name, double amount);
	
	public boolean withdrawFromBankAccount(String name, double amount);
	
	public double getBankBalance(String bankName);
	
	public boolean hasBankSupport();
	
	public String getName();
}
