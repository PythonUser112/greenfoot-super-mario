import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

abstract public class TextWorld extends World
{
    private ArrayList<Label> texts = new ArrayList();
    private boolean transiting = false;
    private Label title;
    private int speed = 50;
    private long last_frame;
    final int delta_target;
    static final int FRAMES = 3;
    private int frame = FRAMES;

    public TextWorld(int width, int height, int cell)
    {
        this(width, height, cell, true, Color.WHITE, false, 30, 20);
    }

    public TextWorld(int width, int height, int cell, boolean border)
    {
        this(width, height, cell, border, Color.WHITE, false, 30, 20);
    }

    public TextWorld(int width, int height, int cell, int delta_target)
    {
        this(width, height, cell, true, Color.WHITE, false, 30, delta_target);
    }

    public TextWorld(int width, int height, int cell, boolean border, int delta_target)
    {
        this(width, height, cell, border, Color.WHITE, false, 30, delta_target);
    }

    public TextWorld(int width, int height, int cell, Color titleColor)
    {
        this(width, height, cell, true, titleColor, false, 30, 20);
    }

    public TextWorld(int width, int height, int cell, Color titleColor, int delta_target)
    {
        this(width, height, cell, true, titleColor, false, 30, delta_target);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor)
    {
        this(width, height, cell, border, titleColor, false, 30, 20);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor, int delta_target)
    {
        this(width, height, cell, border, titleColor, false, 30, delta_target);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor, boolean rainbowFlicker)
    {
        this(width, height, cell, border, titleColor, rainbowFlicker, 30, 20);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor, boolean rainbowFlicker, int delta_target)
    {
        this(width, height, cell, border, titleColor, rainbowFlicker, 30, delta_target);
    }

    public TextWorld(int width, int height, int cell, boolean border, Color titleColor, boolean rainbowFlicker, int titleSize, int delta_target)
    {
        super(width, height, cell, border);
        title = new Label(formatClassname(getClass().getName()), titleSize, titleColor, rainbowFlicker);
        title.hide();
        addObject(title, getWidth() / 2, getHeight() / 2);
        this.delta_target = delta_target;
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
                    removeObject(label);
                }
                else {
                    label.setText(text);
                    label.setFontSize(textSize);
                    label.setFontColor(color);
                    return;
                }
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
            double diff = (double) (delta - delta_target * FRAMES);
            System.out.println();
            if(diff > 2 * FRAMES) {
                speed += (int) Math.log10(diff);
            }
            else if(diff < -2 * FRAMES) {
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

    void instanceLabel(Label label)
    {
        label.startFadeIn();
    }

    void disposeLabel(Label label)
    {
        label.startFadeOut();
    }

    void instanceTitle(Label title)
    {
        title.startFadeIn();
    }

    void disposeTitle(Label title)
    {
        title.startFadeOut();
    }

    public void titleTransition() {}
    public void action() {}
}
