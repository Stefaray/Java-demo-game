package enery;

import java.awt.Image;

//�ϰ���ĳ�����
public abstract class Enery {
	public int type;
	public int x, y;
	public int width, height;
	public Image img;


	public Enery(int type, int x, int y, int width, int height, Image img) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}


}