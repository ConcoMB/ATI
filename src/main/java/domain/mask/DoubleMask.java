package domain.mask;

public class DoubleMask {

	private Mask maskX;
	private Mask maskY;
	
	public DoubleMask(Mask maskX, Mask maskY) {
		this.maskX = maskX;
		this.maskY = maskY;
	}

	public Mask getMaskX() {
		return maskX;
	}
	public Mask getMaskY() {
		return maskY;
	}
	
	
	
}
