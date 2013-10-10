package gui.tp3;

import gui.MessageFrame;
import gui.Panel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.DragAndDropListener;
import application.utils.TrackingUtils;
import domain.tracking.Frontier;

public class ImageTrackingDialog extends JDialog{
	
	private Panel panel;
	private DragAndDropListener dragAndDropListener;
	
	public ImageTrackingDialog(final Panel panel) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.panel = panel;
		setTitle("Image Tracking");
		setBounds(1, 1, 300, 200);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 3);
		this.setResizable(false);
		setLayout(null);

		JPanel pan1 = new JPanel();
		pan1.setBounds(0, 0, 300, 50);
		
		JPanel pan2 = new JPanel();
		pan2.setBounds(0, 50, 300, 50);

		JLabel pLabel = new JLabel("(x0,y0): ");
		final JTextField pxTextField = new JTextField("0");
		pxTextField.setColumns(5);
		final JTextField pyTextField = new JTextField("0");
		pyTextField.setColumns(5);
		
		JLabel qLabel = new JLabel("(x1,y1): ");
		final JTextField qxTextField = new JTextField("0");
		qxTextField.setColumns(5);
		final JTextField qyTextField = new JTextField("0");
		qyTextField.setColumns(5);
		

		JButton okButton = new JButton("OK");
		okButton.setSize(300, 40);
		okButton.setBounds(0, 100, 300, 40);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int px = 0;
				int py = 0;
				int qx = 0;
				int qy = 0;
				try {
					px = Integer.valueOf(pxTextField.getText());
					py = Integer.valueOf(pyTextField.getText());
					qx = Integer.valueOf(qxTextField.getText());
					qy = Integer.valueOf(qyTextField.getText());
				} catch (NumberFormatException ex) {
					new MessageFrame("Invalid values");
					return;
				}

				if (px < 0 || py < 0 || px > qx || py > qy ||
						qx > panel.getImage().getWidth() || qy > panel.getImage().getHeight()) {
					new MessageFrame("Invalid values");
					return;
				}
				Point p = new Point(px, py);
				Point q = new Point(qx, qy);
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
				dispose();
			}
		});

		pan1.add(pLabel);
		pan1.add(pxTextField);
		pan1.add(pyTextField);
		pan2.add(qLabel);
		pan2.add(qxTextField);
		pan2.add(qyTextField);
		
		dragAndDropListener = new DragAndDropListener(panel, pxTextField, pyTextField, qxTextField, qyTextField);
		panel.addMouseListener(dragAndDropListener);
		panel.addMouseMotionListener(dragAndDropListener);

		this.add(pan1);
		this.add(pan2);
		this.add(okButton);
	}

	private boolean between(int m, int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return m >= min && m <= max;
	}
	
	@Override
	public void dispose() {
		System.out.println("Disposed");
		panel.removeMouseListener(dragAndDropListener);
		super.dispose();
	}
	
}
