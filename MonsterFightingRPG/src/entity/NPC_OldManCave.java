package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldManCave extends Entity {

	public NPC_OldManCave(GamePanel gp) {

		super(gp);

		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 30;
		solidArea.height = 30;
		
		// This is to allow the dialogue set to start at 0 and not 1
		// which would skip the dialogue in dialogues[0][x]
		dialogueSet = -1;
		
		getImage();
		setDialogue();
	}

	public void getImage() {

		up1 = setup("/npc/oldman_up_1 (1)", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2 (1)", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1 (1)", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2 (1)", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1 (1)", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2 (1)", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1 (1)", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2 (1)", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Hello sir! Welcome to this cave!";
		dialogues[0][1] = "If you want to save your game and heal up go \nto the center of the pool of water and \npress 'enter'";
		
		dialogues[1][0] = "I heard there is spooky boss to the south east";
		dialogues[1][2] = "He is extremely strong";
		dialogues[1][2] = "There is a bunch of treasure near him";
		
		dialogues[2][0] = "You may want to avoid battle";
	}
	
	public void setAction() {
		
		if(onPath == true) {
			
//			int goalCol = 12;
//			int goalRow = 9;
			
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol, goalRow);
		}
		else {
			actionLockCounter++;
			
			// Checks every 2 secs because 60FPS
			if(actionLockCounter == 120) {
				
				Random random = new Random();
				int i = random.nextInt(100)+1; // Pick up a number from 1 to 100
				
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}
	}
	
	public void speak() {
		
		// Do this character specific stuff 
		facePlayer();
		startDialogue(this, dialogueSet);
		
		dialogueSet++;
		
		if(dialogues[dialogueSet][0] == null) {
			
			// This resets the dialogue
			dialogueSet = 0;
			// Repeat the last dialogue
			//dialogueSet--;
		}
		//onPath = true;
	}

}
