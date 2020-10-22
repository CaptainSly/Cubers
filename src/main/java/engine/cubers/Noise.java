package engine.cubers;

import engine.CuberGame;
import engine.FastNoiseLite;

public class Noise {

	private FastNoiseLite noise;
	
	public float[][] heightMap;
	
	public Noise() {
		noise = new FastNoiseLite();
		
		noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
		heightMap = new float[CuberGame.CHUNK_SIZE][CuberGame.CHUNK_SIZE];
		
		for (int x = 0; x < CuberGame.CHUNK_SIZE; x++) {
			for (int y = 0; y < CuberGame.CHUNK_SIZE; y++) {
				heightMap[x][y] = noise.GetNoise(x, y);
			}
		}
		
	}
	
	public float[][] getHeightMap() {
		return heightMap;
	}
	

}
