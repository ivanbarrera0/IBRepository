package main;

import entity.Entity;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][][];
	Entity eventMaster;
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventMaster = new Entity(gp);
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 4;
			eventRect[map][col][row].height = 4;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		
		setDialogue();
	}
	
	public void setDialogue() {
		
		eventMaster.dialogues[0][0] = "You fall into a pit!";
		eventMaster.dialogues[1][0] = "You drink the water.\nYour life and mana have been recovered.\n"
				+ "(Your game progress has been saved)";
		eventMaster.dialogues[1][1] = "Damn, this some good water.";
	}
	
	public void checkEvent() {
		
		// Check if the player is more than 1 tile away from last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			// Map is different than original
			// Else if is used to prevent teleport from triggering twice 
			
			if(hit(0, 4, 17, "any") == true) {teleport(1,6,5);}
			else if(hit(1,6,5,"any") == true) {teleport(0,5,17);}
			else if(hit(0,17,18,"right") == true) {healingPool(gp.dialogueState);}
			else if(hit(0,6,36,"left") == true) {healingPool(gp.dialogueState);}
			else if(hit(0,29,6,"up") == true) {healingPool(gp.dialogueState);}
			else if(hit(0,44,35,"up") == true) {healingPool(gp.dialogueState);}
			else if(hit(0,12,44,"any") == true) {teleport(2,27,25);}
			else if(hit(1,42,24,"right") == true) {healingPool(gp.dialogueState);}
			else if(hit(1,18,7,"up") == true) {healingPool(gp.dialogueState);}
			else if(hit(1,6,42,"left") == true) {healingPool(gp.dialogueState);}
			else if(hit(2,27,26,"any") == true) {teleport(0,13,45);}
			else if(hit(2, 27, 21, "up") == true) {speak(gp.npc[2][0]);}
			
//			if(hit(0, 26, 16,"right") == true) {damagePit(gp.dialogueState);}
//			else if(hit(0, 23, 7, "up") == true) {healingPool(gp.dialogueState);}
//			else if(hit(0, 8, 40, "any") == true) {teleport(1, 12, 13);}
//			else if(hit(1, 12, 13, "any") == true) {teleport(0, 8, 40);}
//			else if(hit(1, 12, 9, "up") == true) {speak(gp.npc[1][0]);}
		}
		
		
	}
	
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		// The int map helps set up events to other maps
		if(map == gp.currentMap) {
			
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.solidArea.intersects(eventRect[map][col][row])) {
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
					
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}
	
	public void damagePit(int gameState) {
		
		gp.gameState = gameState;
		gp.playSE(6);
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			eventMaster.startDialogue(eventMaster, 1);
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			// This method respawns the monsters 
			gp.aSetter.setMonster();
			gp.saveLoad.save();
		}
	}
	
	public void teleport(int map, int col, int row) {
		
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(13);
	}
	
	public void speak(Entity entity) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}
}
