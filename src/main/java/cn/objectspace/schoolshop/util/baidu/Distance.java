package cn.objectspace.schoolshop.util.baidu;

public class Distance {

	private String distanceText;
	
	private int distanceNum;
	
	private String durationText;
	
	private int durationNum;
	
	public Distance(){}
	
	public Distance(String distanceText,int distanceNum,String durationText,int durationNum){
		this.distanceText = distanceText;
		this.distanceNum = distanceNum;
		this.durationText = durationText;
		this.durationNum = durationNum;
	}

	public String getDistanceText() {
		return distanceText;
	}

	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}

	public int getDistanceNum() {
		return distanceNum;
	}

	public void setDistanceNum(int distanceNum) {
		this.distanceNum = distanceNum;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public int getDurationNum() {
		return durationNum;
	}

	public void setDurationNum(int durationNum) {
		this.durationNum = durationNum;
	}
	
}
