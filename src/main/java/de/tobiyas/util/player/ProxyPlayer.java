package de.tobiyas.util.player;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.spongepowered.api.Game;

import de.tobiyas.util.sponge.SpongeUtils;

public class ProxyPlayer {

	/**
	 * The UUID of the Player.
	 */
	private final UUID playerID;
	
	/**
	 * The Sponge game to use.
	 */
	private final Game game;
	
	/**
	 * The Bukkit player
	 */
	private Player bukkitPlayer;
	
	/**
	 * The Sponge player
	 */
	private org.spongepowered.api.entity.Player spongePlayer;
	
	
	private boolean useSponge = SpongeUtils.isSpongeUsed();
	private boolean useBukkit = SpongeUtils.isBukkitUsed();
	
	
	public ProxyPlayer(UUID playerID) {
		this.playerID = playerID;
		this.game = null;
		
		this.bukkitPlayer = getBukkitPlayer();
	}
	
	
	public ProxyPlayer(UUID playerID, Game game) {
		this.playerID = playerID;
		this.game = game;
		
		this.spongePlayer = getSpongePlayer();
	}

	
	/**
	 * Gets a Bukkit representation of the Player.
	 * 
	 * @return
	 */
	private Player getBukkitPlayer(){
		return Bukkit.getPlayer(playerID);
	}
	
	/**
	 * Gets a Sponge representation of the Player.
	 * 
	 * @return
	 */
	private org.spongepowered.api.entity.Player getSpongePlayer(){
		return game.getPlayer(playerID);
	}
	

