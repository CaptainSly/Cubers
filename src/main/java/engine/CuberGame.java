package engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import engine.cubers.Cuber;
import engine.cubers.CuberChunk;
import engine.cubers.CuberType;
import engine.cubers.Noise;

public class CuberGame extends Game {

	private PerspectiveCamera cam;
	private Model model;
	private CuberChunk[] cuberChunk;
	private ModelBatch modelBatch;
	private CameraInputController cap;

	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_AMT = 10;
	private Noise noise;
	
	private boolean d = true;
	

	private Environment envir;

	@Override
	public void create() {
		noise = new Noise();
		modelBatch = new ModelBatch();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		cap = new CameraInputController(cam);

		envir = new Environment();
		envir.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		envir.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cuberChunk = new CuberChunk[CHUNK_AMT];

		for (int i = 0; i < CHUNK_AMT; i++) {
			cuberChunk[i] = new CuberChunk();
		}

		Gdx.gl.glEnable(GL20.GL_CULL_FACE);

		ModelBuilder builder = new ModelBuilder();

		for (int i = 0; i < CHUNK_AMT; i++) {

			for (int x = 0; x < CuberGame.CHUNK_SIZE; x++) {
				for (int y = 0; y < CuberGame.CHUNK_SIZE; y++) {
					for (int z = 0; z < CuberGame.CHUNK_SIZE; z++) {
						cuberChunk[i].getCubers()[x][y][z] = new Cuber(true, CuberType.WATER_CUBER, builder);
						cuberChunk[i].getCubers()[x][y][z].setActive(true);
						cuberChunk[i].getCubers()[x][y][z].getInstance().transform.translate(x * i + 1, y * i + 1,
								z * i + 1);

					}
				}
			}
		}
		
		Gdx.input.setInputProcessor(cap);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);

		cap.update();

		modelBatch.begin(cam);

		// Draws a sphere
		drawPerlin();

		modelBatch.end();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	private void drawCube() {
		for (int i = 0; i < CHUNK_AMT; i++) {
			for (int x = 0; x < CuberGame.CHUNK_SIZE; x++) {
				for (int y = 0; y < CuberGame.CHUNK_SIZE; y++) {
					for (int z = 0; z < CuberGame.CHUNK_SIZE; z++) {
						modelBatch.render(cuberChunk[i].getCubers()[x][y][z].getInstance(), envir);
					}
				}
			}
		}
	}

	private void drawSphere() {

		for (int i = 0; i < CHUNK_AMT; i++) {
			for (int x = 0; x < CuberGame.CHUNK_SIZE; x++) {
				for (int y = 0; y < CuberGame.CHUNK_SIZE; y++) {
					for (int z = 0; z < CuberGame.CHUNK_SIZE; z++) {
						if (Math.sqrt((float) (x - CuberGame.CHUNK_SIZE / 2) * (x - CuberGame.CHUNK_SIZE / 2)
								+ (y - CuberGame.CHUNK_SIZE / 2) * (y - CuberGame.CHUNK_SIZE / 2)
								+ (z - CuberGame.CHUNK_SIZE / 2)
										* (z - CuberGame.CHUNK_SIZE / 2)) <= CuberGame.CHUNK_SIZE / 2) {
							if (cuberChunk[i].getCubers()[x][y][z].isActive()) {
								cuberChunk[i].getCubers()[x][y][z].getInstance().transform.translate(x, y, z);
								modelBatch.render(cuberChunk[i].getCubers()[x][y][z].getInstance(), envir);
							}
						}

					}
				}
			}
		}
	}

	private void drawPerlin() {

		for (int i = 0; i < CHUNK_AMT; i++) {
			for (int x = 0; x < CHUNK_SIZE; x++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {

					float height = (noise.getHeightMap()[x][z] * (CHUNK_SIZE - 1) * 1.0f) * 1.0f;

					for (int y = 0; y < height; y++) {

						if (cuberChunk[i].getCubers()[x][y][z].isActive())
							modelBatch.render(cuberChunk[i].getCubers()[x][y][z].getInstance(), envir);
					}

				}
			}
		}
	}

}
