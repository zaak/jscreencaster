package ui;

import helper.Screen;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;

public class SelectionOverlay extends JFrame {
    protected boolean isSelecting = false;
    protected Rectangle selectionRectangle;
    protected BufferedImage desktopImage;
    protected BufferedImage bgDesktopImage;

    public SelectionOverlay(Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(100, 200);
        setBackground(new Color(0xFF000000, true));
        setLocationRelativeTo(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startSelection(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishSelection(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                changeSelection(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close();
                }
            }
        });
    }

    protected void startSelection(MouseEvent e) {
        isSelecting = true;
        selectionRectangle.setLocation(e.getX(), e.getY());

    }

    protected void changeSelection(MouseEvent e) {
        int selectionWidth = e.getX() - selectionRectangle.x;
        int selectionHeight = e.getY() - selectionRectangle.y;

        selectionRectangle.setSize(selectionWidth, selectionHeight);
        repaint();
    }

    protected void finishSelection(MouseEvent e) {
        isSelecting = false;
        close();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bgDesktopImage, 0, 0, this);

        if (isSelecting) {
            int minX = selectionRectangle.x;
            int minY = selectionRectangle.y;
            int maxX = (int) selectionRectangle.getMaxX();
            int maxY = (int) selectionRectangle.getMaxY();

            g.drawImage(desktopImage, minX, minY, maxX, maxY, minX, minY, maxX, maxY, this);
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void display() {
        GraphicsDevice gd = Screen.getSourceDevice();
        Rectangle desktopBounds = gd.getDefaultConfiguration().getBounds();

        try {
            desktopImage = new Robot().createScreenCapture(desktopBounds);

            RescaleOp op = new RescaleOp(.5f, 0, null);
            bgDesktopImage = op.filter(desktopImage, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        setLocation(desktopBounds.x, getY());

        setVisible(true);

        gd.setFullScreenWindow(this);
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
