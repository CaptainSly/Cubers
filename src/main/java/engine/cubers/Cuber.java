package engine.cubers;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Cuber {

	private boolean isActive;

	private CuberType cuberType;

	private ModelInstance instance;

	private Vector3 center = new Vector3();
	private Vector3 dims = new Vector3();
	private BoundingBox bounds = new BoundingBox();
	private float radius;
	
	public Cuber(boolean isActive, CuberType cuberType, ModelBuilder builder) {
		this.isActive = isActive;
		this.cuberType = cuberType;

		Model cuberModel = builder.createBox(1f, 1f, 1f,
				new Material(ColorAttribute.createDiffuse(cuberType.getColor())), Usage.Position | Usage.Normal);

		this.instance = new ModelInstance(cuberModel);
		
		instance.calculateBoundingBox(bounds);
		bounds.getCenter(center);
		bounds.getDimensions(dims);
		
		radius = dims.len() / 2f;
		
	}

	public boolean isActive() {
		return isActive;
	}

	public CuberType getCuberType() {
		return cuberType;
	}

	public ModelInstance getInstance() {
		return instance;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setCuberType(CuberType cuberType) {
		this.cuberType = cuberType;
	}

	public void setInstance(ModelInstance instance) {
		this.instance = instance;
	}

	public Vector3 getCenter() {
		return center;
	}

	public Vector3 getDims() {
		return dims;
	}

	public BoundingBox getBounds() {
		return bounds;
	}
	
	public float getRadius() {
		return radius;
	}

}
