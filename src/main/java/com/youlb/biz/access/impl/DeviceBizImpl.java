package com.youlb.biz.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.access.IDeviceBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.access.DeviceInfo;
import com.youlb.entity.access.DeviceInfoDto;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.DateHelper;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: DeviceBizImpl.java 
 * @Description: 设备管理业务实现类 理业务实现类 理业务实现类 理业务实现类 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Service("deviceBiz")
public class DeviceBizImpl implements IDeviceBiz {
	private static Logger log = LoggerFactory.getLogger(DeviceBizImpl.class);

	@Autowired
    private BaseDaoBySql<DeviceInfo> deviceSqlDao;
	public void setDeviceSqlDao(BaseDaoBySql<DeviceInfo> deviceSqlDao) {
		this.deviceSqlDao = deviceSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(DeviceInfo target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**修改密码
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(DeviceInfo device) throws BizException {
		String update = "update DeviceInfo t set t.devicePws=? where t.id=?";
		deviceSqlDao.update(update,new Object[]{SHAEncrypt.digestPassword(device.getDevicePws()),device.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		deviceSqlDao.delete(id);

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			for(String id:ids){
				delete(id);
			}
		}

	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public DeviceInfo get(Serializable id) throws BizException {
		return deviceSqlDao.get(id);
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<DeviceInfo> showList(DeviceInfo target, Operator loginUser)
			throws BizException {
		 StringBuilder sb =new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("from DeviceInfo t where 1=1");
		 if(StringUtils.isNotBlank(target.getId())){
			 sb.append(" and t.id like ?");
			 values.add("%"+target.getId()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceNum())){
			 sb.append(" and t.deviceNum like ?");
			 values.add("%"+target.getDeviceNum()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceModel())){
			 sb.append(" and t.deviceModel like ?");
			 values.add("%"+target.getDeviceModel()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceBorn())){
			 sb.append(" and t.deviceBorn =?");
			 values.add(target.getDeviceBorn());
		 }
		 if(StringUtils.isNotBlank(target.getDeviceStatus())){
			 if("1".equals(target.getDeviceStatus())){
				 sb.append(" and t.deviceStatus =?");
				 values.add(target.getDeviceStatus());
			 }else{
				 sb.append(" and t.deviceStatus is null or t.deviceStatus ='' ");
			 }
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.deviceNum");
		return deviceSqlDao.pageFind(sb.toString(),values.toArray(), target.getPager());
	}

	/**
	 * @param device
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IDeviceBiz#saveOrUpdate(com.youlb.entity.access.DeviceInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public String saveOrUpdate(DeviceInfo device, Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("update t_deviceinfo set fdevicestatus=?,fdevicefactory=?,fdeviceborn=?,fremark=?");
		 values.add(device.getDeviceStatus());
		 values.add(device.getDeviceFactory());
		 values.add(device.getDeviceBorn());
		 values.add(device.getRemark());
		 if("1".equals(device.getDeviceStatus())){
			 sb.append(",flive_time=? ");
			 values.add(new Date());
		 }else{
			 sb.append(",flive_time=null ");
		 }
		 sb.append("where id=?");
		 values.add(device.getId());
		deviceSqlDao.updateSQL(sb.toString(),values.toArray());
		return null;
	}
    /**
     * 批量插入excel数据
     * @param readExcelContent
     * @throws BizException 
     * @see com.youlb.biz.access.IDeviceBiz#saveBatch(java.util.List)
     */
	@Override
	public void saveBatch(List<DeviceInfoDto> readExcelContent) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 sb.append("insert into t_deviceinfo(id,fdevicenum,fdevicemodel,fdevicefactory,fdevicestatus,fappversion,")
		 .append("fmemorysize,fstoragecapacity,fsystemversion,fprocessortype,ffirmwareversion,fkernalversion,fremark,fdeviceborn,fsoftware_type,fversion_num");
		 if(readExcelContent!=null){
			 for(DeviceInfoDto dto:readExcelContent){
				 String deviceStatu="";
				 if("激活".equals(dto.getDeviceStatus())){
					 deviceStatu="1";
				 }else if("未激活".equals(dto.getDeviceStatus())){
					 deviceStatu=null;
				 }
				 List<Object> list = new ArrayList<Object>();
				 list.add(dto.getId());
				 list.add(dto.getDeviceNum());
				 list.add(dto.getDeviceModel());
				 list.add(dto.getDeviceFactory());
				 list.add(deviceStatu);
				 list.add(dto.getApp_version());
				 list.add(dto.getMemory_size());
				 list.add(dto.getStorage_capacity());
				 list.add(dto.getSystem_version());
				 list.add(dto.getProcessor_type());
				 list.add(dto.getFirmware_version());
				 list.add(dto.getKernal_version());
				 list.add(dto.getRemark());
				 list.add(dto.getDeviceBorn());
//				 list.add(DateHelper.strParseDate(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 list.add(dto.getSoftwareType());
				 list.add(dto.getVersionNum());
				 
				 
				 if(StringUtils.isNotBlank(dto.getLiveTime())){
					 sb.append(",flive_time");
					 list.add(DateHelper.strParseDate(dto.getLiveTime(), "yyyy-MM-dd HH:mm:ss"));
				 }
				 if(StringUtils.isNotBlank(dto.getCreateTime())){
					 sb.append(",fcreatetime");
					 list.add(DateHelper.strParseDate(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 }
				 sb.append(") values(");
				 for(Object obj:list){
					 sb.append("?,");
				 }
				 sb.deleteCharAt(sb.length()-1);
				 sb.append(")");
				 //主键为空跳过
				 if(StringUtils.isBlank(dto.getId())){
//					 throw new BizException("设备SN码不能为空！");
					 continue;
				 }
				 try {
					 deviceSqlDao.executeSql(sb.toString(), list.toArray());
//					deviceSqlDao.executeSql(sb.toString(), new Object[]{dto.getId(),dto.getDeviceNum(),dto.getDeviceModel(),dto.getDeviceFactory(),deviceStatu,dto.getApp_version(),
//						                                                dto.getMemory_size(),dto.getStorage_capacity(),dto.getSystem_version(),dto.getProcessor_type(),dto.getFirmware_version(),
//						                                                dto.getKernal_version(),dto.getRemark(),dto.getDeviceBorn(),DateHelper.strParseDate(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss"),
//						                                                dto.getSoftwareType(),dto.getVersionNum(),DateHelper.strParseDate(dto.getLiveTime(), "yyyy-MM-dd HH:mm:ss")});
				} catch (BizException e) {
					e.printStackTrace();
					throw new BizException("导入数据出错！");
//					//已经存在更数据
//					 sb = new StringBuilder();
//					 sb.append("update t_deviceinfo set fdevicenum=?,fdevicemodel=?,fdevicefactory=?,fdevicestatus=?,fappversion=?,")
//					 .append("fmemorysize=?,fstoragecapacity=?,fsystemversion=?,fprocessortype=?,ffirmwareversion=?,fkernalversion=?,fremark=?,fdeviceborn=? where id=?");
//					 deviceSqlDao.executeSql(sb.toString(), new Object[]{dto.getDeviceNum(),dto.getDeviceModel(),dto.getDeviceFactory(),deviceStatu,dto.getApp_version(),
//                         dto.getMemory_size(),dto.getStorage_capacity(),dto.getSystem_version(),dto.getProcessor_type(),dto.getFirmware_version(),
//                         dto.getKernal_version(),dto.getRemark(),dto.getDeviceBorn(),dto.getId()});
//					continue;
				}
			 }
		 }
		
	}

	@Override
	public List<DeviceInfoDto> getDeviceInfoDto(String[] ids)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<DeviceInfoDto> dtoList = new ArrayList<DeviceInfoDto>();
		 sb.append("select id,fdevicenum,fdevicemodel,fdevicefactory,fdevicestatus,fappversion,")
		 .append("fmemorysize,fstoragecapacity,fsystemversion,fprocessortype,ffirmwareversion,fkernalversion,fremark,fdeviceborn,")
		 .append("to_char(flive_time,'YYYY-MM-DD HH24:MI:SS'),to_char(fcreatetime,'YYYY-MM-DD HH24:MI:SS'),fsoftware_type,fversion_num from t_deviceinfo where 1=1");
		 
		 sb.append(SearchHelper.jointInSqlOrHql(Arrays.asList(ids), " id "));
		 List<Object[]> listObj = deviceSqlDao.pageFindBySql(sb.toString(),ids);
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 DeviceInfoDto dto = new DeviceInfoDto();
				 dto.setId(obj[0]==null?"":(String)obj[0]);
				 dto.setDeviceNum(obj[1]==null?"":(String)obj[1]);
				 dto.setDeviceModel(obj[2]==null?"":(String)obj[2]);
				 dto.setDeviceFactory(obj[3]==null?"":(String)obj[3]);
//				 if(obj[4]!=null){
					 if("1".equals(obj[4])){
						 dto.setDeviceStatus("激活");
					 }else{
						 dto.setDeviceStatus("未激活");
					 }
//				 }else{
//					 dto.setDeviceStatus("未激活");
//				 }
				 dto.setApp_version(obj[5]==null?"":(String)obj[5]);
				 dto.setMemory_size(obj[6]==null?"":(String)obj[6]);
				 dto.setStorage_capacity(obj[7]==null?"":(String)obj[7]);
				 dto.setSystem_version(obj[8]==null?"":(String)obj[8]);
				 dto.setProcessor_type(obj[9]==null?"":(String)obj[9]);
				 dto.setFirmware_version(obj[10]==null?"":(String)obj[10]);
				 dto.setKernal_version(obj[11]==null?"":(String)obj[11]);
				 dto.setRemark(obj[12]==null?"":(String)obj[12]);
				 dto.setDeviceBorn(obj[13]==null?"":(String)obj[13]);
				 dto.setLiveTime(obj[14]==null?"":(String)obj[14]);
				 dto.setCreateTime(obj[15]==null?"":(String)obj[15]);
				 dto.setSoftwareType(obj[16]==null?"":(String)obj[16]);
				 dto.setVersionNum(obj[17]==null?"":(String)obj[17]);
				 dtoList.add(dto);
			 }
		 }
		return dtoList;
	}
    /**
     * 激活设备
     * @throws BizException 
     */
	@Override
	public void setLive(String[] ids) throws BizException {
		List<String> asList = Arrays.asList(ids);
		StringBuilder setLive = new StringBuilder("update t_deviceinfo set fdevicestatus=?,flive_time=? where (fdevicestatus is null or fdevicestatus='') ");
		setLive.append(SearchHelper.jointInSqlOrHql(asList, " id "));
		int i= deviceSqlDao.updateSQL(setLive.toString(), new Object[]{"1",new Date(),asList});
		System.out.println(i);
	}

}
