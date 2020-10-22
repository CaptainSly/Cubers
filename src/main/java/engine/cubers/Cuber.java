package engine.cubers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Cuber {

	private boolean isActive;

	private CuberType cuberType;

	private ModelInstance instance;

	public Cuber(boolean isActive, CuberType cuberType, ModelBuilder builder) {
		this.isActive = isActive;
		this.cuberType = cuberType;

		Model cuberModel = builder.createBox(1f, 1f, 1f,
				new Material(ColorAttribute.createDiffuse(cuberType.getColor())), Usage.Position | Usage.Normal);

		this.instance = new ModelInstance(cuberModel);
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

}
