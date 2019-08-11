package cn.sxt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;

/**
 * 飞机游戏的主窗口
 * @author LGuanG
 *
 */

public class MyGameFrame extends Frame{
	
	Image bg = GameUtil.getImage("images/bg1.png");
	Image planeImg = GameUtil.getImage("images/plane.png");

	Plane plane = new Plane(planeImg,250,50);//设置飞机的位置
	Shell[] shells = new Shell[50];
	Explode bao;
	
	Date startTime = new Date();
	Date endTime;
	int period;//游戏持续的时间
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);//画飞机
		//画出所有的炮弹
		for(int i=0;i<shells.length;i++) {
			shells[i].draw(g);
			
			boolean peng = shells[i].getRect().intersects(plane.getRect());
			if(peng) {
				plane.live = false;
				if(bao == null) {
					bao = new Explode(plane.x,plane.y);
					
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				
				
				
				bao.draw(g);
			}
			//计时功能，给出提示
			if(!plane.live) {
				
				g.setColor(Color.red);
				Font f = new Font("宋体",Font.BOLD,50);
				g.setFont(f);
				g.drawString("时间:"+period+"秒",(int)plane.x,(int)plane.y);
			}
		}
		
		g.setColor(c);
	}
	
	//帮助我们反复重画窗口
	class PaintThread extends Thread{
		@Override
		public void run() {
			while(true) {
//				System.out.println("窗口重画一次!");
				repaint();//重画窗口
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//定义键盘监听事件
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
	}
	
	/**
	 * 初始化窗口
	 */
	public void launchFrame() {
		this.setTitle("尚学堂学员_程序猿作品");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		this.setLocation(300,300);
		
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				super.windowClosed(e);
				System.exit(0);
			}
		});
		
		new PaintThread().start();
		addKeyListener(new KeyMonitor());
		
		//初始化50个炮弹
		for(int i=0;i<shells.length;i++) {
			shells[i] = new Shell();
		}
	}

	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
	
	//双缓冲解决Frame的闪烁问题
	private Image offScreenImage = null;
	
	public void update(Graphics g) {
		if(offScreenImage == null) 
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			
			Graphics gOff = offScreenImage.getGraphics();
			paint(gOff);
			g.drawImage(offScreenImage,0,0,null);
		
	}
}
