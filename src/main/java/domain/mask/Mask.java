package domain.mask;

public class Mask {

	private double[][] values;

	public Mask(int width, int height) {
		this.values = new double[height][width];
	}

	public Mask(int squareSide) {
		this(squareSide, squareSide);
	}
	
	public Mask(double[][] values) {
		this(values.length, values[0].length);
		for (int x = 0; x < values.length; x++) {
			for (int y = 0; y < values[0].length; y++) {
				this.values[x][y] = values[x][y];
			}
		}
	}

	public double accessDirectly(int x, int y) {
		return values[x][y];
	}

	void setPixel(int x, int y, double value) {
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}

		this.values[x + getWidth() / 2][y + getHeight() / 2] = value;
	}

	public double getValue(int x, int y) {
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}

		return this.values[x + getWidth() / 2][y + getHeight() / 2];
	}

	private boolean validPixel(int x, int y) {
		boolean validX = x >= -this.getWidth() / 2 && x <= this.getWidth() / 2;
		boolean validY = y >= -this.getHeight() / 2
				&& y <= this.getHeight() / 2;
		return validX && validY;
	}

	public int getHeight() {
		return this.values[0].length;
	}

	public int getWidth() {
		return this.values.length;
	}

	@Override
	public String toString() {
		String resp = "";
		for (int i = 0; i < getWidth(); i++) {
			resp += "\n";
			for (int j = 0; j < getHeight(); j++) {
				resp += values[i][j] + " | ";
			}
		}
		return resp;
	}

	public Mask multiplyBy(Mask mask) {
		if (this.getWidth() != mask.getHeight()) {
			throw new IllegalArgumentException("Incompatible matrix");
		}
		Mask result = new Mask(this.getHeight(), mask.getWidth());

		for (int i = -1 * this.getHeight() / 2; i <= this.getHeight() / 2; i++) {
			for (int j = -1 * mask.getWidth() / 2; j <= mask.getWidth() / 2; j++) {
				int ijValue = 0;
				for (int k = -1 * this.getWidth() / 2; k <= mask.getHeight() / 2; k++) {
					ijValue += this.getValue(i, k) * mask.getValue(k, j);
				}
				result.setPixel(i, j, ijValue);
			}
		}

		return result;
	}

	public boolean isSquared() {
		return this.getHeight() == this.getWidth();
	}

}
