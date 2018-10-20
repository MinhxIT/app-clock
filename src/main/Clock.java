package main;

import hand.Hand;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Clock implements Runnable {
    public String title;
    Display display; //
    public Thread thread;
    public BufferStrategy bufferStrategy;
    public Graphics2D g;
    public Hand secondHand;
    public Hand minuteHand;
    public Hand hourHand;
    double time = System.currentTimeMillis();
    public Clock(String title) {
        this.title = title;
    }

    public void init() {
        display = new Display(title);
        secondHand = new Hand(1,Color.red,50) {
            @Override
            public double time(long currentTime) {
                return currentTime / (60.0 * 1000.0) * Math.PI * 2;
            }
        };
        minuteHand = new Hand(5, Color.black,70) {
            @Override
            public double time(long currentTime) {
                return currentTime / (60.0 * 60 * 1000.0) * Math.PI * 2;
            }
        };
        hourHand = new Hand(7, Color.BLUE,150) {
            @Override
            public double time(long currentTime) {
                return currentTime / (60.0 * 24 * 60 * 1000.0) * Math.PI * 2;
            }
        };

    }

    public void draw() {
        long time = System.currentTimeMillis();
        bufferStrategy = display.appCanvas.getBufferStrategy();
        if (bufferStrategy == null) {
            display.appCanvas.createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bufferStrategy.getDrawGraphics();
        // clear Space
        this.clearSpace(g);
        this.drawFrames(g);
        this.drawNumbers(g);
        this.secondHand.draw(g,time);
        this.minuteHand.draw(g,time);
        this.hourHand.draw(g,time);
        this.drawCenterOval(g);
        // end
        bufferStrategy.show();
        g.dispose();

    }

    private void drawCenterOval(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(Settings.CENTER - 10, Settings.CENTER - 10, 20, 20);

    }

    private void drawNumbers(Graphics2D g) {
        int angleX, angleY;
        int radius;
        double position;
        for (int i = 1; i <= 12; i++) {
            position = i / 12.0 * Math.PI * 2;
            radius = Settings.CENTER - 50;
            angleX = (int) ((Settings.CENTER) + (Math.sin(position) * radius));
            angleY = (int) ((Settings.CENTER) - (Math.cos(position) * radius));
            g.setColor(Color.BLACK);
            g.setFont(new Font("arial", Font.BOLD, 33));
            String a = Integer.toString(i);
            g.drawString(a, angleX, angleY);
        }
        // draw line for each second
        for (int i = 1; i <= 60; i++) {
            position = i / 60.0 * Math.PI * 2;
            radius = Settings.CENTER - 20;
            angleX = (int) ((Settings.CENTER) + (Math.sin(position) * radius));
            angleY = (int) ((Settings.CENTER) - (Math.cos(position) * radius));
            if (i == 15 || i == 30 || i == 45 || i == 60) {
                radius = Settings.CENTER - 70;
            } else {
                radius = Settings.CENTER - 30;
            }
            int angleW = (int) ((Settings.CENTER) + (Math.sin(position) * radius));
            int angleZ = (int) ((Settings.CENTER) - (Math.cos(position) * radius));
            g.setColor(Color.BLACK);
            String a = Integer.toString(i);
            g.drawLine(angleW, angleZ, angleX, angleY);
        }

    }

    private void drawFrames(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval(10, 10, Settings.SIZE - 20, Settings.SIZE - 20);

        g.setColor(Color.WHITE);
        g.fillOval(20, 20, Settings.SIZE - 40, Settings.SIZE - 40);
    }

    private void clearSpace(Graphics g) {
        g.clearRect(0, 0, Settings.SIZE, Settings.SIZE);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        while (true) {
            draw();
        }
    }
}
