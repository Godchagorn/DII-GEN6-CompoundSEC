package model;

public class AccessCard {
    private String cardID;
    private String accessLevel;
    private boolean isActive;

    public AccessCard(String cardID, String accessLevel) {
        this.cardID = cardID;
        this.accessLevel = accessLevel;
        this.isActive = true;
    }

    public String getCardID() { return cardID; }
    public String getAccessLevel() { return accessLevel; }
    public boolean isActive() { return isActive; }

    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
    public void deactivate() { this.isActive = false; }
}