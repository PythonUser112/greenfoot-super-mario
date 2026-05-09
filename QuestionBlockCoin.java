import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public final class QuestionBlockCoin extends QuestionBlock
{
    public QuestionBlockCoin()
    {
        super(new Coin());
    }

    public static void register()
    {
        Tile.register(QuestionBlockCoin.class);
    }
}
