package com.legrand.ss.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象复制工具类
 *
 * @author Eadwyn
 * @version 1.0.0
 * @date 2018年6月14日 下午4:11:58
 * @link
 * @see
 */
public class CloneUtil {
	private static final Logger logger = LoggerFactory.getLogger(CloneUtil.class);
	/**
	 * 深复制对象
	 *
	 * @param obj
	 *            要进行复制的对象
	 * @return 复制后的对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepClone(T obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 将对象写到流里
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());// 从流里读出来
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T)(ois.readObject());
		} catch (Exception e) {
			logger.error("an error occurred while cloning object", e);
			return null;
		}
	}
}

