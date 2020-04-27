package controller;

import application.Start;
import javafx.application.Platform;

public class TimingController extends Thread{
	private static volatile Thread TimingController;
	
	final private int TIMEOUT = 25; //Time out in seconds
	final private int WARNING = 5; //Warning time before the view is returned back to start, in seconds
	
	private boolean rest = true;
	private boolean notification = false;
	private long lastWake;
	private Start control;
	
	public void start() {
        TimingController = new Thread(this);
        TimingController.start();
    }
	
	public void run() {
		Thread thisThread = Thread.currentThread();
		while(TimingController == thisThread) {
			if( !rest ) {
				if( System.currentTimeMillis() - lastWake > (TIMEOUT - WARNING) * 1000) {
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
				if( System.currentTimeMillis() - lastWake > TIMEOUT * 1000) {
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
	
	public void cease() {
		TimingController = null;
	}
	
	public void setControllable(Start start) {
		control = start;
	}
	
	public void update() {
		System.out.println("Click");
		rest = notification = false;
		lastWake = System.currentTimeMillis();
	}
	
	public void reset() {
		rest = true;
		notification = false;
	}
}
