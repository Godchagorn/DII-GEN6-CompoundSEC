package model;

import java.time.LocalDate;

public class ModifyCardLog extends AuditLog {
    private String userName;
    private String oldFloor;
    private String oldRoom;
    private String newFloor;
    private String newRoom;
    private LocalDate newExpiryDate;

    public ModifyCardLog(String cardID, String userName, String oldFloor, String oldRoom,
                         String newFloor, String newRoom, LocalDate newExpiryDate) {
        super(cardID);
        this.userName = userName;
        this.oldFloor = oldFloor;
        this.oldRoom = oldRoom;
        this.newFloor = newFloor;
        this.newRoom = newRoom;
        this.newExpiryDate = newExpiryDate;
    }

    @Override
    public String logEvent() {
        return "Timestamp: " + timestamp + " │ Modified: " + " │ CardID: " + cardID +
                " │ Name: " + userName + " │ OldFloor: " + oldFloor + ", NewFloor: " + newFloor +
                " │ OldRoom: " + oldRoom + ", NewRoom: " + newRoom +
                " │ New Expiry Date: " + newExpiryDate;
    }
}
