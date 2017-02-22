package com.istore.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具类，完成图片的截取
 *
 * @author inc062977
 *
 */
public class ImageHelper {
	/**
	 * 实现图像的等比缩放
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx < sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 实现图像的截取
	 * @param inFilePath 要截取文件的路径
	 * @param outFilePath 截取后输出的路径
	 * @param width 要截取宽度
	 * @param height 要截取的高度
	 * @throws Exception
	 */

	public static void saveImageAsJpg(String inFilePath, String outFilePath)
			throws Exception {
		File file = new File(inFilePath);
		InputStream in = new FileInputStream(file);
		File saveFile = new File(outFilePath);

		BufferedImage srcImage = ImageIO.read(in);
		// 缩放后的图像的宽和高
		int w = srcImage.getWidth();
		int h = srcImage.getHeight();

		if (w < h) {
			int x = 0;
			int y = (h - w) / 2;
			saveSubImage(srcImage, new Rectangle(x, y, w, w), saveFile);
		}

		// 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
		else {
			// 计算X轴坐标
			int x = (w - h) / 2;
			int y = 0;
			saveSubImage(srcImage, new Rectangle(x, y, h, h), saveFile);
		}
		in.close();
	}

	/**
	 * 实现缩放后的截图
	 * @param image 缩放后的图像
	 * @param subImageBounds 要截取的子图的范围
	 * @param subImageFile 要保存的文件
	 * @throws java.io.IOException
	 */
	private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile)
			throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			System.out.println("Bad   subimage   bounds");
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width,
				subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}

}
