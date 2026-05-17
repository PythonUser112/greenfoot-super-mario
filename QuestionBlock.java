import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract sealed class QuestionBlock extends ConcreteTile permits QuestionBlockCoin {
    static final int collisionLayer = Level.LAYER_ENVIRONMENT;
    static final int collisionMask = Level.LAYER_PLAYER | Level.LAYER_ENEMIES;
    static final String filename = "platform-q1.png";

    public boolean loaded = true;
    private int frame = 1;
    static final double frameTime = 0.3;
    private double currentTime = 0;
    public boolean frameDirection = true;
    private Item itemToUnload;

    public QuestionBlock(Item itemToUnload)
    {
        super(collisionLayer, collisionMask, filename);
        this.itemToUnload = itemToUnload;
        this.itemToUnload.setPosition(this.x, this.y);
    }

    public void addedToWorld(World world)
    {
        if(world.getClass() != LevelEditor.class) {
            act();
            world.addObject(itemToUnload, getX(), getY());
            unload();
        }
    }

    @Override
    public void process(double delta)
    {
        super.process(delta);
        this.itemToUnload.setPosition(this.x, this.y);
        if(loaded) {
            currentTime += delta;
            if(currentTime >= frameTime) {
                currentTime -= frameTime;
                if(frameDirection) {
                    if(frame++ == 3) {
                        frame = 2;
                        frameDirection = false;
                    }
                }
                else {
                    if(frame-- == 1) {
                        frame = 2;
                        frameDirection = true;
                    }
                }
                setImage("platform-q" + frame + ".png");
            }
        }
    }

    public void unload()
    {
        itemToUnload.dropOff();
        loaded = false;
        setImage("platform-air.png");
    }

    public static void register()
    {
        QuestionBlockCoin.register();
    }
}
