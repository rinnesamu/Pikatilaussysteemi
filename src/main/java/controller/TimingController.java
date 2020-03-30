package controller;

import application.Start;

public class TimingController extends Thread{
	private boolean rest = true;
	private long lastWake;
	private Start control;
	
	public void run() {
		check();
	}
	
	public void update() {
		rest = false;
		lastWake = System.currentTimeMillis();
		return;
	}
	
	public boolean isResting() {
		return rest;
	}
	
	public void setControllable(Start start) {
		control = start;
	}
	
	public void check() {
		while(true) {
			System.out.println("Ctrl juoksee");
			if( !rest ) {
				if( System.currentTimeMillis() - lastWake > 15000) {
					System.out.println("Palauta alkuruutuun");
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
}
