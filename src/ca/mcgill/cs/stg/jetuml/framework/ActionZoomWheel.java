package ca.mcgill.cs.stg.jetuml.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class ActionZoomWheel implements MouseWheelListener {
	private EditorFrame thisFrame;
	private ActionZoomIn actionIn;
	private ActionZoomOut actionOut;
	private Boolean keyPressed;
	public ActionZoomWheel(ActionZoomIn actIn, ActionZoomOut actOut){
		this.actionIn = actIn;
		this.actionOut = actOut;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
        	this.actionIn.actionPerformed(null);
        	//((GraphFrame) thisFrame.aTabbedPane.getSelectedComponent()).getGraphPanel().changeZoom(1);
        } else {
        	this.actionOut.actionPerformed(null);
        }
		// TODO Auto-generated method stub
	}

}