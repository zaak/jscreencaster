import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;

public class SelectionOverlay extends JFrame {
    protected boolean isSelecting = false;
    protected Rectangle selectionRectangle = new Rectangle();
    protected BufferedImage desktopImage;
    protected BufferedImage bgDesktopImage;

    public SelectionOverlay() {
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
                    setVisible(false);
                    dispose();
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
        System.out.println(selectionRectangle);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bgDesktopImage, 0, 0, this);
        g.drawImage(
            desktopImage,
            selectionRectangle.x, selectionRectangle.y, (int)selectionRectangle.getMaxX(), (int)selectionRectangle.getMaxY(),
            selectionRectangle.x, selectionRectangle.y, (int)selectionRectangle.getMaxX(), (int)selectionRectangle.getMaxY(),
            this
        );
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void display() {
        setUndecorated(true);
        setSize(100, 200);
        setBackground(new Color(0xFF000000, true));
        setLocationRelativeTo(null);

        GraphicsDevice gd = Screen.getSourceDevice();

        Rectangle desktopBounds = gd.getDefaultConfiguration().getBounds();

        System.out.println("->>>>>");
        System.out.println(gd.getIDstring());
        System.out.println(desktopBounds);

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
}
