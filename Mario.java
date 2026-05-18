import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Mario extends MarioObject
{
    static final int collisionLayer = Level.LAYER_PLAYER;
    static final int collisionMask = Level.LAYER_ENVIRONMENT | Level.LAYER_ENEMIES | Level.LAYER_ITEM;

    private double xerr = 0.0;
    private double dy = 0;
    private boolean dying = false;

    private double frameTime = 0.4;
    private double currentTime = 0;
    private int frame = 1;

    public Mario()
    {
        super(collisionLayer, collisionMask);
        setCollisionShape(new BoxShape(this, 13 * Level.TILE_SCALE, 14 * Level.TILE_SCALE));
    }

    @Override
    public void process(double delta)
    {
        super.process(delta);
        if(dying) {
            if(frame++ == 50) {
                getWorldOfType(Level.class).restart();
            }
            dy += delta * Level.GRAVITY;
            moveAndCollide(0, (int) Math.round(dy));
            return;
        }
        currentTime += delta;
        if(isOnGround()) {
            dy = 0;
            if(Greenfoot.isKeyDown("space")) {
                dy = -Level.JUMPHEIGHT;
            }
            if((getGround().collisionLayers & Level.LAYER_ENEMIES) > 0) {
                ((Enemy) getGround()).die();
                if(!Greenfoot.isKeyDown("space")) {
                    dy = -Level.JUMPHEIGHT / 2;
                }
                else {
                    dy = -Level.JUMPHEIGHT; 
                }
            }
        }
        else {
            dy += delta * Level.GRAVITY;
        }
        double dx = xerr;
        if(Greenfoot.isKeyDown("left")) {
            dx -= Level.MOVESPEED * delta;
        }
        if(Greenfoot.isKeyDown("right")) {
            dx += Level.MOVESPEED * delta;
        }
        int dxi = (int) Math.round(dx);
        xerr = dx - dxi;
        if(moveAndSlide((int) Math.round(dx), (int) Math.round(dy))) {
            handleCollision(getCollider());
        }
        // getWorldOfType(Level.class).reposition(x, y);
    }

    private void die()
    {
        dying = true;
        disableCollisions();
        setImage("mario-die.png");
    }

    private void handleCollision(CollisionObject collider)
    {
        System.out.println(collider);
        System.out.println(collider.getClass().isAssignableFrom(Tile.class));
        System.out.println(dy);
        if((collider.collisionLayers & Level.LAYER_ENEMIES) > 0) {
            Enemy enemy = (Enemy) collider;
            if(dy > 0) {
                enemy.die();
                if(!Greenfoot.isKeyDown("space")) {
                    dy = -Level.JUMPHEIGHT / 2;
                }
                else {
                    dy = -Level.JUMPHEIGHT; 
                }
            }
            else {
                die();
            }
        }
        try {
            QuestionBlock block = (QuestionBlock) collider;
            System.out.println(block.loaded);
            if(block.loaded && dy < 0) {
                block.unload();
            }
        } catch(ClassCastException e) {}
    }
}
