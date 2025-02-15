package model;

public class CardModification extends AuditLog {
    private String action;
    private String newAccessLevel;

    public CardModification(String cardID, String action, String newAccessLevel) {
        super(cardID);
        this.action = action;
        this.newAccessLevel = newAccessLevel;
    }

    @Override
    public String logEvent() {
        return timestamp + " - Card ID: " + cardID +
                " - Action: " + action +
                (newAccessLevel != null ? " - New Access Level: " + newAccessLevel : "");
    }
}
