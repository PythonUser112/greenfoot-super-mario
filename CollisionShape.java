/**
 * Abstract base class for CollisionObjects that can detect collisions.
 * 
 * @author buddha
 * @version 1
 */
public abstract class CollisionShape  
{
    public final CollisionObject parent;

    CollisionShape(CollisionObject parent)
    {
        this.parent = parent;
    }

    abstract boolean isColliding(CollisionShape other);
}
