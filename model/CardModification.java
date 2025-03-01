package model;

public class CardModification extends AuditLog {
    private String name;
    private String action;
    private String floor;
    private String room;

    public CardModification(String cardID, String name,
                            String action, String floor, String room) {
        super(cardID);
        this.name = name;
        this.action = action;
        this.floor = floor;
        this.room = room;
    }

    @Override
    public String logEvent() {
        return timestamp + " - Card ID: " + cardID +
                " | Name: " + name +
                " | Floor: " + (floor != null ? floor : "N/A") +
                " | Room: " + (room != null ? room : "N/A") +
                " | Action: " + action;

    }
}
