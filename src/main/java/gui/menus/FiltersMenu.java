package gui.menus;

import gui.Panel;
import gui.Window;
import gui.tp1.filters.EdgeEnhancementDialog;
import gui.tp1.filters.MeanFilterDialog;
import gui.tp1.filters.MedianFilterDialog;
import gui.tp2.filters.AnOtherFilterDialog;
import gui.tp2.filters.DirectionalPrewittFilterDialog;
import gui.tp2.filters.DirectionalSobelFilterDialog;
import gui.tp2.filters.GaussianFilterDialog;
import gui.tp2.filters.KirshBorderDetectorDialog;
import gui.tp2.filters.LaplacianDialog;
import gui.tp2.filters.LogDialog;
import gui.tp2.filters.PrewittBorderDetectorDialog;
import gui.tp2.filters.RobertsBorderDetectorDialog;
import gui.tp2.filters.SobelBorderDetectorDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class FiltersMenu extends JMenu {

	public FiltersMenu() {
		super("Filter");
		setEnabled(true);

		JMenu directionalFilters = new JMenu("Directional filters");
		JMenu secodDerivate = new JMenu("Second derivate filters");
		JMenu laplacian = new JMenu("Laplacian filters");

		
		JMenuItem laplacianFilter = new JMenuItem("Laplacian");
		laplacianFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new LaplacianDialog(panel);
				dialog.setVisible(true);
			}
		});
		JMenuItem logFilter = new JMenuItem("LoG");
		logFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new LogDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem kirshFilter = new JMenuItem("Kirsh");
		kirshFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new KirshBorderDetectorDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem aDifferentFilter = new JMenuItem("Sobel");
		aDifferentFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new DirectionalSobelFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem anOtherFilter = new JMenuItem("An other filter");
		anOtherFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new AnOtherFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem directionalPrewittFilter = new JMenuItem("Prewitt");
		directionalPrewittFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new DirectionalPrewittFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem robertsFilter = new JMenuItem("Roberts");
		robertsFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new RobertsBorderDetectorDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem sobelFilter = new JMenuItem("Sobel");
		sobelFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new SobelBorderDetectorDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem prewittFilter = new JMenuItem("Prewit");
		prewittFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new PrewittBorderDetectorDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem gaussianFilter = new JMenuItem("Gaussian");
		gaussianFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new GaussianFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem edgeEnhancement = new JMenuItem("Edge enhancement");
		edgeEnhancement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new EdgeEnhancementDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem meanFilter = new JMenuItem("Mean");
		meanFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new MeanFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		JMenuItem medianFilter = new JMenuItem("Median");
		medianFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog dialog = new MedianFilterDialog(panel);
				dialog.setVisible(true);
			}
		});

		add(meanFilter);
		add(medianFilter);
		add(gaussianFilter);
		add(new JSeparator());
		add(edgeEnhancement);
		add(secodDerivate);
		
		secodDerivate.add(robertsFilter);
		secodDerivate.add(prewittFilter);
		secodDerivate.add(sobelFilter);
		add(directionalFilters);
		directionalFilters.add(kirshFilter);
		directionalFilters.add(directionalPrewittFilter);
		directionalFilters.add(anOtherFilter);
		directionalFilters.add(aDifferentFilter);
		add(laplacian);
		laplacian.add(laplacianFilter);
		laplacian.add(logFilter);
	}
}
