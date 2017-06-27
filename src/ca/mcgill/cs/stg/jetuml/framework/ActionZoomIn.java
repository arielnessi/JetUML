package ca.mcgill.cs.stg.jetuml.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

final class ActionZoomIn implements ActionListener {
	private EditorFrame thisFrame; 
	public ActionZoomIn(EditorFrame thisFrame){
		this.thisFrame = thisFrame;
	}
	public void actionPerformed(ActionEvent pEvent)
	{
		if( thisFrame.noCurrentGraphFrame())
		{
			return;
		}
		((GraphFrame) thisFrame.aTabbedPane.getSelectedComponent()).getGraphPanel().changeZoom(1);
	}
}