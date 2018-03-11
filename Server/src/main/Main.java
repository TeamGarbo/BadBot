package main;


import controller.Controller;

//HELLO
public class Main {

	public static void main(String[] args) {
		System.out.println("Server: starting...");
		Controller controller = Controller.getInstance();
		controller.addClub("recbad", 5, 2);
		controller.init();
	}
}
