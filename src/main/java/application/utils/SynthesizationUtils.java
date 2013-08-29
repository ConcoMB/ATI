package application.utils;

import static domain.Image.ColorChannel.BLUE;
import static domain.Image.ColorChannel.GREEN;
import static domain.Image.ColorChannel.RED;
import gui.tp2.filters.AbstractBorderDetectorDialog.SynthesizationType;
import domain.Image;
import domain.Image.ColorChannel;

public class SynthesizationUtils {

	public static Image synthesize(SynthesizationType synthesizationType,
			Image... images) {
		switch (synthesizationType) {
		case MAX:
			return synthesize(new MaxFunction(), images);
		case MIN:
			return synthesize(new MinFunction(), images);
		case AVG:
			return synthesize(new AvgFunction(), images);
		case ABS:
			return synthesize(new AbsFunction(), images);
		default:
			return null;
		}
	}

	private static Image synthesize(Function f, Image[] images) {
		Image synthesized = new Image(images[0].getWidth(),
				images[0].getHeight(), images[0].getImageFormat(),
				images[0].getType());
		for (int x = 0; x < synthesized.getWidth(); x++) {
			for (int y = 0; y < synthesized.getHeight(); y++) {
				double[] reds = getPixels(x, y, RED, images);
				double[] greens = getPixels(x, y, GREEN, images);
				double[] blues = getPixels(x, y, BLUE, images);
				synthesized.setPixel(x, y, RED, f.apply(reds));
				synthesized.setPixel(x, y, GREEN, f.apply(greens));
				synthesized.setPixel(x, y, BLUE, f.apply(blues));
			}
		}
		return synthesized;
	}

	private static double[] getPixels(int x, int y, ColorChannel channel,
			Image[] images) {
		double[] data = new double[images.length];
		for (int i = 0; i < images.length; i++) {
			data[i] = images[i].getPixel(x, y, channel);
		}
		return data;
	}

	private static class MaxFunction implements Function {
		@Override
		public double apply(double[] data) {
			double max = Double.MIN_VALUE;
			for (double d : data) {
				max = d > max ? d : max;
			}
			return max;
		}
	}

	private static class MinFunction implements Function {
		@Override
		public double apply(double[] data) {
			double min = Double.MAX_VALUE;
			for (double d : data) {
				min = d < min ? d : min;
			}
			return min;
		}
	}

	private static class AbsFunction implements Function {
		@Override
		public double apply(double[] data) {
			double sum = 0;
			for (double d : data) {
				sum += d * d;
			}
			return Math.sqrt(sum);
		}
	}

	private static class AvgFunction implements Function {
		@Override
		public double apply(double[] data) {
			double sum = 0;
			for (double d : data) {
				sum += d;
			}
			return sum / data.length;
		}
	}

}
