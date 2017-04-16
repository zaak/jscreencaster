package ui;

import util.DisplayManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;

class SelectionOverlay extends JFrame {
    private boolean isSelecting = false;
    private Rectangle selectionRectangle;
    private BufferedImage desktopImage;
    private BufferedImage bgDesktopImage;
    private Runnable onCloseCallback = () -> {};
    private boolean areaSelected = false;

    SelectionOverlay(Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(100, 200);
        setBackground(new Color(0xFF000000, true));
        setLocationRelativeTo(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        ImageIcon iconTarget = new ImageIcon(getClass().getResource("/icons/target.png"));

        setIconImage(iconTarget.getImage());

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(e);

                onCloseCallback.run();
                super.windowClosing(e);
            }
        });
    }

    private void startSelection(MouseEvent e) {
        isSelecting = true;
        selectionRectangle.setLocation(e.getX(), e.getY());

    }

    private void changeSelection(MouseEvent e) {
        int selectionWidth = e.getX() - selectionRectangle.x;
        int selectionHeight = e.getY() - selectionRectangle.y;

        selectionRectangle.setSize(selectionWidth, selectionHeight);
        repaint();
    }

    private void finishSelection(MouseEvent e) {
        isSelecting = false;
        areaSelected = true;
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

    void display() {
        GraphicsDevice gd = DisplayManager.getSourceDevice();
        Rectangle desktopBounds = gd.getDefaultConfiguration().getBounds();

        try {
            desktopImage = new Robot().createScreenCapture(desktopBounds);

            RescaleOp op = new RescaleOp(.5f, 0, null);
            bgDesktopImage = op.filter(desktopImage, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        setLocation(desktopBounds.x, getY());

        setVisible(true);

        gd.setFullScreenWindow(this);
    }

    private void close() {
        onCloseCallback.run();
        setVisible(false);
        dispose();
    }

    void onClose(Runnable onSelectedCallback) {
        this.onCloseCallback = onSelectedCallback;
    }

    public boolean isAreaSelected() {
        return areaSelected;
    }
}
