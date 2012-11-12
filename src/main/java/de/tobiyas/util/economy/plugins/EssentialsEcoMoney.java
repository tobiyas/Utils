package de.tobiyas.util.economy.plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class EssentialsEcoMoney implements MoneyPlugin {

	boolean isActive;
	
	public EssentialsEcoMoney(){
		isActive = initEssentials();
	}
	
	@Override
	public double getMoneyOfPlayer(Player player) {
		try {
			return Economy.getMoney(player.getName());
		} catch (UserDoesNotExistException e) {
			return Double.MIN_VALUE;
		}
	}

	@Override
	public boolean addMoney(Player player, double amount) {
		try {
			Economy.add(player.getName(), amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean transferMoney(Player from, Player to, double amount) {
		if(getMoneyOfPlayer(from) < amount) return false;
		if(getMoneyOfPlayer(to) != Double.MIN_VALUE) return false;
		try {
			if(!Economy.hasEnough(from.getName(), amount)) return false;
		} catch (UserDoesNotExistException e) {
			return false;
		}
		
		removeMoney(from, amount);
		addMoney(to, amount);
		
		return true;
	}

	@Override
	public double removeMoney(Player player, double amount) {
		if(!Economy.playerExists(player.getName())) return Double.MIN_VALUE;
		try {
			if(!Economy.hasEnough(player.getName(), amount)) return Double.MAX_VALUE;
		} catch (UserDoesNotExistException e) {
			return Double.MIN_VALUE;
		}
		
		double retValue;
		try {
			Economy.subtract(player.getName(), amount);
			retValue = Economy.getMoney(player.getName());
		} catch (Exception e) {
			return Double.MIN_VALUE;
		}
		return retValue;		
	}

	@Override
	public boolean isActive() {
		return isActive;
	}
	
	private boolean initEssentials(){
		try{
			Essentials tempPlugin = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
			if(tempPlugin == null) return false;
			if(!tempPlugin.isEnabled()) return false;
		}catch(NoClassDefFoundError e){
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "EssentialsECO";
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
