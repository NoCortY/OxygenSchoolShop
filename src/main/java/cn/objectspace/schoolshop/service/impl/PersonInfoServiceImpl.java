package cn.objectspace.schoolshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.objectspace.schoolshop.dao.PersonInfoDao;
import cn.objectspace.schoolshop.dto.PersonInfoExecution;
import cn.objectspace.schoolshop.entity.PersonInfo;
import cn.objectspace.schoolshop.enums.PersonInfoStateEnum;
import cn.objectspace.schoolshop.service.PersonInfoService;
import cn.objectspace.schoolshop.util.PageCalculator;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

	@Override
	public PersonInfoExecution getPersonInfoList(
			PersonInfo personInfoCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(
				personInfoCondition, rowIndex, pageSize);
		int count = personInfoDao.queryPersonInfoCount(personInfoCondition);
		PersonInfoExecution se = new PersonInfoExecution();
		if (personInfoList != null) {
			se.setPersonInfoList(personInfoList);
			se.setCount(count);
		} else {
			se.setState(PersonInfoStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	@Transactional
	public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
		if (personInfo == null) {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		} else {
			try {
				int effectedNum = personInfoDao.insertPersonInfo(personInfo);
				if (effectedNum <= 0) {
					return new PersonInfoExecution(
							PersonInfoStateEnum.INNER_ERROR);
				} else {// 创建成功
					personInfo = personInfoDao.queryPersonInfoById(personInfo
							.getUserId());
					return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS,
							personInfo);
				}
			} catch (Exception e) {
				throw new RuntimeException("addPersonInfo error: "
						+ e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
		if (personInfo == null || personInfo.getUserId() == null) {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		} else {
			try {
				int effectedNum = personInfoDao.updatePersonInfo(personInfo);
				if (effectedNum <= 0) {
					return new PersonInfoExecution(
							PersonInfoStateEnum.INNER_ERROR);
				} else {// 创建成功
					personInfo = personInfoDao.queryPersonInfoById(personInfo
							.getUserId());
					return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS,
							personInfo);
				}
			} catch (Exception e) {
				throw new RuntimeException("updatePersonInfo error: "
						+ e.getMessage());
			}
		}
	}

}
