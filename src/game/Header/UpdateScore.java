package game.Header;

import java.util.ArrayList;
import java.util.List;

public class UpdateScore {
	FrameHeader frameHeader=new FrameHeader();
	
	private int score;
	private List<Integer> col=new ArrayList<Integer>();
	public void changeScore(int scor){
		col.removeAll(col);
		
		this.score=scor;
		while(score!=0){
			col.add(score%10);
			score/=10;
		}
		Integer []array=col.toArray(new Integer[col.size()]);
		
		frameHeader.scoreUpdate(array);
	}

}
