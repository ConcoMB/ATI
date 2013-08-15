package gui;

import gui.tp0.FileMenu;

import javax.swing.*;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public MenuBar() {

		// Menu for TP0
		this.add(new FileMenu());

	}

}
