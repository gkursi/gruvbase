package xyz.qweru.api.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import xyz.qweru.api.render.gui.BufferUtils;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Renderer in the world context
 * full credit to <a href="https://github.com/0x3C50/Renderer/blob/master/src/main/java/me/x150/renderer/render/Renderer3d.java">0x3C50</a>
 */
@SuppressWarnings("unused")
public class Renderer3d {
    static final List<FadingBlock> fades = new CopyOnWriteArrayList<>();
    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
    }

    private static void endRender() {
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    static float transformColor(float f) {
        return f;
    }

    private static Vec3d transformVec3d(Vec3d in) {
        Camera camera = client.gameRenderer.getCamera();
        Vec3d camPos = camera.getPos();
        return in.subtract(camPos);
    }

    static float[] getColor(Color c) {
        return new float[]{c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f};
    }

    private static void useBuffer(DrawMode mode, Consumer<BufferBuilder> runner) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.begin(mode, VertexFormats.POSITION_COLOR);

        runner.accept(bb);

        setupRender();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferUtils.draw(bb);
        endRender();
    }

    /**
     * Renders a block outline
     *
     * @param stack      The MatrixStack
     * @param color      The color of the outline
     * @param start      Start position of the block
     * @param dimensions Dimensions of the block
     */
    public static void renderOutline(MatrixStack stack, Color color, Vec3d start, Vec3d dimensions) {
        Matrix4f m = stack.peek().getPositionMatrix();
        genericAABBRender(DrawMode.DEBUG_LINES, m, start, dimensions, color, (buffer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, matrix) -> {
            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);
        });
    }


    /**
     * Renders both a filled and outlined block
     *
     * @param stack        The MatrixStack
     * @param colorFill    The color of the filling
     * @param colorOutline The color of the outline
     * @param start        The start coordinate
     * @param dimensions   The dimensions
     */
    public static void renderEdged(MatrixStack stack, Color colorFill, Color colorOutline, Vec3d start, Vec3d dimensions) {
        Matrix4f matrix = stack.peek().getPositionMatrix();
        float[] fill = getColor(colorFill);
        float[] outline = getColor(colorOutline);

        Vec3d vec3d = transformVec3d(start);
        Vec3d end = vec3d.add(dimensions);
        float x1 = (float) vec3d.x;
        float y1 = (float) vec3d.y;
        float z1 = (float) vec3d.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;
        float redFill = fill[0];
        float greenFill = fill[1];
        float blueFill = fill[2];
        float alphaFill = fill[3];
        float redOutline = outline[0];
        float greenOutline = outline[1];
        float blueOutline = outline[2];
        float alphaOutline = outline[3];
        useBuffer(DrawMode.QUADS, buffer -> {
            buffer.vertex(matrix, x1, y2, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y2, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y2, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y2, z1).color(redFill, greenFill, blueFill, alphaFill);

            buffer.vertex(matrix, x1, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y2, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y2, z2).color(redFill, greenFill, blueFill, alphaFill);

            buffer.vertex(matrix, x2, y2, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y2, z1).color(redFill, greenFill, blueFill, alphaFill);

            buffer.vertex(matrix, x2, y2, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y2, z1).color(redFill, greenFill, blueFill, alphaFill);

            buffer.vertex(matrix, x1, y2, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y2, z2).color(redFill, greenFill, blueFill, alphaFill);

            buffer.vertex(matrix, x1, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z1).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x2, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
            buffer.vertex(matrix, x1, y1, z2).color(redFill, greenFill, blueFill, alphaFill);
        });

        useBuffer(DrawMode.DEBUG_LINES, buffer -> {
            buffer.vertex(matrix, x1, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);

            buffer.vertex(matrix, x1, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);

            buffer.vertex(matrix, x1, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);

            buffer.vertex(matrix, x2, y1, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z1).color(redOutline, greenOutline, blueOutline, alphaOutline);

            buffer.vertex(matrix, x2, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x2, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);

            buffer.vertex(matrix, x1, y1, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
            buffer.vertex(matrix, x1, y2, z2).color(redOutline, greenOutline, blueOutline, alphaOutline);
        });
    }

    private static void genericAABBRender(DrawMode mode, Matrix4f stack, Vec3d start, Vec3d dimensions, Color color, RenderAction action) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        Vec3d vec3d = transformVec3d(start);
        Vec3d end = vec3d.add(dimensions);
        float x1 = (float) vec3d.x;
        float y1 = (float) vec3d.y;
        float z1 = (float) vec3d.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;
        useBuffer(mode, bufferBuilder -> action.run(bufferBuilder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, stack));
    }

    /**
     * Renders a filled block
     *
     * @param stack      The MatrixStack
     * @param color      The color of the filling
     * @param start      Start coordinates
     * @param dimensions Dimensions
     */
    public static void renderFilled(MatrixStack stack, Color color, Vec3d start, Vec3d dimensions) {
        Matrix4f s = stack.peek().getPositionMatrix();
        genericAABBRender(DrawMode.QUADS, s, start, dimensions, color, (buffer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, matrix) -> {
            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);

            buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x2, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y2, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y2, z2).color(red, green, blue, alpha);

            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z1).color(red, green, blue, alpha);
            buffer.vertex(matrix, x2, y1, z2).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z2).color(red, green, blue, alpha);
        });
    }

    /**
     * Renders a simple line from {@code start} to {@code end}
     *
     * @param matrices The MatrixStack
     * @param color    The color of the line
     * @param start    The start coordinate
     * @param end      The end coordinate
     */
    public static void renderLine(MatrixStack matrices, Color color, Vec3d start, Vec3d end) {
        Matrix4f s = matrices.peek().getPositionMatrix();
        genericAABBRender(DrawMode.DEBUG_LINES, s, start, end.subtract(start), color, (buffer, x, y, z, x1, y1, z1, red, green, blue, alpha, matrix) -> {
            buffer.vertex(matrix, x, y, z).color(red, green, blue, alpha);
            buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
        });
    }

    /**
     * @param original       the original color
     * @param redOverwrite   the new red (or -1 for original)
     * @param greenOverwrite the new green (or -1 for original)
     * @param blueOverwrite  the new blue (or -1 for original)
     * @param alphaOverwrite the new alpha (or -1 for original)
     * @return the modified color
     */
    public static Color modifyColor(Color original, int redOverwrite, int greenOverwrite, int blueOverwrite, int alphaOverwrite) {
        return new Color(redOverwrite == -1 ? original.getRed() : redOverwrite, greenOverwrite == -1 ? original.getGreen() : greenOverwrite, blueOverwrite == -1 ? original.getBlue() : blueOverwrite, alphaOverwrite == -1 ? original.getAlpha() : alphaOverwrite);
    }

    interface RenderAction {
        void run(BufferBuilder buffer, float x, float y, float z, float x1, float y1, float z1, float red, float green, float blue, float alpha, Matrix4f matrix);
    }

    record FadingBlock(Color outline, Color fill, Vec3d start, Vec3d dimensions, long created, long lifeTime) {
        long getLifeTimeLeft() {
            return Math.max(0, created - System.currentTimeMillis() + lifeTime);
        }

        boolean isDead() {
            return getLifeTimeLeft() == 0;
        }
    }
}
