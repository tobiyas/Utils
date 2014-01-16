package de.tobiyas.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
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
		/*
		String serverName = plugin.interactConfig().getHostAddress();
		String serverPort = plugin.interactConfig().getHostPort();
		String serverDB = plugin.interactConfig().getHostDB();
		
		String userName = plugin.interactConfig().getHostUsername();
		String password = plugin.interactConfig().getHostPassword();
		*/
		
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", sqlProperties.userName);
		connectionProperties.put("password", sqlProperties.password);
		connectionProperties.put("autoReconnect", "false");
		connectionProperties.put("maxReconnects", "0");
		
		return DriverManager.getConnection(
				"jdbc:mysql://"
				+ sqlProperties.serverName + ":"
				+ sqlProperties.serverPort + "/"
				+ sqlProperties.serverDB  ,
				connectionProperties);
	}
	
	
	/**
	 * Tries to create the default table with only a Column for the PlayerName.
	 * 
	 * @param sqlProperties the Properties of the Connection
	 * @param dbTable the Table to check
	 * @param sqlCommandIfNotExist the SQL command to execute if not existent.
	 */
	public static void tryCreateDBIfNotExist(SQLProperties sqlProperties, String dbTable, String sqlCommandIfNotExist){
		
		Connection connection = null;
		Statement statement = null;
		try{
			connection = getSQLConnection(sqlProperties);
			statement = connection.createStatement();
			
			/*String sqlCommand = "CREATE TABLE " 
							+ "IF NOT EXISTS "
							+ dbTable
							+"(" 
							+   "id INT (255) auto_increment, "
							+	"playerName VARCHAR (64) NOT NULL, "
							+	"PRIMARY KEY (id)"
							+")";
			
			statement.execute(sqlCommandIfNotExist);
			*/
			
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
