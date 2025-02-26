package model;

import java.util.Objects;

public class AccessCard {
    private String cardID;
    private AccessBehavior accessBehavior;
    private boolean isActive;

    public AccessCard(String cardID, AccessBehavior accessBehavior) {
        this.cardID = cardID;
        this.accessBehavior = accessBehavior;
        this.isActive = true;
    }

    public void setAccessBehavior(AccessBehavior accessBehavior, String adminKey) {
        if (adminKey.equals("Admin123")) {
            this.accessBehavior = accessBehavior;
        } else {
            System.out.println("Not allowed!");
        }
    }

    public String getAccessLevel() {
        return accessBehavior.grantAccess();
    }

    public boolean canAccess(String area) {
        return accessBehavior.canAccess(area);
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessCard that = (AccessCard) obj;
        return Objects.equals(cardID, that.cardID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardID);
    }
}
