import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Random;
import java.util.Timer;

public class Pong extends Canvas
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
		win.setSize(screenSize.width,screenSize.height);
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		win.add( new Pong() );
		win.setVisible(true);
	}
	  public static JLabel createLabel(String text, int AY, int AX) {
	        JLabel label = new JLabel(text, JLabel.CENTER);
	        label.setAlignmentX(AX);
	        label.setAlignmentY(AY);
	        label.setVisible(true);
	        return label;
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
			ball.x = paddle1.x+18;
			ball.y = paddle1.y+60;
		}
		if(ball.x>screenSize.width){
			state=1;
			player1score++;
			ball.x = paddle2.x-18;
			ball.y=paddle2.y+60;
		}
			
		
		
	}
	
	public boolean isFocusable() { return true;	}
}