package com.wfe.graphics;

import com.wfe.shaders.ShaderProgram;
import com.wfe.shaders.UniformMatrix;
import com.wfe.shaders.UniformSampler;
import com.wfe.utils.MyFile;

public class SpriteShader extends ShaderProgram {

	private static final MyFile VERTEX = new MyFile("shaders/sprite.vert");
	private static final MyFile FRAGMENT = new MyFile("shaders/sprite.frag");
	
	public UniformMatrix projection = new UniformMatrix("projection");
	public UniformMatrix model = new UniformMatrix("model");
	private UniformSampler image = new UniformSampler("image");
	
	public SpriteShader() {
		super(VERTEX, FRAGMENT, "position");
		storeAllUniformLocations(projection, model, image);
		connectTextureUnits();
	}

	@Override
	protected void connectTextureUnits() {
		begin();
		image.loadTexUnit(0);
		end();
	}

}
