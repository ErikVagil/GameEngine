package RenderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Models.RawModel;

public class Loader
{
	//two lists for garbage collection
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices)
	{
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadTexture(String fileName)
	{
		Texture texture = null;
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	//garbage collection, clear VAOs and VBOs when done
	public void cleanUp()
	{
		for (int vao : vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo : vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
		
		for (int texture : textures)
		{
			GL11.glDeleteTextures(texture);
		}
	}
	
	private int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID); //for garbage collection
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordianteSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID); //for garbage collection
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //bind VBO
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, 
				coordianteSize /*number of data points in each vertex*/, 
				GL11.GL_FLOAT /*data type*/, 
				false /*normalized?*/, 
				0 /*distance between vertices in list, is there data between each x,y,z*/, 
				0 /*starting point of data*/);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //unbind VBO
	}
	
	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data); //add data to buffer
		buffer.flip(); //go from writing to reading data
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip(); //go from writing to reading data
		return buffer;
	}
}
