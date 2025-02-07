package xyz.qweru.api.render.font;

import net.minecraft.util.Identifier;

public class Font {
    public static String NAMESPACE = "gruvhack";
    public static Font DEFAULT_18 = new Font("Verdana", 18f, -3);
    public static Font DEFAULT_12 = new Font("Verdana", 12f, -3);

    private final String name;
    private final float size;
    private float offsetY;

    public FontRenderer get() {
        return renderer;
    }

    public float getSize() {
        return size;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public String getName() {
        return name;
    }

    private final FontRenderer renderer;

    public Font(String name, float size) {
        this.renderer = new FontRenderer(name, Identifier.of(NAMESPACE, "fonts"), size, offsetY);
        this.name = name;
        this.size = size;
        this.offsetY = 0;
    }

    public Font(String name, float size, float offsetY) {
        this.renderer = new FontRenderer(name, Identifier.of(NAMESPACE, "fonts"), size);
        this.name = name;
        this.size = size;
        this.offsetY = offsetY;
    }

    /**
     * Verdana
     * @param size size
     */
    public Font(float size) {
        this(null, size, 0);
    }

    /**
        Call this to load the default fonts
     */
    public static void init() {}
}
