package teamgarbo.github.com.badbotapp.message;

public class GameStartMessage extends Message {
    private int courtNumber;

    public GameStartMessage(String clubID, String playerID, int courtNumber){
        super(clubID, playerID);
        this.courtNumber = courtNumber;
    }

    public int getCourtNumber() {
        return courtNumber;
    }
}
