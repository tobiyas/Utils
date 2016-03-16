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
package de.tobiyas.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class SQL {

	private static JavaPlugin plugin;
	
	
	/**
	 * The Plugin to use.
	 * 
	 * @param plugin to use
	 */
	public static void init(JavaPlugin plugin){
		SQL.plugin = plugin;
	}
	
	
	/**
	 * Creates a SQL Connection.
	 * <br>
	 * <br>WARNING: IT HAS TO BE CLOSED MANUALLY!!!
	 * 
	 * @return the connection
	 * 
	 * @throws SQLException on no connection can be established
	 */
	public static Connection getSQLConnection(SQLProperties sqlProperties) throws SQLException{
		String url = "jdbc:mysql://"
				+ sqlProperties.serverName + ":"
				+ sqlProperties.serverPort + "/"
				+ sqlProperties.serverDB;
		
		return DriverManager.getConnection( url, sqlProperties.userName, sqlProperties.password );
	}
	
	
	/**
	 * Tries to create the default table with only a Column for the PlayerName.
	 * 
	 * @param sqlProperties the Properties of the Connection
	 * @param dbTable the Table to check
	 * @param sqlCommandIfNotExist the SQL command to execute if not existent.
	 */
	public static void tryCreateDBIfNotExist(SQLProperties sqlProperties, String dbTable, String sqlCommand){
		
		Connection connection = null;
		Statement statement = null;
		try{
			connection = getSQLConnection(sqlProperties);
			statement = connection.createStatement();
			
			statement.execute(sqlCommand);
			statement.close();
			
		}catch(Exception exp){
			plugin.getLogger().log(Level.WARNING, 
					"Could NOT create Default Table! Please check your settings! Message: " 
					+ exp.getLocalizedMessage());
		}finally{
			try{
				if(connection != null)
					connection.close();
				
				if(statement != null)
					statement.close();
			}catch(Exception exp){}
		}
	}


	/**
	 * This opens a connection to the SQL DB.
	 * <br>If the connection fails, false is returned.
	 * @return
	 */
	public static boolean checkDBIsReachable(SQLProperties sqlProperties) {
		Connection connection = null;
		try{
			connection = getSQLConnection(sqlProperties);
			return true;
		}catch(Exception exp){
			return false;
		}finally{
			try{
				if(connection != null) connection.close();
			}catch(Exception exp){ return false; }
		}
	}
	
	
	/**
	 * The Properties of a SQL connection
	 * 
	 * @author Tobiyas
	 */
	public static class SQLProperties{
		/**
		 * Connection name of the Server (aka URL).
		 */
		public String serverName;
		/**
		 * The Port to the DB
		 */
		public String serverPort;
		/**
		 * The DB To connect to.
		 */
		public String serverDB;
		
		//user Data
		/**
		 * The username of the DB
		 */
		public String userName;
		/**
		 * The password of the DB
		 */
		public String password;
	}
}
