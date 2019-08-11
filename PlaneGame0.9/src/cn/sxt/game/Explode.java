package cn.sxt.game;

import java.awt.Graphics;
import java.awt.Image;

/**
 * ±¨’®¿‡
 * @author LGuanG
 *
 */

public class Explode {
	double x,y;
	static Image[] imgs = new Image[16];
	static {
		for(int i=0;i<16;i++) {
			imgs[i] = GameUtil.getImage("image_explode/e"+(i+1)+".png");
			imgs[i].getWidth(null);
		}
	}
	
	int count;
	
	public Explode(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		if(count<=15) {
			g.drawImage(imgs[count],(int)x,(int)y,null);
			count++;
		}
	}

}
