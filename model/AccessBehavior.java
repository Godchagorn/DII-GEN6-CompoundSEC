package model;

public interface AccessBehavior {
    boolean canAccess(String area);
    String grantAccess();
}
