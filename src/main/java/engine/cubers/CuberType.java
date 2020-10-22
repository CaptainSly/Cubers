package engine.cubers;

import com.badlogic.gdx.graphics.Color;

public enum CuberType {

	DEFAULT_CUBER(Color.WHITE), GRASS_CUBER(Color.GREEN), STONE_CUBER(Color.GRAY), WATER_CUBER(Color.BLUE),;

	private Color color;

	CuberType(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
