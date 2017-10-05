package com.wfe.core;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.wfe.core.input.Cursor;
import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;
import com.wfe.core.input.Scroll;


public class Display {

	private static Display instance;
	
	private long window;
	
	private int width, height;
	private String title;
	private boolean resized;
	
	private boolean grabbed;
	
	public static Display getInstance()
	{
		if(instance == null)
			instance = new Display();
			
		return instance;
	}
	
	private Display() {
		
	}
	
	public void initialize(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		// Configure GLFW
		glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
	
        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL)
        	throw new RuntimeException("Failed to create the GLFW window");
        
        glfwSetKeyCallback(window, new Keyboard());
        glfwSetMouseButtonCallback(window, new Mouse());
        glfwSetCursorPosCallback(window, new Cursor());
        glfwSetScrollCallback(window, new Scroll());
        
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Center the window
        glfwSetWindowPos(
        		window,
        		(vidmode.width() - width) / 2,
        		(vidmode.height() - height) / 2
        		);

        // Make the OpenGL content current
        glfwMakeContextCurrent(window);
        
        // Enable v-sync
        glfwSwapInterval(1);
        
        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
     	// OpenGL context, or any context that is managed externally.
     	// LWJGL detects the context that is current in the current thread,
     	// creates the GLCapabilities instance and makes the OpenGL
     	// bindings available for use.
        GL.createCapabilities();
        
        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                resize(width, height);
            }
        });
	}
	
	public boolean isGrabbed()
	{
		return grabbed;
	}
	
	public void setGrabbed(boolean value)
	{
		glfwSetInputMode(window, GLFW_CURSOR, value ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
	    grabbed = value;
	}
	
	// Returns true if the user has attempted to close the window
	public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }
	
	public void pollEvents()
	{
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}
	
	public void swapBuffers()
	{
		// Swap the color buffers
		glfwSwapBuffers(window);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public boolean isResized()
	{
		return resized;
	}
	
	public void setResized(boolean resized)
	{
		this.resized = resized;
	}
	
	public void cleanUp()
	{
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void resize(int width, int height)
	{
		this.width = width;
		this.height = height;
		resized = true;
	}
	
}
