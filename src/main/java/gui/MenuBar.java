package gui;

import gui.tp0.FileMenu;
import gui.tp1.filters.FiltersMenu;
import gui.tp1.noises.NoiseMenu;
import gui.tp1.punctual.PunctualOperationsMenu;

import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public MenuBar() {
		add(new FileMenu());
		add(new PunctualOperationsMenu());
		add(new FiltersMenu());
		add(new NoiseMenu());
	}

}
