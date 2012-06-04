package de.tobiyas.util.economy.plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.iCo6.iConomy;
import com.iCo6.system.Accounts;

public class IconomyMoney implements MoneyPlugin {
	
	private boolean isActive;
	private Accounts accounts;
	
	public IconomyMoney(){
		isActive = initIcon();
	}

	@Override
	public double getMoneyOfPlayer(Player player) {
		if(!isActive) return 0;
		
		if(!accounts.exists(player.getName())) return 0;
		return accounts.get(player.getName()).getHoldings().getBalance();
		
	}

	@Override
	public boolean addMoney(Player player, double amount) {
		if(!isActive) return false;

		if(!accounts.exists(player.getName())) return false;
		
		accounts.get(player.getName()).getHoldings().add(amount);
		return true;
	}

	@Override
	public boolean transferMoney(Player from, Player to, double amount) {
		if(!isActive) return false;
		
		if(!accounts.exists(from.getName())) return false;
		if(!accounts.exists(to.getName())) return false;
		
		if(!accounts.get(from.getName()).getHoldings().hasEnough(amount)) return false;
		
		accounts.get(from.getName()).getHoldings().subtract(amount);
		accounts.get(to.getName()).getHoldings().add(amount);
		
		return true;
	}

	@Override
	public double removeMoney(Player player, double amount) {
		if(!isActive) return 0;
		return 0;
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
	public void addToBankAccount(String name, double amount) {
		// TODO Auto-generated method stub
		
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
