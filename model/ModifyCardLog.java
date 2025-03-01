package model;

public class ModifyCardLog extends AuditLog {
    private String userName;
    private String oldFloor;
    private String oldRoom;
    private String newFloor;
    private String newRoom;

    public ModifyCardLog(String cardID, String userName, String oldFloor, String oldRoom, String newFloor, String newRoom) {
        super(cardID);
        this.userName = userName;
        this.oldFloor = oldFloor;
        this.oldRoom = oldRoom;
        this.newFloor = newFloor;
        this.newRoom = newRoom;
    }

    @Override
    public String logEvent() {
        return "Timestamp: " + timestamp + " │ Modified: " + " │ Name: " + userName + " │ CardID: " + cardID + " │ OldFloor: " + oldFloor + ", NewFloor: " + newFloor +" │ OldRoom: " + oldRoom +  ", NewRoom: " + newRoom;
    }
}
