/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
		
		Set<String> names = config.getChildren("accounts");
		
		for(String name : names)
			accounts.add(new BankAccount(name, config.getDouble("accounts." + name)));
		
		return accounts;
	}
	
}
