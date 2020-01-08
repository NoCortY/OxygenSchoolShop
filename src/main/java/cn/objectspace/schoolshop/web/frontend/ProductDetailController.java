package cn.objectspace.schoolshop.web.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import cn.objectspace.schoolshop.entity.PersonInfo;
import cn.objectspace.schoolshop.entity.Product;
import cn.objectspace.schoolshop.service.ProductService;
import cn.objectspace.schoolshop.util.HttpServletRequestUtil;
import cn.objectspace.schoolshop.util.QRCodeUtil;
import cn.objectspace.schoolshop.util.baidu.ShortNetAddress;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;

	private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=wxd7f6c5b8899fba83&"
			+ "redirect_uri=115.28.159.6/myo2o/shop/adduserproductmap&"
			+ "response_type=code&scope=snsapi_userinfo&state=";
	private static String URLSUFFIX = "#wechat_redirect";

	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if (productId != -1) {
			product = productService.getProductById(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4Product(HttpServletRequest request,
			HttpServletResponse response) {
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (productId != -1 && user != null && user.getUserId() != null) {
			long timpStamp = System.currentTimeMillis();
			String content = "{\"productId\":" + productId + ",\"customerId\":"
					+ user.getUserId() + ",\"createTime\":" + timpStamp + "}";
			String longUrl = URLPREFIX + content + URLSUFFIX;
			String shortUrl = ShortNetAddress.getShortURL(longUrl);
			BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
					response);
			try {
				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
						response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
