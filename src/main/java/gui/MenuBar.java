package gui;

import gui.tp0.FileMenu;
import gui.tp1.PunctualOperationsMenu;

import javax.swing.*;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public MenuBar() {
		add(new FileMenu());
		add(new PunctualOperationsMenu());
	}

}
