package model;

public class LowFloorAccess extends AccessLevel {
    public LowFloorAccess() {
        super("Low Floor");
    }

    @Override
    public boolean canAccess(String area) {
        return area.equalsIgnoreCase("Low Floor");
    }
}