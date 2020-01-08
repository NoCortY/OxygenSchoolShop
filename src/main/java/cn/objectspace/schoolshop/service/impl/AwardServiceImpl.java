package cn.objectspace.schoolshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.objectspace.schoolshop.dao.AwardDao;
import cn.objectspace.schoolshop.dto.AwardExecution;
import cn.objectspace.schoolshop.entity.Award;
import cn.objectspace.schoolshop.enums.AwardStateEnum;
import cn.objectspace.schoolshop.service.AwardService;
import cn.objectspace.schoolshop.util.FileUtil;
import cn.objectspace.schoolshop.util.ImageUtil;
import cn.objectspace.schoolshop.util.PageCalculator;

@Service
public class AwardServiceImpl implements AwardService {

	@Autowired
	private AwardDao awardDao;

	@Override
	public AwardExecution getAwardList(Award awardCondition, int pageIndex,
			int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Award> awardList = awardDao.queryAwardList(awardCondition,
				rowIndex, pageSize);
		int count = awardDao.queryAwardCount(awardCondition);
		AwardExecution ae = new AwardExecution();
		ae.setAwardList(awardList);
		ae.setCount(count);
		return ae;
	}

	@Override
	public Award getAwardById(long awardId) {
		return awardDao.queryAwardByAwardId(awardId);
	}

	@Override
	@Transactional
	public AwardExecution addAward(Award award, CommonsMultipartFile thumbnail) {
		if (award != null && award.getShopId() != null) {
			award.setCreateTime(new Date());
			award.setLastEditTime(new Date());
			award.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(award, thumbnail);
			}
			try {
				int effectedNum = awardDao.insertAward(award);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品失败:" + e.toString());
			}
			return new AwardExecution(AwardStateEnum.SUCCESS, award);
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public AwardExecution modifyAward(Award award,
			CommonsMultipartFile thumbnail) {
		if (award != null && award.getShopId() != null) {
			award.setLastEditTime(new Date());
			if (thumbnail != null) {
				Award tempAward = awardDao.queryAwardByAwardId(award
						.getAwardId());
				if (tempAward.getAwardImg() != null) {
					FileUtil.deleteFile(tempAward.getAwardImg());
				}
				addThumbnail(award, thumbnail);
			}
			try {
				int effectedNum = awardDao.updateAward(award);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新商品信息失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new RuntimeException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}
	}

	private void addThumbnail(Award award, CommonsMultipartFile thumbnail) {
		String dest = FileUtil.getShopImagePath(award.getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		award.setAwardImg(thumbnailAddr);
	}

}
