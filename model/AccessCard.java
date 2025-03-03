package model;

import java.time.LocalDate;

//Floor level access control and room level access control

//ใช้ข้อมูล floor ใน AccessCard เพื่อตรวจสอบว่าผู้ใช้สามารถเข้าใช้ชั้นไหนได้
//Admin สามารถกำหนดหรือเปลี่ยนแปลง floor ของบัตรได้

//ใช้ข้อมูล room ใน AccessCard ตรวจสอบว่าผู้ใช้มีสิทธิ์เข้าใช้ห้องใด
//Admin สามารถกำหนดหรือแก้ไข room ที่ User สามารถเข้าถึงได้
//มีการใช้ Strategy Pattern ใน RoleBasedAccess และ AttributeBasedAccess เพื่อตรวจสอบสิทธิ์

public class AccessCard {
    private String cardID;
    private String userId;
    private String floor;
    private String room;
    private String name;
    private boolean active;
    private LocalDate expiryDate;

    public AccessCard(String cardID, String userId, String floor, String room, String name, LocalDate expiryDate) {
        this.cardID = cardID;
        this.userId = userId;
        this.floor = floor;
        this.room = room;
        this.name = name;
        this.expiryDate = expiryDate;
        this.active = true;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardID() {
        return cardID;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String newFloor) {
        this.floor = newFloor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String newRoom) {
        this.room = newRoom;
    }



    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean canAccessRoom(String room) {
        return room.startsWith(floor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessCard that = (AccessCard) obj;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card ID: " + cardID + ", User ID: " + userId + ", Name: " + name +
                ", Floor: " + floor + ", Room: " + room + ", Expiry Date: " + expiryDate;
    }



    //Time-Based Encryption
    //ใช้ isWithinAccessDate() ตรวจสอบว่าบัตรยังสามารถใช้งานได้หรือไม่ หากหมดอายุแล้ว จะไม่สามารถเข้าใช้ระบบได้
    public boolean isWithinAccessDate() {
        return LocalDate.now().isBefore(expiryDate) || LocalDate.now().isEqual(expiryDate);
    }
}
