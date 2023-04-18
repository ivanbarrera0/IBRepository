package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Blue extends Entity {
	
	GamePanel gp;
	public static final String objName = "Blue Potion";
	
	public OBJ_Potion_Blue(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_consumable;
		name = objName;
		value = 10;
		down1 = setup("/objects/potion_blue", gp.tileSize, gp.tileSize);
		description = "[Blue Potion]\nHeals you by " + value;
		price = 25;
		stackable = true;
		setDialogue();
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "You drank the " + name + "\n"
				+ "Your health has been recovered by " + value;
	}
	
	public boolean use(Entity entity) {
		
		startDialogue(this,0);
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
		return true;
	}
	
	public boolean useInCombat(Entity entity) {
		
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
		return true;
	}
	
	public String getName() {
		
		return objName;
	}
}
