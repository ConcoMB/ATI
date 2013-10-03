package gui.menus;

import gui.Panel;
import gui.Window;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import application.utils.TrackingUtils;
import domain.Image;
import domain.tracking.Frontier;

@SuppressWarnings("serial")
public class TrackingMenu extends JMenu {

	private boolean between(int m, int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return m >= min && m <= max;
	}

	public TrackingMenu() {
		super("Tracking");
		setEnabled(true);

		JMenuItem imageTracking = new JMenuItem("Image tracking");
		imageTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel panel = (((Window) getTopLevelAncestor()).getPanel());
				if (panel.getImage() == null) {
					return;
				}
				Point p = new Point(140, 140);
				Point q = new Point(160, 160);

				Set<Point> outer = new HashSet<Point>();
				Set<Point> outerBorder = new HashSet<Point>();
				Set<Point> innerBorder = new HashSet<Point>();
				Set<Point> inner = new HashSet<Point>();

				for (int x = 0; x < panel.getImage().getWidth(); x++) {
					for (int y = 0; y < panel.getImage().getHeight(); y++) {
						Point r = new Point(x, y);
						if (x < p.x || x > q.x || y < p.y || y > q.y) {
							outer.add(r);
						} else if ((between(y, p.y, q.y) && (x == p.x || x == q.x))
								|| (between(x, p.x, q.x) && (y == p.y || y == q.y))) {
							outerBorder.add(r);
						} else if ((between(y, p.y + 1, q.y - 1) && (x == p.x + 1 || x == q.x - 1))
								|| (between(x, p.x + 1, q.x - 1) && (y == p.y + 1 || y == q.y - 1))) {
							innerBorder.add(r);
						} else {
							inner.add(r);
						}
					}
				}
				TrackingUtils.track(new Frontier(panel.getImage(), outer,
						outerBorder, innerBorder, inner), panel);
				panel.repaint();
			}
		});

		add(imageTracking);
	}
}
