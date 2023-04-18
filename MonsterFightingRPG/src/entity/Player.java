package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.CombatDialogue;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Axe;
import object.OBJ_Axe_Diamond;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Potion_Blue;
import object.OBJ_Potion_Gold;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Diamond;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCanceled = false;
	public String itemPlayerUsed;
	CombatDialogue cd = new CombatDialogue(gp);

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.keyH = keyH;

		// Shows half way point of the map;
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		// Attack Area
//		attackArea.width = 36;
//		attackArea.height = 36;

		setDefaultValues();
	}

	public void setDefaultValues() {

		// Default position
//		worldX = gp.tileSize * 20;
//		worldY = gp.tileSize * 44;

		// Test the enemy collision
//		worldX = gp.tileSize * 36;
//		worldY = gp.tileSize * 36;

// Test the cave
		 worldX = gp.tileSize * 7;
		 worldY = gp.tileSize * 6;
		 gp.currentMap = 1;

		speed = 4;
		direction = "down";

		// Player Status
		level = 1;
		maxLife = 10;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		life = maxLife;
		strength = 2;
		dexterity = 5;
		exp = 0;
		nextLevelExp = 5;
		coin = 150;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		// projectile = new OBJ_Rock(gp);
		attack = getAttack(); // Attack stat plus weapon attack
		defense = getDefense(); // Defense stat plus dexterity

		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		setDialogue();
	}

	public void setDefaultPositions() {

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}

	public void setDialogue() {

		dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger!";
	}

	public void restoreStatus() {

		life = maxLife;
		mana = maxMana;
		invincible = false;
		attacking = false;
	}

	// Player's starting inventory
	public void setItems() {

		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Potion_Red(gp));
	}

	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}

	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}

	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		return currentWeaponSlot;
	}

	public int getCurrentShieldSlot() {
		int currentShieldSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentShield) {
				currentShieldSlot = i;
			}
		}
		return currentShieldSlot;
	}

	public void getPlayerImage() {

		up1 = setup("/player/mc_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/mc_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/mc_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/mc_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/mc_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/mc_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/mc_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/mc_right_2", gp.tileSize, gp.tileSize);
	}

	public void getPlayerAttackImage() {

		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
		}
		if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/mc_axe_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/mc_axe_up_2", gp.tileSize, gp.tileSize * 2);
			attackDown1 = setup("/player/mc_axe_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/mc_axe_down_2", gp.tileSize, gp.tileSize * 2);
			attackLeft1 = setup("/player/mc_axe_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/mc_axe_left_2", gp.tileSize * 2, gp.tileSize);
			attackRight1 = setup("/player/mc_axe_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/mc_axe_right_2", gp.tileSize * 2, gp.tileSize);
		}
	}

	public void update() {

		if (attacking == true) {
			attacking();
		} else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true || keyH.enterPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// Check tile Collision
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// Check NPC Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// Check Monster Collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			// Check Interactive Tile Collision
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

			// Check Event
			// TURN ON TO ALLOW EVENTS!!!!!
			gp.eHandler.checkEvent();

			// if collision is false, player can move
			if (collisionOn == false && keyH.enterPressed == false) {

				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			if (keyH.enterPressed == true && attackCanceled == false) {
				gp.playSE(7);
				attacking = true;
				spriteCounter = 0;
			}

			attackCanceled = false;
			gp.keyH.enterPressed = false;

			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}

		}

		// Can not shoot more than one fireball at a time
		if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
				&& projectile.haveResource(this) == true) {

			// Set Default Coordinates
			projectile.set(worldX, worldY, direction, true, this);

			// Subtract the cost (Mana, etc)
			projectile.subtractResource(this);

			// Add projectile to list
			gp.projectileList.add(projectile);

			shotAvailableCounter = 0;

			gp.playSE(10);
		}

		// Outside of key if statement so counter runs even if you stand still
		// Counter for invincible
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}

		if (life > maxLife) {
			life = maxLife;
		}
		if (mana > maxMana) {
			mana = maxMana;
		}

		if (life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.playSE(12);
		}
	}

	// Attacking Animation
	public void attacking() {

		spriteCounter++;

		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			// Save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
//			int solidAreaWidth = solidArea.width;
//			int solidAreaHeight = solidArea.height;

			// Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			// Check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			//initiateCombat();

			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);

			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidArea.width;
			solidArea.height = solidArea.height;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void pickUpObject(int i) {

		if (i != 999) {

			// Pick only objects
			if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {

				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			// Obstacle
			else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				if (keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}

			else {
				String text;

				if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
					gp.playSE(1);
					text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
				} else {
					text = "Your inventory is full!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}

	public void interactNPC(int i) {

		if (gp.keyH.enterPressed == true) {
			if (i != 999) {
				attackCanceled = true;
				gp.npc[gp.currentMap][i].speak();
			}
		}
	}

	// Player contacts monster
	public void contactMonster(int i) {

		if (i != 999) {

			if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				gp.playSE(6);
				initiateCombat();
			}
		}
	}

	public void damageMonster(int i, int attack) {
		if (i != 999) {
			if (gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(5);

				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				// Deletes monster from array (monster killed)
				if (gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage(gp.monster[gp.currentMap][i].name + " was slain!");
					gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
	}

	public int damageMonsterCombat(int i, int attack) {
		if (i != 999) {
			gp.playSE(5);

			int damage = attack - gp.monster[gp.currentMap][i].defense;
			if (damage <= 0) {
				damage = 1;
			}
			cd.criticalHitCheck();
			if(cd.isCritical == true) {
				damage = damage * 3;
				gp.ui.getCritical();
			}
			cd.isCritical = false;
			gp.monster[gp.currentMap][i].life -= damage;
			return damage;
		}
		return -1;
	}

	public int damagePlayerCombat(int i, int defense) {

		if (i != 999) {
			gp.playSE(5);

			int damage = gp.monster[gp.currentMap][i].attack - defense;
			if (damage <= 0) {
				damage = 1;
			}
			cd.criticalHitCheck();
			if(cd.isCritical == true) {
				damage = damage * 3;
				gp.ui.getCritical();
			}
			cd.isCritical = false;
			gp.player.life -= damage;
			return damage;
		}
		return -1;
	}

	public void monsterIsDefeated(int i) {

		if (gp.monster[gp.currentMap][i].life <= 0) {
			gp.gameState = gp.combatDialogueState;
		}
	}

	public void damageInteractiveTile(int i) {

		// Making only the axe capable of destroying this tree tile
		if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) == true
				&& gp.iTile[gp.currentMap][i].invincible == false) {

			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;

			// Generate Particle
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

			if (gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}

	public void checkLevelUp() {

		if (exp >= nextLevelExp) {

			level++;
			exp = 0;
			nextLevelExp = nextLevelExp + 5;
			maxLife += 2;
			strength++;
			dexterity++;
			// Update the attack and defense value after level up
			attack = getAttack();
			defense = getDefense();

			gp.playSE(8);
			dialogues[0][0] = "You are level " + level + " now!\nYou feel stronger!";
			startDialogue(this, 0);
		}
	}

	public void selectItem() {

		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

		if (itemIndex < inventory.size()) {

			Entity selectedItem = inventory.get(itemIndex);

			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {

				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) {

				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_consumable) {
				if (selectedItem.use(this) == true) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else {
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	
	public void selectItemCombat() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

		if (itemIndex < inventory.size()) {

			Entity selectedItem = inventory.get(itemIndex);
			
			setItemPlayerUsed(selectedItem.name);
			
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {

				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) {

				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_consumable) {
				if (selectedItem.useInCombat(this) == true) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else {
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	
	public void setItemPlayerUsed(String str) {
		itemPlayerUsed = str;
	}
	
	public String getItemPlayerUsed() {
		return itemPlayerUsed;
	}

	public int searchItemInInventory(String itemName) {

		int itemIndex = 999;

		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}

		return itemIndex;
	}

	public boolean canObtainItem(Entity item) {

		// Check the space in the inventory
		// if it can be stacked
		boolean canObtain = false;

		// Creating a new item from the name of the merchant's
		// item
		Entity newItem = gp.eGenerator.getObject(item.name);

		// Check if stackable
		if (newItem.stackable == true) {

			int index = searchItemInInventory(newItem.name);

			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			// New item so need to check vacancy
			else {
				if (inventory.size() != maxInventorySize) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
		}
		// Not stackable so check vacancy
		else {
			if (inventory.size() != maxInventorySize) {
				inventory.add(newItem);
				canObtain = true;
			}
		}
		return canObtain;
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {
					image = attackUp1;
				}
				if (spriteNum == 2) {
					image = attackUp2;
				}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = attackDown1;
				}
				if (spriteNum == 2) {
					image = attackDown2;
				}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {
					image = attackLeft1;
				}
				if (spriteNum == 2) {
					image = attackLeft2;
				}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = attackRight1;
				}
				if (spriteNum == 2) {
					image = attackRight2;
				}
			}
			break;
		}

		if (invincible == true) {
			// Make player transparent to show that player is invincible
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		// Image Observer
		g2.drawImage(image, tempScreenX, tempScreenY, null); // Original is tempScreenX, tempScreenY
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Reset Alpha

		// Debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
	}
}
