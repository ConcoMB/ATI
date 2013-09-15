package gui.menus;


import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public MenuBar() {
		add(new FileMenu());
		add(new PunctualOperationsMenu());
		add(new FiltersMenu());
		add(new NoiseMenu());
		add(new DiffusionMenu());
		add(new ThresholdMenu());
	}
}
