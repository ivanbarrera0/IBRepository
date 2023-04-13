package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = false;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		// Updated from 10 to allow more tiles
		tile = new Tile[30];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/NewMap3.txt", 0);
		loadMap("/maps/CaveMap.txt", 1);
		loadMap("/maps/merchanthut.txt", 2);
	}
	
	public void getTileImage() {
			
		
		setup(0, "000", false);
		setup(1, "033", false);
		setup(2, "036", false);
		setup(3, "037", false);
		setup(4, "appletree", true);
		setup(5, "bellflower", false);
		setup(6, "cave", true);
		setup(7, "cave_dirt_down", true);
		setup(8, "cave_dirt_left", true);
		setup(9, "cave_dirt_right", true);
		setup(10, "cave_dirt_top", true);
		setup(11, "cherryblossomtree", false);
		setup(12, "dirt", false);
		setup(13, "earth", false);
		setup(14, "grass", false);
		setup(15, "mushroom", false);
		setup(16, "sunflower", false);
		setup(17, "table01", true);
		setup(18, "tree", true);
		setup(19, "water001", true);
		setup(20, "water002", true);
		setup(21, "floor01", false);
		setup(22, "wall", true);
		
		// Here is the old tiles
		/*
			setup(0, "newgrass", false);
			setup(1, "wall", true);
			setup(2, "newwater", true);
			setup(3, "earth", false);
			setup(4, "newtree", true);
			setup(5, "sand", false);
			setup(6, "hut", false);
			setup(7, "floor01", false);
			setup(8, "table01", true);
			*/
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath, int map) {
		
		try {
			// Read map data
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// Draw the tiles that are shown to improve rendering
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
		if(drawPath == true) {
			g2.setColor(new Color(255, 0, 0, 70));
			
			for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
				
				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
			
		}
	}
}
