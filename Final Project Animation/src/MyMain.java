public class MyMain {

	public static void main(String[] args) {

		// TASK 1: CREATE A CANVAS FOR ANIMATION
		Canvas canvas = new Canvas();
		canvas.requestFocus();
		
		//TASK 2:  ADD A USER GAME OBJECT
		
		Type_A_GameObject userA = new Type_A_GameObject(100, 0);
		userA.setVelocity(10);
		canvas.addKeyListener(userA);
		canvas.addGameObject(userA);
		
		Type_B_GameObject userB = new Type_B_GameObject(0, 0);
		userB.setVelocity(10);
		canvas.addKeyListener(userB);
		canvas.addGameObject(userB);
		
		Type_C_GameObject userC = new Type_C_GameObject(0, 400);
		userC.setVelocity(10);
		canvas.addKeyListener(userC);
		canvas.addGameObject(userC);
		
		Type_D_GameObject userD = new Type_D_GameObject(200, 200);
		userD.setVelocity(10);
		canvas.addKeyListener(userD);
		canvas.addGameObject(userD);		
	}

}

