package cn.objectspace.schoolshop.util.weixin.message.pojo;
/**
 * 用户授权token
 * @author liusai01
 *
 */
public class UserAccessToken {

	private String accessToken;
	
	private String expiresIn;
	
	private String refreshToken;
	
	private String openId;
	
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
	public String toString() {
		return "accessToken:"+this.getAccessToken()+",openId:"+this.getOpenId();
	}
	
}
