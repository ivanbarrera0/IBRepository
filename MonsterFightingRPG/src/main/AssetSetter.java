package main;

import entity.NPC_Girl;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_Bat;
import monster.MON_Ghost;
import monster.MON_GreenSlime;
import monster.MON_RedSlime;
import monster.MON_Señor_Ghost;
import monster.MON_Snake;
import monster.MON_Spider;
import monster.MON_Zombie;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Save;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*20;
		gp.obj[mapNum][i].worldY = gp.tileSize*43;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*7;
		gp.obj[mapNum][i].worldY = gp.tileSize*46;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*6;
		gp.obj[mapNum][i].worldY = gp.tileSize*7;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*6;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*42;
		gp.obj[mapNum][i].worldY = gp.tileSize*5;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*45;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*22;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*23;
		gp.obj[mapNum][i].worldY = gp.tileSize*22;
		i++;
		
		mapNum = 1;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*5;
		gp.obj[mapNum][i].worldY = gp.tileSize*38;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*6;
		gp.obj[mapNum][i].worldY = gp.tileSize*31;
		i++;
		
		// The next two should contain rare items
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*46;
		gp.obj[mapNum][i].worldY = gp.tileSize*41;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*46;
		gp.obj[mapNum][i].worldY = gp.tileSize*45;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*46;
		gp.obj[mapNum][i].worldY = gp.tileSize*29;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*46;
		gp.obj[mapNum][i].worldY = gp.tileSize*4;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*5;
		i++;
	}
	
	public void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*25;
		gp.npc[mapNum][i].worldY = gp.tileSize*43;
		i++;
		
//		gp.npc[mapNum][i] = new NPC_OldMan(gp);
//		gp.npc[mapNum][i].worldX = gp.tileSize*22;
//		gp.npc[mapNum][i].worldY = gp.tileSize*22;
//		i++;
//		
//		gp.npc[mapNum][i] = new NPC_Girl(gp);
//		gp.npc[mapNum][i].worldX = gp.tileSize*23;
//		gp.npc[mapNum][i].worldY = gp.tileSize*8;
//		
		mapNum = 1;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*6;
		gp.npc[mapNum][i].worldY = gp.tileSize*7;
		i++;
		
		
		mapNum = 2;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*27;
		gp.npc[mapNum][i].worldY = gp.tileSize*19;
		
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*20;
		gp.monster[mapNum][i].worldY = gp.tileSize*25;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*25;
		gp.monster[mapNum][i].worldY = gp.tileSize*25;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*38;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[mapNum][i] = new MON_Snake(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*44;
		gp.monster[mapNum][i].worldY = gp.tileSize*44;
		i++;
		
		gp.monster[mapNum][i] = new MON_Snake(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*42;
		gp.monster[mapNum][i].worldY = gp.tileSize*20;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*43;
		gp.monster[mapNum][i].worldY = gp.tileSize*13;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*7;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
		i++;
		
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*39;
		gp.monster[mapNum][i].worldY = gp.tileSize*45;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*9;
		gp.monster[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*9;
		gp.monster[mapNum][i].worldY = gp.tileSize*7;
		i++;
		
		gp.monster[mapNum][i] = new MON_Snake(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*13;
		i++;
		
		mapNum = 1;
		i = 0;
		
		gp.monster[mapNum][i] = new MON_Spider(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*10;
		gp.monster[mapNum][i].worldY = gp.tileSize*19;
		i++;
		
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*6;
		gp.monster[mapNum][i].worldY = gp.tileSize*26;
		i++;
		
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*9;
		gp.monster[mapNum][i].worldY = gp.tileSize*33;
		i++;
		
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*24;
		gp.monster[mapNum][i].worldY = gp.tileSize*43;
		i++;
		
		// This should be the Boss Ghost
		gp.monster[mapNum][i] = new MON_Señor_Ghost(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*44;
		gp.monster[mapNum][i].worldY = gp.tileSize*43;
		i++;
		
		gp.monster[mapNum][i] = new MON_Ghost(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*43;
		gp.monster[mapNum][i].worldY = gp.tileSize*41;
		i++;
		
		gp.monster[mapNum][i] = new MON_Ghost(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*43;
		gp.monster[mapNum][i].worldY = gp.tileSize*45;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*44;
		gp.monster[mapNum][i].worldY = gp.tileSize*35;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*44;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*36;
		i++;
		
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*29;
		i++;
		
		gp.monster[mapNum][i] = new MON_Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*43;
		gp.monster[mapNum][i].worldY = gp.tileSize*8;
		i++;
		
		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*28;
		gp.monster[mapNum][i].worldY = gp.tileSize*5;
		i++;
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 44);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 45);
		i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 46);
		i++;
//		
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 7);
//		i++;
//		
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 7);
//		i++;
//		
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 7);
//		i++;
//		
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 7);
//		i++;
//		
//		// To the entrance of the hut
//		
	}
}
