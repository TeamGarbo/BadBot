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
			int binStart = club.getLowestElo();
			int binEnd = club.getHighestElo();
			int binSize = (binEnd-binStart)/5;

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}
