package cn.sxt.game;

import java.io.IOException;
import java.net.URL;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class GameUtil {
	//��������ý�������˽�л�
	private GameUtil() {
		
	}
	
	public static Image getImage(String path) {
		BufferedImage bi = null;
		try {
			URL u = GameUtil.class.getClassLoader().getResource(path);
			bi = ImageIO.read(u);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

}