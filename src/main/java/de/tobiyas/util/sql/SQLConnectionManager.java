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
import java.util.Properties;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.sql.SQL.SQLProperties;

public class SQLConnectionManager {

	private UtilsUsingPlugin plugin;
	
	private String connectionString;
	//private String tablePrefix = "";
	private Connection connection = null;
	
	
	//Connection infos
	private String serverName;
	private String serverPort;
	private String serverDB;
	private String username;
	private String password;
	
	
	// Scale waiting time by this much per failed attempt
	private final double SCALING_FACTOR = 40.0;

	// Minimum wait in nanoseconds (default 500ms)
	private final long MIN_WAIT = 500L * 1000000L;

	// Maximum time to wait between reconnects (default 5 minutes)
	private final long MAX_WAIT = 5L * 60L * 1000L * 1000000L;

	// How long to wait when checking if connection is valid (default 3 seconds)
	private final int VALID_TIMEOUT = 3;

	// When next to try connecting to Database in nanoseconds
	private long nextReconnectTimestamp = 0L;

	// How many connection attempts have failed
	private int reconnectAttempt = 0;

	
	
	public SQLConnectionManager(UtilsUsingPlugin plugin, SQLProperties properties) {
		this.username = properties.userName;
		this.password = properties.password;
		this.serverName = properties.serverName;
		this.serverPort = properties.serverPort;
		this.serverDB = properties.serverDB;
		
		this.plugin = plugin;
	}
	
	/**
	 * Check connection status and re-establish if dead or stale.
	 * 
	 * If the very first immediate attempt fails, further attempts will be made
	 * in progressively larger intervals up to MAX_WAIT intervals.
	 * 
	 * This allows for MySQL to time out idle connections as needed by server
	 * operator, without affecting McMMO, while still providing protection
	 * against a database outage taking down Bukkit's tick processing loop due
	 * to attempting a database connection each time McMMO needs the database.
	 * 
	 * @return the boolean value for whether or not we are connected
	 */
	public boolean checkConnected() {
		boolean isClosed = true;
		boolean isValid = false;
		boolean exists = (connection != null);

		// If we're waiting for server to recover then leave early
		if (nextReconnectTimestamp > 0
				&& nextReconnectTimestamp > System.nanoTime()) {
			return false;
		}

		if (exists) {
			try {
				isClosed = connection.isClosed();
			} catch (SQLException e) {
				isClosed = true;
				e.printStackTrace();
				plugin.getDebugLogger().logStackTrace(e);
			}

			if (!isClosed) {
				try {
					isValid = connection.isValid(VALID_TIMEOUT);
				} catch (SQLException e) {
					// Don't print stack trace because it's valid to lose idle
					// connections to the server and have to restart them.
					isValid = false;
				}
			}
		}

		// Leave if all ok
		if (exists && !isClosed && isValid) {
			// Housekeeping
			nextReconnectTimestamp = 0;
			reconnectAttempt = 0;
			return true;
		}

		// Cleanup after ourselves for GC and MySQL's sake
		if (exists && !isClosed) {
			try {
				connection.close();
			} catch (SQLException ex) {
				// This is a housekeeping exercise, ignore errors
			}
		}

		// Try to connect again
		connect();

		// Leave if connection is good
		try {
			if (connection != null && !connection.isClosed()) {
				// Schedule a database save if we really had an outage
				if (reconnectAttempt > 1) {
					checkConnected();
				}

				nextReconnectTimestamp = 0;
				reconnectAttempt = 0;
				return true;
			}
		} catch (SQLException e) {
			// Failed to check isClosed, so presume connection is bad and
			// attempt later
			e.printStackTrace();
			plugin.getDebugLogger().logStackTrace(e);
		}

		reconnectAttempt++;
		nextReconnectTimestamp = (long) (System.nanoTime() + Math.min(MAX_WAIT,
				(reconnectAttempt * SCALING_FACTOR * MIN_WAIT)));
		return false;
	}

	/**
	 * Attempt to connect to the mySQL database.
	 */
	private void connect() {
		connectionString = "jdbc:mysql://"
				+ serverName + ":"
				+ serverPort + "/"
				+ serverDB;

		try {
			plugin.getDebugLogger().log("Attempting connection to MySQL...");

			// Force driver to load if not yet loaded
			Class.forName("com.mysql.jdbc.Driver");
			Properties connectionProperties = new Properties();
			connectionProperties.put("user", username);
			connectionProperties.put("password", password);
			connectionProperties.put("autoReconnect", "false");
			connectionProperties.put("maxReconnects", "0");
			connection = DriverManager.getConnection(connectionString,
					connectionProperties);

			plugin.getDebugLogger().log("Connection to MySQL was a success!");
		} catch (SQLException ex) {
			connection = null;

			if (reconnectAttempt == 0 || reconnectAttempt >= 11) {
				plugin.getDebugLogger().logError("Connection to MySQL failed!");
				plugin.getDebugLogger().logStackTrace(ex);
			}
		} catch (ClassNotFoundException ex) {
			connection = null;

			if (reconnectAttempt == 0 || reconnectAttempt >= 11) {
				plugin.getDebugLogger().logError("MySQL database driver not found!");
			}
		}
	}

}
