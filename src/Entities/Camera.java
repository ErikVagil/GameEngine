package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position = new Vector3f(0,1,0);
	private Vector3f direction = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private static final float MOVE_SPEED = 0.02f;
	private static final float MOUSE_SPEED = 0.1f;
	
	public Camera()
	{
		
	}
	
	public void move()
	{
		direction.z = (float)Math.cos(yaw) * (float)Math.cos(pitch);
		direction.y = (float)Math.sin(yaw) * (float)Math.cos(pitch);
		direction.x = (float)Math.sin(pitch);
		direction.normalise(direction);
		direction.scale(MOVE_SPEED);
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= MOVE_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += MOVE_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= MOVE_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += MOVE_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			//Vector3f.add(position, direction, position);
			position.y += MOVE_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_C))
		{
			//Vector3f.add(position, new Vector3f(-direction.x, -direction.y, -direction.z), position);
			position.y -= MOVE_SPEED;
		}
		if(Mouse.isButtonDown(0))
		{
			yaw += Mouse.getDX() * MOUSE_SPEED;
			pitch -= Mouse.getDY() * MOUSE_SPEED;
		}
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}
}
