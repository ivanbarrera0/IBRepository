package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Girl extends Entity {

	public NPC_Girl(GamePanel gp) {

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

		up1 = setup("/npc/girl_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/girl_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/girl_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/girl_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/girl_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/girl_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/girl_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/girl_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		
		dialogues[0][0] = "Hello! :)";
		dialogues[0][1] = "My name is Marisol";
		
		dialogues[1][0] = "I'm an artist";
		dialogues[1][1] = "I did a lot of the art \nfor this game";
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
