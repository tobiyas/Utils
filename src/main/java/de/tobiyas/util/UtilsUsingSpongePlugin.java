package de.tobiyas.util;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.SpongeEventHandler;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;


public abstract class UtilsUsingSpongePlugin {

	/**
	 * The Game instance to use.
	 */
	protected Game game;
	
	/**
	 * The Logger to use.
	 */
	protected Logger logger;
	
	
	/**
	 * The Server is created.
	 * 
	 * @param event to use
	 */
	@SpongeEventHandler
	public final void onConstruction(PreInitializationEvent event){
		game = event.getGame();
		event.getPluginLog();
		
		preStart();
	}
	
	
	@SpongeEventHandler
    public final void onServerStarting(ServerStartingEvent event) {
       serverStarting();
    }
	
	
	@SpongeEventHandler
	public final void onServerClosing(ServerStoppingEvent event){
		serverStopping();
	}

	
	@SpongeEventHandler
	public final void onServerClosed(ServerStoppedEvent event){
		serverStopped();
	}
	
	
	
	
	/**
	 * The Server is Stopping
	 */
	public abstract void serverStopping();

	/**
	 * The Server has Stopped.
	 */
	public void serverStopped() {}


	/**
	 * The Server is starting.
	 */
	public abstract void serverStarting();


	/**
	 * Pre start is called.
	 */
	public void preStart(){};
	
}
