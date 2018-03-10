package main;

import controller.Controller;

public class Main {

	public static void main(String[] args) {
		System.out.println("Server: starting...");
		Controller controller = Controller.getInstance();
		controller.addClub("recbad");
		controller.init();
	}
	
}
