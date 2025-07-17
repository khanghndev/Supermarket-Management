/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author Minh
 */
public class roundButton extends JButton {
    private Shape shape;

    public roundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Không vẽ nền mặc định
        setFocusPainted(false); // Tắt viền khi được focus
        setBorderPainted(false); // Tắt viền mặc định
        setOpaque(false); // Làm trong suốt nền
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Khử răng cưa
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Màu nền
        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY); // Khi bấm
        } else {
            g2.setColor(getBackground());
        }
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Vẽ text
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        String text = getText();
        int x = (r.width - fm.stringWidth(text)) / 2;
        int y = (r.height + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}
