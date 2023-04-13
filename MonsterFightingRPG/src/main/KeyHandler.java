package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	CombatDialogue cd = new CombatDialogue(gp);

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

	// Debug
	boolean checkDrawTime = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// Title State
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// Play State
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// Pause State
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// Dialogue State
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}

		// Character State
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		} else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		// GameOver State
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		// Trade State
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		// Map State
		else if (gp.gameState == gp.mapState) {
			mapState(code);
		}
		// Combat State
		else if(gp.gameState == gp.combatState) {
			combatState(code);
		}
		else if(gp.gameState == gp.combatDialogueState) {
			combatDialogueState(code);
		}
		else if(gp.gameState == gp.combatInventoryState) {
			combatInventoryState(code);
		}

	}

	public void titleState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if (gp.ui.commandNum == 1) {
				gp.saveLoad.load();
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if (gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}

	public void playState(int code) {
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {

			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.mapState;
		}
		if (code == KeyEvent.VK_X) {
			if(gp.map.miniMapOn == false) {
				gp.map.miniMapOn = true;
			}
			else {
				gp.map.miniMapOn = false;
			}
		}

		// Debug
		if (code == KeyEvent.VK_T) {
			if (checkDrawTime == false) {
				checkDrawTime = true;
			} else if (checkDrawTime == true) {
				checkDrawTime = false;
			}
		}

		// Debug - refreshes map
		if (code == KeyEvent.VK_R) {
			switch (gp.currentMap) {
			case 0:
				gp.tileM.loadMap("/maps/world01.txt", 0);
			case 1:
				gp.tileM.loadMap("/maps/interior01.txt", 1);
			}
		}
	}

	public void pauseState(int code) {
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true; // Change to z?
		}
	}

	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		playerInventory(code);
	}

	// For the options menu
	public void optionsState(int code) {

		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 3;
			break;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(9);
				}
			}
		}

		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}
	}

	public void gameOverState(int code) {

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(9);
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}

		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(0);
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.resetGame(true);
			}
		}
	}

	public void tradeState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(9);
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(9);
			}
		}
		if(gp.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		
		if(gp.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}
	
	public void mapState(int code) {
		
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	
	public void combatState(int code) {
		
		
		if (code == KeyEvent.VK_A) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 3;
			}
			gp.playSE(9);
		}

		if (code == KeyEvent.VK_D) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 3) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}
		
		// Fight Option
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.ui.combat_attack();
				gp.ui.commandNum = -1;
				gp.ui.subState = 0;
			} 
		}
		
		// Item Option
		
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 1) {
				gp.ui.combat_item();
				gp.ui.commandNum = -1;
				gp.ui.subState = 1;
			} 
		}
		
		
		// Run Option
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 3) {
				
//				Random random = new Random();
//			    int randomNumber = random.nextInt(100) + 1;
//			    
//			    if(randomNumber > 25) {
//			    	gp.gameState = gp.dialogueState;
//			    }
				
				// I need to make it so escape is not always allowed
				gp.gameState = gp.playState;
				gp.stopMusic();
				gp.playMusic(0);
				gp.ui.commandNum = -1;
				gp.playSE(14);
				gp.player.invincible = true;
			} 
		}
	}
	
	public void combatDialogueState(int code) {
		
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true; // Change to z?
		}
	}

	public void combatInventoryState(int code) {
		
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItemCombat();
			gp.ui.useItem();
			gp.ui.itemUsedInCombat = true;
			gp.ui.commandNum = -1;
			gp.gameState = gp.combatDialogueState;	
		}
		playerInventory(code);
	}
	
	// Player's inventory is independent
	// so we can call it from anywhere
	public void playerInventory(int code) {

		if (code == KeyEvent.VK_W) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
				gp.playSE(9);
			}
		}
	}

	public void npcInventory(int code) {

		if (code == KeyEvent.VK_W) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
				gp.playSE(9);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
	}

}
