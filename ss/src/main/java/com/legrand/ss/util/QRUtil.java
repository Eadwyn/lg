package com.legrand.ss.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRUtil {

	public static boolean encodeQrCode(String content, int width, int height, String savePath) {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置容错等级
		hints.put(EncodeHintType.MARGIN, 1);// 设置边距默认是5

		try {
			BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path path = new File(savePath).toPath();
			MatrixToImageWriter.writeToPath(matrix, "png", path);// 写到指定路径下
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 读取二维码
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void decodeQrCode(String imgPath) {
		MultiFormatReader reader = new MultiFormatReader();
		File file = new File(imgPath);
		try {
			BufferedImage image = ImageIO.read(file);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码
			Result result = reader.decode(binaryBitmap, hints);
			System.out.println("解析结果:" + result.toString());
			System.out.println("二维码格式:" + result.getBarcodeFormat());
			System.out.println("二维码文本内容:" + result.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 生成条形码
	 *
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	// int width = 105, height = 50; 长度很容易报错:NotFoundException
	public static void encodeBarCode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析条形码
	 *
	 * @param imgPath
	 * @return
	 */
	public static String decodeBarCode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			/*
			 * Map<DecodeHintType, Object> hints = new Hashtable<>();
			 * hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
			 * hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); result = new
			 * MultiFormatReader().decode(bitmap, hints);
			 */
			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		QRUtil.encodeQrCode("8E:13:ED:4E:34:97", 160, 160, "d:/qrcode.png");
		QRUtil.encodeQrCode("D6:57:6A:42:17:89", 160, 160, "d:/qrcode2.png");
//		QRUtil.encodeBarCode("6923450657713", 160, 160, "d:/barcode.png");		
	}
}
