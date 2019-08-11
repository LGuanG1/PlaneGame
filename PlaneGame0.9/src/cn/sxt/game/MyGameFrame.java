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
 * �ɻ���Ϸ��������
 * @author LGuanG
 *
 */

public class MyGameFrame extends Frame{
	
	Image bg = GameUtil.getImage("images/bg1.png");
	Image planeImg = GameUtil.getImage("images/plane.png");

	Plane plane = new Plane(planeImg,250,50);//���÷ɻ���λ��
	Shell[] shells = new Shell[50];
	Explode bao;
	
	Date startTime = new Date();
	Date endTime;
	int period;//��Ϸ������ʱ��
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);//���ɻ�
		//�������е��ڵ�
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
			//��ʱ���ܣ�������ʾ
			if(!plane.live) {
				
				g.setColor(Color.red);
				Font f = new Font("����",Font.BOLD,50);
				g.setFont(f);
				g.drawString("ʱ��:"+period+"��",(int)plane.x,(int)plane.y);
			}
		}
		
		g.setColor(c);
	}
	
	//�������Ƿ����ػ�����
	class PaintThread extends Thread{
		@Override
		public void run() {
			while(true) {
//				System.out.println("�����ػ�һ��!");
				repaint();//�ػ�����
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//������̼����¼�
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
	 * ��ʼ������
	 */
	public void launchFrame() {
		this.setTitle("��ѧ��ѧԱ_����Գ��Ʒ");
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
		
		//��ʼ��50���ڵ�
		for(int i=0;i<shells.length;i++) {
			shells[i] = new Shell();
		}
	}

	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
	
	//˫������Frame����˸����
	private Image offScreenImage = null;
	
	public void update(Graphics g) {
		if(offScreenImage == null) 
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			
			Graphics gOff = offScreenImage.getGraphics();
			paint(gOff);
			g.drawImage(offScreenImage,0,0,null);
		
	}
}
