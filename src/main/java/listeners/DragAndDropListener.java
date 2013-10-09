package listeners;

import gui.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class DragAndDropListener implements MouseListener {
	JTextField px, py, qx, qy;
	Panel panel;
	
	public DragAndDropListener(Panel panel, JTextField px, JTextField py, JTextField qx, JTextField qy) {
		this.px = px;
		this.py = py;
		this.qx = qx;
		this.qy = qy;
		this.panel = panel;
	}
		
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("released ("+e.getX()+","+e.getY()+")");
		qx.setText(String.valueOf(e.getX()));
		qy.setText(String.valueOf(e.getY()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("pressed ("+e.getX()+","+e.getY()+")");
		px.setText(String.valueOf(e.getX()));
		py.setText(String.valueOf(e.getY()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
