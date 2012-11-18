
public class MWPMobBehaviour {
	Mob mob;
	MWPFiler filer;
	MWPRestVoids voids;
	LivingEntity Attacker;
	LivingEntity Defender;
	String dWorld = etc.getServer().getDefaultWorld().getName();
	String world;
	
	
	public MWPMobBehaviour(Mob mob, String world){
		filer = new MWPFiler(world);
		voids = new MWPRestVoids();
		this.mob = mob;
		this.world = world;
	}
	
	public MWPMobBehaviour(LivingEntity attacker,LivingEntity defender){
		this.Attacker = attacker; 
		this.Defender = defender;
		this.filer = new MWPFiler(attacker.getWorld().getName());
	}
	
	public boolean processMobSpawn(){
		if (world.equals(dWorld)){
			return false;
		}
		if (filer.existFile()){
			if (!filer.spawnMobs() && voids.isAgressiveMob(mob)){
				return true;
			}
			
			if (!filer.spawnAnimals() && voids.isPassiveMob(mob)){
				return true;
			}
			
			if (!filer.spawnSquids() && voids.isSquid(mob)){
				return true;
			}
			
		}
		return false;
	}

	public boolean processAttack(){
		if (Defender.getWorld().getName().equals(dWorld)){
			return false;
		}
		if (Attacker.isPlayer() && Defender.isPlayer()){
			if (!filer.isPvpEnabled()){
				return true;
			}
		}
		return false;
	}
}
