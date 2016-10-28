package util;

import javafx.scene.paint.Color;

/**
 * Created by Tim on 11/10/16.
 */
public class ColorUtil {

    public static Color createTranslucentColor(Color c, double translucency){
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), translucency);
    }
}
