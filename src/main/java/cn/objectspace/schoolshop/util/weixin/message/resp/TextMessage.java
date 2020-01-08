package cn.objectspace.schoolshop.util.weixin.message.resp;

import cn.objectspace.schoolshop.util.baidu.Point;

/** 
 * 文本消息 
 *  
 * @author xiangli
 * @date 2015-02-10
 */  
public class TextMessage extends BaseMessage {  
    // 回复的消息内容  
    private String Content;  
    //坐标点
    private Point point;
    //openId
    private String openId;
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}  
}  