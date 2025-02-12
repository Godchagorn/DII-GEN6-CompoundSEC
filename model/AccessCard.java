package model;

public class AccessCard {
    private String cardID;
    private AccessLevel accessLevel;
    private boolean isActive;

    public AccessCard(String cardID, AccessLevel accessLevel) {
        this.cardID = cardID;
        this.accessLevel = accessLevel;
        this.isActive = true;
    }

    public String getMaskedCardID(){
        return "****" + cardID.substring(cardID.length()-4);
    }

    public void setAccessLevel(AccessLevel accessLevel, String adminKey){
        if (adminKey.equals("Admin123")) {
            this.accessLevel = accessLevel;
        }else {
            System.out.println("Not allowed!");
        }
    }

    public String getAccessLevel() {
        return accessLevel.getLevelName();
    }

    public boolean canAccess(String area) {
        return accessLevel.canAccess(area);
    }
    public boolean isActive(){
        return isActive;
    }
    public void deactivate() {
        this.isActive = false;
    }

}