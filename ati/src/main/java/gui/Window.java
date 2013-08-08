package gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	private Panel panel = new Panel(this);

	private Menu menuBar = new Menu();

    private GraphicsConfiguration config;

	public Window() {

        config = getGraphicsConfiguration();
		setTitle("TPS Analisis y Tratamiento de Imagenes");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 900, 800);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 2);
		setResizable(false);
		this.setMinimumSize(new Dimension(600, 600));
		panel.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
        panel.initKeyBindings();
		add(panel);


	}



	public Panel getPanel() {
		return panel;
	}

	public void enableTools() {
		menuBar.getComponent(0).setEnabled(true);
		((Tp0) menuBar.getComponent(0)).saveImage.setEnabled(true);
	}

    public GraphicsConfiguration getConfig() {
        return config;
    }
}
