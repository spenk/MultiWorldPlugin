import java.util.logging.Logger;

public class MWPCreateWorld {
	String worldName;
	String worldType;
	Long seed;

	public MWPCreateWorld(String worldName) {
		this.worldName = worldName;
		worldType = "";
		seed = 0L;
	}

	public MWPCreateWorld(String worldName, String worldType) {
		Logger.getLogger("Minecraft").info("[MultiWorldPlugin] World "+worldName+" is created");
		etc.getServer().loadWorld(worldName,World.Type.valueOf(worldType.toUpperCase()));
		this.worldName = worldName;
		this.worldType = worldType;
		seed = 0L;
	}

	public MWPCreateWorld(String worldName, String worldType, Long seed) {
		etc.getServer().loadWorld(worldName,
				World.Type.valueOf(worldType.toUpperCase()), seed);
		this.worldName = worldName;
		this.worldType = worldType;
		this.seed = seed;
	}

	public void createWorldProperties() {
		MWPFiler filer = new MWPFiler(worldName, seed);
		filer.createFile();
		return;
	}

	public void loadWorld() {
		etc.getServer().loadWorld(worldName);
	}

	public void switchWorld(Player player, int dimension) {
		Logger.getLogger("Minecraft").info("[MultiWorldPlugin] "+player.getName()+" is Teleported to "+worldName);
		int Dim = MWPRestVoids.TransferWorldToInt(dimension);
		World[] world = etc.getServer().getWorld(worldName);
		player.switchWorlds(world[Dim]);
		return;
	}
}
