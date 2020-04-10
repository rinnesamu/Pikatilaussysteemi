package controller;

import application.Start;
import javafx.application.Platform;

public class TimingController extends Thread{
	private boolean rest = true;
	private long lastWake;
	private Start control;
	
	public void run() {
		while(true) {
			if( !rest ) {
				if( System.currentTimeMillis() - lastWake > 15000) {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					        control.initUI();
					    }
					});
					rest = true;
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
		rest = false;
		lastWake = System.currentTimeMillis();
	}
}
