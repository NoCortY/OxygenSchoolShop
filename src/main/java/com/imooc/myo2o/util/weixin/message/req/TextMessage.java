package com.imooc.myo2o.util.weixin.message.req;


/**
 * 消息基类（普通用户 -> 公众帐号）
 * 
 * @author xiangli
 * @date 2015-02-10
 */
public class TextMessage extends BaseMessage {  
  // 消息内容  
  private String Content;  

  public String getContent() {  
      return Content;  
  }  

  public void setContent(String content) {  
      Content = content;  
  }  
} 