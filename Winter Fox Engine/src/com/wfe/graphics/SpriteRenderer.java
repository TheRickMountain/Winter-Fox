package com.wfe.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Display;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector2f;
import com.wfe.textures.Texture;
import com.wfe.utils.MathUtils;

public class SpriteRenderer {

	private static SpriteRenderer instance;
	
	private SpriteShader shader;
	
	private Mesh mesh;
	
	private Matrix4f projection = new Matrix4f();
	private Matrix4f model = new Matrix4f();
	
	public static SpriteRenderer getInstance() {
		if(instance == null)
			instance = new SpriteRenderer();
		
		return instance;
	}
	
	private SpriteRenderer() {
		shader = new SpriteShader();
		setupMeshData();
		
		MathUtils.createOrthoProjectionMatrix(projection, 0, Display.getWidth(), Display.getHeight(), 0);

		shader.begin();
		shader.projection.loadMatrix(projection);
		shader.end();
	}
	
	public void begin() {
		shader.begin();
		
		if(Display.isResized()) {
			shader.projection.loadMatrix(projection);
		}
		
		GL30.glBindVertexArray(mesh.getVao());
		GL20.glEnableVertexAttribArray(0);
	}
	
	public void render(Texture texture, Vector2f position, Vector2f size) {
		texture.bindToUnit(0);
		
		MathUtils.createModelMatrix(model, position.x, position.y, 0, size.x, size.y);
		shader.model.loadMatrix(model);
	
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, mesh.getVertexCount());
	}
	
	public void end() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.end();
	}
	
	public void cleanUp()
	{
		mesh.cleanUp();
		shader.cleanUp();
	}
	
	private void setupMeshData() {
		float[] data = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };
	
		mesh = new Mesh(data, 2);
	}
	
}
