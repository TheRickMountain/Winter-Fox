#version 330 core
in vec2 data;

out vec2 TextureCoords;

uniform mat4 projection;
uniform mat4 model;

void main()
{
	gl_Position = projection * model * vec4(data, 0.0f, 1.0f);
	TextureCoords = data;
}
