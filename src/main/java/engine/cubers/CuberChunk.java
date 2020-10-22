package engine.cubers;

import com.badlogic.gdx.math.Vector3;

import engine.CuberGame;

public class CuberChunk {


	private Cuber[][][] cubers;

	public CuberChunk() {
		cubers = new Cuber[CuberGame.CHUNK_SIZE][CuberGame.CHUNK_SIZE][CuberGame.CHUNK_SIZE];
	}
	
	public void addCuber(Vector3 pos, Cuber cuber) {
		cubers[(int) pos.x][(int) pos.y][(int) pos.z] = cuber;
	}

	public void delCuber(Vector3 pos) {
		cubers[(int) pos.x][(int) pos.y][(int) pos.z] = null;
	}

	public Cuber getCuber(Vector3 pos) {
		return cubers[(int) pos.x][(int) pos.y][(int) pos.z];
	}

	public Cuber[][][] getCubers() {
		return cubers;
	}
	
	

}
