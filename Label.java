import greenfoot.*;

public final class Label extends Actor
{
    static final Color ALPHA = new Color(0, 0, 0, 0);
    static final int MAXFRAME = 15;

    private String text;
    private int fontSize;
    private Color fontColor, originalColor, targetColor;
    private int originalWidth, originalHeight;
    private GreenfootImage img;
    private TransitionState transition;
    private int transitionFrame;
    private boolean rainbowFlicker = false;
    private int rainbowState = 0;

    public Label(String text, int fontSize, Color fontColor)
    {
        this.text = text;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.transition = TransitionState.NoTransition;
        this.transitionFrame = 0;
        updateImage();
    }

    public Label(String text, int fontSize, Color fontColor, boolean rainbowFlicker)
    {
        this.text = text;
        this.fontSize = fontSize;
        if(!rainbowFlicker) {
            this.fontColor = fontColor;
        }
        else {
            this.fontColor = Color.RED;
            this.rainbowFlicker = true;
        }
        this.transition = TransitionState.NoTransition;
        this.transitionFrame = 0;
        updateImage();
    }

    public void rainbow()
    {
        this.fontColor = Color.RED;
        rainbowFlicker = true;
    }

    private void updateImage()
    {
        img = new GreenfootImage(text, fontSize, fontColor, ALPHA);
        originalWidth = img.getWidth();
        originalHeight = img.getHeight();
        setImage(img);
    }

    public void act()
    {
        if(transition != TransitionState.NoTransition) {
            if(transitionFrame++ == MAXFRAME) {
                transitionFrame = 0;
                if(transition == TransitionState.FadeOut || transition == TransitionState.Shrink) {
                    hide();
                }
                if(transition == TransitionState.FadeColor) {
                    fontColor = targetColor;
                }
                transition = TransitionState.NoTransition;
            }
            else {
                switch(transition) {
                    case FadeIn: processFadeIn(); break;
                    case FadeOut: processFadeOut(); break;
                    case FadeColor: processFadeColor(); break;
                    case Shrink: processShrink(); break;
                    case Grow: processGrow(); break;
                }
                return;
            }
        }
        if(rainbowFlicker && !hidden()) {
            updateImage();
            if(rainbowState == 0) {
                if(fontColor.getGreen() == 255) {
                    rainbowState++;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed(), 
                    fontColor.getGreen() + 5, fontColor.getBlue());
                }
            }
            if(rainbowState == 1) {
                if(fontColor.getRed() == 0) {
                    rainbowState++;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed() - 5, 
                    fontColor.getGreen(), fontColor.getBlue());
                }
            }
            if(rainbowState == 2) {
                if(fontColor.getBlue() == 255) {
                    rainbowState++;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed(), 
                    fontColor.getGreen(), fontColor.getBlue() + 5);
                }
            }
            if(rainbowState == 3) {
                if(fontColor.getGreen() == 0) {
                    rainbowState++;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed(), 
                    fontColor.getGreen() - 5, fontColor.getBlue());
                }
            }
            if(rainbowState == 4) {
                if(fontColor.getRed() == 255) {
                    rainbowState++;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed() + 5, 
                    fontColor.getGreen(), fontColor.getBlue());
                }
            }
            if(rainbowState == 5) {
                if(fontColor.getBlue() == 0) {
                    rainbowState = 0;
                    return;
                }
                else {
                    fontColor = new Color(fontColor.getRed(), 
                    fontColor.getGreen(), fontColor.getBlue() - 5);
                }
            }
        }
    }

    private void processFadeIn()
    {
        img.setTransparency(255 * transitionFrame / MAXFRAME);
    }

    private void processFadeOut()
    {
        img.setTransparency(255 - 255 * transitionFrame / MAXFRAME);
    }

    private void processFadeColor()
    {
        int actR = (originalColor.getRed() * transitionFrame
        + targetColor.getRed() * (MAXFRAME - transitionFrame)) / 17;
        int actG = (originalColor.getGreen() * transitionFrame
        + targetColor.getGreen() * (MAXFRAME - transitionFrame)) / 17;
        int actB = (originalColor.getBlue() * transitionFrame
        + targetColor.getBlue() * (MAXFRAME - transitionFrame)) / 17;
        this.fontColor = new Color(actR, actG, actB);
        updateImage();
    }

    private void processShrink()
    {
        img.scale(Math.max(originalWidth - originalWidth * transitionFrame / MAXFRAME, 1),
        Math.max(originalHeight - originalHeight * transitionFrame / MAXFRAME, 1));
    }

    private void processGrow()
    {
        updateImage();
        img.scale(Math.max(originalWidth * transitionFrame / MAXFRAME, 1),
        Math.max(originalHeight * transitionFrame / MAXFRAME, 1));
    }

    public void setFontSize(int fontSize)
    {
        if(!isTransiting()) {
            this.fontSize = fontSize;
            updateImage();
        }
        else {
            System.err.println("Can't set font size: currently in transition");
        }
    }

    public void setFontColor(Color fontColor)
    {
        if(!isTransiting()) {
            this.fontColor = fontColor;
            updateImage();
        }
        else {
            System.err.println("Can't set font color: currently in transition");
        }
    }

    public void setText(String text)
    {
        if(!isTransiting()) {
            this.text = text;
            updateImage();
        }
        else {
            System.err.println("Can't set text: currently in transition");
        }
    }

    public void hide()
    {
        this.img.setTransparency(0);
    }

    public void show()
    {
        this.img.setTransparency(255);
    }

    public void startFadeIn()
    {
        if(!isTransiting()) {
            hide();
            this.transition = TransitionState.FadeIn;
        }
        else {
            System.err.println("Can't transit: currently in transition");
        }
    }

    public void startFadeOut()
    {
        if(!isTransiting()) {
            show();
            this.transition = TransitionState.FadeOut;
        }
        else {
            System.err.println("Can't transit: currently in transition");
        }
    }

    public void startFadeColor(Color target)
    {
        if(!isTransiting()) {
            show();
            this.transition = TransitionState.FadeColor;
            this.originalColor = fontColor;
            this.targetColor = target;
        }
        else {
            System.err.println("Can't transit: currently in transition");
        }
    }

    public void startShrink()
    {
        if(!isTransiting()) {
            show();
            this.transition = TransitionState.Shrink;
        }
        else {
            System.err.println("Can't transit: currently in transition");
        }
    }

    public void startGrow()
    {
        if(!isTransiting()) {
            hide();
            this.transition = TransitionState.Grow;
        }
        else {
            System.err.println("Can't transit: currently in transition");
        }
    }

    public boolean isTransiting()
    {
        return this.transition != TransitionState.NoTransition;
    }

    public boolean hidden()
    {
        return this.img.getTransparency() == 0;
    }
}

enum TransitionState
{
    NoTransition,
    FadeIn,
    FadeOut,
    Shrink,
    Grow,
    FadeColor
}