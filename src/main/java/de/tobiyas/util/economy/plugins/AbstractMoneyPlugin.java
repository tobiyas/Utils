package de.tobiyas.util.economy.plugins;

import org.bukkit.OfflinePlayer;

import de.tobiyas.util.player.PlayerUtils;


public abstract class AbstractMoneyPlugin implements MoneyPlugin {


	@Override
	public double getMoneyOfPlayer(String playerName) {
		return getMoneyOfPlayer(PlayerUtils.getOfflinePlayer(playerName));
	}

	@Override
	public boolean addMoney(String playerName, double amount) {
		return addMoney(PlayerUtils.getOfflinePlayer(playerName), amount);
	}

	@Override
	public boolean transferMoney(String from, String to, double amount) {
		OfflinePlayer fromPlayer = PlayerUtils.getOfflinePlayer(from);
		OfflinePlayer toPlayer = PlayerUtils.getOfflinePlayer(to);
		
		if(fromPlayer == null || toPlayer == null) return false;
		return transferMoney(fromPlayer, toPlayer, amount);
	}

	@Override
	public boolean removeMoney(String playerName, double amount) {
		return removeMoney(PlayerUtils.getOfflinePlayer(playerName), amount);
	}

	@Override
	public void createBankAccount(String name, String owner) {
		OfflinePlayer ownerPlayer = PlayerUtils.getOfflinePlayer(owner);
		createBankAccount(name, ownerPlayer);
	}


}
