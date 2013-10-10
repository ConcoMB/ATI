package gui.tp3;

import gui.MessageFrame;
import gui.Panel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.DragAndDropListener;
import application.Loader;
import application.utils.TrackingUtils;
import domain.tracking.Frontier;

@SuppressWarnings("serial")
public class VideoTrackingDialog extends JDialog {

	private Panel panel;
	private DragAndDropListener dragAndDropListener;

	public VideoTrackingDialog(final Panel panel) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.panel = panel;
		setTitle("Video Tracking");
		setBounds(1, 1, 300, 200);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3 + panel.getWidth(),
				size.height / 3 - getHeight() / 3);
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

				if (px > qx) {
					int aux;
					aux = qx;
					qx = px;
					px = aux;
				}
				if (py > qy) {
					int aux;
					aux = qy;
					qy = py;
					py = aux;
				}
				if (px < 0 || py < 0 || qx > panel.getImage().getWidth()
						|| qy > panel.getImage().getHeight()) {
					new MessageFrame("Invalid values");
					return;
				}

				track(px, py, qx, qy);
			}
		});

		pan1.add(pLabel);
		pan1.add(pxTextField);
		pan1.add(pyTextField);
		pan2.add(qLabel);
		pan2.add(qxTextField);
		pan2.add(qyTextField);

		dragAndDropListener = new DragAndDropListener(panel, pxTextField,
				pyTextField, qxTextField, qyTextField);
		panel.addMouseListener(dragAndDropListener);
		panel.addMouseMotionListener(dragAndDropListener);

		this.add(pan1);
		this.add(pan2);
		this.add(okButton);
	}

	@Override
	public void dispose() {
		panel.removeMouseListener(dragAndDropListener);
		super.dispose();
	}

	private void track(int px, int py, int qx, int qy) {
		dispose();
		String[] splitted = Loader.getCurrentFile().getAbsolutePath()
				.split("1.");
		if (splitted.length > 2) {
			new MessageFrame("Invalid file");
			return;
		}
		String filePrefix = splitted[0];
		String extension = splitted[1];
		Frontier frontier = new Frontier(new Point(px, py), new Point(qx, qy),
				panel.getImage());
		TrackingUtils.track(frontier, panel);
		panel.repaint();
		int i = 2;
		boolean read = true;
		int width = panel.getImage().getWidth();
		int height = panel.getImage().getHeight();
		while (read) {
			try {
				File currentFile = new File(filePrefix + i + "." + extension);
				if (!currentFile.exists()) {
					read = false;
					break;
				}
				panel.setTempImage(Loader.loadImage(currentFile));
				frontier.setImage(panel.getTempImage());
				panel.paintImmediately(0, 0, width, height);
				TrackingUtils.track(frontier, panel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}

	}

	
}
