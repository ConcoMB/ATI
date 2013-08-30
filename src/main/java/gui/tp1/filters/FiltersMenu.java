package gui.tp1.filters;

import gui.Panel;
import gui.Window;
import gui.tp2.filters.GaussianFilterDialog;
import gui.tp2.filters.KirshBorderDetectorDialog;
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
		
		JMenuItem kirshFilter = new JMenuItem("Kirsh filter");
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
		
		JMenuItem robertsFilter = new JMenuItem("Roberts filter");
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
		
		JMenuItem sobelFilter = new JMenuItem("Sobel filter");
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
		
		JMenuItem prewittFilter = new JMenuItem("Prewit filter");
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
		
		JMenuItem gaussianFilter = new JMenuItem("Gaussian filter");
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
		
		JMenuItem meanFilter = new JMenuItem("Mean filter");
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
		
		
		JMenuItem medianFilter = new JMenuItem("Median filter");
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
		add(robertsFilter);
		add(prewittFilter);
		add(sobelFilter);
		add(new JSeparator());
		add(kirshFilter);
		
	}
}
