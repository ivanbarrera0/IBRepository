import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Type_B_GameObject extends GameObject implements KeyListener {

	private int wall = 800;
	private int highlight;

	public Type_B_GameObject(int x, int y) {
		super(x, y);
		setDirection(Direction.NONE);

		imageList = new LinkedList<Icon>();
		imageList.add(new ImageIcon("images/Type_B_Left.png"));
		imageList.add(new ImageIcon("images/Type_B_Right.png"));
		imageList.add(new ImageIcon("images/Type_B_Down.png"));

	}

	public void move(Canvas c) {
		Icon icon = getCurrentImage();

		int iconHeight = icon.getIconHeight();
		int iconWidth = icon.getIconWidth();
		int canvasHeight = (int) c.getSize().getHeight();
		int canvasWidth = (int) c.getSize().getWidth();
		highlight = c.getHighlighted();

		// MOVE BLUE GAME OBJECT

		if (c.getHighlighted() == 1) {
			switch (getDirection()) {
			case Direction.DOWN:
				setY(getY() + getVelocity());
				if (getY() + iconHeight > canvasHeight) {
					setY((int) (canvasHeight - iconHeight));
				}
				break;
			case Direction.LEFT:
				setX(getX() + getVelocity());
				if (getX() + iconWidth > canvasWidth) {
					setX((int) (canvasWidth - iconWidth));
				}
				break;
			case Direction.RIGHT:
				setX(getX() - getVelocity());
				if (getX() < 0) {
					setX(0);
				}
				break;
			default:
				break;
			}
		} else {

			if (getDirection() == Direction.RIGHT) {
				setX(getX() - getVelocity());
				if (getX() < 0) {
					setX(0);

					setDirection(Direction.DOWN);
				}

			} else if (getDirection() == Direction.DOWN) {
				setY(getY() + getVelocity());
				if (getY() + iconWidth > wall) {
					setY((int) (wall - iconWidth));
					setDirection(Direction.LEFT);
				}
			} else {
				// Direction left
				setX(getX() + getVelocity());
				if (getX() + iconWidth > c.getSize().getWidth()) {
					setX((int) (c.getSize().getWidth() - iconWidth));
					setDirection(Direction.RIGHT);
				}
			}
		}
	}

	// SPECIFY THE IMAGE TO DISPLAY
	// USED FOR ANIMATION
	public void setImage() {
		switch (getDirection()) {
		case Direction.NONE:
			break;
		case Direction.LEFT:
			currentImage = 0;
			break;
		case Direction.RIGHT:
			currentImage = 1;
			break;
		case Direction.DOWN:
			currentImage = 2;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if (highlight == 1) {
			if (e.getKeyCode() != KeyEvent.VK_TAB) {
				setDirection(Direction.NONE);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (highlight == 1) {

			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setDirection(Direction.DOWN);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				setDirection(Direction.LEFT);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				setDirection(Direction.RIGHT);
			}
		}
	}

}
