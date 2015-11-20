package de.tobiyas.util.economy.plugins;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.tobiyas.curencies.API.CurrencyAPI;

public class OwnCurrencyPlugin extends AbstractMoneyPlugin {

	private boolean isPresent(){
		return Bukkit.getPluginManager().isPluginEnabled("Currencies");
	}
	
	@Override
	public boolean isActive() {
		return isPresent();
	}

	@Override
	public double getMoneyOfPlayer(OfflinePlayer player) {
		return CurrencyAPI.getDefaultCurrencyOfPlayer(player.getUniqueId());
	}

	@Override
	public boolean addMoney(OfflinePlayer player, double amount) {
		CurrencyAPI.addDefaultCurrencyOfPlayer(player.getUniqueId(), amount);
		return true;
	}

	@Override
	public boolean transferMoney(OfflinePlayer from, OfflinePlayer to, double amount) {
		double money = getMoneyOfPlayer(from);
		if(money < amount) return false;
		
		removeMoney(from, amount);
		addMoney(to, amount);
		return true;
	}

	@Override
	public boolean removeMoney(OfflinePlayer player, double amount) {
		CurrencyAPI.removeDefaultCurrencyOfPlayer(player.getUniqueId(), amount);
		return true;
	}

	@Override
	public void removeBankAccount(String name) {
		//Not supported.
	}

	@Override
	public boolean addToBankAccount(String name, double amount) {
		//Not supported.
		return false;
	}

	@Override
	public boolean withdrawFromBankAccount(String name, double amount) {
		//Not supported.
		return false;
	}

	@Override
	public double getBankBalance(String bankName) {
		//Not supported.
		return 0;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public String getName() {
		return "Currencies";
	}

	@Override
	public void createBankAccount(String name, OfflinePlayer owner) {
		//Not supported.
	}

}
