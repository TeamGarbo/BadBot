package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import tests.DatabaseHandler;
import model.BadClub;
import model.BadPlayer;
import model.BadSession;

public class TestDatabaseHandler {

	@Test
	public void testPlayers() {
		ArrayList<BadPlayer> expectedPlayers = new ArrayList<>();
		expectedPlayers.add(new BadPlayer("name1", "1"));
		expectedPlayers.add(new BadPlayer("name2", "2"));
		expectedPlayers.add(new BadPlayer("name3", "3"));

		DatabaseHandler.savePlayerDatabase(expectedPlayers);

		ArrayList<BadPlayer> currentPlayers = DatabaseHandler.getPlayerDatabase();

		assertEquals(expectedPlayers.size(), currentPlayers.size());

		for (int i = 0; i < expectedPlayers.size(); ++i)
			assertTrue(expectedPlayers.get(i).getID().equals(currentPlayers.get(i).getID()));
	}

	@Test
	public void testSessions() {
		ArrayList<BadPlayer> expectedPlayers = new ArrayList<>();
		expectedPlayers.add(new BadPlayer("name1", "1"));
		expectedPlayers.add(new BadPlayer("name2", "2"));
		expectedPlayers.add(new BadPlayer("name3", "3"));

		ArrayList<BadSession> expectedSessions = new ArrayList<>();
		BadSession expectedSession = new BadSession(new Date());
		expectedSession.addPlayers(expectedPlayers);
		expectedSessions.add(expectedSession);

		DatabaseHandler.saveSessionDatabase(expectedSessions);

		ArrayList<BadSession> currentSessions = DatabaseHandler.getSessionDatabase();

		ArrayList<BadPlayer> currentPlayers = currentSessions.get(0).getPlayers();

		
		
		//assertEquals(expectedPlayers.size(), currentPlayers.size());

//		for (int i = 0; i < expectedPlayers.size(); ++i) {
//			assertTrue(expectedPlayers.get(i).getID().equals(currentPlayers.get(i).getID()));
//			System.out.println(currentPlayers.get(i).getID());
//		}
	}
	
	@Test
	public void testName() throws Exception {
		ArrayList<BadPlayer> expectedPlayers = new ArrayList<>();
		expectedPlayers.add(new BadPlayer("name1", "1"));
		expectedPlayers.add(new BadPlayer("name2", "2"));
		expectedPlayers.add(new BadPlayer("name3", "3"));
		
		BadClub expectedClub = new BadClub();
	}
}
