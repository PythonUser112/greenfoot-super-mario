import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

abstract public class TextWorld extends World
{
    private ArrayList<Label> texts = new ArrayList();
    private boolean transiting = false;
    private Label title;
    private int speed = 50;
    private long last_frame;
    static final int DELTA = 20;
    static final int FRAMES = 3;
    private int frame = FRAMES;

    public TextWorld(int width, int height, int cell, boolean border)
    {
        super(width, height, cell, border);
        title = new Label(formatClassname(getClass().getName()), 30, Color.WHITE);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
    }

    public TextWorld(int width, int height, int cell)
    {
        super(width, height, cell, true);
        title = new Label(formatClassname(getClass().getName()), 30, Color.WHITE);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor)
    {
        super(width, height, cell, border);
        title = new Label(formatClassname(getClass().getName()), 30, titleColor);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
    }

    public TextWorld(int width, int height, int cell, Color titleColor)
    {
        super(width, height, cell, true);
        title = new Label(formatClassname(getClass().getName()), 30, titleColor);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor, boolean rainbowFlicker)
    {
        super(width, height, cell, border);
        title = new Label(formatClassname(getClass().getName()), 30, titleColor, rainbowFlicker);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
    }

    public static String formatClassname(String input)
    {
        return input.replaceAll("(?<!^)([A-Z0-9])", " $1");
    }

    public final void showTitle(int y)
    {
        title.setLocation(getWidth() / 2, y);
        instanceTitle(title);
    }

    public final void hideTitle()
    {
        disposeTitle(title);
    }

    public final Label createText(String text, int x, int y)
    {
        return createText(text, x, y, 20, Color.WHITE);
    }

    public final Label createText(String text, int x, int y, int textSize)
    {
        return createText(text, x, y, textSize, Color.WHITE);
    }

    public final Label createText(String text, int x, int y, Color color)
    {
        return createText(text, x, y, 20, color);
    }

    public final Label createText(String text, int x, int y, int textSize, Color color)
    {
        Label label = new Label(text, textSize, color);
        instanceLabel(label);
        addObject(label, x, y);
        texts.add(label);
        transiting = true;
        return label;
    }

    public final void updateText(String text, int x, int y)
    {
        updateText(text, x, y, 20, Color.WHITE);
    }

    public final void updateText(String text, int x, int y, int textSize)
    {
        updateText(text, x, y, textSize, Color.WHITE);
    }

    public final void updateText(String text, int x, int y, Color color)
    {
        updateText(text, x, y, 20, color);
    }

    public final void updateText(String text, int x, int y, int textSize, Color color)
    {
        for(int i = 0; i < texts.size(); i++) {
            Label label = texts.get(i);
            if(label.getX() == x && label.getY() == y) {
                if(text == "") {
                    texts.remove(i);
                    disposeLabel(label);
                }
                else {
                    label.setText(text);
                    label.setFontSize(textSize);
                    label.setFontColor(color);
                }
                return;
            }
        }
        createText(text, x, y, textSize, color);
    }

    public final void clearText()
    {
        for(int i = 0; i < texts.size(); i++) {
            Label label = texts.get(i);
            if(!label.isTransiting()) {
                disposeLabel(label);
            }
        }
        transiting = true;
    }

    public final void act()
    {
        if(frame-- == 0) {
            long delta = System.currentTimeMillis() - last_frame;
            double diff = (double) (delta - DELTA * FRAMES) / 1000.0;
            if(diff > 2) {
                speed += (int) Math.log10(diff);
            }
            else if(diff < -2) {
                speed -= (int) Math.log10(-diff);
            }
            last_frame = System.currentTimeMillis();
            frame = FRAMES;
        }
        Greenfoot.setSpeed(speed);
        for(int i = 0; i < texts.size(); i++) {
            if(texts.get(i).hidden()) {
                removeObject(texts.get(i));
                texts.remove(i);
                i--;
            }
        }
        if(title.isTransiting()) {
            titleTransition();
            return;
        }
        if(transiting) {
            transiting = false;
            for(int i = 0; i < texts.size(); i++) {
                if(texts.get(i).isTransiting()) {
                    transiting = true;
                    break;
                }
            }
        }
        else {
            action();
        }
    }

    public void titleTransition() {}
    abstract void action();
    abstract void disposeLabel(Label label);
    abstract void instanceLabel(Label label);
    abstract void disposeTitle(Label title);
    abstract void instanceTitle(Label title);
}
