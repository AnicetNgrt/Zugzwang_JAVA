package ZwangGUI;

import javax.swing.*;
import java.awt.*;

public class ZwangGuiElement extends JPanel {
    private int perWidth = 50;
    private int perHeight = 50;
    private String bgColor;

    public ZwangGuiElement() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = (perWidth / 100) * getParent().getPreferredSize().width;
        int height = (perHeight / 100) * getParent().getPreferredSize().height;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.decode(bgColor));
    }

    public int getPerWidth() {
        return perWidth;
    }

    public void setPerWidth(int perWidth) {
        this.perWidth = perWidth;
    }

    public int getPerHeight() {
        return perHeight;
    }

    public void setPerHeight(int perHeight) {
        this.perHeight = perHeight;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
