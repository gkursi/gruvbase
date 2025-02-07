package xyz.qweru.api.module;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawContext;
import xyz.qweru.gruvhack.Gruvhack;

public abstract class HudModule extends Module {
    protected double x = 0, y = 0;
    protected double width = 0, height = 0;

    public HudModule(String name, String description) {
        super(name, description, Category.HUD);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public void addJson(JsonObject root) {
        super.addJson(root);
        // assume the object for this module has already been created by the superclass
        JsonObject object = root.getAsJsonObject(getName());
        object.addProperty("x", x);
        object.addProperty("y", y);
        root.add(getName(), object);
    }

    @Override
    public void readJson(JsonObject json) {
        super.readJson(json);
        JsonObject object = json.getAsJsonObject(getName());
        if(object == null) {
            return;
        }
        x = object.get("x").getAsDouble();
        y = object.get("y").getAsDouble();
    }

    public void render(DrawContext context, float tickdelta) {}
}
