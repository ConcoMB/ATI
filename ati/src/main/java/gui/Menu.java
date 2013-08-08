package gui;

import gui.tp0.BasicOperations;

import javax.swing.*;

public class Menu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public Menu() {

		// Menu for TP0
		this.add(new BasicOperations());

	}

}
