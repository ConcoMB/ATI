package domain.mask;

public class QuadrupleMask {

	private Mask maskA, maskB, maskC, maskD;

	public QuadrupleMask(Mask maskA, Mask maskB, Mask maskC, Mask maskD) {
		this.maskA = maskA;
		this.maskB = maskB;
		this.maskC = maskC;
		this.maskD = maskD;
	}

	public Mask getMaskA() {
		return maskA;
	}

	public Mask getMaskB() {
		return maskB;
	}

	public Mask getMaskC() {
		return maskC;
	}

	public Mask getMaskD() {
		return maskD;
	}
	
	
}
