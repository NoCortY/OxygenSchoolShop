package com.imooc.myo2o.util.weixin.message.req;

/** 
 * 图片消息 
 *  
 * @author xiangli
 * @date 2015-02-10
 */  
public class ImageMessage extends BaseMessage {  
    // 图片链接  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}  