
public class MWPPlayer {
Player player;
String worldName;
String dWorldName = etc.getServer().getDefaultWorld().getName();

	public MWPPlayer (Player player, String worldName){
		this.player = player;
		this.worldName = worldName;
	}
	
	public boolean isAllowedToEnter(){
		if (this.isAllowedGroup() && this.isAllowedPlayer()){
			return true;
		}
		return false;
	}
	
	public boolean isAllowedGroup(){
		if (worldName.equals(dWorldName)){
			return true;
		}
		MWPFiler filer = new MWPFiler(worldName);
		String group = (player.getGroups().length > 0 ? player.getGroups()[0] : etc.getDataSource().getDefaultGroup().Name);
		
		if (filer.getUnallowedGroups() == null){
			return true;
		}
		if (!filer.getUnallowedGroups().contains(group)){
			return true;
		}
		return false;
	}
	
	public boolean isAllowedPlayer(){
		if (worldName.equals(dWorldName)){
			return true;
		}
		MWPFiler filer = new MWPFiler(worldName);
		if (filer.getUnallowedPlayers() == null){
			return true;
		}
		if (!filer.getUnallowedPlayers().contains(player.getName())){
			return true;
		}
		return false;
	}
	
	public boolean isAllowedCommand(String command){
		if (worldName.equals(dWorldName)){
			return true;
		}
		MWPFiler filer = new MWPFiler(worldName);
		if (filer.getUnallowedCommands() == null){
			return true;
		}
		if (!filer.getUnallowedCommands().contains(command)){
			return true;
		}
		return false;
	}
	
	public boolean setAllowedPlayer(String player, boolean allowed){
		MWPFiler filer = new MWPFiler(worldName);
		if (allowed){
				return filer.removeUnallowedParam("UnallowedPlayers", player);
		}
		return filer.addUnallowedParam("UnallowedPlayers", player);
	}
	
	public boolean setAllowedGroup(String group, boolean allowed){
		MWPFiler filer = new MWPFiler(worldName);
		if (allowed){
				return filer.removeUnallowedParam("UnallowedGroups", group);
		}
		return filer.addUnallowedParam("UnallowedGroups", group);
	}
	
	public boolean setAllowedCommand(String command, boolean allowed){
		MWPFiler filer = new MWPFiler(worldName);
		if (allowed){
				return filer.removeUnallowedParam("UnallowedCommands", command);
		}
		return filer.addUnallowedParam("UnallowedCommands", command);
	}
	
}
