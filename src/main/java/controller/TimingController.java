package controller;

import application.Start;
import javafx.application.Platform;

public class TimingController extends Thread{
	private boolean rest = true;
	private boolean notification = false;
	private long lastWake;
	private Start control;
	
	public void run() {
		while(true) {
			if( !rest ) {
				if( System.currentTimeMillis() - lastWake > 10000) {
					if(!notification) {
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
								control.timeOutWarning();
						    }
						});
						notification = true;
					}
				}
				if( System.currentTimeMillis() - lastWake > 15000) {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					        control.initUI();
					    }
					});
					rest = notification = true;
				}
			}
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setControllable(Start start) {
		control = start;
	}
	
	public void update() {
		rest = notification = false;
		lastWake = System.currentTimeMillis();
	}
}
