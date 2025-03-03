package decorator;

import strategy.AccessStrategy;

public class LoggingDecorator extends AccessControlDecorator {
    public LoggingDecorator(AccessStrategy decoratedAccess) {
        super(decoratedAccess);
    }


    //Each attempts will be logged with necessary info
    //

    @Override
    public boolean hasAccess(String userId, String room) {
        boolean result = super.hasAccess(userId, room);
        System.out.println("User " + userId + " accessed " + room + ": " + (result ? "GRANTED" : "DENIED"));
        return result;
    }
}
