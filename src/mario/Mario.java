
package mario;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import enery.*;

//�Լ��Ľ�ɫ��
public class Mario extends Thread {
	public static final int XSPEED = 3;
	public static final int YSPEED = 3;
	public static final int JUMPHEIGHT = 11;
	public GameFrame gf;

	public boolean canJump = true;

	// ����µ�����
	public int x = 0, y = 100;
	// ����µ��ٶ�
	// public int xspeed = XSPEED;
	public double yspeed = YSPEED;
	// ����µĿ��
	public int width = 27, height = 30;
	// ����µ�ͼƬ
	public static Image img = new ImageIcon("image/mari1.png").getImage();
	public static Image imgR = new ImageIcon("image/mari1.png").getImage();
	public static Image imgL = new ImageIcon("image/mari_left1.png").getImage();
	public boolean left = false, right = false, down = false, up = false;

	public Mario(GameFrame gf) {
		this.gf = gf;
		this.Gravity();
		// this.setPriority(MAX_PRIORITY);
	}

	public boolean hit(char dir) {

		Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
		Rectangle rect = null;

		for (int i = 0; i < gf.eneryList.size(); ++i) {
			Enery enery = gf.eneryList.get(i);
			switch (dir) {
			case 'L':
				rect = new Rectangle(enery.x + 5, enery.y, enery.width, enery.height);
				if (myrect.intersects(rect)) {
					if (enery.type == 2) {
						// �Ե�Ģ��
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//�ӷ֣�������
						 */
					}

					// y = enery.y - 31;
					// System.out.println(img.getHeight(null));
					//System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL");
					return true;
				}

				break;
			case 'R':
				rect = new Rectangle(enery.x - 5, enery.y, enery.width, enery.height);

				if (myrect.intersects(rect)) {
					if (enery.type == 2) {
						// �Ե�Ģ��
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//�ӷ֣�������
						 */
					}
					// y = enery.y - 31;
					// System.out.println(img.getHeight(null));
					//System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
					// System.out.println(y+" "+enery.y);
					return true;
				}

				break;
			case 'U':
				rect = new Rectangle(enery.x, enery.y + 15, enery.width, enery.height - 15);
				if (myrect.intersects(rect)) {
					if (enery.type == 2) {
						// �Ե�Ģ��
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//�ӷ֣�������
						 */
					}
					while (y < enery.y + 30) {
						//System.out.println("pppppppppp" + y + "  " + enery.y);
						y++;
					}
					return true;
				}
				break;

			// ���� һ �ж�,������������սӴ���

			case 'D':
				rect = new Rectangle(enery.x, enery.y - 1, enery.width, enery.height - 15);
				if (myrect.intersects(rect)) {
					if (enery.type == 2) {
						// �Ե�Ģ��
						gf.eneryList.remove(enery);
						return false;
					}
					while (y + 30 > enery.y) {
						// System.out.println("dddddddd");
						y--;
						// System.out.println(y + 30);
						// System.out.println(enery.y);
					}

					return true;
				}
				break;
			}
		}
		return false;
	}

	public void run() {
		while (true) {
			if (left) {
				// ��ײ����
				if (hit('L') || x <= 0)
					;
				else {

					if (this.x > 300)
						this.x -= XSPEED;

					else if (gf.bg.x <= 0) {
						// �����ϰ��������ƶ�

						gf.bg.x += XSPEED;
						for (Enery enery : gf.eneryList)
							enery.x += XSPEED;
					} else {
						this.x -= XSPEED;
					}
				}
			}

			if (right) {

				if (hit('R') || x >= 800)
					; // �����������ƶ�
				else {
					if (this.x < 600) {
						this.x += XSPEED;

					}

					else if (gf.bg.x >= -1600) {
						// �����ϰ��������ƶ�
						gf.bg.x -= XSPEED;
						for (Enery enery : gf.eneryList)
							enery.x -= XSPEED;
					} else {
						this.x += XSPEED;
					}
				}

			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// �����ײ

	// ����Ƿ�����
	// ÿ0.01s���
	public void Gravity() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					boolean d = hit('D');
					boolean u = hit('U');

					if (u && d) {
						//System.out.println("uuuuuuuuuuuddddddddddddd");
						yspeed = 0;

					} else if (u && !d) {
						yspeed = 0;
					} else if (d && !u) {
						//System.out.println("dddddddddddddddddddddddd");
						yspeed = 0;
						if (up) {
							yspeed = -JUMPHEIGHT;
							canJump = true;

						}
					} else if (!u && !d) {
						//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						if (canJump && yspeed > -5) {
							// һ��ʱ��(yspeed>-5)����Զ�����;
							if (up) {
								yspeed = -JUMPHEIGHT;
								canJump = false;
							}
						}
						if (yspeed < 10) {
							// ���ڵ����������ٶ���Ч,����ٶ�10
							// System.out.println("33333333333333333333333");
							yspeed += 1;
							// System.out.println(yspeed);
						}
					}
					// �ĳɵ��Ͽ��ж�������,���е����˾Ͳ�������
					/*
					 * if (hit('D')) {
					 * 
					 * // ��غ������Ծ yspeed = 0; // System.out.println("hitddddddddddddddddd"); //
					 * canJump = false; if (up) { yspeed = -JUMPHEIGHT; //
					 * System.out.println("22222222222222222222222222"); canJump = true; } } else {
					 * boolean u=hit('U'); if (canJump && yspeed > -5&& !u) { //
					 * һ��ʱ��(yspeed>-5)����Զ�����; if (up) { yspeed = -JUMPHEIGHT; canJump = false; } }
					 * if (yspeed < 10) { // ���ڵ����������ٶ���Ч,����ٶ�10 //
					 * System.out.println("33333333333333333333333"); yspeed += 1; //
					 * System.out.println(yspeed); } if (u) { yspeed = 0;
					 * 
					 * // System.out.println("hituppppppppppppppp"); }
					 * 
					 * }
					 */

					y += yspeed;

					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}.start();

	}
}
