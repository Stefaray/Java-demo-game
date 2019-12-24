package mario;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;


//键盘按下监听类
public class MyKeyListener extends Thread implements KeyListener {
	
	//laststate防止向左站着不动时子弹往右
	char laststate;
	
	// A D W J 改键用
	private static final int LEFT = 65;
	private static final int RIGHT = 68;
	private static final int JUMP = 87;
	private static final int BOOM = 74;
	
	public GameFrame gf;
	
	//canAddBoom用来控制是否能够按住来不停生成子弹
	private boolean canAddBoom = true;

	public MyKeyListener(GameFrame gf) {
		this.gf = gf;

	}

	public void addBoom() {
		if (laststate == MyKeyListener.LEFT) {
			Boom b = new Boom(gf.mario.x, gf.mario.y + 10, 10);
			b.speed = -5;
			gf.boomList.add(b);

		}
		if (laststate == MyKeyListener.RIGHT) {
			Boom b = new Boom(gf.mario.x + 10, gf.mario.y + 10, 10);
			b.speed = 5;
			gf.boomList.add(b);
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case MyKeyListener.RIGHT:
			gf.mario.right = true;
			gf.mario.left = false;
			laststate = MyKeyListener.RIGHT;
			break;

		case MyKeyListener.LEFT:
			gf.mario.left = true;
			gf.mario.right = false;
			laststate = MyKeyListener.LEFT;
			break;
		case MyKeyListener.BOOM:
			if (canAddBoom) {
				addBoom();
				canAddBoom = false;
			}
			break;
		case MyKeyListener.JUMP:
			gf.mario.up = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == MyKeyListener.RIGHT) {
			gf.mario.right = false;
			Mario.img = new ImageIcon("image/mari1.png").getImage();
		}
		if (code == MyKeyListener.LEFT) {
			gf.mario.left = false;
			Mario.img = new ImageIcon("image/mari_left1.png").getImage();
		}
		if (code == MyKeyListener.JUMP) {
			gf.mario.up = false;

		}
		if (code == MyKeyListener.BOOM) {
			canAddBoom = true;

		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
