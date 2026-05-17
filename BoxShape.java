import greenfoot.GreenfootImage;
/**
 * A rectangle shape collision object.
 * 
 * @author buddha
 * @version 1
 */
public class BoxShape extends CollisionShape 
{
    public final int sizex;
    public final int sizey;

    public BoxShape(CollisionObject parent, int sizex, int sizey)
    {
        super(parent);
        this.sizex = sizex;
        this.sizey = sizey;
    }

    public BoxShape(CollisionObject parent)
    {
        this(parent, parent.getImage().getWidth(), parent.getImage().getHeight());
    }

    public boolean isColliding(CollisionShape other)
    {
        if(other.getClass() == BoxShape.class) {
            BoxShape _other = (BoxShape) other;
            int startx = this.parent.x - this.sizex / 2;
            int endx = this.parent.x + (int) Math.ceil(this.sizex / 2.0);
            int starty = this.parent.y - this.sizey / 2;
            int endy = this.parent.y + (int) Math.ceil(this.sizey / 2.0);
            int ostartx = other.parent.x - _other.sizex / 2;
            int oendx = other.parent.x + (int) Math.ceil(_other.sizex / 2.0);
            int ostarty = other.parent.y - _other.sizey / 2;
            int oendy = other.parent.y + (int) Math.ceil(_other.sizey / 2.0);
            return ((ostartx <= startx && startx <= oendx) || (ostartx <= endx && endx <= oendx)) && ((ostarty <= starty && starty <= oendy) || (ostarty <= endy && endy <= oendy));
        }
        return other.isColliding(this);
    }
}
