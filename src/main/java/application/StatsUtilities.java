package application;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import domain.Image;
import domain.Image.ColorChannel;

public class StatsUtilities {

	/**
	 * Greyscale
	 */
	public static BufferedImage getHistogram(Image image) {
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		double[] histData = getHistogramFrequencies(image);
		dataset.addSeries("Histogram", histData, histData.length);
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		JFreeChart chart = ChartFactory.createHistogram("Histogram",
				"Grey level", "Frequency", dataset, orientation, false,
				false, false);
		return chart.createBufferedImage(600, 400);
	}
	
	/**
	 * Color
	 */
	public static BufferedImage getHistogram(Image image, ColorChannel color) {
		String colorStr = null, title = null;
		Color c = null;
		switch (color) {
		case RED: 
			colorStr = "Red scale";
			title = "Red histogram";
			c = Color.red;
			break;
		case GREEN:
			colorStr = "Green scale";
			title = "Green histogram";
			c = Color.green;
			break;
		case BLUE:
			colorStr = "Blue scale";
			title = "Blue histogram";
			c = Color.blue;
			break;
		}
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		double[] histData = getHistogramFrequencies(image, color);
		dataset.addSeries(title, histData, histData.length);
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		JFreeChart chart = ChartFactory.createHistogram(title,
				colorStr, "Frequency", dataset, orientation, false,
				false, false);
		final XYPlot plot = chart.getXYPlot();
		XYBarRenderer barRenderer = (XYBarRenderer)plot.getRenderer();
		barRenderer.setSeriesPaint(0, c);
		return chart.createBufferedImage(300, 200);
	}
	
	private static double[] getHistogramFrequencies(Image image) {
		double[] result = new double[image.getHeight() * image.getWidth()];
		for (int i = 0; i < result.length; i++) {
			result[i] = image.getGraylevelFromPixel(
					(int) Math.floor(i / image.getWidth()), i % image.getHeight());
		}
		return result;
	}
	
	private static double[] getHistogramFrequencies(Image image, ColorChannel color) {
		double[] result = new double[image.getHeight() * image.getWidth()];
		for (int i = 0; i < result.length; i++) {
			result[i] = image.getPixel(
					(int) Math.floor(i / image.getWidth()), i % image.getHeight(), color);
		}
		return result;
	}
}
