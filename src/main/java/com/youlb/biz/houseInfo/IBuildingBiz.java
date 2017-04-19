package com.youlb.biz.houseInfo;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IBuildingBiz.java 
 * @Description: 楼栋业务接口
 * @author: Pengjy
 * @date: 2015年6月29日
 * 
 */
public interface IBuildingBiz extends IBaseBiz<Building>{

	/**
	 * @param area
	 */
	void saveOrUpdate(Building building,Operator loginUser)throws BizException, UnsupportedEncodingException, ClientProtocolException, IOException, JsonException ;

	/**通过neibId获取buildId
	 * @param parentId
	 * @return
	 */
	String getNeibId(String parentId)throws BizException;

	/**通过neibId获取楼栋列表
	 * @param neibId
	 * @return
	 */
	List<Building> getBuildingListByNeibId(String neibId)throws BizException;

	boolean checkBuildingName(Building building)throws BizException;

	boolean checkBuildingNum(Building building)throws BizException;
	


}
