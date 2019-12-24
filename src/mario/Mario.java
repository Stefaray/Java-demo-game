
package mario;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import enery.*;

//自己的角色类
public class Mario extends Thread {
	public static final int XSPEED = 3;
	public static final int YSPEED = 3;
	public static final int JUMPHEIGHT = 11;
	public GameFrame gf;

	public boolean canJump = true;

	// 马里奥的坐标
	public int x = 0, y = 100;
	// 马里奥的速度
	// public int xspeed = XSPEED;
	public double yspeed = YSPEED;
	// 马里奥的宽高
	public int width = 27, height = 30;
	// 马里奥的图片
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
						// 吃到蘑菇
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//加分，声音；
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
						// 吃到蘑菇
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//加分，声音；
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
						// 吃到蘑菇
						gf.eneryList.remove(enery);
						return false;
						/*
						 * ToDo//加分，声音；
						 */
					}
					while (y < enery.y + 30) {
						//System.out.println("pppppppppp" + y + "  " + enery.y);
						y++;
					}
					return true;
				}
				break;

			// 上移 一 判断,最后调整到表面刚接触。

			case 'D':
				rect = new Rectangle(enery.x, enery.y - 1, enery.width, enery.height - 15);
				if (myrect.intersects(rect)) {
					if (enery.type == 2) {
						// 吃到蘑菇
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
				// 碰撞到了
				if (hit('L') || x <= 0)
					;
				else {

					if (this.x > 300)
						this.x -= XSPEED;

					else if (gf.bg.x <= 0) {
						// 背景障碍物向右移动

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
					; // 任人物向右移动
				else {
					if (this.x < 600) {
						this.x += XSPEED;

					}

					else if (gf.bg.x >= -1600) {
						// 背景障碍物向左移动
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

	// 检测碰撞

	// 检查是否贴地
	// 每0.01s检测
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
							// 一定时间(yspeed>-5)后可以二段跳;
							if (up) {
								yspeed = -JUMPHEIGHT;
								canJump = false;
							}
						}
						if (yspeed < 10) {
							// 不在地面重力加速度生效,最大速度10
							// System.out.println("33333333333333333333333");
							yspeed += 1;
							// System.out.println(yspeed);
						}
					}
					// 改成地上空中都可以跳,空中调完了就不能再跳
					/*
					 * if (hit('D')) {
					 * 
					 * // 落地后可以跳跃 yspeed = 0; // System.out.println("hitddddddddddddddddd"); //
					 * canJump = false; if (up) { yspeed = -JUMPHEIGHT; //
					 * System.out.println("22222222222222222222222222"); canJump = true; } } else {
					 * boolean u=hit('U'); if (canJump && yspeed > -5&& !u) { //
					 * 一定时间(yspeed>-5)后可以二段跳; if (up) { yspeed = -JUMPHEIGHT; canJump = false; } }
					 * if (yspeed < 10) { // 不在地面重力加速度生效,最大速度10 //
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
