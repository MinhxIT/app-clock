package hand;


import main.Settings;

import java.awt.*;

public abstract class Hand {
    int stroke;
    Color color;
    int length;
    public Hand(int stroke,Color color,int length){
        this.stroke = stroke;
        this.color = color;
        this.length = length;
    }
    public abstract double time(long currentTime);
    public void draw(Graphics2D g, long currentTime){
        int angleX, angleY;
        int radius;
        radius = Settings.CENTER - this.length;
        double time = this.time(currentTime);
        angleX = (int) ((Settings.CENTER) + (Math.sin(time) * radius));
        angleY = (int) ((Settings.CENTER) - (Math.cos(time) * radius));
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.stroke));
        g.drawLine(Settings.CENTER, Settings.CENTER, angleX, angleY);
        g.setStroke(new BasicStroke(0));

    }
}
