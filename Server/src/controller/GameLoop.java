package controller;

import model.BadClub;

public class GameLoop implements Runnable{

	BadClub club;
	
	public GameLoop(BadClub badClub) {
		this.club = badClub;
		new Thread(this).start();
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}
