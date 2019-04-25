package anakthsh;

import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DeferredDocumentListener implements DocumentListener {

	private final Timer timer;

    public DeferredDocumentListener(int timeOut, ActionListener listener, boolean repeats) {
        timer = new Timer(timeOut, listener);
        timer.setRepeats(repeats);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
    
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
