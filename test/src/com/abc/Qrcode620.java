package com.abc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class Qrcode620 {
	public static void creatCode(String content, String imgPath, int version,
			String startRgb, String endRgb) throws IOException {
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeVersion(version);
		System.out.println("�汾�ţ� " + qrcode.getQrcodeVersion());
		qrcode.setQrcodeErrorCorrect('Q');
		qrcode.setQrcodeEncodeMode('B');
		System.out.println("�ɴ洢���ͣ�" + qrcode.getQrcodeEncodeMode());
		int imgSize = 67 + (version - 1) * 12;
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize,
				BufferedImage.TYPE_INT_BGR);
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, imgSize, imgSize);
		boolean[][] calQrcode = qrcode.calQrcode(content.getBytes("UTF-8"));
		int pixoff = 2;
		int startR = 0, startG = 0, startB = 0;
		int endR = 0, endG = 0, endB = 0;
		if (null != endRgb) {
			String[] rgb = startRgb.split(",");
			startR = Integer.valueOf(rgb[0]);
			startG = Integer.valueOf(rgb[1]);
			startB = Integer.valueOf(rgb[2]);
		}
		if (null != startRgb) {
			String[] rgb = endRgb.split(",");
			endR = Integer.valueOf(rgb[0]);
			endG = Integer.valueOf(rgb[1]);
			endB = Integer.valueOf(rgb[2]);
		}
		Random random = new Random();
		for (int i = 0; i < calQrcode.length; i++) {// ������
			System.out.println("����:  " + calQrcode.length + "   ������  "
					+ calQrcode[i].length);
			for (int j = 0; j < calQrcode.length; j++) {// ������
				if (calQrcode[i][j]) {
					int r = startR + (endR - startR) * (j + 1)
							/ calQrcode.length;
					int g = startG + (endG - startG) * (j + 1)
							/ calQrcode.length;
					int b = startB + (endB - startB) * (j + 1)
							/ calQrcode.length;
					Color color = new Color(r, g, b);
					gs.setColor(color);
					gs.fillRect(i * 3 + pixoff, j * 3 + pixoff, 3, 3);
				}
			}
		}

		BufferedImage logo = ImageIO.read(new File("D:/1.jpg"));

		int logoSize = imgSize / 3;

		int o = (imgSize - logoSize) / 2;

		gs.drawImage(logo, o, o, logoSize, logoSize, null);

		gs.dispose();

		bufferedImage.flush();

		try {
			ImageIO.write(bufferedImage, "png", new File("D:/qrcode.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("����");
	}

	public static void main(String[] args) throws IOException {
		String imgPath = "D:/qrcode.png";
		int version = 20;
		String content = "BEGIN:VCARD\r\n" + "FN:����:������\r\n" + "ORG:�����ص���\r\n"
				+ "TITLE:�ص�������\r\n" + "TEL;CELL;VOICE:����Ӵ\r\n" +

				"ADR;HOME:����С��\r\n" + "EMAIL;HOME:463782@qq.com\r\n"
				+ "END:VCARD";
		String startRgb = "0,255,0";
		String endRgb = "120,0,150";
		creatCode(content, imgPath, version, startRgb, endRgb);

	}

}
