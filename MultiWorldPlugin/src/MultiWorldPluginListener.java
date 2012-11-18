/**
 * @project MultiWorld
 * @author Spenk
 * @canary 5.1.11
 */

import java.util.List;
import java.util.logging.Logger;

public class MultiWorldPluginListener extends PluginListener{
	Logger log = Logger.getLogger("Minecraft");
	List<String> commands;
	List<String> players;
	
	public boolean onCommand(Player player, String[] split) {
		if (split[0].equalsIgnoreCase("/world")){
		MWPCommand command = new MWPCommand(split, player);
		command.processCommand();
		return true;
		}
		return false;
	}
		
	public boolean onConsoleCommand(String[] split){
		if (split[0].equalsIgnoreCase("world")){
		MWPCommand command = new MWPCommand(split, null);
		command.processCommand();
		return true;
		}
		return false;	
	}
	
	
	public PluginLoader.HookResult canPlayerUseCommand(Player player, String command){
		String devWName = etc.getServer().getDefaultWorld().getName();
		if (player.getWorld().getName().equals(devWName)){
			return PluginLoader.HookResult.DEFAULT_ACTION;
		}
		MWPPlayer mwplayer = new MWPPlayer(player, player.getWorld().getName());
		if (!mwplayer.isAllowedCommand(command)){
		return PluginLoader.HookResult.PREVENT_ACTION;
		}
	return PluginLoader.HookResult.DEFAULT_ACTION;
	}
	
	
    public boolean onMobSpawn(Mob mob){
    	MWPMobBehaviour MobBehaviour = new MWPMobBehaviour(mob, mob.getWorld().getName());
    	 return MobBehaviour.processMobSpawn();
    }
    
    
    public boolean onAttack(LivingEntity attacker,LivingEntity defender,java.lang.Integer amount){
    	MWPMobBehaviour MobBehaviour = new MWPMobBehaviour(attacker, defender);
    	return MobBehaviour.processAttack();
    }
    
    public boolean onHealthChange(Player player,int oldValue,int newValue){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    return playerhandler.processHealth();
    }
    
    public int onFoodLevelChange(Player player,int oldFoodLevel,int newFoodLevel){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	if (playerhandler.processFood()){
    		return oldFoodLevel;
    	}
    	return newFoodLevel;
    }
    
    public boolean onExpChange(Player player,int oldValue,int newValue){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	return playerhandler.processExp();
    }
    
    public void onDisconnect(Player player){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	playerhandler.saveLocation(player);
    }
    
    public void onKick(Player mod,Player player,java.lang.String reason){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	playerhandler.saveLocation(player);
    }
    
    public void onBan(Player mod,Player player,java.lang.String reason){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	playerhandler.saveLocation(player);
    }
    
    public void onLogin(Player player){
    	player.sendMessage("derp");
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	playerhandler.respawnPlayer(player);
    	player.sendMessage("derp");
    }
    
    public boolean onBlockPlace(Player player,Block block,Block blockClicked,Item itemInHand){
    	MWPPlayerHandler playerhandler = new MWPPlayerHandler(player);
    	return playerhandler.processBlockPlace(block);
    }
    public void onPlayerMove(Player player,Location from,Location to){
    	MWPPlayer mwpp = new MWPPlayer(player, player.getWorld().getName());
    	if(!mwpp.isAllowedToEnter()){
    		player.teleportTo(etc.getServer().getDefaultWorld().getSpawnLocation());
    	}
    }
}
