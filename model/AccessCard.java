package model;

import java.util.Objects;

public class AccessCard {
    private String cardID;
    private AccessLevel accessLevel;
    private boolean isActive;

    public AccessCard(String cardID, AccessLevel accessLevel) {
        this.cardID = cardID;
        this.accessLevel = accessLevel;
        this.isActive = true;
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
    public boolean isActive() {
        return isActive;
    }
    public void deactivate(){
        this.isActive = false;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessCard that = (AccessCard) obj;
        return Objects.equals(cardID, that.cardID);
    }

    @Override
    public int hashCode(){
        return Objects.hash(cardID);
    }
}