package model;

public class Match {
    private boolean hasEnded = false;

    public void setMatchEnd(){
        hasEnded = true;
    }

    public boolean hasEnded(){
        return hasEnded;
    }
}
