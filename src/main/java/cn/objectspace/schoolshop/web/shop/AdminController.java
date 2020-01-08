package cn.objectspace.schoolshop.web.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.objectspace.schoolshop.entity.Shop;
import cn.objectspace.schoolshop.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "shop", method = { RequestMethod.GET,
		RequestMethod.POST })
public class AdminController {
	@RequestMapping(value = "/test")
	public Map<String, Object> productcategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String kaptchaExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.out.println(kaptchaExpected);
		modelMap.put("verifyCode", kaptchaExpected);
		return modelMap;
	}

	@RequestMapping(value = "/ownerlogin")
	public String ownerLogin(HttpServletRequest request) {
		return "shop/ownerlogin";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	private String register() {
		return "shop/register";
	}

	@RequestMapping(value = "/changepsw", method = RequestMethod.GET)
	private String changePsw() {
		return "shop/changepsw";
	}
	
	@RequestMapping(value = "/ownerbind", method = RequestMethod.GET)
	private String ownerBind() {
		return "shop/ownerbind";
	}

	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String myList() {
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanage", method = RequestMethod.GET)
	private String shopManage(HttpServletRequest request) {
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute(
					"currentShop");
			if (currentShopObj == null) {
				return "shop/shoplist";
			} else {
				return "shop/shopmanage";
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			return "shop/shopmanage";
		}
	}

	@RequestMapping(value = "/shopedit", method = RequestMethod.GET)
	private String shopEdit() {
		return "shop/shopedit";
	}

	@RequestMapping(value = "/productmanage", method = RequestMethod.GET)
	private String productManage() {
		return "shop/productmanage";
	}

	@RequestMapping(value = "/productedit", method = RequestMethod.GET)
	private String productEdit() {
		return "shop/productedit";
	}

	@RequestMapping(value = "/productcategorymanage", method = RequestMethod.GET)
	private String productCategoryManage() {
		return "shop/productcategorymanage";
	}

	@RequestMapping(value = "/shopauthmanage", method = RequestMethod.GET)
	private String shopAuthManage() {
		return "shop/shopauthmanage";
	}

	@RequestMapping(value = "/shopauthedit", method = RequestMethod.GET)
	private String shopAuthEdit() {
		return "shop/shopauthedit";
	}

	@RequestMapping(value = "/productbuycheck", method = RequestMethod.GET)
	private String productBuyCheck() {
		return "shop/productbuycheck";
	}

	@RequestMapping(value = "/awarddelivercheck", method = RequestMethod.GET)
	private String awardDeliverCheck() {
		return "shop/awarddelivercheck";
	}

	@RequestMapping(value = "/usershopcheck", method = RequestMethod.GET)
	private String userShopCheck() {
		return "shop/usershopcheck";
	}

	@RequestMapping(value = "/awardmanage", method = RequestMethod.GET)
	private String awardManage() {
		return "shop/awardmanage";
	}

	@RequestMapping(value = "/awardedit", method = RequestMethod.GET)
	private String awardEdit() {
		return "shop/awardedit";
	}

	@RequestMapping(value = "/customermanage", method = RequestMethod.GET)
	private String customerManage() {
		return "shop/customermanage";
	}
}
