package com.wfe.core;

import org.lwjgl.opengl.GL11;

import com.wfe.graphics.SpriteRenderer;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;

public class MainApp {

	public static void main(String[] args) 
	{
		Display display = Display.getInstance();
		display.initialize(1280, 720, "Paleon");
		
		SpriteRenderer spriteRenderer = SpriteRenderer.getInstance();
		
		Game game = new Game();
		game.loadContent();
		
		while(!display.isCloseRequested())
		{
			display.pollEvents();
			
			if(Display.isResized())
			{
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
				display.setResized(false);
			}
			
			Keyboard.startEventFrame();
			Mouse.startEventFrame();
			
			game.update();
			
			Keyboard.clearEventFrame();
			Mouse.clearEventFrame();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			
			game.draw(spriteRenderer);
			
			display.swapBuffers();
		}
		
		game.cleanUp();
		
		spriteRenderer.cleanUp();
		
		display.cleanUp();
	}
	
}
