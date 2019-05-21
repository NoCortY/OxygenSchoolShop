package com.SchoolShop.o2o.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.SchoolShop.o2o.dao.ShopDao;
import com.SchoolShop.o2o.dto.ShopExecution;
import com.SchoolShop.o2o.entity.Shop;
import com.SchoolShop.o2o.enums.ShopStateEnum;
import com.SchoolShop.o2o.exceptions.ShopOperationException;
import com.SchoolShop.o2o.service.ShopService;
import com.SchoolShop.o2o.util.FileUtil;
import com.SchoolShop.o2o.util.ImageUtil;
import com.SchoolShop.o2o.util.PageCalculator;
import com.SchoolShop.o2o.util.PathUtil;
@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		//空值判断
		//看shop是否包含必须值
		if(shop == null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋值，添加时间修改时间
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
				//Runtime 事务会得以终止并回滚
			}else {
				if(shopImg != null) {
					//存储图片
					try {
						addShopImg(shop,shopImg);
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
					shop.getShopImg();
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) throws UnsupportedEncodingException {
		//创建临时文件
		String fileExtension  = ImageUtil.getFileExtension(shopImg);
		File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName()+fileExtension);
		try {
			FileUtil.inputStreamToFile(shopImg.getInputStream(), shopImgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获得shop图片目录的相对子路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgFile, dest);
		System.out.println(shopImgAddr);
		shop.setShopImg(shopImgAddr);
		//添加结束后删除临时文件
		shopImgFile.delete();
	}
	
	@Override
	public Shop getByShopId(long shopId) {
		
		return shopDao.queryByShopId(shopId);
	}
	@Override
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg, String fileName)
			throws ShopOperationException {
		try {
			if(shop==null||shop.getShopId()==null) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);
			}else {
				//1.判断是否需要处理图片
				if(shopImg!=null&&fileName!=null&&!"".equals(fileName)) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if(tempShop.getShopImg()!=null) {
						try {
							ImageUtil.deleteFileOrPath(tempShop.getShopImg());
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						addShopImg(shop,shopImg);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if(effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);   
				}else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("modifyShop error:"+e.getMessage());
		}
	}
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
