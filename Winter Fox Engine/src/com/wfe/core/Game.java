package com.wfe.core;

import com.wfe.graphics.SpriteRenderer;
import com.wfe.math.Vector2f;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;

public class Game {
	
	private Texture texture;
	
	public void loadContent()
	{
		
		texture = ResourceManager.loadTexture("stone", Texture.newTexture(new MyFile("textures/stone.png"))
				.nearestFiltering().build());
	}
	
	public void update()
	{
		
	}
	
	public void draw(SpriteRenderer spriteRenderer)
	{
		spriteRenderer.begin();
		
		spriteRenderer.render(texture, new Vector2f(0, 0), new Vector2f(32, 32));
		
		spriteRenderer.end();
	}
	
	public void cleanUp()
	{
		ResourceManager.cleanUp();
	}
	
}
