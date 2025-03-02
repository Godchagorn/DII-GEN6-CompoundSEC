package strategy;

import service.AccessControlSystem;
import model.AccessCard;

public class AttributeBasedAccess implements AccessStrategy {
    private AccessControlSystem accessControlSystem;

    public AttributeBasedAccess(AccessControlSystem accessControlSystem) {
        this.accessControlSystem = accessControlSystem;
    }

    @Override
    public boolean hasAccess(String userId, String room) {
        AccessCard card = accessControlSystem.getCard(userId);
        return card != null && card.isWithinAccessDate() && card.getRoom().equals(room);
    }
}
