package main;

import java.util.Random;

public class CombatDialogue {

	GamePanel gp;

	public int damagedPlayer;
	public int damagedEnemy;
	public String usedItem;
	public boolean isCritical = false;
	public boolean escape = false;
	int monsterExp;
	int coin;

	public CombatDialogue(GamePanel gp) {
		this.gp = gp;
	}

	public String combatDialogue(int c, String attacker, String defender) {

		String dialogue = "";

		switch (c) {
		case 0:
			dialogue = attacker + " attacked! " + defender + " took " + damagedEnemy + " damage.";
			break;
		case 1:
			dialogue = attacker + " attacked! " + defender + " took " + damagedPlayer + " damage.";
			break;
		}

		return dialogue;
	}

	public String defeatedDialogue(String defeated, String coinsGained) {

		String dialogue = "";
		dialogue += defeated + " was slain! " + "Player gained " + monsterExp + " exp" + "\nYou gained " + coinsGained
				+ " coins";
		return dialogue;
	}
	
	public String defeatedPlayerDialogue() {
		
		String dialogue = "You were defeated...";
		return dialogue;
	}

	public String usedItemInCombat(String item) {

		String dialogue = "";
		dialogue += "Player used " + item;
		if (item == "Red Potion") {
			dialogue += " you recovered \nsome health";
		} else if (item == "Woodcutter's Axe" || item == "Normal Sword") {
			dialogue = "Player became equipped with " + item;
		} else if (item == "Key") {
			dialogue = "You showed the monster a key...";
		}
		return dialogue;
	}
	
	public String escapeMessage() {
		
		String dialogue = "";
		
		if(escape == true) {
			dialogue = "You ran away";
		}
		else {
			dialogue = "You were not able to escape";
		}
		
		return dialogue;
	}

	public void getMonsterExp(int exp) {
		monsterExp = exp;
	}

	public void getUsedItemName(String usedItemName) {
		usedItem = usedItemName;
	}

	public void criticalHitCheck() {
		Random random = new Random();
		int randomNumber = random.nextInt(100) + 1;
		if (randomNumber <= 50) {
			isCritical = true;
		}
	}
}
