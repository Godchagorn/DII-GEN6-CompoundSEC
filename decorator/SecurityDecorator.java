package decorator;

import strategy.AccessStrategy;

import java.time.LocalTime;

public class SecurityDecorator extends AccessControlDecorator {
    public SecurityDecorator(AccessStrategy decoratedAccess) {
        super(decoratedAccess);
    }

    @Override
    public boolean hasAccess(String userId, String room) {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(22, 0))) {
            System.out.println("Access Denied: Outside of operational hours.");
            return false;
        }
        return super.hasAccess(userId, room);
    }
}
