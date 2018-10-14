import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Set;

public class SetUp implements Runnable{
    public String title;
    Display display; //
    public Thread thread;
    public BufferStrategy bufferStrategy;
    public Graphics2D g;

    public SetUp(String title){
        this.title = title;
    }
    public void init(){
        display = new Display(title);

    }
    public void draw(){
        bufferStrategy = display.appCanvas.getBufferStrategy();
        if(bufferStrategy==null){
            display.appCanvas.createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0,0,Settings.SIZE,Settings.SIZE);

        // draw
        g.setColor(Color.BLACK);
        g.fillOval(10,10,Settings.SIZE-20,Settings.SIZE-20);

        g.setColor(Color.WHITE);
        g.fillOval(20,20,Settings.SIZE-40,Settings.SIZE-40);

        int angleX,angleY;
        int radius;
        double position;
        double time = System.currentTimeMillis();
        // draw Number
        for (int i = 1; i <= 12; i++) {
            position = i/12.0 * Math.PI*2;
            radius = Settings.CENTER-50;
            angleX = (int) ((Settings.CENTER) + (Math.sin(position)*radius));
            angleY = (int) ((Settings.CENTER) - (Math.cos(position)*radius));
            g.setColor(Color.BLACK);
            g.setFont(new Font("arial",Font.BOLD,33));
            String a = Integer.toString(i);
            g.drawString(a,angleX,angleY);
        }
        // draw line for each second
        for (int i = 1; i <= 60; i++) {
            position = i/60.0 * Math.PI*2;
            radius = Settings.CENTER-20;
            angleX = (int) ((Settings.CENTER) + (Math.sin(position)*radius));
            angleY = (int) ((Settings.CENTER) - (Math.cos(position)*radius));
            if (i==15||i==30||i==45||i==60){
                radius = Settings.CENTER-70;
            }else {
                radius = Settings.CENTER-30;
            }
            int angleW = (int) ((Settings.CENTER) + (Math.sin(position)*radius));
            int angleZ = (int) ((Settings.CENTER) - (Math.cos(position)*radius));
            g.setColor(Color.BLACK);
            String a = Integer.toString(i);
            g.drawLine(angleW,angleZ,angleX,angleY);
        }
        //second hand
        radius = Settings.CENTER-50;
        time = System.currentTimeMillis()/(60.0*1000.0)*Math.PI*2;
        angleX = (int) ((Settings.CENTER) + (Math.sin(time)*radius));
        angleY = (int) ((Settings.CENTER) - (Math.cos(time)*radius));
        g.setColor(Color.RED);
        g.drawLine(Settings.CENTER,Settings.CENTER,angleX,angleY);
        //minute hand
        radius = Settings.CENTER-70;
        time = System.currentTimeMillis()/(60.0*60.0*1000.0)*Math.PI*2;
        angleX = (int) ((Settings.CENTER) + (Math.sin(time)*radius));
        angleY = (int) ((Settings.CENTER) - (Math.cos(time)*radius));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(6));
        g.drawLine(Settings.CENTER,Settings.CENTER,angleX,angleY);
        g.setStroke(new BasicStroke(0));
        //hour hand
        radius = Settings.CENTER-150;
        time = System.currentTimeMillis()/(60.0*60.0*24.0*1000.0)*Math.PI*2;
        angleX = (int) ((Settings.CENTER) + (Math.sin(time)*radius));
        angleY = (int) ((Settings.CENTER) - (Math.cos(time)*radius));
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(12));
        g.drawLine(Settings.CENTER,Settings.CENTER,angleX,angleY);
        g.setStroke(new BasicStroke(0));
        //center oval
        g.setColor(Color.RED);
        g.fillOval(Settings.CENTER-10,Settings.CENTER-10,20,20 );

        // end
        bufferStrategy.show();
        g.dispose();

    }
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        while (true){
            draw();
        }
    }
}
