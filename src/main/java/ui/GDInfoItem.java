package ui;

import java.awt.*;

public class GDInfoItem {
    private String idString;
    private Rectangle bounds;

    public GDInfoItem(GraphicsDevice gd) {
        idString = gd.getIDstring();
        bounds = gd.getDefaultConfiguration().getBounds();
    }

    public String getIdString() {
        return idString;
    }

    @Override
    public String toString() {
        return idString + " (" + bounds.x + "," + bounds.y + ";" + bounds.width + "x" + bounds.height + ")";
    }
}
