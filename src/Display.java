import javax.swing.*;
import java.awt.*;

public class Display {
    public String title;
    public JFrame appWindow; // cửa sổ
    public static Canvas appCanvas; // appCanvas
    public Thread thread;
    public Display(String title){
        this.title = title;
        createDisplay();
    }
    public void createDisplay(){
        appWindow = new JFrame();
        appWindow.setSize(Settings.SIZE,Settings.SIZE);
        appWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appWindow.setResizable(false);
        appCanvas = new Canvas();
        appCanvas.setPreferredSize(new Dimension(Settings.SIZE,Settings.SIZE));
        appCanvas.setBackground(Color.MAGENTA);
        appWindow.add(appCanvas);
        appWindow.setVisible(true);
        appWindow.pack();
    }
}
