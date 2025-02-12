package service;

import model.AccessCard;
import java.util.HashMap;

public class AccessControlSystem implements CardManagementInterface {
    private HashMap<String, AccessCard> cards;

    public AccessControlSystem() {
        cards = new HashMap<>();
    }

    @Override
    public void addCard(String cardID, String accessLevel) {
        cards.put(cardID, new AccessCard(cardID, accessLevel));
    }

    @Override
    public void modifyCard(String cardID, String newAccessLevel) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).setAccessLevel(newAccessLevel);
        }
    }

    @Override
    public void revokeCard(String cardID) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).deactivate();
        }
    }

    public boolean checkAccess(String cardID, String requestedAccessLevel) {
        if (cards.containsKey(cardID) && cards.get(cardID).isActive()) {
            return cards.get(cardID).getAccessLevel().equals(requestedAccessLevel);
        }
        return false;
    }
}