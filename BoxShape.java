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

    public boolean isColliding(BoxShape other)
    {
        int startx = this.parent.x - this.sizex / 2;
        int endx = this.parent.x + (int) Math.ceil(this.sizex / 2.0);
        int starty = this.parent.y - this.sizey / 2;
        int endy = this.parent.y + (int) Math.ceil(this.sizey / 2.0);
        int ostartx = other.parent.x - other.sizex / 2;
        int oendx = other.parent.x + (int) Math.ceil(other.sizex / 2.0);
        int ostarty = other.parent.y - other.sizey / 2;
        int oendy = other.parent.y + (int) Math.ceil(other.sizey / 2.0);
        return ((ostartx <= startx && startx <= oendx) || (ostartx <= endx && endx <= oendx)) && ((ostarty <= starty && starty <= oendy) || (ostarty <= endy && endy <= oendy));
    }

    public boolean isColliding(CollisionShape other)
    {
        return other.isColliding(this);
    }
}
