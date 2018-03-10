package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.BadPlayer;
import model.BadSession;

public class DatabaseHandler {

	public static String PLAYER_DATABASE_PATH = "player_database.db";
	public static String SESSION_DATABASE_PATH = "session_database.db";

	public static void savePlayerDatabase(ArrayList<BadPlayer> players) {
		
		try {
			FileOutputStream fout = new FileOutputStream(PLAYER_DATABASE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(players);
		} catch (FileNotFoundException e) {
			System.err.println("Player database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error saving player database file.");
			e.printStackTrace();
		}
		
	}

	public static void saveSessionDatabase(ArrayList<BadSession> sessions) {
		
		try {
			FileOutputStream fout = new FileOutputStream(SESSION_DATABASE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(sessions);
		} catch (FileNotFoundException e) {
			System.err.println("Session database file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error saving session database file.");
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

}
