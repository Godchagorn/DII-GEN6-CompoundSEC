package strategy;

import service.AccessControlSystem;
import model.AccessCard;

public class RoleBasedAccess implements AccessStrategy {
    private AccessControlSystem accessControlSystem;

    public RoleBasedAccess(AccessControlSystem accessControlSystem) {
        this.accessControlSystem = accessControlSystem;
    }

    @Override
    public boolean hasAccess(String userId, String room) {
        AccessCard card = accessControlSystem.getCard(userId);
        if (card == null) return false;

        String assignedRoom = card.getRoom();
        return assignedRoom.equals(room);
    }
}
