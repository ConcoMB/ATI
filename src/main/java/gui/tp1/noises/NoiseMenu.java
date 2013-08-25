package gui.tp1.noises;

import gui.Panel;
import gui.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class NoiseMenu extends JMenu {

	public NoiseMenu() {
		super("Noise");
		setEnabled(true);
		
		JMenuItem saltAndPepper = new JMenuItem("Salt and Pepper");
		saltAndPepper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog sAndP = new SaltAndPepperDialog(panel);
				sAndP.setVisible(true);
			}
		});
		
		JMenuItem gaussian = new JMenuItem("Gaussian");
		gaussian.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog gauss = new GaussianDialog(panel);
				gauss.setVisible(true);
			}
		});
		
		JMenuItem rayleigh = new JMenuItem("Rayleigh");
		rayleigh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog exp = new RayleighDialog(panel);
				exp.setVisible(true);
			}
		});
		
		JMenuItem exponential = new JMenuItem("Exponential");
		exponential.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				JDialog exp = new ExponentialDialog(panel);
				exp.setVisible(true);
			}
		});
		
		add(exponential);
		add(rayleigh);
		add(gaussian);
		add(saltAndPepper);
	}
		
}
