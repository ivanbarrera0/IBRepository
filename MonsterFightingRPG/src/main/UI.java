package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import monster.MON_GreenSlime;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {

	GamePanel gp;
	CombatDialogue cd = new CombatDialogue(gp);
	Graphics2D g2;
	public Font times_new_roman40, times_new_roman80B;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public String combatEvent = "";
	public boolean isCritical = false;
	public int commandNum = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int monsterIndexDisplay = 999;
	int subState = 0;
	int counter = 0;
	public Entity npc;
	int charIndex = 0;
	String combinedText = "";

	// Combat Values
	int monsterIndex;
	boolean itemUsedInCombat = false;
	boolean enemyTurn = false;
	boolean enemyIsDefeated = false;
	boolean playerIsDefeated = false;
	boolean escapeAllowed = false;
	boolean tryToEscape = false;

	public UI(GamePanel gp) {
		this.gp = gp;

		times_new_roman40 = new Font("Times New Roman", Font.PLAIN, 40);
		times_new_roman80B = new Font("Times New Roman", Font.BOLD, 80);

		// Create Hud Object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
	}

	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(times_new_roman40);
		g2.setColor(Color.white);

		// Title State
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// Play State
		if (gp.gameState == gp.playState) {
			//drawPlayerLife();
			drawMessage();
		}
		// Pause State
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		// Dialogue State
		if (gp.gameState == gp.dialogueState) {
			//drawPlayerLife();
			drawDialogueScreen();
		}
		// Character State
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}

		// Options State
		if (gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}

		// Game Over State
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// Transition State
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// Trade State
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		// Combat State
		if (gp.gameState == gp.combatState) {
			drawCombatMenu();
		}
		// Combat Dialogue State
		if (gp.gameState == gp.combatDialogueState) {
			drawCombat();
			drawCombatDialogueScreen();
		}
		// Combat Inventory State
		if (gp.gameState == gp.combatInventoryState) {
			drawCombat();
			drawCombatInventoryScreen(gp.player);
		}
		if (gp.gameState == gp.transitionToCombatState) {
			drawTransitionToCombatScreen();
		}
		if(gp.gameState == gp.transitionFromCombatState) {
			drawTransitionFromCombatScreen();
		}
	}

	public void drawPlayerLife() {

		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;

		// Draw Max Life
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}

		// Reset
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;

		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}

		// Draw Max Mana
		x = (gp.tileSize / 2) - 5;
		y = (int) (gp.tileSize * 1.5);
		i = 0;
		while (i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}

		// Draw Mana
		x = (gp.tileSize / 2) - 5;
		y = (int) (gp.tileSize * 1.5);
		i = 0;

		while (i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}

	}

	public void drawMessage() {

		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

		for (int i = 0; i < message.size(); i++) {

			if (message.get(i) != null) {

				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1; // messageCounter++;
				messageCounter.set(i, counter); // Set the counter to the array because it is an arraylist
				messageY += 50;

				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}

	public void drawTitleScreen() {

		// Title Name

		g2.setColor(new Color(54, 11, 169));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
		String text = "Dungeon Depths";
		int x = getXForCenteredText(text);
		int y = gp.tileSize * 3;

		// Shadow
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		// Main Text
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// Blue Boy Image
		x = gp.screenWidth / 2 - (gp.tileSize * 2 / 2);
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

		text = "New Game";
		x = getXForCenteredText(text);
		y += gp.tileSize * 3.5;
		g2.drawString(text, x, y);

		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Load Game";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Quit";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}

	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}

	public void drawDialogueScreen() {

		// Window
		int x = gp.tileSize * 3;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 6);
		int height = gp.tileSize * 4;

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;

		if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			// currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

			// Dialogue is shown character by character
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

			if (charIndex < characters.length) {

				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			//

			if (gp.keyH.enterPressed == true) {

				charIndex = 0;
				combinedText = "";

				if (gp.gameState == gp.dialogueState) {

					npc.dialogueIndex++;
					gp.keyH.enterPressed = false;
				}
			}
		} else { // If no text is in the array

			npc.dialogueIndex = 0;

			if (gp.gameState == gp.dialogueState) {
				gp.gameState = gp.playState;
			}
		}

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawCharacterScreen() {

		// Create a Frame
		final int frameX = gp.tileSize * 2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// Text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;

		// Names
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
//		g2.drawString("Mana", textX, textY);
//		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;

		// Values
		int tailX = (frameX + frameWidth) - 30;
		// Reset textY
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

//		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
//		textX = getXForAlignToRightText(value, tailX);
//		g2.drawString(value, textX, textY);
//		textY += lineHeight;

		value = String.valueOf(gp.player.strength);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
	}

	public void drawInventory(Entity entity, boolean cursor) {

		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;

		if (entity == gp.player) {
			// Frame
			frameX = gp.tileSize * 12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// Slot
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// Draw Player's Items, changes to entity inventory
		for (int i = 0; i < entity.inventory.size(); i++) {

			// Equip Cursor
			if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}

			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			// Display Amount
			if (entity == gp.player && entity.inventory.get(i).amount > 1) {

				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;

				String s = "" + entity.inventory.get(i).amount;
				amountX = getXForAlignToRightText(s, slotX + 44);
				amountY = slotY + gp.tileSize;

				// Shadow of the number
				g2.setColor(new Color(60, 60, 60));
				g2.drawString(s, amountX, amountY);
				// Number
				g2.setColor(Color.white);
				g2.drawString(s, amountX - 3, amountY - 3);
			}

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// Cursor
		if (cursor == true) {

			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;

			// Draw Cursor
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// Description Frame
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 3;

			// Draw description text
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if (itemIndex < entity.inventory.size()) {

				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
	}

	public void drawGameOverScreen() {

		// Darken the screen
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";
		g2.setColor(Color.black);
		x = getXForCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y - 4);

		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXForCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}

		// Back to Title Screen
		text = "Quit";
		x = getXForCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawOptionsScreen() {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		// Sub Window
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0:
			options_top(frameX, frameY);
			break;
		case 1:
			options_fullScreenNotification(frameX, frameY);
			break;
		case 2:
			options_control(frameX, frameY);
			break;
		case 3:
			options_endGameConfirmation(frameX, frameY);
			break;
		}

		gp.keyH.enterPressed = false;
	}

	public void options_top(int frameX, int frameY) {

		int textX;
		int textY;

		// Title
		String text = "Options";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		// Full Screen On/Off
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				} else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}

		// Music

		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		// SE

		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
		}

		// Control

		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}

		// End Game

		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}

		// Back

		textY += gp.tileSize * 2;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}

		// Full Screen CheckBox
		textX = frameX + (int) (gp.tileSize * 4.5);
		textY = frameY + gp.tileSize * 2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}

		// Music Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// SE
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		gp.config.saveConfig();
	}

	public void options_fullScreenNotification(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialogue = "This change will take \neffect after restarting \nthe game";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// Back
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}

	public void options_control(int frameX, int frameY) {

		int textX;
		int textY;

		// Title
		String text = "Control";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;

		textX = frameX + (int) (gp.tileSize * 5.5);
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("F", textX, textY);
		textY += gp.tileSize;
		g2.drawString("C", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;

		// Back
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}

	public void options_endGameConfirmation(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialogue = "Quit the game and \nreturn to the title screen?";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// Yes
		String text = "Yes";
		textX = getXForCenteredText(text);
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.resetGame(true);
			}
		}

		// No
		text = "No";
		textX = getXForCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}

	public void drawTransition() {

		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	
	public void drawTradeScreen() {

		switch (subState) {
		case 0:
			trade_select();
			break;
		case 1:
			trade_buy();
			break;
		case 2:
			trade_sell();
			break;
		}
		gp.keyH.enterPressed = false;
	}

	public void trade_select() {

		npc.dialogueSet = 0;
		drawDialogueScreen();
		// Draw Window
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);

		// Draw Texts
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += gp.tileSize;
		if (commandNum == 2) {
			g2.drawString(">", x - 24, y);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}
		g2.drawString("Leave", x, y);
	}

	public void trade_buy() {

		// Draw two inventories
		drawInventory(gp.player, false);
		drawInventory(npc, true);

		// Draw Hint Window
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your coin: " + gp.player.coin, x + 24, y + 60);

		// Draw Price Window
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {

			x = (int) (gp.tileSize * 5.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXForAlignToRightText(text, gp.tileSize * 8 - 20);
			g2.drawString(text, x, y + 34);

			// Buy an Item
			if (gp.keyH.enterPressed == true) {

				if (npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc, 2);
				} else {
					if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
					} else {
						subState = 0;
						npc.startDialogue(npc, 3);
					}
				}
			}
		}
	}

	public void trade_sell() {

		// Draw Player Inventory
		drawInventory(gp.player, true);

		// Draw Hint Window
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 60);

		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your coin: " + gp.player.coin, x + 24, y + 60);

		// Draw Price Window
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {

			x = (int) (gp.tileSize * 15.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			// Half the price of the item so that the player cannot sell items at full price
			int price = gp.player.inventory.get(itemIndex).price / 2;
			String text = "" + price;
			x = getXForAlignToRightText(text, gp.tileSize * 18 - 20);
			g2.drawString(text, x, y + 34);

			// Sell an Item
			if (gp.keyH.enterPressed == true) {

				if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
						|| gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 4);
				} else {
					if (gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					} else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coin += price;
				}
			}
		}

	}

	public void drawTransitionToCombatScreen() {

		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.combatState;
		}
	}
	
	public void drawTransitionFromCombatScreen() {
		
		gp.stopMusic();
		gp.playMusic(0);
		
		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
		}
	}

	public void drawCombatMenu() {

		g2.setColor(new Color(119, 255, 51));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

//		int x;
//		int y;
//		String text;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));

		// Draw player on combat screen
		g2.drawImage(gp.player.right1, (int) (gp.tileSize * 4.5), gp.tileSize * 5, gp.tileSize, gp.tileSize, null);

		// Draw the player's hp
		double oneScale = (double) gp.tileSize / gp.player.maxLife;
		double hpBarValue = oneScale * gp.player.life;

		g2.setColor(new Color(35, 35, 35));
		g2.fillRect((int) (gp.tileSize * 4.5) - 1, gp.tileSize * 5 - 16, gp.tileSize, 12);

		g2.setColor(new Color(255, 0, 30));
		g2.fillRect((int) (gp.tileSize * 4.5), gp.tileSize * 5 - 15, (int) hpBarValue, 10);
		
		// Draw the value of player's life near the life bar
		g2.drawString("Life: ", (int)(gp.tileSize*3.5), (int)(gp.tileSize*4.5));
		g2.drawString(gp.player.life + "",gp.tileSize*4+20, (int)(gp.tileSize*4.5));
		g2.drawString("/",gp.tileSize*4+40, (int)(gp.tileSize*4.5));
		g2.drawString(gp.player.maxLife + "",gp.tileSize*5, (int)(gp.tileSize*4.5));
		

		// Draw enemy
		// Get the entity, check to see if the enemy's index is not 999
		// Check if the monster is not null
		// if (gp.monster[gp.currentMap][monsterIndexDisplay] != null)

		if (monsterIndexDisplay != 999 && gp.monster[gp.currentMap][monsterIndexDisplay] != null) {

			// Get the monster's exp
			cd.getMonsterExp(gp.monster[gp.currentMap][gp.ui.monsterIndexDisplay].exp);

			g2.drawImage(gp.monster[gp.currentMap][monsterIndexDisplay].left1, (int) (gp.tileSize * 14),
					gp.tileSize * 5, gp.tileSize, gp.tileSize, null);
			// Draw the enemy's hp
			oneScale = (double) gp.tileSize / gp.monster[gp.currentMap][monsterIndexDisplay].maxLife;
			hpBarValue = oneScale * gp.monster[gp.currentMap][monsterIndexDisplay].life;
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect((int) (gp.tileSize * 14) - 1, gp.tileSize * 5 - 16, gp.tileSize, 12);

			g2.setColor(new Color(255, 0, 30));
			g2.fillRect((int) (gp.tileSize * 14), gp.tileSize * 5 - 15, (int) hpBarValue, 10);
		}
		// The player exits combat because the enemy was defeated
		else {
			gp.gameState = gp.playState;
			gp.stopMusic();
			gp.playMusic(0);
			gp.player.checkLevelUp();
		}

		// Check if Enemy is defeated

		if (enemyIsDefeated == true) {
			// Player gains exp
			gp.player.exp += gp.monster[gp.currentMap][monsterIndexDisplay].exp;
			// Player gains coins
			int coins = (int) (gp.monster[gp.currentMap][monsterIndexDisplay].exp * (Math.random() * 10));
			// Player can not get less than 2 coins per battle
			if (coins < 2) {
				coins = 2;
			}
			gp.player.coin += coins;
			String coinsGained = coins + "";
			enemyTurn = false;
			enemyIsDefeated = false;
			enemyDefeated(gp.monster[gp.currentMap][monsterIndexDisplay].name, coinsGained);
			gp.monster[gp.currentMap][monsterIndexDisplay] = null;
		}

		// Allow enemy to attack after player's attack
		if (enemyTurn == true) {
			cd.damagedPlayer = gp.player.damagePlayerCombat(monsterIndexDisplay, gp.player.getDefense());
			combat(1, gp.monster[gp.currentMap][monsterIndexDisplay].name, "Player");
			enemyTurn = false;
		}

		// Look at how enemy is defeated to fix the issue of the string of damaging the
		// player
		// kills the player is skipped
		if (gp.player.life <= 0) {
			playerIsDefeated = true;
			// playerDefeated();
		}

		switch (subState) {
		case 0:
			combat_menu();
			break;
		case 1:
			combat_attack();
			break;
		case 2:
			combat_item();
//		case 3:
//			combat_team();
//			break;
		}
		gp.keyH.enterPressed = false;

	}

	public void combat_menu() {

		int x = (int) (gp.tileSize * 2.5);
		int y = gp.tileSize * 9;

		int width = gp.tileSize * 14;
		int height = (int) (gp.tileSize * 3);
		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

		x += (int) (gp.tileSize * 1.5);
		y += (int) (gp.tileSize * 1.5);

		g2.drawString("Fight", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 24, y);
//			if (gp.keyH.enterPressed == true) {
//			}
		}

		x += gp.tileSize * 3;
		g2.drawString("Item", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 24, y);
