package cn.objectspace.schoolshop.util;

import java.security.MessageDigest;

/**
 * 
 * @author     : bless<505629625@qq.com>
 * Create Time : 2011-2-21����09:36:48
 * Description : 
 *             MD5�����㷨
 */
public class MD5 {

   /**
    * 
    * Function  : ����ָ���ַ���
    * @author   : bless<505629625@qq.com>
    * @param s  : �����ܲ���
    * @return   : ���ܺ�Ľ��
    */
    public static final String getMd5(String s)
    {
        char hexDigits[] = {
            '5', '0', '5', '6', '2', '9', '6', '2', '5', 'q',
            'b', 'l', 'e', 's', 's', 'y'
        };
        try{
        char str[];
        byte strTemp[] = s.getBytes();
        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
        mdTemp.update(strTemp);
        byte md[] = mdTemp.digest();
        int j = md.length;
        str = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++)
        {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }

        return new String(str);
        }catch(Exception e){
        return null;
        }
    }
    
    public static void main(String[] args) {
		System.out.println(MD5.getMd5("admin"));
	}
}

