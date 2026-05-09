import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * A simple CollisionObject that handles collision on some layers.
 * 
 * @author buddha
 * @version 1
 */

public abstract class CollisionObject extends Actor
{
    private CollisionShape shape = null;
    private final int collisionLayers;
    private final int collisionMasks;

    public int x;
    public int y;
    private long lastCall;

    private CollisionObject lastCollider;
    private CollisionObject ground;
    private int lastgroundx, lastgroundy;

    public CollisionObject(int collisionLayers, int collisionMasks)
    {
        this.collisionLayers = collisionLayers;
        this.collisionMasks = collisionMasks;
        this.lastCall = System.currentTimeMillis();
    }

    public void setCollisionShape(CollisionShape shape)
    {
        this.shape = shape;
    }

    public boolean isTouching(Class klass)
    {
        return touching(klass) != null;
    }

    private Actor touching(Class klass)
    {
        if(shape == null) {
            return null;
        }
        if(super.isTouching(klass)) {
            if(!klass.isAssignableFrom(CollisionObject.class)) {
                // Wir prüfen nur CollisionObjects auf ihre CollisionShapes :-)
                return getOneIntersectingObject(klass.asSubclass(Actor.class));
            }
            // Holt sich alle CollisionShapes
            java.util.List<CollisionObject> intersecting = getIntersectingObjects(klass.asSubclass(CollisionObject.class));
            for(int i = 0; i < intersecting.size(); i++) {
                CollisionObject current = intersecting.get(i);
                // Prüft, ob die CollisionShapes sich "erkennen" können und ineinander sind
                if((current.collisionLayers & this.collisionMasks) > 0 && current.isIntersecting(this.shape)) {
                    return current;
                }
            }
        }
        return null;
    }

    public boolean isIntersecting(CollisionShape shape)
    {
        if(shape.isColliding(this.shape)) {
            return true;
        }
        return false;
    }

    private boolean stepx(int sx) {
        this.x += sx;
        setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
        lastCollider = (CollisionObject) touching(CollisionObject.class);
        if(lastCollider != null) {
            this.x -= sx;
            return true;
        }
        return false;
    }

    private boolean stepy(int sy) {
        this.y += sy;
        setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
        lastCollider = (CollisionObject) touching(CollisionObject.class);
        if(lastCollider != null) {
            this.y -= sy;
            return true;
        }
        return false;
    }

    private boolean move(int dx, int dy)
    {
        int x = this.x + dx;
        int y = this.y + dy;
        int sx = Math.abs(dx) / dx;
        int sy = Math.abs(dy) / dy;
        int step = 0;
        if(dx == 0) {
            while(step <= Math.abs(dy)) {
                if(!stepy(sx)) {
                    return true;
                }
                step++;
            }
        }
        else if(dy == 0) {
            while(step <= Math.abs(dx)) {
                if(!stepx(sx)) {
                    return true;
                }
                step++;
            }
        }
        else {
            int err = dx - dy;
            int e2, tmpx, tmpy;
            boolean xblocked = false;
            boolean yblocked = false;
            do {
                if(!xblocked && !yblocked) {
                    tmpx = this.x;
                    tmpy = this.y;
                    e2 = 2 * err;
                    if(e2 > -dy) {
                        err -= dy;
                        this.x += sx;
                    }
                    if(e2 < dx) {
                        err += dx;
                        this.y += sy;
                    }
                    setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
                    lastCollider = (CollisionObject) touching(CollisionObject.class);
                    if(lastCollider != null) {
                        this.x = tmpx;
                        this.y = tmpy;
                        return true;
                    }
                }
                else if(xblocked) {
                    yblocked = stepy(sy);
                    if(yblocked) {
                        return true;
                    }
                }
                else {
                    xblocked = stepx(sx);
                    if(xblocked) {
                        return true;
                    }
                }
            } while(this.x != x || this.y != y);
            return xblocked || yblocked;
        }
        return false;
    }

    private void slide()
    {
        y += 1;
        ground = (CollisionObject) touching(CollisionObject.class);
        y -= 1;
        if(ground != null) {
            move(ground.x - this.x, ground.y - this.y);
        }
    }

    public boolean isOnFloor()
    {
        return ground != null;
    }

    public boolean moveAndSlide(int dx, int dy)
    {
        slide();
        return move(dx, dy);
    }

    public CollisionObject getSlideCollision()
    {
        return lastCollider;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setPosition(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public final void act()
    {
        setLocation(x - getWorldOfType(Level.class).globx, y - getWorldOfType(Level.class).globy);
        long current = System.currentTimeMillis();
        process((double)(current - lastCall) / 1000.0);
        lastCall = current;
    }

    public void process(double delta) {}
}
