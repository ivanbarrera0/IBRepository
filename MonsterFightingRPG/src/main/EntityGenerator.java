package main;

import entity.Entity;
import object.OBJ_Axe;
import object.OBJ_Axe_Diamond;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Blue;
import object.OBJ_Potion_Gold;
import object.OBJ_Potion_Red;
import object.OBJ_Rock;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Diamond;
import object.OBJ_Sword_Normal;

public class EntityGenerator {

	GamePanel gp;

	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}

	public Entity getObject(String itemName) {

		Entity obj = null;

		// Save the items in the inventory
		// Need to change the reference when changing the item name
		switch (itemName) {
		case OBJ_Axe.objName:
			obj = new OBJ_Axe(gp);
			break;
		case OBJ_Key.objName:
			obj = new OBJ_Key(gp);
			break;
		case OBJ_Potion_Red.objName:
			obj = new OBJ_Potion_Red(gp);
			break;
		case OBJ_Shield_Blue.objName:
			obj = new OBJ_Shield_Blue(gp);
			break;
		case OBJ_Shield_Wood.objName:
			obj = new OBJ_Shield_Wood(gp);
			break;
		case OBJ_Sword_Normal.objName:
			obj = new OBJ_Sword_Normal(gp);
			break;
		case OBJ_Door.objName:
			obj = new OBJ_Door(gp);
			break;
		case OBJ_Chest.objName:
			obj = new OBJ_Chest(gp);
			break;
		case OBJ_Heart.objName:
			obj = new OBJ_Heart(gp);
			break;
		case OBJ_ManaCrystal.objName:
			obj = new OBJ_ManaCrystal(gp);
			break;
		case OBJ_Rock.objName:
			obj = new OBJ_Rock(gp);
			break;
		case OBJ_Coin_Bronze.objName:
			obj = new OBJ_Coin_Bronze(gp);
			break;
		case OBJ_Potion_Blue.objName:
			obj = new OBJ_Potion_Blue(gp);
			break;
		case OBJ_Potion_Gold.objName:
			obj = new OBJ_Potion_Gold(gp);
			break;
		case OBJ_Axe_Diamond.objName:
			obj = new OBJ_Axe_Diamond(gp);
			break;
		case OBJ_Sword_Diamond.objName:
			obj = new OBJ_Sword_Diamond(gp);
			break;
		}
		return obj;
	}
}
