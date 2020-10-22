package engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import engine.cubers.Cuber;
import engine.cubers.CuberChunk;
import engine.cubers.CuberType;
import engine.cubers.Noise;

public class CuberGame extends InputAdapter implements ApplicationListener {

	private PerspectiveCamera cam;
	private CuberChunk[] cuberChunk;
	private ModelBatch modelBatch;
	private CameraInputController cap;

	private Vector3 position;

	private Stage stage;
	private Label label;
	private StringBuilder sb;

	private FreeTypeFontGenerator gen;
	private FreeTypeFontParameter par;
	private BitmapFont font;

	private int visibleCount;

	private Cuber selected, selecting;
	private Material selectionMaterial, originalMaterial;
	
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_AMT = 10;
	private Noise noise;

	private Environment envir;

	@Override
	public void create() {
		noise = new Noise();
		modelBatch = new ModelBatch();

		position = new Vector3();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		cap = new CameraInputController(cam);

		gen = new FreeTypeFontGenerator(Gdx.files.internal("consola.ttf"));
		par = new FreeTypeFontParameter();

		par.size = 22;
		par.color = Color.WHITE;
		font = gen.generateFont(par);
		stage = new Stage();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		sb = new StringBuilder();

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
						cuberChunk[i].getCubers()[x][y][z] = new Cuber(true, CuberType.GRASS_CUBER, builder);
						cuberChunk[i].getCubers()[x][y][z].setActive(true);
						cuberChunk[i].getCubers()[x][y][z].getInstance().transform.translate(x * i, y * i, z * i);

					}
				}
			}
		}

		selectionMaterial = new Material();
		selectionMaterial.set(ColorAttribute.createDiffuse(Color.ORANGE));
		originalMaterial = new Material();
		
		stage.addActor(label);
		Gdx.input.setInputProcessor(new InputMultiplexer(this, cap));
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL20.GL_BACK);

		cap.update();

		modelBatch.begin(cam);
		visibleCount = 0;

		// Draws a sphere
		drawPerlin();

		modelBatch.end();

		sb.setLength(0);
		sb.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
		sb.append(" Visible Blocks: ").append(visibleCount);

		label.setText(sb);
		stage.draw();

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
	
//	public Cuber getObject(int screenX, int screenY) {
//		Ray ray = cam.getPickRay(screenX, screenY);
//		Cuber result;
//		float distance = -1;
//		
//		for (int i = 0; i < CHUNK_AMT; i++) {
//			for (int j = 0; j < cuberChunk[i].getCubers().length; j++) {
//				result = cuberChunk[i].getCuber(position)
//			}
//		}
//		
//		return result;
//	}

	private boolean isVisible(final Camera cam, Cuber cuber) {
		cuber.getInstance().transform.getTranslation(position);
		position.add(cuber.getCenter());
		return cam.frustum.sphereInFrustum(position, cuber.getRadius());
	}

	private void drawPerlin() {

		for (int i = 0; i < CHUNK_AMT; i++) {
			for (int x = 0; x < CHUNK_SIZE; x++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {

					float height = (noise.getHeightMap()[x][z] * (CHUNK_SIZE - 1) * 1.0f) * 1.0f;

					for (int y = 0; y < height; y++) {

						if (isVisible(cam, cuberChunk[i].getCubers()[x][y][z])) {
							modelBatch.render(cuberChunk[i].getCubers()[x][y][z].getInstance(), envir);
							visibleCount++;
						}
					}

				}
			}
		}
	}

}
