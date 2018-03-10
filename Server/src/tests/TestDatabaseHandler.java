package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.BadPlayer;

public class TestDatabaseHandler {

	@Test
	public void test() {
		ArrayList<BadPlayer> players = new ArrayList<>();
		players.add(new BadPlayer("name1", "1"));
		players.add(new BadPlayer("name2", "2"));
		players.add(new BadPlayer("name3", "3"));
		
	}

}
