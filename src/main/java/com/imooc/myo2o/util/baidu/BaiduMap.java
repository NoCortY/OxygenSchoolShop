package com.imooc.myo2o.util.baidu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imooc.myo2o.util.weixin.WeixinUtil;

public class BaiduMap {

	private static Logger log = LoggerFactory.getLogger(BaiduMap.class);

	public static String ak = "BHxf7rAcSXiuKleB8n5ghCFQ";

	/**
	 * 根据ip获取经纬度
	 * 
	 * @param ip
	 */
	public static Point getLocation(String ip) {
		Point retPoint = new Point();
		String url = "http://api.map.baidu.com/location/ip?";
		String coor = "bd09ll";
		url += "ak=" + ak + "&ip=" + ip + "&coor=" + coor;
		JSONObject object = WeixinUtil.httpRequest(url, "GET", null);
		JSONObject content = object.getJSONObject("content");
		JSONObject point = content.getJSONObject("point");
		retPoint.setLng(point.getDouble("x"));
		retPoint.setLat(point.getDouble("y"));
		return retPoint;
	}

	/**
	 * 根据地址获取经纬度
	 * 
	 * @param address
	 */
	public static Point geocoder(String address) {
		Point point = new Point();
		try {
			address = URLEncoder.encode(address, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		String url = "http://api.map.baidu.com/geocoder/v2/?";
		String outPut = "json";
		url += "address=" + address + "&output=" + outPut + "&ak=" + ak;
		JSONObject object = WeixinUtil.httpRequest(url, "GET", null);
		if (object != null) {
			JSONObject result = object.getJSONObject("result");
			JSONObject location = result.getJSONObject("location");
			point.setLng(location.getDouble("lng"));
			point.setLat(location.getDouble("lat"));
			return point;
		}
		return null;
	}

	/**
	 * 反地里编码，根据经纬度获取实际地理信息
	 * 
	 * @param lat
	 * @param lng
	 */
	public static Point antiGeocoder(double lat, double lng) {
		Point point = new Point();
		String url = "http://api.map.baidu.com/geocoder/v2/?";
		String outPut = "json";
		int pois = 0;
		String coorType = "bd09ll";
		url += "ak=" + ak + "&callback=renderReverse&location=" + lat + ","
				+ lng + "&output=" + outPut + "&pois=" + pois + "&coordtype="
				+ coorType;
		JSONObject object = WeixinUtil.httpRequest(url, "GET", null);
		if (object != null) {
			JSONObject result = object.getJSONObject("result");
			point.setAddress(result.getString("formatted_address"));
			return point;
		}
		return null;
	}

	/**
	 * 获取点之间的距离
	 * 
	 * @param origins
	 *            起点（可以为坐标点或地址名称）
	 * @param destinations
	 *            终点（最多有5个）
	 * @param mode
	 *            导航模式 driving和walking，默认为driving
	 */
	public static List<Distance> getDistance(Point origins,
			List<Point> destinations, String mode) {
		String url = "http://api.map.baidu.com/direction/v1/routematrix?";
		url += "output=json&ak=" + ak + "&origins=" + origins.getLat() + ","
				+ origins.getLng() + "&destinations=";
		for (Point point : destinations) {
			url += point.getLat() + "," + point.getLng() + "|";
		}
		url = url.substring(0, url.length() - 1);
		if (null == mode) {
			url += "&mode=walking";
		} else {
			url += "&mode=" + mode;
		}
		JSONObject object = WeixinUtil.httpRequest(url, "GET", null);
		if (object != null) {
			JSONObject result = object.getJSONObject("result");
			JSONArray elements = result.getJSONArray("elements");
			int length = elements.size();
			JSONObject element = null;
			JSONObject distance = null;
			JSONObject duration = null;
			List<Distance> list = new ArrayList<Distance>();
			Distance dis = null;
			for (int i = 0; i < length; i++) {
				element = elements.getJSONObject(i);
				distance = element.getJSONObject("distance");
				duration = element.getJSONObject("duration");
				dis = new Distance(distance.getString("text"),
						distance.getInt("value"), duration.getString("text"),
						duration.getInt("value"));
				list.add(dis);
			}
			return list;
		}
		return null;
	}

	/**
	 * 生成静态百度地图
	 * 
	 */
	public static String staticMap(Point origin, Point destination, int width,
			int height) {
		String url = "http://api.map.baidu.com/staticimage/v2?";
		double centerLng = (origin.getLng() + destination.getLng()) / 2;
		double centerLat = (origin.getLat() + destination.getLat()) / 2;
		url += "ak=" + ak + "&width=" + width + "&height=" + height
				+ "&center=" + centerLng + "," + centerLat + "&scale=2&zoom=15";
		url += "&markers=" + origin.getLng() + "," + origin.getLat() + "|"
				+ destination.getLng() + "," + destination.getLat();
		return url;
	}

	/**
	 * 从其他地图的坐标系转换成百度的坐标
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static Point geoconv(double longitude, double latitude) {
		String url = "http://api.map.baidu.com/geoconv/v1/?";
		String coords = longitude + "," + latitude;
		url += "coords=" + coords + "&from=1&to=5&ak=" + ak;
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
		int status = jsonObject.getInt("status");
		Point point = new Point();
		if (status != 0) {
			log.debug("geoconv exception.(" + longitude + "," + latitude + ")");
		} else {
			JSONArray result = jsonObject.getJSONArray("result");
			JSONObject object = (JSONObject) result.get(0);
			point.setLng(object.getDouble("x"));
			point.setLat(object.getDouble("y"));
		}
		return point;
	}

	public static void main(String[] args) {
		Point point = BaiduMap.geoconv(116.302895, 40.049877);
		System.out.println(point.getLng());
		Point origin = new Point();
		Point target = new Point();
		origin.setLng(116.40387397);
		origin.setLat(39.91488908);
		target.setLng(116.3295978439);
		target.setLat(40.077536388839);
		System.out.println(staticMap(origin, target, 200, 200));
	}
}
