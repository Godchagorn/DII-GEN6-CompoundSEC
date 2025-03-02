package decorator;

import strategy.AccessStrategy;

public abstract class AccessControlDecorator implements AccessStrategy {
    protected AccessStrategy decoratedAccess;

    public AccessControlDecorator(AccessStrategy decoratedAccess) {
        this.decoratedAccess = decoratedAccess;
    }

    @Override
    public boolean hasAccess(String userId, String room) {
        return decoratedAccess.hasAccess(userId, room);
    }
}
