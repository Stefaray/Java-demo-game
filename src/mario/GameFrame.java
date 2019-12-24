package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import enery.Coin;
import enery.Enery;
import enery.Pipe;

public class GameFrame extends JFrame {

	
	// 背景图片
	public BackgroundImage bg = new BackgroundImage();

	public Enery pipe, coin, brick;
	
	//public JLabel label=new JLabel("Score");

	// 容器装敌人
	public ArrayList<Enery> eneryList = new ArrayList<Enery>();

	// 子弹容器
	public ArrayList<Boom> boomList = new ArrayList<Boom>();

	// 子弹的速度
	public int bspeed = 0;

	// 画地图，制定规则，是1画砖头，是2画金币，是3画水管
	public int[][] map = new Map().readMap();

	public void initFrame() {
		// 设置窗体相关属性
		this.setSize(800, 600);
		this.setTitle("山寨版超级玛丽");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);

		// 该窗体添加键盘监听
		MyKeyListener kl = new MyKeyListener(this);
		this.addKeyListener(kl);
		
		//label.setBounds(10, 10,20,10);
		//add(label);
		
	}

	// 构造函数里面初始化背景图片和马里奥对象
	public GameFrame() throws Exception {
		mario.start();

		// 窗体重绘线程
		new Thread() {
			public void run() {
				while (true) {
					// 重绘窗体
					
					repaint();
					//label.repaint();
					// 检查子弹是否出界
					checkBoom();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		// 读取地图，并配置地图
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				// 读取到的是1，画砖头
				if (map[i][j] == 1) {
					brick = new Pipe(1,j * 30, i * 30, 30, 30, new ImageIcon("image/brick.png").getImage());
					eneryList.add(brick);
				}
				// 读到2画金币
				if (map[i][j] == 2) {
					coin = new Pipe(2,j * 30, i * 30, 30, 30, new ImageIcon("image/coin_brick.png").getImage());
					eneryList.add(coin);
				}
				// 读到3画水管
				if (map[i][j] == 3) {
					pipe = new Pipe(3,j * 30, i * 30, 30, 30, new ImageIcon("image/pipe.png").getImage());
					eneryList.add(pipe);
				}

			}
		}
	}
	// 设置背景音乐
	// com.huaxin.music.Util.startMusic("music/bg1.wav");

	public void paint(Graphics g) {
		
		// 利用双缓冲画背景图片和马里奥
		BufferedImage bi = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, bg.x, bg.y, null);
		big.drawImage(bg.img, bg.x + 800, bg.y, null);
		big.drawImage(bg.img, bg.x + 1600, bg.y, null);
		big.drawImage(bg.img, bg.x + 2400, bg.y, null);

		for (int i = 0; i < eneryList.size(); i++) {
			Enery e = eneryList.get(i);
			big.drawImage(e.img, e.x, e.y, e.width, e.height, null);
		}

		// 画子弹
		for (int i = 0; i < boomList.size(); i++) {
			Boom b = boomList.get(i);
			Color c = big.getColor();
			big.setColor(Color.red);
			big.fillOval(b.x += b.speed, b.y, b.width, b.width);
			big.setColor(c);
		}

		// 画人物
		if(mario.left)
			big.drawImage(Mario.imgL, mario.x, mario.y, mario.width, mario.height, null);
		else if(mario.right){
			big.drawImage(Mario.imgR, mario.x, mario.y, mario.width, mario.height, null);

		}
		else
			
			big.drawImage(Mario.img, mario.x, mario.y, mario.width, mario.height, null);
			// 1+
		big.drawString("SCORE", 10, 10);
		//big.
		g.drawImage(bi, 0, 0, null);

	}

	// 检查子弹是否出界，出界则从容器中移除，不移除的话，内存会泄漏
	public void checkBoom() {
		for (int i = 0; i < boomList.size(); i++) {
			Boom b = boomList.get(i);
			if (b.x < 0 || b.x > 800) {
				boomList.remove(i);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		GameFrame gf = new GameFrame();
		gf.initFrame();

	}
	public Mario mario = new Mario(this);

}