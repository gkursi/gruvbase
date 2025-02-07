package xyz.qweru.api.render;

import java.awt.*;

public record ColorScheme(Color background, Color foreground, Color border1, Color border2, Color border3, Color border4, Color text) {
    public static ColorScheme DARK = new ColorScheme(
            Color.decode("#282828"),
            Color.decode("#ebdbb2"),
            Color.decode("#fabd2f"),
            Color.decode("#fb4934"),
            Color.decode("#b8bb26"),
            Color.decode("#83a598"),
            Color.decode("#ebdbb2")
    );
}
