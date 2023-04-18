package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Diamond extends Entity {
	
	public static final String objName = "Diamond Sword";
	
	public OBJ_Sword_Diamond(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = objName;
		down1 = setup("/objects/sword_diamond", gp.tileSize, gp.tileSize);
		attackValue = 10;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nA sword forged with \ndiamonds";
		price = 1000;
	}
}
