package de.tobiyas.util.economy.plugins;


public interface MoneyPlugin {
	
	public boolean isActive();
	
	//Player Stuff
	public double getMoneyOfPlayer(String playerName);
	
	public boolean addMoney(String playerName, double amount);
	
	public boolean transferMoney(String from, String to, double amount);
	
	public boolean removeMoney(String playerName, double amount);
	
	//Bank stuff
	public void createBankAccount(String name);
	
	public void removeBankAccount(String name);
	
	public boolean addToBankAccount(String name, double amount);
	
	public boolean withdrawFromBankAccount(String name, double amount);
	
	public double getBankBalance(String bankName);
	
	public boolean hasBankSupport();
	
	public String getName();
}
