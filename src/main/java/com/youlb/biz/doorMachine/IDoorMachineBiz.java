package com.youlb.biz.doorMachine;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.doorMachine.DoorMachine;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IDoorMachineBiz extends IBaseBiz<DoorMachine> {

	void saveOrUpdate(DoorMachine doorMachine, Operator loginUser)throws BizException;
    /**
     * 检查组合是否已经存在
     * @param doorMachine
     * @return
     */
	boolean checkExist(DoorMachine doorMachine)throws BizException;
	/**
	 * 获取软件型号列表
	 * @return
	 */
	List<String> getSoftwareTypeList()throws BizException ;

}
