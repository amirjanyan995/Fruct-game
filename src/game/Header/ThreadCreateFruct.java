package game.Header;

public class ThreadCreateFruct extends Thread {
	private static FrameHeader frameHeader=new FrameHeader();
	
	private int intervalCreateFruct=1500;
	@Override
	public void run() {
		while (true) {
			try {
				try {
					frameHeader.addFruct();
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
				Thread.sleep(intervalCreateFruct);
			} catch (InterruptedException e) {
				e.toString();
			}
		}
	}
	public int getIntervalCreateFruct() {
		return intervalCreateFruct;
	}
	public void setIntervalCreateFruct(int intervalCreateFruct) {
		this.intervalCreateFruct = intervalCreateFruct;
	}
}