//			if (gp.keyH.enterPressed == true) {
//				// Show team members
//			}
		}

		x += gp.tileSize * 3;
		g2.drawString("Team", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - 24, y);
//			if (gp.keyH.enterPressed == true) {
//				// Consumables
//			}
		}

		x += gp.tileSize * 3;
		g2.drawString("Run", x, y);
		if (commandNum == 3) {
			g2.drawString(">", x - 24, y);
		}
	}

	public void combat_attack() {

		int x = (int) (gp.tileSize * 2.5);
		int y = gp.tileSize * 9;
		monsterIndex = gp.cChecker.checkEntity(gp.player, gp.monster);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

		x += (int) (gp.tileSize * 1.5);
		y += (int) (gp.tileSize * 1.5);

		g2.drawString("Fight", x, y);

		if (enemyTurn == false) {
			cd.damagedEnemy = gp.player.damageMonsterCombat(monsterIndex, gp.player.getAttack());
			combat(0, "Player", gp.monster[gp.currentMap][monsterIndexDisplay].name);
			enemyTurn = true;
		}
		
		//gp.monster[gp.currentMap][monsterIndexDisplay].dying = true;

		if (gp.monster[gp.currentMap][monsterIndexDisplay].life <= 0) {
			gp.player.monsterIsDefeated(monsterIndexDisplay);
			enemyIsDefeated = true;
		}

		if (gp.player.life <= 0) {
			playerDefeated();
		}
	}

	// Helper Method to have the gameState be combatInventoryState
	public void combat_item() {

		gp.gameState = gp.combatInventoryState;
		// Monster is allowed to attack after player's chooses item
		enemyTurn = true;
	}

