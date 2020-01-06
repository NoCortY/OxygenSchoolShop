package com.SchoolShop.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	public static String generateThumbnail(File thumbnail,String targetAddr) throws UnsupportedEncodingException {
		basePath=URLDecoder.decode(basePath,"utf-8");
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(700, 700)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/watermark.png")),0.7f).outputQuality(0.8f)
			.toFile(dest);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	public static String generateNormalThumbnail(File thumbnail,String targetAddr) throws UnsupportedEncodingException {
		basePath=URLDecoder.decode(basePath,"utf-8");
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(750, 500)
			.watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/watermark.png")),0.7f).outputQuality(0.9f)
			.toFile(dest);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	/*//file to CommonsMultipartFile
	public static CommonsMultipartFile path2MultipartFile(String filePath) throws IOException{
		File file = new File(filePath);
		FileInputStream input = new FileInputStream(file);
		CommonsMultipartFile multipartFile = new CommonsMultipartFile("file",file.getName(),"text/plain",IOUtils.toByteArray(input));
		return multipartFile;
	}*/
	//创建目标路径所涉及到的目录即/home/image/xxx.jpg 那么 home image 都将自动创建
	private static void makeDirPath(String targetAddr) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	//获取输入文件流的扩展名
	public static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	//获取输入文件流的扩展名
		private static String getFileExtension(File thumbnail) {
			String originalFileName = thumbnail.getName();
			return originalFileName.substring(originalFileName.lastIndexOf("."));
		}
	
	//生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	public static String getRandomFileName() {
		//获取随机五位数
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+rannum;
	}
	public static void main(String[] args) throws IOException {
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		basePath=URLDecoder.decode(basePath,"utf-8");
		Thumbnails.of(new File("G:/程序/Java程序/o2o/src/main/resources/123.png"))
		.size(750, 500).watermark(Positions.BOTTOM_RIGHT,
				ImageIO.read(new File(basePath+"/watermark.png")),0.7f).outputQuality(0.9f)
				.toFile("G:/程序/Java程序/o2o/src/main/resources/123news.png");
				
	}
	//判断storePath是文件的路径还是目录的路径
	//如果storePath是文件则删除该文件
	//如果storePath是目录则删除该目录下的所有文件
	public static void deleteFileOrPath(String storePath) throws UnsupportedEncodingException {
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		 if(fileOrPath.exists()) {
			 if(fileOrPath.isDirectory()) {
				 File files[] = fileOrPath.listFiles();
				 for(int i=0;i<files.length;i++) {
					 files[i].delete();
				 }
			 }
			 fileOrPath.delete();
		 }
	}
}
