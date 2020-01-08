package cn.objectspace.schoolshop.util.weixin.message.pojo;

/** 
* view类型的菜单 
*  
* @author xiangli 
* @date 2015-02-11 
*/  
public class ViewButton extends Button {  
   private String type;  
   private String url;  
 
   public String getType() {  
       return type;  
   }  
 
   public void setType(String type) {  
       this.type = type;  
   }  
 
   public String getUrl() {  
       return url;  
   }  
 
   public void setUrl(String url) {  
       this.url = url;  
   }  
}  