	/**
	 * Returns the Name of the Player.
	 * 
	 * @return The Name.
	 */
	public String getName() {
		if(useSponge) return spongePlayer.getName();
		if(useBukkit) return bukkitPlayer.getName();
		
		return null;
	}
	
	
	/**
	 * Returns the Display name of the Player.
	 * 
	 * @return The current displayName.
	 */
	public String getDisplayName(){
		if(useSponge) return spongePlayer.getName();
		if(useBukkit) return bukkitPlayer.getName();
		
		return null;
	}
	
	
	/**
	 * Sets the Display Name of the Player.
	 * 
	 * @param name to set.
	 */
	public void setDisplayName(String name) {
//		if(useSponge) spongePlayer.setDisplayName(name);//TODO not yet present.
		if(useBukkit) bukkitPlayer.setDisplayName(name);
		
		return;
	}


	
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}


	
	public ItemStack getItemInHand() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setItemInHand(ItemStack item) {
		// TODO Auto-generated method stub
		
	}


	
	public ItemStack getItemOnCursor() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setItemOnCursor(ItemStack item) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isSleeping() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public int getSleepTicks() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public GameMode getGameMode() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setGameMode(GameMode mode) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public int getExpToLevel() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public double getEyeHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public double getEyeHeight(boolean ignoreSneaking) {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public Location getEyeLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent,
			int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Egg throwEgg() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Snowball throwSnowball() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Arrow shootArrow() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public int getRemainingAir() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setRemainingAir(int ticks) {
		// TODO Auto-generated method stub
		
	}


	
	public int getMaximumAir() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setMaximumAir(int ticks) {
		// TODO Auto-generated method stub
		
	}


	
	public int getMaximumNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setMaximumNoDamageTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}


	
	public double getLastDamage() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public int _INVALID_getLastDamage() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setLastDamage(double damage) {
		// TODO Auto-generated method stub
		
	}


	
	public void _INVALID_setLastDamage(int damage) {
		// TODO Auto-generated method stub
		
	}


	
	public int getNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setNoDamageTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}


	
	public Player getKiller() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean addPotionEffect(PotionEffect effect) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean addPotionEffect(PotionEffect effect, boolean force) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean addPotionEffects(Collection<PotionEffect> effects) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean hasPotionEffect(PotionEffectType type) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void removePotionEffect(PotionEffectType type) {
		// TODO Auto-generated method stub
		
	}


	
	public Collection<PotionEffect> getActivePotionEffects() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean hasLineOfSight(Entity other) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean getRemoveWhenFarAway() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setRemoveWhenFarAway(boolean remove) {
		// TODO Auto-generated method stub
		
	}


	
	public EntityEquipment getEquipment() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setCanPickupItems(boolean pickup) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean getCanPickupItems() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setCustomName(String name) {
		// TODO Auto-generated method stub
		
	}


	
	public String getCustomName() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setCustomNameVisible(boolean flag) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isCustomNameVisible() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isLeashed() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public Entity getLeashHolder() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean setLeashHolder(Entity holder) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Location getLocation(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setVelocity(Vector velocity) {
		// TODO Auto-generated method stub
		
	}


	
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean teleport(Location location) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean teleport(Location location, TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean teleport(Entity destination) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean teleport(Entity destination, TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public List<Entity> getNearbyEntities(double x, double y, double z) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public int getEntityId() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public int getFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public int getMaxFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setFireTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}


	
	public void remove() {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public Entity getPassenger() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean setPassenger(Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean eject() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public float getFallDistance() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setFallDistance(float distance) {
		// TODO Auto-generated method stub
		
	}


	
	public void setLastDamageCause(EntityDamageEvent event) {
		// TODO Auto-generated method stub
		
	}


	
	public EntityDamageEvent getLastDamageCause() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public UUID getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public int getTicksLived() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setTicksLived(int value) {
		// TODO Auto-generated method stub
		
	}


	
	public void playEffect(EntityEffect type) {
		// TODO Auto-generated method stub
		
	}


	
	public EntityType getType() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean isInsideVehicle() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean leaveVehicle() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public Entity getVehicle() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
		// TODO Auto-generated method stub
		
	}


	
	public List<MetadataValue> getMetadata(String metadataKey) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean hasMetadata(String metadataKey) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void removeMetadata(String metadataKey, Plugin owningPlugin) {
		// TODO Auto-generated method stub
		
	}


	
	public void damage(double amount) {
		// TODO Auto-generated method stub
		
	}


	
	public void _INVALID_damage(int amount) {
		// TODO Auto-generated method stub
		
	}


	
	public void damage(double amount, Entity source) {
		// TODO Auto-generated method stub
		
	}


	
	public void _INVALID_damage(int amount, Entity source) {
		// TODO Auto-generated method stub
		
	}


	
	public double getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public int _INVALID_getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setHealth(double health) {
		// TODO Auto-generated method stub
		
	}


	
	public void _INVALID_setHealth(int health) {
		// TODO Auto-generated method stub
		
	}


	
	public double getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public int _INVALID_getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setMaxHealth(double health) {
		// TODO Auto-generated method stub
		
	}


	
	public void _INVALID_setMaxHealth(int health) {
		// TODO Auto-generated method stub
		
	}


	
	public void resetMaxHealth() {
		// TODO Auto-generated method stub
		
	}


	
	public <T extends Projectile> T launchProjectile(
			Class<? extends T> projectile) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public <T extends Projectile> T launchProjectile(
			Class<? extends T> projectile, Vector velocity) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean isPermissionSet(String name) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isPermissionSet(Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean hasPermission(String name) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean hasPermission(Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public PermissionAttachment addAttachment(Plugin plugin, String name,
			boolean value) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public PermissionAttachment addAttachment(Plugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public PermissionAttachment addAttachment(Plugin plugin, String name,
			boolean value, int ticks) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void removeAttachment(PermissionAttachment attachment) {
		// TODO Auto-generated method stub
		
	}


	
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		
	}


	
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setOp(boolean value) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isConversing() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void acceptConversationInput(String input) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean beginConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void abandonConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		
	}


	
	public void abandonConversation(Conversation conversation,
			ConversationAbandonedEvent details) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendMessage(String[] messages) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isBanned() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setBanned(boolean banned) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isWhitelisted() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setWhitelisted(boolean value) {
		// TODO Auto-generated method stub
		
	}


	
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public long getFirstPlayed() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public long getLastPlayed() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public boolean hasPlayedBefore() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void sendPluginMessage(Plugin source, String channel, byte[] message) {
		// TODO Auto-generated method stub
		
	}


	
	public Set<String> getListeningPluginChannels() {
		// TODO Auto-generated method stub
		return null;
	}




	
	public String getPlayerListName() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setPlayerListName(String name) {
		// TODO Auto-generated method stub
		
	}


	
	public void setCompassTarget(Location loc) {
		// TODO Auto-generated method stub
		
	}


	
	public Location getCompassTarget() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public InetSocketAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void sendRawMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	
	public void kickPlayer(String message) {
		// TODO Auto-generated method stub
		
	}


	
	public void chat(String msg) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean performCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isSneaking() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setSneaking(boolean sneak) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isSprinting() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setSprinting(boolean sprinting) {
		// TODO Auto-generated method stub
		
	}


	
	public void saveData() {
		// TODO Auto-generated method stub
		
	}


	
	public void loadData() {
		// TODO Auto-generated method stub
		
	}


	
	public void setSleepingIgnored(boolean isSleeping) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isSleepingIgnored() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void playNote(Location loc, byte instrument, byte note) {
		// TODO Auto-generated method stub
		
	}


	
	public void playNote(Location loc, Instrument instrument, Note note) {
		// TODO Auto-generated method stub
		
	}


	
	public void playSound(Location location, Sound sound, float volume,
			float pitch) {
		// TODO Auto-generated method stub
		
	}


	
	public void playSound(Location location, String sound, float volume,
			float pitch) {
		// TODO Auto-generated method stub
		
	}


	
	public void playEffect(Location loc, Effect effect, int data) {
		// TODO Auto-generated method stub
		
	}


	
	public <T> void playEffect(Location loc, Effect effect, T data) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendBlockChange(Location loc, Material material, byte data) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean sendChunkChange(Location loc, int sx, int sy, int sz,
			byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void sendBlockChange(Location loc, int material, byte data) {
		// TODO Auto-generated method stub
		
	}


	
	public void sendSignChange(Location loc, String[] lines)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void sendMap(MapView map) {
		// TODO Auto-generated method stub
		
	}


	
	public void updateInventory() {
		// TODO Auto-generated method stub
		
	}


	
	public void awardAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		
	}


	
	public void removeAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean hasAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void incrementStatistic(Statistic statistic)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void incrementStatistic(Statistic statistic, int amount)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic, int amount)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void setStatistic(Statistic statistic, int newValue)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public int getStatistic(Statistic statistic)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void incrementStatistic(Statistic statistic, Material material)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic, Material material)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public int getStatistic(Statistic statistic, Material material)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void incrementStatistic(Statistic statistic, Material material,
			int amount) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic, Material material,
			int amount) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void setStatistic(Statistic statistic, Material material,
			int newValue) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void incrementStatistic(Statistic statistic, EntityType entityType)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic, EntityType entityType)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public int getStatistic(Statistic statistic, EntityType entityType)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void incrementStatistic(Statistic statistic, EntityType entityType,
			int amount) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void decrementStatistic(Statistic statistic, EntityType entityType,
			int amount) {
		// TODO Auto-generated method stub
		
	}


	
	public void setStatistic(Statistic statistic, EntityType entityType,
			int newValue) {
		// TODO Auto-generated method stub
		
	}


	
	public void setPlayerTime(long time, boolean relative) {
		// TODO Auto-generated method stub
		
	}


	
	public long getPlayerTime() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public long getPlayerTimeOffset() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public boolean isPlayerTimeRelative() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void resetPlayerTime() {
		// TODO Auto-generated method stub
		
	}


	
	public void setPlayerWeather(WeatherType type) {
		// TODO Auto-generated method stub
		
	}


	
	public WeatherType getPlayerWeather() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void resetPlayerWeather() {
		// TODO Auto-generated method stub
		
	}


	
	public void giveExp(int amount) {
		// TODO Auto-generated method stub
		
	}


	
	public void giveExpLevels(int amount) {
		// TODO Auto-generated method stub
		
	}


	
	public float getExp() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setExp(float exp) {
		// TODO Auto-generated method stub
		
	}


	
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setLevel(int level) {
		// TODO Auto-generated method stub
		
	}


	
	public int getTotalExperience() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setTotalExperience(int exp) {
		// TODO Auto-generated method stub
		
	}


	
	public float getExhaustion() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setExhaustion(float value) {
		// TODO Auto-generated method stub
		
	}


	
	public float getSaturation() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setSaturation(float value) {
		// TODO Auto-generated method stub
		
	}


	
	public int getFoodLevel() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setFoodLevel(int value) {
		// TODO Auto-generated method stub
		
	}


	
	public Location getBedSpawnLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setBedSpawnLocation(Location location) {
		// TODO Auto-generated method stub
		
	}


	
	public void setBedSpawnLocation(Location location, boolean force) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean getAllowFlight() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setAllowFlight(boolean flight) {
		// TODO Auto-generated method stub
		
	}


	
	public void hidePlayer(Player player) {
		// TODO Auto-generated method stub
		
	}


	
	public void showPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}


	
	public boolean canSee(Player player) {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isOnGround() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isFlying() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setFlying(boolean value) {
		// TODO Auto-generated method stub
		
	}


	
	public void setFlySpeed(float value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public void setWalkSpeed(float value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public float getFlySpeed() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public float getWalkSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	public void setTexturePack(String url) {
		// TODO Auto-generated method stub
		
	}


	
	public void setResourcePack(String url) {
		// TODO Auto-generated method stub
		
	}


	
	public Scoreboard getScoreboard() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void setScoreboard(Scoreboard scoreboard)
			throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isHealthScaled() {
		// TODO Auto-generated method stub
		return false;
	}


	
	public void setHealthScaled(boolean scale) {
		// TODO Auto-generated method stub
		
	}


	
	public void setHealthScale(double scale) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	
	public double getHealthScale() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
