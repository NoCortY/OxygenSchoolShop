package cn.objectspace.schoolshop.web.superadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.objectspace.schoolshop.entity.LocalAuth;
import cn.objectspace.schoolshop.service.LocalAuthService;
import cn.objectspace.schoolshop.util.HttpServletRequestUtil;
import cn.objectspace.schoolshop.util.MD5;

@Controller
@RequestMapping("/superadmin")
public class LoginController {
	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		if (userName != null && password != null) {
			password = MD5.getMd5(password);
			LocalAuth localAuth = localAuthService
					.getLocalAuthByUserNameAndPwd(userName, password);
			if (localAuth != null) {
				if (localAuth.getPersonInfo().getAdminFlag() == 1) {
					modelMap.put("success", true);
					request.getSession().setAttribute("user",
							localAuth.getPersonInfo());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "非管理员没有权限访问");
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}
}
