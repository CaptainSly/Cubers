package engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Engine {
	
	private static LwjglApplicationConfiguration config;
	
	
	public static void main(String[] args) {
		config = new LwjglApplicationConfiguration();
		config.title = "Cubers";
		config.width = 1280;
		config.height = 800;
		config.resizable = false;
		
		new LwjglApplication(new CuberGame());
	}

}
