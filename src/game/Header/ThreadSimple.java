package game.Header;

import java.util.ConcurrentModificationException;

public class ThreadSimple extends Thread{
	FrameHeader frameHeader=new FrameHeader();
	private int speed=14;
	@Override
	public void run() {
		try {
			while(true){
				try{
					frameHeader.setLocation();
				}catch (ConcurrentModificationException e) {
					//System.out.println(e);
				}
				Thread.sleep(speed);
			}
		} catch (InterruptedException e) {
		}
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
