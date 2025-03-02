package model;

import java.time.LocalDate;

public class CardModification extends AuditLog {
    private String name;
    private String action;
    private String oldFloor;
    private String oldRoom;
    private String newFloor;
    private String newRoom;
    private LocalDate newExpiryDate;

    public CardModification(String cardID, String name, String action, String oldFloor, String oldRoom,
                            String newFloor, String newRoom, LocalDate newExpiryDate) {
        super(cardID);
        this.name = name;
        this.action = action;
        this.oldFloor = oldFloor;
        this.oldRoom = oldRoom;
        this.newFloor = newFloor;
        this.newRoom = newRoom;
        this.newExpiryDate = newExpiryDate;
    }

    @Override
    public String logEvent() {
        return timestamp + " - Card ID: " + cardID +
                " | Name: " + name +
                " | Old Floor: " + oldFloor +
                " | Old Room: " + oldRoom +
                " | New Floor: " + newFloor +
                " | New Room: " + newRoom +
                " | New Expiry Date: " + newExpiryDate +
                " | Action: " + action;
    }
}
