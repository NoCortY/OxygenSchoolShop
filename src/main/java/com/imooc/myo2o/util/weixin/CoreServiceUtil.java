package com.imooc.myo2o.util.weixin;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imooc.myo2o.util.baidu.BaiduMap;
import com.imooc.myo2o.util.baidu.Point;
import com.imooc.myo2o.util.weixin.message.resp.TextMessage;

/**
 * 核心服务类
 */
public class CoreServiceUtil {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static TextMessage processRequest(HttpServletRequest request) {
		TextMessage textMessage = new TextMessage();
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 回复文本消息
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
				// 获取用户位置信息
				else if (eventType
						.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					String latitude = requestMap.get("Latitude");
					String longitude = requestMap.get("Longitude");
					Point point = BaiduMap.geoconv(
							Double.parseDouble(longitude),
							Double.parseDouble(latitude));
					request.getSession().setAttribute("longitude",
							point.getLng());
					request.getSession().setAttribute("Latitude",
							point.getLat());
					respContent = "你的经纬度为：(" + point.getLng() + ","
							+ point.getLat() + ")";
					textMessage.setPoint(point);
					textMessage.setOpenId(fromUserName);
				}
			}
			textMessage.setContent(respContent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return textMessage;
	}
}
