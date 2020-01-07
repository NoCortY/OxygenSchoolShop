package com.imooc.myo2o.util.weixin.message.pojo;

/** 
 * 普通按钮（子按钮） 
 *  
 * @author xiangli 
 * @date 2015-02-11 
 */  
public class CommonButton extends Button {  
    private String type;  
    private String key;  
  
    public String getType() {  
        return type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String getKey() {  
        return key;  
    }  
  
    public void setKey(String key) {  
        this.key = key;  
    }  
}  
