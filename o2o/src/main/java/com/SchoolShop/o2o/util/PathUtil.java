package com.SchoolShop.o2o.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() throws UnsupportedEncodingException {
		String os = System.getProperty("os.name");
		String basePath="";
		basePath=URLDecoder.decode(basePath,"utf-8");
		//将编码调整为utf-8  应变不同系统
		if(os.toLowerCase().startsWith("win")) {
			basePath = "G:/程序/Java程序/image";
		}else {
			basePath = "/home/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	public static String getShopImagePath(long shopId) throws UnsupportedEncodingException {
		String imagePath="/upload/item/shop/"+shopId+"/";
		imagePath=URLDecoder.decode(imagePath,"utf-8");
		return imagePath.replace("/", seperator); 
	}
}
