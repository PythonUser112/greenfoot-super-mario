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

    private int stepx = 0;
    private int stepy = 0;
    private int lastdx = 0;
    private int lastdy = 0;

    private CollisionObject collider;

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
        stepx++;
        setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
        CollisionObject curr = (CollisionObject) touching(CollisionObject.class);
        if(curr != null) {
            collider = curr;
            this.x -= sx;
            stepx--;
            setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
            return true;
        }
        return false;
    }

    private boolean stepy(int sy) {
        this.y += sy;
        stepy++;
        setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
        CollisionObject curr = (CollisionObject) touching(CollisionObject.class);
        if(curr != null) {
            while(curr != null) {
                collider = curr;
                this.y -= sy;
                setLocation(this.x - getWorldOfType(Level.class).globx, this.y - getWorldOfType(Level.class).globy);
                curr = (CollisionObject) touching(CollisionObject.class);
            }
            stepy--;
            return true;
        }
        return false;
    }

    private boolean _move(int dx, int dy)
    {
        int sx = (int) Math.signum(dx);
        int sy = (int) Math.signum(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        stepx = 0;
        stepy = 0;
        collider = null;
        if(dx == 0) {
            while(stepy < dy) {
                if(stepy(sy)) {
                    return true;
                }
            }
        }
        else if(dy == 0) {
            while(stepx < dx) {
                if(stepx(sx)) {
                    return true;
                }
            }
        }
        else {
            int err = dx - dy;
            int e2, tmpx, tmpy;
            boolean xblocked = false;
            boolean yblocked = false;
            do {
                if(!xblocked && !yblocked && stepx < dx && stepy < dy) {
                    e2 = 2 * err;
                    if(e2 > -dy) {
                        err -= dy;
                        xblocked = stepx(sx);
                        stepx++;
                    }
                    if(e2 < dx) {
                        err += dx;
                        yblocked = stepy(sy);
                    }
                }
                else if(xblocked || stepx == dx) {
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
            } while(stepx < dx && stepy < dy);
            return xblocked;
        }
        return false;
    }

    public boolean isOnGround()
    {
        if(stepy(1)) {
            return true;
        }
        this.y--;
        return false;
    }

    public CollisionObject getCollider()
    {
        return collider;
    }

    public boolean moveAndCollide(int dx, int dy)
    {
        boolean collision = _move(dx, dy);
        lastdx = stepx;
        lastdy = stepy;
        return collision;
    }

    public boolean moveAndSlide(int dx, int dy)
    {
        boolean collision = _move(dx, dy);
        lastdx = stepx * (int) Math.signum(dx);
        lastdy = stepy * (int) Math.signum(dy);
        CollisionObject last = collider;
        if(isOnGround()) {
            int sx = (int) Math.signum(collider.dx());
            int sy = (int) Math.signum(collider.dy());
            collision = collision || _move(collider.dx(), collider.dy());
            lastdx += stepx * sx;
            lastdy += stepy * sy;
            collider = last;
        }
        return collision;
    }

    public int dx()
    {
        return lastdx;
    }

    public int dy()
    {
        return lastdy;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
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
