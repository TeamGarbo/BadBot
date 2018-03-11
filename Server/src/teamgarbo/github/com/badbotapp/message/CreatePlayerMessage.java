package teamgarbo.github.com.badbotapp.message;

public class CreatePlayerMessage extends Message{

	String name;
	int elo;
	
	public CreatePlayerMessage(String clubID, String playerID, String name, int elo) {
		super(clubID, playerID);
		this.name = name;
		this.elo = elo; 
	}

	public String getName() {
		return name;
	}

	public int getElo() {
		return elo;
	}

	

	
}
