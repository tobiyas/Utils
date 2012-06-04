package de.tobiyas.util.economy.defaults.banks;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.tobiyas.util.config.YAMLConfigExtended;

public class BankStore {
	
	private static String path;

	public static boolean saveBanks(List<BankAccount> accounts){
		YAMLConfigExtended config = new YAMLConfigExtended(path);
		config.createSection("accounts");
		
		for(BankAccount account : accounts){
			config.set("accounts." + account.getName(), account.getBalance());
		}
		
		return config.save();
	}
	
	public static List<BankAccount> loadBanks(String path){
		BankStore.path = path;
		List<BankAccount> accounts = new LinkedList<BankAccount>();
		
		YAMLConfigExtended config = new YAMLConfigExtended(path);
		if(!config.load().getValidLoad()) return accounts;
		
		Set<String> names = config.getYAMLChildren("accounts");
		
		for(String name : names)
			accounts.add(new BankAccount(name, config.getDouble("accounts." + name)));
		
		return accounts;
	}
	
}
