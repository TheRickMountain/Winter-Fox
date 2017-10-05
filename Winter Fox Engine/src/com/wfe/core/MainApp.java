package com.wfe.core;

import org.lwjgl.opengl.GL11;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;

public class MainApp {

	public static void main(String[] args) 
	{
		Display display = Display.getInstance();
		display.initialize(1280, 720, "Paleon");
		
		while(!display.isCloseRequested())
		{
			display.pollEvents();
			
			if(display.isResized())
			{
				GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
				display.setResized(false);
			}
			
			Keyboard.startEventFrame();
			Mouse.startEventFrame();
			
			Keyboard.clearEventFrame();
			Mouse.clearEventFrame();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			
			// Render
			
			display.swapBuffers();
		}
		
		display.cleanUp();
	}
	
}
