//
//package mario;
//
//import java.awt.Image;
//import java.awt.Rectangle;
//
//import javax.swing.ImageIcon;
//
//import enery.*;
//
////�Լ��Ľ�ɫ��
//public class Mario extends Thread {
//	public static final int XSPEED = 3;
//	public static final int YSPEED = 5;
//	public static final int JUMPHEIGHT = 11;
//	public GameFrame gf;
//
//	public boolean canJump = false;
//
//	// ����µ�����
//	public int x = 0, y = 100;
//	// ����µ��ٶ�
//	// public int xspeed = XSPEED;
//	public double yspeed = YSPEED;
//	// ����µĿ��
//	public int width = 27, height = 30;
//	// ����µ�ͼƬ
//	public static Image img = new ImageIcon("image/mari1.png").getImage();
//	public static Image imgR = new ImageIcon("image/mari1.png").getImage();
//	public static Image imgL = new ImageIcon("image/mari_left1.png").getImage();
//	public boolean left = false, right = false, down = false, up = false;
//
//	public Mario(GameFrame gf) {
//		this.gf = gf;
//		this.Gravity();
//		// this.setPriority(MAX_PRIORITY);
//	}
//
//	public boolean hit(char dir) {
//
//		Rectangle myrect = new Rectangle(this.x, this.y, this.width, this.height);
//		Rectangle rect = null;
//
//		for (int i = 0; i < gf.eneryList.size(); ++i) {
//			Enery enery = gf.eneryList.get(i);
//			switch (dir) {
//			case 'L':
//				rect = new Rectangle(enery.x + 5, enery.y, enery.width, enery.height);
//				if (enery.type == 2) {
//					gf.eneryList.remove(enery);
//					gf.eneryList.remove(enery);
//
//					/*
//					 * ToDo//�ӷ֣�������
//					 */
//				} else if (myrect.intersects(rect)) {
//					// y = enery.y - 31;
//					// System.out.println(img.getHeight(null));
//					return true;
//				}
//				break;
//			case 'R':
//				rect = new Rectangle(enery.x - 5, enery.y, enery.width, enery.height);
//				if (enery.type == 2) {
//					gf.eneryList.remove(enery);
//					gf.eneryList.remove(enery);
//				}
//
//				else if (myrect.intersects(rect)) {
//					// y = enery.y - 31;
//					// System.out.println(img.getHeight(null));
//					return true;
//				}
//
//				break;
//			case 'U':
//				rect = new Rectangle(enery.x, enery.y + 5, enery.width, enery.height);
//				if (enery.type == 2) {
//					gf.eneryList.remove(enery);
//					gf.eneryList.remove(enery);
//				}
//
//				else if (myrect.intersects(rect)) {
//					y += 2;
//					// System.out.println(img.getHeight(null));
//					return true;
//				}
//				break;
//
//				// ���� һ �ж�,������������սӴ���
//
//			case 'D':
//				rect = new Rectangle(enery.x, enery.y - 1, enery.width, enery.height);
//				if (enery.type == 2) {
//					gf.eneryList.remove(enery);
//					gf.eneryList.remove(enery);
//				} else if (myrect.intersects(rect)) {
//
//					if (y + 30 > enery.y) {
//						y--;
//						// System.out.println("---------------y");
//					}
//					// if(hit('U')!=true)
//					// y = enery.y - 31;
//					// System.out.println(img.getHeight(null));
//					return true;
//				}
//				break;
//			}
//		}
//		return false;
//	}
//
//	public void run() {
//		while (true) {
//			if (left) {
//				// ��ײ����
//				if (hit('L') || x <= 0)
//					;// this.xspeed = 0;
//				else {
//
//					if (this.x > 300)
//						this.x -= XSPEED;
//
//					else if (gf.bg.x <= 0) {
//						// �����ϰ��������ƶ�
//
//						gf.bg.x += XSPEED;
//						for (Enery enery : gf.eneryList)
//							enery.x += XSPEED;
//					} else {
//						this.x -= XSPEED;
//					}
//				}
//				// this.xspeed = XSPEED;
//			}
//
//			if (right) {
//
//				if (hit('R') || x >= 800)
//					;// this.xspeed = 0;
//				// �����������ƶ�
//				else {
//					if (this.x < 600)
//						this.x += XSPEED;
//
//					else if (gf.bg.x >= -2400) {
//						// �����ϰ��������ƶ�
//						gf.bg.x -= XSPEED;
//						for (Enery enery : gf.eneryList)
//							enery.x -= XSPEED;
//					} else {
//						this.x += XSPEED;
//					}
//					// this.xspeed = XSPEED;
//				}
//
//				try {
//					Thread.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}
//
//	// �����ײ
//
//	// ����Ƿ�����
//	// ÿ0.01s���
//	public void Gravity() {
//		new Thread() {
//			public void run() {
//				while (true) {
//					try {
//						sleep(10);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					/*
//					 * if (hit('U')) { yspeed = 0;
//					 * 
//					 * System.out.println("hituppppppppppppppp"); } else if (hit('D')) { // ��غ������Ծ
//					 * yspeed = 0; System.out.println("hitddddddddddddddddd"); canJump = true; if
//					 * (up && canJump) { // ���󲻿������� yspeed = -JUMPHEIGHT;
//					 * System.out.println("22222222222222222222222222"); canJump = false; } } else
//					 * if (yspeed < 5) { // ���ڵ����������ٶ���Ч,����ٶ�5
//					 * System.out.println("33333333333333333333333"); yspeed += 1;
//					 * //System.out.println(yspeed); }
//					 */
//					if (hit('D')) {
//
//						// ��غ������Ծ
//						yspeed = 0;
//						// System.out.println("hitddddddddddddddddd");
//						canJump = true;
//						if (up && canJump) {
//							// ���󲻿�������
//							yspeed = -JUMPHEIGHT;
//							// System.out.println("22222222222222222222222222");
//							canJump = false;
//						}
//					} else {
//						if (yspeed < 20) {
//							// ���ڵ����������ٶ���Ч,����ٶ�5
//							// System.out.println("33333333333333333333333");
//							yspeed += 1;
//							// System.out.println(yspeed);
//						}
//						if (hit('U')) {
//							yspeed = 0;
//
//							// System.out.println("hituppppppppppppppp");
//						}
//
//					}
//
//					y += yspeed;
//
//					try {
//						sleep(10);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//
//		}.start();
//
//	}
//}
