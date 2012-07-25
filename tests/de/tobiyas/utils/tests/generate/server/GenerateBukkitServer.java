package de.tobiyas.utils.tests.generate.server;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.tobiyas.utils.tests.generate.player.GeneratePlayer;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Server.class)
public class GenerateBukkitServer {
	
	public static void generateServer(){
		Server mockServer = Mockito.mock(Server.class);
		mockServer = generateLogger(mockServer);
		
		try{
			Field field = Bukkit.class.getDeclaredField("server");
			field.setAccessible(true);
			field.set(null, mockServer);
		}catch(Exception e){
			System.out.println("Could not load new Bukkit-Server!");
		}
	}
	
	private static Server generateLogger(Server server){
		Logger mockLogger = Logger.getAnonymousLogger();
		Mockito.when(server.getLogger()).thenReturn(mockLogger);
		
		return server;
	}
	
	public static void generatePlayerOnServer(String playerName){
		Server mockServer = Bukkit.getServer();
		Player mockPlayer = GeneratePlayer.generatePlayer(playerName);
		
		Mockito.when(mockServer.getPlayer(playerName)).thenReturn(mockPlayer);
	}

	public static void dropServer() {
		try{
			Field field = Bukkit.class.getDeclaredField("server");
			field.setAccessible(true);
			field.set(null, null);
		}catch(Exception e){
			System.out.println("Could not unload Bukkit-Server!");
		}
	}
}
