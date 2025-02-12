package service;

import model.*;
import java.util.HashMap;

public class AccessControlSystem implements CardManagementInterface {
    private HashMap<String, AccessCard> cards;

    public AccessControlSystem() {
        cards = new HashMap<>();
    }

    private AccessLevel getAccessLevel(String accessLevel){
        switch (accessLevel.toLowerCase()) {
            case "medium floor":
                return new MediumFloorAccess();
            case "high floor":
                return new HighFloorAccess();
            default:
                return new LowFloorAccess();
        }
    }

    @Override
    public void addCard(String cardID, String accessLevel) {
        cards.put(cardID, new AccessCard(cardID, getAccessLevel(accessLevel)));
    }

    @Override
    public void modifyCard(String cardID, String newAccessLevel) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).setAccessLevel(getAccessLevel(newAccessLevel), "Admin123");
        }
    }

    @Override
    public void revokeCard(String cardID) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).deactivate();
        }
    }

    public boolean checkAccess(String cardID, String requestedArea) {
        if (cards.containsKey(cardID) && cards.get(cardID).isActive()) {
            return cards.get(cardID).canAccess(requestedArea);
        }
        return false;
    }
}