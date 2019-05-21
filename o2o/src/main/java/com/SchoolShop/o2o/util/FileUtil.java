package com.SchoolShop.o2o.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	public static void inputStreamToFile(InputStream ins,File file) {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while((bytesRead = ins.read(buffer))!=-1) {
				os.write(buffer,0,bytesRead);
			}
		}catch(Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常:"+e.getMessage());
		}finally {
			try {
				if(os != null) {
					os.close();
				}
				if(ins != null) {
					ins.close();
				}
			}catch(IOException e) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常:"+e.getMessage());
			}
		}
	}
}
