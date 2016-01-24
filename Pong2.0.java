import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.util.Random;
import java.util.Timer;

public class Pong2.0 extends Canvas
{
	Point delta;
	Ellipse2D.Double ball;
	Rectangle paddle1;
	Rectangle paddle2;
	static int state=4;
	static int player1score=0;
	static int player2score=0;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void main( String[] args )
	{
		JFrame win = new JFrame("Alex's Pong Game");
		JFrame score = new JFrame("Instructions");
		JPanel panel = new JPanel();
		score.setSize(700,300);
		JLabel text = new JLabel("Welcome to Alex's pong game!   ",SwingConstants.CENTER);
		JLabel instructions0 = new JLabel("        Instructions:",SwingConstants.CENTER);
		JLabel instructions = new JLabel("1. player 1 moves up and down using w and s and shoots using d");
		JLabel instructions1 = new JLabel("2. player 2 moves up and down using up and down arrows and shoots using left arrow");
		JLabel instructions2 = new JLabel("3. to begin, click on the game screen and press enter, game goes to five!");
		text.setFont(new Font("Serif", Font.BOLD, 35));
		instructions0.setFont(new Font("Serif", Font.PLAIN, 28));
		instructions.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions1.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions2.setFont(new Font("Serif", Font.PLAIN, 20));
		panel.add(text);
		panel.add(instructions0);
		panel.add(instructions);
		panel.add(instructions1);
		panel.add(instructions2);
		panel.setBackground(Color.cyan);
		//score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(screenSize.width,screenSize.height);
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		win.add( new Pong() );
		win.setVisible(true);
		score.add(panel);
		score.setLocationRelativeTo(null);
		score.setVisible(true);
		try {
		    Thread.sleep(10000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		score.setVisible(false);
	}

	public Pong()
	{
		enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
		requestFocus();
		int hw =screenSize.width/2;
		int hh = screenSize.height/2;
		ball = new Ellipse2D.Double(hw,hh-50,20,20);
		delta = new Point(-10,10);
		paddle1 = new Rectangle(50,hh-100,20,150);
		paddle2= new Rectangle (screenSize.width-90,hh-100,20,150);
		
		Timer t = new Timer(true);
		t.schedule( new java.util.TimerTask()
		{
			public void run()
			{
				doStuff();
				repaint();
			}
		}, 10, 10);

	}

	public void paint( Graphics g )
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.black);
		g2.fill(ball);
		
		g2.setColor(Color.blue);
		g2.fill(paddle1);
		
		g2.setColor(Color.red);
		g2.fill(paddle2);
		
	}

	public void processKeyEvent(KeyEvent e)
	{
		if ( e.getID() == KeyEvent.KEY_PRESSED )
		{
			if ( e.getKeyCode() == KeyEvent.VK_W ){
				paddle1.y -= 25; 
			}
			if ( e.getKeyCode() == KeyEvent.VK_S ){
				paddle1.y += 25;
			}
			if(e.getKeyCode()==KeyEvent.VK_UP){
				paddle2.y-=25;
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				paddle2.y+=25;
			}
			if(e.getKeyCode()==KeyEvent.VK_D && state==0){
				Random random = new Random() ;
		        int rand1 = random.nextInt(5) + 5;
		        int rand2 =  random.nextInt(5) + 5;
		        if(rand2%2==0)
		        	rand2=-rand2;
				delta.x=rand1;
				delta.y=rand2;
				state=3;
			}
			if(e.getKeyCode()==KeyEvent.VK_ENTER && state==4){
				Random random = new Random() ;
		        int rand1 = random.nextInt(5) + 5;
		        if(rand1%2==0)
		        	rand1=-rand1;
		        int rand2 =  random.nextInt(5) + 5;
		        if(rand2%2==0)
		        	rand2=-rand2;
				delta.x=rand1;
				delta.y=rand2;
				state=3;
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT && state==1){
				Random random = new Random() ;
		        int rand1 = random.nextInt(5) + 5;
		        int rand2 =  random.nextInt(5) + 5;
		        if(rand2%2==0)
		        	rand2=-rand2;
				delta.x=-rand1;
				delta.y=rand2;
				state=3;
			}
		}
	}
	
	public void doStuff()
	{
		if(state==3){
			ball.x += delta.x;
			ball.y += delta.y;
		}
		// and bounce if we hit a wall
		if ( ball.y < 0 || ball.y+20 > screenSize.height )
			delta.y = -delta.y;
		if ( ball.x < 0 )
			delta.x = -delta.x;
			
		// check if the ball is hitting the paddle
		if ( ball.intersects(paddle1) )
			delta.x = -delta.x;
		if(ball.intersects(paddle2))
			delta.x = -delta.x;
			
		// check for scoring
		if (ball.x<0 ){
			state=0;
			player2score++;
			JPanel panel = new JPanel();
			JLabel score1 = new JLabel();
			JFrame score = new JFrame("score");
			score1.setText("score:"+Integer.toString(player1score)+"-"+Integer.toString(player2score));
			score1.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(score1);
			panel.setBackground(Color.white);
			 panel.setLayout(new GridBagLayout());
			score.add(panel);
			score.setSize(400,200);
			score.setLocationRelativeTo(null);
			score.setVisible(true);
			try {
			    Thread.sleep(2000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			score.setVisible(false);
			ball.x = paddle1.x+18;
			ball.y = paddle1.y+60;
		}
		if(ball.x>screenSize.width){
			state=1;
			player1score++;
			JPanel panel = new JPanel();
			JLabel score1 = new JLabel();
			JFrame score = new JFrame("score");
			score1.setText("score:"+Integer.toString(player1score)+"-"+Integer.toString(player2score));
			score1.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(score1);
			panel.setLayout(new GridBagLayout());
			score.add(panel);
			panel.setBackground(Color.white);
			score.setSize(400,200);
			score.setLocationRelativeTo(null);
			score.setVisible(true);
			try {
			    Thread.sleep(2000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			score.setVisible(false);
			ball.x = paddle2.x-18;
			ball.y=paddle2.y+60;
		}
		
		if(player1score==5){
			player1score=0;
			player2score=0;
			JPanel panel = new JPanel();
			JLabel score1 = new JLabel();
			JFrame score = new JFrame("score");
			score1.setText("player 1 wins!");
			score1.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(score1);
			panel.setLayout(new GridBagLayout());
			score.add(panel);
			panel.setBackground(Color.white);
			score.setSize(400,200);
			score.setLocationRelativeTo(null);
			score.setVisible(true);
			try {
			    Thread.sleep(5000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			score.setVisible(false);
		}
		if(player2score==5){
			player2score=0;
			player1score=0;
			JPanel panel = new JPanel();
			JLabel score1 = new JLabel();
			JFrame score = new JFrame("score");
			score1.setText("player 2 wins!");
			score1.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(score1);
			panel.setLayout(new GridBagLayout());
			score.add(panel);
			panel.setBackground(Color.white);
			score.setSize(400,200);
			score.setLocationRelativeTo(null);
			score.setVisible(true);
			try {
			    Thread.sleep(5000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			score.setVisible(false);
		}
			
		
		
	}
	
	public boolean isFocusable() { return true;	}
}