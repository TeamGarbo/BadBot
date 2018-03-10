package controller;

public class Controller {

	
	private Controller() {
		
	}
	
	private static Controller instance;
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
}
