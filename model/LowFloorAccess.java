package model;

public class LowFloorAccess extends AccessLevel {
    public LowFloorAccess() {
        super("Low Floor");
    }

    @Override
    public boolean canAccess(String area) {
        return area.equalsIgnoreCase("Low Floor");
    }

    @Override
    public String grantAccess(){
        return "Access Granted: Low Floor - Open to all basic cardholders";
    }
}