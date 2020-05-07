package controller;

import application.Start;
import javafx.application.Platform;

/**
 * A class that extends Thread.
 * It starts a thread in the background and observes a Start class once it has been set via setControllable() method.
 * 
 * @author mikae
 *
 */
public class TimingController extends Thread{
	private static volatile Thread TimingController;
	
	final private int TIMEOUT = 25; //Time out in seconds
	final private int WARNING = 5; //Warning time before the view is returned back to start, in seconds
	
	private boolean rest = true;
	private boolean notification = false;
	private long lastWake;
	private Start control;
	
	/**
	 * Starts the thread and observes the set Start class.
	 */
	public void start() {
        TimingController = new Thread(this);
        TimingController.start();
    }
	
	/**
	 * The method that is running after the thread has been started via start().
	 * Will continue running until cease() is called.
	 */
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
	
	/**
	 * Stops the running, and thus the observing.
	 */
	public void cease() {
		TimingController = null;
	}
	
	/**
	 * Sets the start class that this thread should observe.
	 * @param start The Start class to be observed.
	 */
	public void setControllable(Start start) {
		control = start;
	}
	
	/**
	 * Whenever an action is done, this method should be called to reset the timer.
	 */
	public void update() {
		System.out.println("Click");
		rest = notification = false;
		lastWake = System.currentTimeMillis();
	}
	
	/**
	 * Sets the thread back to starting position.
	 */
	public void reset() {
		rest = true;
		notification = false;
	}
}
