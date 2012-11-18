
public class MultiWorldHooked {
	
	PluginInterface CustomListener = new MultiWorldHooked.CustomListener();

	public class CustomListener implements PluginInterface{
		public String getName(){
	            return "MWH_PLAYERINTERFACE";
	       }
		public int getNumParameters() {
		      return 5;
		}
		public String checkParameters(Object[] os) { 
			if ((os.length < 1) || (os.length > getNumParameters())) {
				return "Invalid amount of parameters.";
			}
			return null;
		}
		public Object run(Object[] param) {
			String hook = (String)param[0];
			Player player = etc.getServer().matchPlayer((String)param[1]);
			String worldName = (String)param[2];
			if (player == null){
				return false;
			}
			MWPFiler worldInterface = new MWPFiler(worldName);
			if (!worldInterface.existFile()){
				return false;
			}
			
			MWPPlayer playerInterface = new MWPPlayer(player, worldName);
			
			if (hook.equals("CAN_PLAYER_ENTER")){
				if (playerInterface.isAllowedToEnter()){
					return true;
				}
				return false;
			}
			
			if (hook.equals("IS_GROUP_ALLOWED")){
				if (playerInterface.isAllowedGroup()){
					return true;
				}
				return false;
			}
			
			
			if (hook.equals("IS_PLAYER_ALLOWED")){
				if (playerInterface.isAllowedPlayer()){
					return true;
				}
				return false;
			}
			
			if (hook.equals("CAN_USE_COMMAND")){
				String command = (String)param[3];
				if (playerInterface.isAllowedCommand(command)){
					return true;
				}
				return false;
			}
			
			if (hook.equals("SET_GROUP_ALLOWED")){
				String group = (String)param[3];
				Boolean allowed = (Boolean)param[4];
				return playerInterface.setAllowedGroup(group, allowed);
			}
			
			if (hook.equals("SET_PLAYER_ALLOWED")){
				String p = (String)param[3];
				Boolean allowed = (Boolean)param[4];
				return playerInterface.setAllowedPlayer(p, allowed);
			}
			
			if (hook.equals("SET_COMMAND_ALLOWED")){
				String command = (String)param[3];
				Boolean allowed = (Boolean)param[4];
				return playerInterface.setAllowedCommand(command, allowed);
			}
			
			return null;
		}
	}
}
