package com.wfe.core.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class Cursor extends GLFWCursorPosCallback {

	private static float x, y, dx, dy;
    
    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }

    public static float getDX() {
        float dx = Cursor.dx;
        Cursor.dx = 0;
        return dx;
    }

    public static float getDY() {
        float dy = Cursor.dy;
        Cursor.dy = 0;
        return dy;
    }

    @Override
    public void invoke(long window, double x, double y) {
        Cursor.dx = (float) x - Cursor.x;
        Cursor.dy = (float) y - Cursor.y;

        Cursor.x = (float) x;
        Cursor.y = (float) y;
    }
	
}
