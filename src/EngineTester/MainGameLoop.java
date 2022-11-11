package EngineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Entities.Entity;
import Models.RawModel;
import Models.TextureModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.Renderer;
import Shaders.StaticShader;
import Textures.ModelTexture;

public class MainGameLoop
{
    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        
        //cube
		float[] c_vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		};
		
		//cube
		float[] c_textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		//cube
		int[] c_indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		
		//plane
		float[] p_vertices = {
				-0.5f,0.5f,0f,
				-0.5f,-0.5f,0f,
				0.5f,-0.5f,0f,
				0.5f,0.5f,0f
		};
		
		//plane
		float[] p_textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		//plane
		int[] p_indices = {
				0,1,2,
				0,2,3
		};
		
		//instructions
		float[] i_vertices = {
				-0.5f,0.5f,0f,
				-0.5f,-0.5f,0f,
				0.5f,-0.5f,0f,
				0.5f,0.5f,0f
		};
				
		//instructions
		float[] i_textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0
		};
				
		//instructions
		int[] i_indices = {
				0,1,2,
				0,2,3
		};
        
        //cube model
        RawModel c_model = loader.loadToVAO(c_vertices, c_textureCoords, c_indices);
        TextureModel c_textureModel = new TextureModel(c_model, new ModelTexture(loader.loadTexture("rock_texture")));
        Entity c_entity = new Entity(c_textureModel, new Vector3f(0,1,-7),0,0,0,1);
        
        //plane model
        RawModel p_model = loader.loadToVAO(p_vertices, p_textureCoords, p_indices);
        TextureModel p_textureModel = new TextureModel(p_model, new ModelTexture(loader.loadTexture("grass_texture")));
        
        //instructions model
        RawModel i_model = loader.loadToVAO(i_vertices, i_textureCoords, i_indices);
        TextureModel i_textureModel = new TextureModel(i_model, new ModelTexture(loader.loadTexture("controls_text")));
        Entity i_entity = new Entity(i_textureModel, new Vector3f(-1,1.5f,-3),0,0,0,1);
        
        //camera
        Camera camera = new Camera();
        
        //float time = 0f;
        
        while(!Display.isCloseRequested())
        {
        	//game logic
        	camera.move();
        	c_entity.increaseRotation(1, 1, 0);
        	
        	//prepare render
        	renderer.prepare();
        	shader.start();
        	shader.loadViewMatrix(camera);
        	
        	//render entities
            renderer.render(c_entity, shader);
            renderer.render(i_entity, shader);
            for (int i = 0; i < 100; i += 2)
            {
            	for (int j = 0; j < 100; j += 2)
                {
                	renderer.render(new Entity(p_textureModel, new Vector3f(i-50, -0.51f, j-57),90,0,0,2), shader);
                }
            }
            
            //finish render
            shader.stop();
            DisplayManager.updateDisplay();
            //time += 0.00833f;
        }
        
        //clear memory
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
