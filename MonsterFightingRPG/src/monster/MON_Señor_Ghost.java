package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Se�or_Ghost extends Entity {

	GamePanel gp;

	public MON_Se�or_Ghost(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		name = "Se�or Ghost";
		speed = 4;
		maxLife = 100;
		life = maxLife;
		attack = 25;
		defense = 10;
		exp = 1000;
		projectile = new OBJ_Rock(gp);

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {

		up1 = setup("/monster/ghostsenor_down_2", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/ghostsenor_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/ghostsenor_down_1", gp.tileSize, gp.tileSize);

	}
	
	public void update() {
		
		super.update();
		
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance)/gp.tileSize;
		
		// Aggro monster when you get too close
		if(onPath == false && tileDistance < 5) {
			
			int i = new Random().nextInt(100)+1;
			if(i > 50) {
				onPath = true;
			}
		}
		// Deaggro monsters when you get too far away
		if(onPath == true && tileDistance > 10) {
			onPath = false;
		}
	}

	public void setAction() {

		if (onPath == true) {

			// Target is player
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

			searchPath(goalCol, goalRow);
			
			int i = new Random().nextInt(100) + 1;
			if (i > 197 && projectile.alive == false && shotAvailableCounter == 30) {
				projectile.set(worldX, worldY, direction, true, this);
				gp.projectileList.add(projectile);
				shotAvailableCounter = 0;
			}
		} else {
			actionLockCounter++;

			// Checks every 2 secs because 60FPS
			if (actionLockCounter == 120) {

				Random random = new Random();
				int i = random.nextInt(100) + 1; // Pick up a number from 1 to 100

				if (i <= 25) {
					direction = "up";
				}
				if (i > 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}
	}

	public void damageReaction() {

		actionLockCounter = 0;
		// Aggro Monsters after taking damage
		onPath = true;
	}

	public void checkDrop() {

		// Cast a die
		int i = new Random().nextInt(100) + 1;

		// Set the monster drop
		if (i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if (i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if (i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}

	}
}
