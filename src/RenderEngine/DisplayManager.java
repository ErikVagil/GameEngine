package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int FPS_CAP = 120;

    public static void createDisplay()
    {
        ContextAttribs attribs = new ContextAttribs(3,2)
        		.withForwardCompatible(true)
        		.withProfileCore(true);

        try
        {
        	//display init settings go here
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("LWJGL 2 Project");
        } catch (LWJGLException e)
        {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT); //sets bounds of the window that can be rendered to
        Mouse.setCursorPosition(WIDTH/2, HEIGHT/2);
        Mouse.getDX();
        Mouse.getDY();
    }

    public static void updateDisplay()
    {
        Display.sync(FPS_CAP); //caps FPS
        Display.update(); //updates display
    }

    public static void closeDisplay()
    {
        Display.destroy();
    }
}
