package gui;

import gui.tp0.FileMenu;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Panel panel = new Panel(this);
	private MenuBar menuBar = new MenuBar();
    private GraphicsConfiguration config;

	public Window() {
        config = getGraphicsConfiguration();
		setTitle("Images Analysis and Treatment");
		setBounds(1, 1, 500, 500);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(size.width / 3 - getWidth() / 3, size.height / 3
				- getHeight() / 2);
//		setResizable(false);
		setMinimumSize(new Dimension(600, 600));
		panel.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
        panel.initKeyBindings();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
	}

	public Panel getPanel() {
		return panel;
	}

	public void enableTools() {
		menuBar.getComponent(0).setEnabled(true);
		((FileMenu) menuBar.getComponent(0)).saveImage.setEnabled(true);
		((FileMenu) menuBar.getComponent(0)).cropImage.setEnabled(true);
	}

    public GraphicsConfiguration getConfig() {
        return config;
    }
    
    
}
