package strategy;

public interface AccessStrategy {
    boolean hasAccess(String userId, String room);
}
