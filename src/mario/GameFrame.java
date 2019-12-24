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

	
	// ����ͼƬ
	public BackgroundImage bg = new BackgroundImage();

	public Enery pipe, coin, brick;
	
	//public JLabel label=new JLabel("Score");

	// ����װ����
	public ArrayList<Enery> eneryList = new ArrayList<Enery>();

	// �ӵ�����
	public ArrayList<Boom> boomList = new ArrayList<Boom>();

	// �ӵ����ٶ�
	public int bspeed = 0;

	// ����ͼ���ƶ�������1��שͷ����2����ң���3��ˮ��
	public int[][] map = new Map().readMap();

	public void initFrame() {
		// ���ô����������
		this.setSize(800, 600);
		this.setTitle("ɽկ�泬������");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);

		// �ô�����Ӽ��̼���
		MyKeyListener kl = new MyKeyListener(this);
		this.addKeyListener(kl);
		
		//label.setBounds(10, 10,20,10);
		//add(label);
		
	}

	// ���캯�������ʼ������ͼƬ������¶���
	public GameFrame() throws Exception {
		mario.start();

		// �����ػ��߳�
		new Thread() {
			public void run() {
				while (true) {
					// �ػ洰��
					
					repaint();
					//label.repaint();
					// ����ӵ��Ƿ����
					checkBoom();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		// ��ȡ��ͼ�������õ�ͼ
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				// ��ȡ������1����שͷ
				if (map[i][j] == 1) {
					brick = new Pipe(1,j * 30, i * 30, 30, 30, new ImageIcon("image/brick.png").getImage());
					eneryList.add(brick);
				}
				// ����2�����
				if (map[i][j] == 2) {
					coin = new Pipe(2,j * 30, i * 30, 30, 30, new ImageIcon("image/coin_brick.png").getImage());
					eneryList.add(coin);
				}
				// ����3��ˮ��
				if (map[i][j] == 3) {
					pipe = new Pipe(3,j * 30, i * 30, 30, 30, new ImageIcon("image/pipe.png").getImage());
					eneryList.add(pipe);
				}

			}
		}
	}
	// ���ñ�������
	// com.huaxin.music.Util.startMusic("music/bg1.wav");

	public void paint(Graphics g) {
		
		// ����˫���廭����ͼƬ�������
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

		// ���ӵ�
		for (int i = 0; i < boomList.size(); i++) {
			Boom b = boomList.get(i);
			Color c = big.getColor();
			big.setColor(Color.red);
			big.fillOval(b.x += b.speed, b.y, b.width, b.width);
			big.setColor(c);
		}

		// ������
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

	// ����ӵ��Ƿ���磬��������������Ƴ������Ƴ��Ļ����ڴ��й©
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