package gui.tp1.filters;

import gui.Panel;
import gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class SpatialOperationsMenu extends JMenu {

	public SpatialOperationsMenu() {
		super("Spatial operations");
		setEnabled(true);
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
		
		add(edgeEnhancement);
		
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
		
		add(meanFilter);
		
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
		
		add(medianFilter);
	}
}
