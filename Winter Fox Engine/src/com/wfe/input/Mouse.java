package com.wfe.input;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_6;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_7;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_8;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Created by Rick on 06.10.2016.
 */
public class Mouse extends GLFWMouseButtonCallback {

    public static final int BUTTON_1 = GLFW_MOUSE_BUTTON_1;
    public static final int BUTTON_2 = GLFW_MOUSE_BUTTON_2;
    public static final int BUTTON_3 = GLFW_MOUSE_BUTTON_3;
    public static final int BUTTON_4 = GLFW_MOUSE_BUTTON_4;
    public static final int BUTTON_5 = GLFW_MOUSE_BUTTON_5;
    public static final int BUTTON_6 = GLFW_MOUSE_BUTTON_6;
    public static final int BUTTON_7 = GLFW_MOUSE_BUTTON_7;
    public static final int BUTTON_8 = GLFW_MOUSE_BUTTON_8;

    public static final int LEFT_BUTTON = GLFW_MOUSE_BUTTON_LEFT;
    public static final int RIGHT_BUTTON = GLFW_MOUSE_BUTTON_RIGHT;
    static final int MIDDLE_BUTTON = GLFW_MOUSE_BUTTON_MIDDLE;

    public static final int BUTTON_LAST = GLFW_MOUSE_BUTTON_LAST;

    private static List<Integer> events = new ArrayList<>();
    private static List<Integer> eventsThisFrame = new ArrayList<>();
    private static List<Integer> eventsLastFrame = new ArrayList<>();

    public static boolean isButtonPressed(int button) {
        return eventsThisFrame.contains(button) && !eventsLastFrame.contains(button);
    }

    public static boolean isButtonReleased(int button) {
        return !eventsThisFrame.contains(button) && eventsLastFrame.contains(button);
    }

    public static boolean isButtonDown(int button) {
        return eventsThisFrame.contains(button);
    }

    public static void startEventFrame() {
        eventsThisFrame.clear();
        eventsThisFrame.addAll(events);
    }

    public static void clearEventFrame() {
        eventsLastFrame.clear();
        eventsLastFrame.addAll(eventsThisFrame);
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        Mouse.setButton(button, action != GLFW_RELEASE);

        for (int mod : Keys.MODIFIERS)
            if ((mods & mod) == mod)
                Mouse.setButton(mod, true);
    }

    private static void setButton(int button, boolean pressed) {
        if (pressed && !events.contains(button)) {
            events.add(button);
        }

        if (!pressed && events.contains(button)) {
            events.remove((Integer) button);
        }
    }

}
