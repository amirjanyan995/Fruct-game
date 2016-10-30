package game.Header;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameHeader extends JFrame {
	/*
	 * Frame settings
	 */
	private final int frameWidth=500;
	private final int frameHeigth=500;
	private final int bottomBorder=405;
	private final Color backgroundColor=new Color(255, 255, 180);
	/*
	 * Game Settings
	 */
	private static List<JLabel> col = new ArrayList<JLabel>();
	private static Random r = new Random();
	
	private static ImageIcon fructIcon[] = new ImageIcon[7];
	private static ImageIcon iconRigth = new ImageIcon("image/marioL.png");
	private static ImageIcon iconLeft = new ImageIcon("image/marioR.png");
	
	private static JLabel mario,gameOver,lifeCounter,counter;
	private static JLabel numberLabel[]=new JLabel[10];
	
	private static ThreadSimple threadLocation;
	private static ThreadCreateFruct threadCreateFruct;
	
	private static JPanel scorePanel=new JPanel();
	
	private static int score = 0,marioSpeed = 5,marioLife=3;
	
	//private static UpdateScore updateScore=new UpdateScore();
	
	public void initGui(){
		setTitle("Хватайка");
		setBounds(100, 100, frameWidth, frameHeigth);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		getContentPane().setFocusable(true);
		getContentPane().setBackground(backgroundColor);

		initFructIcon();
		initnumberIcon();
		
		threadLocation = new ThreadSimple();
		threadLocation.start();

		threadCreateFruct = new ThreadCreateFruct();
		threadCreateFruct.start();

		// ***************************************
			//Score Panel
		/*
		scorePanel.setBounds(getWidth()-150, getHeight()-69, 150, 40);
		scorePanel.setLayout(new FlowLayout());
		add(scorePanel);
		*/
			//Basket
		mario=new JLabel();
		mario.setIcon(iconRigth);
		mario.setLocation(r.nextInt(370), 385);
		mario.setSize(iconRigth.getIconWidth(), iconRigth.getIconHeight());
		add(mario);
			//Game Score
		counter=new JLabel();
		counter.setBounds(465, 425, 40, 40);
		counter.setText(Integer.toString(score));
		add(counter);
			//Game Over Label
		gameOver=new JLabel();
		gameOver.setBounds(210,425,100,40);
		add(gameOver);
			//Life Counter
		lifeCounter=new JLabel();
		lifeCounter.setBounds(10, 425, 40, 40);
		lifeCounter.setText(Integer.toString(marioLife));
		add(lifeCounter);
		//********************************************
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 37) {
					mario.setIcon(iconRigth);
					setLocationLeft();
				}
				if (e.getKeyCode() == 39) {
					mario.setIcon(iconLeft);
					setLocationRighth();
				}
			}
		});
		setVisible(true);
	}
	/*
	 *  Add fruit on game frame
	 */
	public void addFruct() throws InterruptedException {
		System.out.println( );
			int index = r.nextInt(7); // array index
			Point p = new Point(r.nextInt(460), -30); // label location
			col.add(new JLabel());
			col.get(col.size() - 1).setIcon(fructIcon[index]);
			col.get(col.size() - 1).setLocation(p);
			col.get(col.size() - 1).setSize(32, 34);
			add(col.get(col.size() - 1));
	}
	/*
	 * Initialization fruit icon
	 */
	private static void initFructIcon() {
		fructIcon[0] = new ImageIcon("image/bomba.png");
		fructIcon[1] = new ImageIcon("image/apple.png");
		fructIcon[2] = new ImageIcon("image/burger.png");
		fructIcon[3] = new ImageIcon("image/lemon.png");
		fructIcon[4] = new ImageIcon("image/pumpkin.png");
		fructIcon[5] = new ImageIcon("image/tomat.png");
		fructIcon[6] = new ImageIcon("image/watermelon.png");
	}
	private static void initnumberIcon(){
		for(int i=0;i<numberLabel.length;i++){
			String url=new String("image/numbers/"+Integer.toString(i)+".png");
			numberLabel[i]=new JLabel();
			numberLabel[i].setIcon(new ImageIcon(url));
		}
	}
	/*
	 * poxum e fruit kordinatner@
	 */
	public void setLocation() throws ConcurrentModificationException {
		for (JLabel l : col) {// 353
			//change fruit location 
			int y = l.getY();
			if ((y + 1) != 421) {
				l.setLocation(l.getX(), l.getY() + 1);
			}
			//stugum brnel e ardyoq karzinan
			if (cordinat(l)) {
				if(!isBomb((ImageIcon) l.getIcon()))
					score++;
				col.remove(l);
				l.setVisible(false);
				checkCounter();
			}
			//stugum hasele e getnin te voch 
			if (y >= bottomBorder) {
				l.setVisible(false);
				col.remove(l);
			}
		}
	}
	/*
	 * kyanqer@ verjanlu depqum  anjatum e lriv oatokner@ voronq xekavarum en xax@
	 */
	private static void gameStop(){
		gameOver.setText("Game Over");
		threadCreateFruct.interrupt();
		threadLocation.interrupt();
	}
	/*
	 * karzinayi kordinatneri stugum 
	 */
	private boolean cordinat(JLabel l){
		if (((l.getY() + 34) >= 405) && ((l.getX() >= mario.getX() && l.getX() <= mario.getX() + 48)
				|| (l.getX() + 32 >= mario.getX() && l.getX() + 32 <= mario.getX() + 48))) {
				return true;
			}
		return false;
	}
	/*
	 * karzinayi brnaci stugum bob te voch
	 */
	private boolean isBomb(ImageIcon icon){
	//ete bomb veradarcnel  true aylapes false
		if(icon.getDescription().endsWith("bomba.png")){
			marioLife--;	
			lifeCounter.setText(Integer.toString(marioLife));
			return true;
		}
		return false;
	}
	/*
	 * stugum e miavorneri qanak@ vori ardyunqic kaxvac poxum e xaxi aragutyun@
	 */
	private static void checkCounter(){
		//updateScore.changeScore(score);
		counter.setText(Integer.toString(score));
		if (score % 10 == 0)
			// aragacnel fructneri aragutyun@
			threadLocation.setSpeed(threadLocation.getSpeed()-1);
		if (score % 30 == 0) {
			marioSpeed += 1;
			threadCreateFruct.setIntervalCreateFruct(threadCreateFruct.getIntervalCreateFruct() - 200);
		}
		if(marioLife==0){
			gameStop();
		}
	}
	/*
	 * Mario rigth
	 */
	private void setLocationRighth() {
		int x = mario.getX();
		if ((x + marioSpeed) >= 438) {
			x += 438 - x;
		} else
			x += marioSpeed;
		mario.setLocation(x, mario.getY());
	}
	/*
	 * Mario Left
	 */
	private void setLocationLeft() {
		int x = mario.getX();
		if ((x - marioSpeed) <= 2) {
			x = 0;
		} else
			x -= marioSpeed;
		mario.setLocation(x, mario.getY());
	}
	public void scoreUpdate(Integer []array){
		scorePanel.removeAll();
		for(int i=array.length-1;i>=0;i--){
			scorePanel.add(numberLabel[array[i]]);
		}
		//scorePanel.add(numberLabel[r.nextInt(10)]);
	}
}
