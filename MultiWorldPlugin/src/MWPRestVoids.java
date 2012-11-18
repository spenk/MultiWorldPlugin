import java.io.File;


public class MWPRestVoids {
	
public static boolean isValidWorldType(String worldType){
if (worldType.equalsIgnoreCase("flat") || worldType.equalsIgnoreCase("default")){
	return true;
}
return false;
}
public static int TransferNumToWorld(int num){
	if (num == 0){return 0;}
	if (num == 1){return -1;}
	if (num == 2){return 1;}
	return 0;
}

public static int TransferWorldToInt(int world){
	if (world == 0){return 0;}
	if (world == -1){return 1;}
	if (world == 1){return 2;}
	return 0;
}

public static boolean isValidPropsParam(String param){
	if (!param.equals("UnallowedPlayers") && !param.equals("UnallowedCommands") && !param.equals("UnallowedGroups")){
		return false;
	}
	return true;
}

public static boolean existWorld(String world){
	if (new File(world).exists()){
		return true;
	}
	return false;
}

public static boolean isValidLong(String longString){
	try{
		Long.parseLong(longString);
	}catch (NumberFormatException nfe){
		return false;
	}
	return true;
}

public boolean isAgressiveMob(Mob mob){
	if (mob.getName().equals("Spider")||mob.getName().equals("Slime")||mob.getName().equals("Zombie")||mob.getName().equals("Skeleton")||mob.getName().equals("Enderman")||mob.getName().equals("CaveSpider")|| mob.getName().equals("Silverfish")||mob.getName().equals("Creeper")){
		return true;
	}
	return false;
}

public boolean isPassiveMob(Mob mob){
	if (mob.getName().equals("Sheep")||mob.getName().equals("Pig")||mob.getName().equals("Chicken")||mob.getName().equals("Cow")){
		return true;
	}
	return false;
}

public boolean isSquid(Mob mob){
	if (mob.getName().equals("Squid")){
		return true;
	}
	return false;
}

public void killmobs(World[] world){
	for (Mob m : world[0].getMobList()){
		m.setHealth(0);
	}
	
	for (Mob m : world[1].getMobList()){
		m.setHealth(0);
	}
	
	for (Mob m : world[2].getMobList()){
		m.setHealth(0);
	}
}

public void killAnimals(World[] world){
	for (Mob m : world[2].getAnimalList()){
		m.setHealth(0);
	}
	for (Mob m : world[1].getAnimalList()){
		m.setHealth(0);
	}
	for(Mob m : world[0].getAnimalList()){
		m.setHealth(0);
	}
}

public void setCreative(String world, Boolean b){
	for (Player p : etc.getServer().getPlayerList()){
		if (p.getWorld().getName().equalsIgnoreCase(world)){
			if (b){
			p.setCreativeMode(1);
			}else{
				p.setCreativeMode(0);
			}
			
		}
	}
}
}
