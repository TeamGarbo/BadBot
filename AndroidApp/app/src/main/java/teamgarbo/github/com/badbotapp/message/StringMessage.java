package teamgarbo.github.com.badbotapp.message;

public class StringMessage extends Message {

	private String string;
	
	public StringMessage(String clubID, String playerID, String string) {
		super(clubID, playerID);
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
