package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.BadClub;
import model.BadPlayer;
import model.BadSession;

public class DatabaseHandler {

	private static String PLAYER_DATABASE_PATH = "player_database.db";
	private static String SESSION_DATABASE_PATH = "session_database.db";
	private static String CLUBS_DATABASE_PATH = "club_databases/";
	
	public static void savePlayerDatabase(ArrayList<BadPlayer> players) {

		try {
			FileOutputStream fout = new FileOutputStream(PLAYER_DATABASE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(players);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("Player database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error saving player database file.");
			e.printStackTrace();
		}

	}

	public static ArrayList<BadPlayer> getPlayerDatabase() {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PLAYER_DATABASE_PATH));
			return (ArrayList<BadPlayer>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Player database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error writing database files.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading specified class.");
			e.printStackTrace();
		}

		return null;

	}

	public static void saveSessionDatabase(ArrayList<BadSession> sessions) {

		try {
			FileOutputStream fout = new FileOutputStream(SESSION_DATABASE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(sessions);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("Session database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error saving session database file.");
			e.printStackTrace();
		}

	}

	public static ArrayList<BadSession> getSessionDatabase() {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_DATABASE_PATH));
			return (ArrayList<BadSession>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Session database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error writing database files.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading specified class.");
			e.printStackTrace();
		}

		return null;

	}
	
	public static void savePlayerDatabase(BadClub club) {

		try {
			FileOutputStream fout = new FileOutputStream(CLUBS_DATABASE_PATH + club.getClubID() + ".db");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(club);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("Club database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error saving club database file.");
			e.printStackTrace();
		}

	}
	
	public static BadClub getSessionDatabase(String id) {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CLUBS_DATABASE_PATH + id + ".db"));
			return (BadClub) ois.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Session database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error writing database files.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading specified class.");
			e.printStackTrace();
		}

		return null;

	}
}
