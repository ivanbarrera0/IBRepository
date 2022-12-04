import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Type_A_GameObject extends GameObject implements KeyListener {

	// private boolean goUp = false;
	private int highlight;

	public Type_A_GameObject(int x, int y) {
		super(x, y);
		setVelocity(10);
		setDirection(Direction.NONE);

		imageList = new LinkedList<Icon>();
		imageList.add(new ImageIcon("images/Type_A_Up.png"));
		imageList.add(new ImageIcon("images/Type_A_Down.png"));
	}

	public void move(Canvas c) {
		Icon icon = getCurrentImage();

		int iconHeight = icon.getIconHeight();
		int iconWidth = icon.getIconWidth();
		int canvasHeight = (int) c.getSize().getHeight();
		int canvasWidth = (int) c.getSize().getWidth();
		highlight = c.getHighlighted();

		// MOVE BLUE GAME OBJECT

		if (c.getHighlighted() == 0) {
			switch (getDirection()) {
			case Direction.UP:
				setY(getY() - getVelocity());
				if (getY() < 0) {
					setY(0);
				}
				break;
			case Direction.DOWN:
				setY(getY() + getVelocity());
				if (getY() + iconHeight > canvasHeight) {
					setY((int) (canvasHeight - iconHeight));
				}
				break;
			default:
				break;
			}

		} else {

			if (getDirection() == Direction.DOWN) {
				setY(getY() + getVelocity());
				if (getY() + iconWidth > c.getSize().getWidth()) {
					setY((int) (c.getSize().getWidth() - iconWidth));
					setDirection(Direction.UP);
				}

			} else {
				setY(getY() - getVelocity());
				if (getY() < 0) {
					setY(0);

					setDirection(Direction.DOWN);
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
		case Direction.UP:
			currentImage = 0;
			break;
		case Direction.DOWN:
			currentImage = 1;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if(highlight == 0) {
			if (e.getKeyCode() != KeyEvent.VK_TAB) {
				setDirection(Direction.NONE);
			}
		}
	}

	public void keyPressed(KeyEvent e) {

		if (highlight == 0) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				setDirection(Direction.UP);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setDirection(Direction.DOWN);
			}
		}
	}

}
