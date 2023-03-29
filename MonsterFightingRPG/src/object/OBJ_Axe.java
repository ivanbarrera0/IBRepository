package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	// This is needed to prevent a bug in which buying an 
	// unstackable item from the merchant can cause problems
	// when equipping an item
	public static final String objName = "Woodcutter's Axe";
	
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = objName;
		down1 = setup("/objects/axe", gp.tileSize,gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nA rusty axe";
		price = 75;
	}
}
