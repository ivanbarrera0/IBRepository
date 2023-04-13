package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Save extends Entity {
	
	GamePanel gp;
	public static final String objName = "Door";
	
	public OBJ_Save(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		down1 = setup("/objects/save", gp.tileSize, gp.tileSize);
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDialogue();
	}
	
	public void setDialogue() {
		
//		dialogues[0][0] = "Your game has been saved!";
//		dialogues[0][1] = "You have been healed.";
	}
	
	public void interact() {
//		startDialogue(this,0);
	}
}