// This is to allow the combat between player and enemy to be drawn
	public void drawCombat() {

		g2.setColor(new Color(119, 255, 51));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));

		// Draw player on combat screen
		g2.drawImage(gp.player.right1, (int) (gp.tileSize * 4.5), gp.tileSize * 5, gp.tileSize, gp.tileSize, null);

		// Draw the player's hp
		double oneScale = (double) gp.tileSize / gp.player.maxLife;
		double hpBarValue = oneScale * gp.player.life;

		g2.setColor(new Color(35, 35, 35));
		g2.fillRect((int) (gp.tileSize * 4.5) - 1, gp.tileSize * 5 - 16, gp.tileSize, 12);

		g2.setColor(new Color(255, 0, 30));
		g2.fillRect((int) (gp.tileSize * 4.5), gp.tileSize * 5 - 15, (int) hpBarValue, 10);
		
		g2.drawString("Life: ", (int)(gp.tileSize*3.5), (int)(gp.tileSize*4.5));
		g2.drawString(gp.player.life + "",gp.tileSize*4+20, (int)(gp.tileSize*4.5));
		g2.drawString("/",gp.tileSize*4+40, (int)(gp.tileSize*4.5));
		g2.drawString(gp.player.maxLife + "",gp.tileSize*5, (int)(gp.tileSize*4.5));

		// Draw enemy
		// Get the entity
		int monsterIndex = gp.cChecker.checkEntity(gp.player, gp.monster);
		if (monsterIndex != 999) {
			g2.drawImage(gp.monster[gp.currentMap][monsterIndex].left1, (int) (gp.tileSize * 14), gp.tileSize * 5,
					gp.tileSize, gp.tileSize, null);

			// Draw the enemy's hp
			oneScale = (double) gp.tileSize / gp.monster[gp.currentMap][monsterIndex].maxLife;
			hpBarValue = oneScale * gp.monster[gp.currentMap][monsterIndex].life;

			g2.setColor(new Color(35, 35, 35));
			g2.fillRect((int) (gp.tileSize * 14) - 1, gp.tileSize * 5 - 16, gp.tileSize, 12);

			g2.setColor(new Color(255, 0, 30));
			g2.fillRect((int) (gp.tileSize * 14), gp.tileSize * 5 - 15, (int) hpBarValue, 10);
		}
	}

	public void drawCombatDialogueScreen() {

		int x = (int) (gp.tileSize * 2.5);
		int y = gp.tileSize * 9;

		int width = gp.tileSize * 14;
		int height = (int) (gp.tileSize * 3);
		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

		x += (int) (gp.tileSize * 0.5);
		y += (int) (gp.tileSize * 1);
		
		// Add the extra line for when a critical hit happens
		if (isCritical == true) {
			combatEvent += "\nIt's a critical hit!";
			isCritical = false;
		}
		
		if(tryToEscape == true) {
			if(escapeAllowed == true) {
				combatEvent = cd.escapeMessage(true);
			}
			else {
				combatEvent = cd.escapeMessage(false);
			}
		}
		
		// Add the extra line to the string to say you were defeated
		if (playerIsDefeated == true) {
			combatEvent += "\nYou were slain...";
		}

		// Below is the code that allows the text in combat to be displayed 
		// character by character
		if (combatEvent != null) {
			char characters[] = combatEvent.toCharArray();

			if (charIndex < characters.length) {

				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}

			if (gp.keyH.enterPressed == true) {

				charIndex = 0;
				combinedText = "";
				// Make the string null so that the if statement can proceed
				combatEvent = null;
			}
		}
		else {

			if (gp.keyH.enterPressed == true) {
				gp.keyH.enterPressed = false;
				gp.ui.subState = 0;
				gp.gameState = gp.combatState;
			}
		}
		// originally combatEvent
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		//
		
		if(tryToEscape == true && gp.keyH.enterPressed == true) {
			gp.keyH.enterPressed = false;
			tryToEscape = false;
			if(escapeAllowed == true) {
				gp.gameState = gp.playState;
				gp.stopMusic();
				gp.playMusic(0);
				gp.ui.commandNum = -1;
				gp.playSE(14);
				gp.player.invincible = true;
			}
			else {
				gp.gameState = gp.combatState;
				enemyTurn = true;
				gp.ui.commandNum = -1;
				gp.ui.subState = 0;
			}
		}

		if (playerIsDefeated == true && gp.keyH.enterPressed == true) {
			gp.keyH.enterPressed = false;
			playerIsDefeated = false;
			gp.ui.subState = 0;
			gp.gameState = gp.playState;
			gp.stopMusic();
			gp.playMusic(0);
		}
	}

	public void drawCombatInventoryScreen(Entity entity) {

		// Draw inventory methods

		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;

		// Frame
		frameX = gp.tileSize * 12;
		frameY = gp.tileSize;
		frameWidth = gp.tileSize * 6;
		frameHeight = gp.tileSize * 5;
		slotCol = playerSlotCol;
		slotRow = playerSlotRow;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// Slot
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// Draw Player's Items, changes to entity inventory
		for (int i = 0; i < entity.inventory.size(); i++) {

			// Equip Cursor
			if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}

			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			// Display Amount
			if (entity == gp.player && entity.inventory.get(i).amount > 1) {

				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;

				String s = "" + entity.inventory.get(i).amount;
				amountX = getXForAlignToRightText(s, slotX + 44);
				amountY = slotY + gp.tileSize;

				// Shadow of the number
				g2.setColor(new Color(60, 60, 60));
				g2.drawString(s, amountX, amountY);
				// Number
				g2.setColor(Color.white);
				g2.drawString(s, amountX - 3, amountY - 3);
			}

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// Cursor

		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;

		// Draw Cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

		// Description Frame
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 3;

		// Draw description text
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));

		int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

		if (itemIndex < gp.player.inventory.size()) {

			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

			for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}
	}

	public void combat(int event, String attacker, String defender) {
		gp.gameState = gp.combatDialogueState;
		combatEvent = cd.combatDialogue(event, attacker, defender);
	}

	public void useItem() {
		gp.gameState = gp.combatDialogueState;
		// cd.getUsedItemName(gp.player.getItemPlayerUsed());
		// String itemUsed = gp.player.getItemPlayerUsed();
		combatEvent = cd.usedItemInCombat(gp.player.getItemPlayerUsed());
	}

	public void getCritical() {
		isCritical = true;
	}

	public void enemyDefeated(String defeated, String coinsGained) {
		gp.gameState = gp.combatDialogueState;
		combatEvent = cd.defeatedDialogue(gp.monster[gp.currentMap][monsterIndexDisplay].name, coinsGained);
		// Enemy should start flickering now
	}

	public void playerDefeated() {
		gp.gameState = gp.combatDialogueState;
		// playerIsDefeated = true;
		combatEvent = cd.defeatedPlayerDialogue();
	}

	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}

	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public int getXForCenteredText(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getXForAlignToRightText(String text, int tailX) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}

}
