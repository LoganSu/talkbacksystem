package com.youlb.biz.countManage.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.countManage.IDeviceCountBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.countManage.DeviceCount;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
import com.youlb.utils.sms.SmsUtil;

/** 
 * @ClassName: DeviceCountBizImpl.java 
 * @Description: 设备账号业务实现类 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Service("deviceCountBiz")
public class DeviceCountBizImpl implements IDeviceCountBiz {
	@Autowired
	private BaseDaoBySql<DeviceCount> deviceCountSqlDao;
	public void setDeviceCountSqlDao(BaseDaoBySql<DeviceCount> deviceCountSqlDao) {
		this.deviceCountSqlDao = deviceCountSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(DeviceCount target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(DeviceCount target) throws BizException {
		String hql ="update DeviceCount set countPsw=? where id=?";
		deviceCountSqlDao.update(hql, new Object[]{SHAEncrypt.digestPassword(target.getCountPsw()),target.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		deviceCountSqlDao.delete(id);
		String sql = "delete from users t where t.local_sip=(select fsipnum from t_devicecount where id=?) and t.sip_type in ('2','5')";
		deviceCountSqlDao.executeSql(sql, new Object[]{id});
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
	public DeviceCount get(Serializable id) throws BizException {
		DeviceCount count = deviceCountSqlDao.get(id);
		String address = findAddressByRoomId(count.getDomainId());
		if(StringUtils.isNotBlank(count.getDeviceCountDesc())){
			count.setAddress(address+"-"+count.getDeviceCountDesc());
		}else{
			count.setAddress(address);
		}
		return count;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<DeviceCount> showList(DeviceCount target, Operator loginUser)
			throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("from DeviceCount t where 1=1");
		if(StringUtils.isNotBlank(target.getDeviceCount())){
			sb.append(" and t.deviceCount like ?");
			values.add("%"+target.getDeviceCount()+"%");
		}
		if(StringUtils.isNotBlank(target.getCountType())){
			sb.append(" and t.countType = ?");
			values.add(target.getCountType());
		}
		if(StringUtils.isNotBlank(target.getCountStatus())){
			sb.append(" and t.countStatus = ?");
			values.add(target.getCountStatus());
		}
		
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"t.domainId"));
			values.add(domainIds);
		}
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc");
		List<DeviceCount> findList = deviceCountSqlDao.pageFind(sb.toString(), values.toArray(), target.getPager());
		//处理地址显示
		 if(findList!=null&&!findList.isEmpty()){
			 for(DeviceCount count:findList){
//				 Map<String,StringBuilder> map = new HashMap<String, StringBuilder>();
//				 map.put(count.getDomainId(), new StringBuilder());
//				 map = getAddress(map);
//				 count.setAddress(map.values().toString().substring(1, map.values().toString().length()-1));
				 String address ="";
				 if(StringUtils.isNotBlank(count.getDeviceCountDesc())){
					 address = findAddressByRoomId(count.getDomainId())+"-"+count.getDeviceCountDesc();
				 }else{
					 address = findAddressByRoomId(count.getDomainId());
				 }
				 count.setAddress(address);
			 }
		 }
		 return findList;
	}
	
	/**
     * 递归获取详细地址集合
     * @param map key=id-domainSn  value= StringBuilder
     * @return
     
	private Map<String,StringBuilder> getAddress(Map<String,StringBuilder> map){
		    String sql="SELECT d.fparentid ,d.fremark from t_domain d where d.id=?";
			boolean b=false;
			Map<String,StringBuilder> newMap = new HashMap<String, StringBuilder>();
			for(String key:map.keySet()){
				Object[] obj = (Object[]) deviceCountSqlDao.findObjectBySql(sql, new Object[]{key.substring(0, 32)});
				StringBuilder sb = map.get(key).insert(0, (String)obj[1]);
				//新key domainsn不变
				String newkey=(String)obj[0];
				newMap.put(newkey,sb);
				//当parentId==1时 已经拿到全部地址
				if("1".equals(obj[0])){
					b=true;
				}
			}
			//到顶级域的时候返回结果
			if(b){
				return newMap;
			}
			return getAddress(newMap);
	}
	*/

	/**
	 * @param deviceCount
	 * @throws BizException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @see com.youlb.biz.countManage.IDeviceCountBiz#saveOrUpdate(com.youlb.entity.countManage.DeviceCount)
	 */
	@Override
	public void saveOrUpdate(DeviceCount deviceCount) throws BizException, ClientProtocolException, IOException, ParseException, JsonException {
		//entityid转换成domainid
//		String sql ="SELECT d.id from t_domain d where d.fentityid=?";
//		List<String> slist = deviceCountSqlDao.pageFindBySql(sql, new Object[]{deviceCount.getTreecheckbox().get(0)});
		if(StringUtils.isBlank(deviceCount.getId())){
			//生成sip账号
			String domainId = deviceCount.getTreecheckbox().get(0);
			deviceCount.setDomainId(domainId);
			//同步
			String sipNum = UUID.randomUUID().toString().replace("-", "");
//			synchronized (deviceCount) {
//				//一个域可以绑定多个账号，避免重复sip账号
//				String sql = "SELECT max(fsipnum) from t_devicecount t where t.fdomainid=?";
//				List<String> maxList = deviceCountSqlDao.pageFindBySql(sql, new Object[]{domainId});
//				if(maxList!=null&&!maxList.isEmpty()&&!maxList.contains(null)){
//					String maxSip = maxList.get(0);
//					if(maxSip.contains("-")){
//						String[] maxArr = maxSip.split("-");
//						int parseInt = Integer.parseInt(maxArr[1]);
//						sipNum = maxArr[0]+"-"+ ++parseInt;
//					}else{
//						sipNum = maxSip+"-1";
//					}
//				}else{
//					sipNum = getSipNum(domainId);
//				}
//			}
			deviceCount.setSipNum(sipNum);//sipNum
			deviceCount.setId(null);
			//生成序号8位数
		    Session session = deviceCountSqlDao.getCurrSession();
			SQLQuery query = session.createSQLQuery("SELECT '1'||substring('000000'||nextval('tbl_devicecount_seq'),length(currval('tbl_devicecount_seq')||'')) ");
			List<String> list =  query.list();
			deviceCount.setDeviceCount(list.get(0));
			deviceCount.setCountPsw(SHAEncrypt.digestPassword(SmsUtil.random()));
			deviceCountSqlDao.add(deviceCount);
			//添加真正的sip账号fs拨号使用
			String sipType = deviceCount.getCountType();
//			String serialize="";
			if("3".equals(sipType)){
				sipType="5";
//				serialize="SELECT '11'||substring('0000000'||nextval('tbl_sipcount_seq'),length(currval('tbl_sipcount_seq')||'')) ";
				
			}else if("1".equals(sipType)){
				sipType="2";
//				serialize="SELECT '1'||substring('00000000'||nextval('tbl_sipcount_seq'),length(currval('tbl_sipcount_seq')||'')) ";
				
			}
//			SQLQuery query1 = session.createSQLQuery(serialize);
//		    List<String> list1 =  query1.list();
//		    String addSip ="insert into users (user_sip,user_password,local_sip,sip_type,fs_ip,fs_port) values(?,?,?,?,?,?)";
//		    String password = UUID.randomUUID().toString().replace("-", "");
			//获取fs ip和端口
			//获取社区名称
			String neiborName = getNeiborNameByDomainId(domainId);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			//同步数据以及平台
			HttpPost request = new HttpPost(SysStatic.FIRSTSERVER+"/fir_platform/create_sip_num");
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
			formParams.add(new BasicNameValuePair("local_sip", sipNum));
			formParams.add(new BasicNameValuePair("sip_type", sipType));
			formParams.add(new BasicNameValuePair("neibName", neiborName));
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			request.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity_rsp = response.getEntity();
				ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
				if(resultDto!=null){
					if(!"0".equals(resultDto.getCode())){
						throw new BizException(resultDto.getMsg());
					}else{
						Map<String,Object> map = (Map<String, Object>) resultDto.getResult();
						if(map!=null&&!map.isEmpty()){
							Map<String,Object> user_sipMap = (Map<String, Object>) map.get("user_sip");
						    String addSip ="insert into users (user_sip,user_password,local_sip,sip_type,fs_ip,fs_port) values(?,?,?,?,?,?)";
						    //{user_sip=2000000338, userPassword=63d7b817141c4d30840cd24e16200859, sipType=6, linkId=87, fs_ip=192.168.1.222, fs_post=35162}
						    deviceCountSqlDao.executeSql(addSip, new Object[]{user_sipMap.get("user_sip"),user_sipMap.get("userPassword"),
						    		user_sipMap.get("linkId"),user_sipMap.get("sipType"),user_sipMap.get("fs_ip"),user_sipMap.get("fs_post")});//住户sip 6

						}
				     }
				}else{
					throw new BizException("创建sip账号出错！");
				}
	       }
			
			
			
			//同步数据以及平台
//			HttpGet get = new HttpGet(SysStatic.FIRSTSERVER+"/users/get_fs_by_nei_name.json?neib_name="+neiborName);
//			CloseableHttpResponse execute = httpClient.execute(get);
//			if(execute.getStatusLine().getStatusCode()==200){
//				HttpEntity entity_rsp = execute.getEntity();
//				ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
//				if(resultDto!=null){
//					if("0".equals(resultDto.getCode())){
//						Map<String,Object> result = (Map<String, Object>) resultDto.getResult();
//						if(result!=null&&!result.isEmpty()){
//							String fs_ip = (String) result.get("fs_ip");
//							Integer fs_port = (Integer) result.get("fs_port");
//							deviceCountSqlDao.executeSql(addSip, new Object[]{Integer.parseInt(list1.get(0)),password,sipNum,sipType,fs_ip,fs_port});//门口机sip账号类型为2 管理机sip账号是5
//							//同步sip账号
//							Synchronization_sip.synchronization_sip(sipNum, list1.get(0), password, sipType, fs_ip,fs_port);
//						}else{
//							throw new BizException("请联系管理员先在一级平台添加社区ip信息再操作");
//						}
//					 }else{
//							throw new BizException(resultDto.getMsg());
//					  }
//				  }
//				}
			
		}else{
			updateAll(deviceCount);
		}
		
	}
    /**
     * 更新操作
     * @param deviceCount
     * @throws BizException 
     */
    private void updateAll(DeviceCount deviceCount) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("update DeviceCount set deviceCountDesc=?,countType=?,countStatus=?,warnPhone=?,warnEmail=? ");
		 values.add(deviceCount.getDeviceCountDesc());
		 values.add(deviceCount.getCountType());
		 values.add(deviceCount.getCountStatus());
		 values.add(deviceCount.getWarnPhone());
		 values.add(deviceCount.getWarnEmail());
		 if(deviceCount.getLongitude() !=null){
			 sb.append(",longitude=?");
			 values.add(deviceCount.getLongitude());
		 }else{
			 sb.append(",longitude=null");
		 }
		 if(deviceCount.getLatitude()!=null){
			 sb.append(",latitude=?");
			 values.add(deviceCount.getLatitude());
		 }else{
			 sb.append(",latitude=null");
		 }
		 sb.append(" where id=?");
		 values.add(deviceCount.getId());
		 deviceCountSqlDao.update(sb.toString(), values.toArray());
		
	}

	/**
     * 通过域id获取社区名称
     * @param domainId
     * @return
     * @throws BizException 
     */
	private String getNeiborNameByDomainId(String domainId) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 sb.append("WITH RECURSIVE r AS (SELECT d.* from t_domain d  where d.id=? ")
		 .append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid )")
		 .append(" SELECT r.fremark from r where r.flayer='1'");
		 List<String> list = deviceCountSqlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(list!=null&&!list.isEmpty()){
			 return list.get(0);
		 }
		return "";
	}

	/**
	 * @param domainId
	 * @return
	 * @throws BizException 
	 */
	private String getSipNum(String domainId) throws BizException {
		String sql ="select d.id,d.flayer from t_domain d where d.id=?";
		List<Object[]> list = deviceCountSqlDao.pageFindBySql(sql, new Object[]{domainId});
		StringBuilder sb = new StringBuilder();
		getSipNumDetail(list.get(0),sb);
		return sb.toString();
	}

	/**
	 * @param obj
	 * @return
	 * @throws BizException 
	 */
	private Object[] getSipNumDetail(Object[] obj,StringBuilder sb) throws BizException {
		if(obj!=null){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT td.id,td.flayer,");
			if(obj[1].equals(SysStatic.AREA)){
				sql.append("t.fareanum ");
			}else if(obj[1].equals(SysStatic.NEIGHBORHOODS)){
				sql.append("t.fneibnum ");
			}else if(obj[1].equals(SysStatic.BUILDING)){
				sql.append("t.fbuildingnum ");
			}else if(obj[1].equals(SysStatic.UNIT)){
				sql.append("t.funitnum ");
			}else if(obj[1].equals(SysStatic.ROOM)){
				sql.append("t.froomnum ");
			}
			sql.append("from  t_domain d INNER JOIN t_domain td on td.id=d.fparentid INNER JOIN ");
			if(obj[1].equals(SysStatic.AREA)){
				sql.append(" t_area ");
			}else if(obj[1].equals(SysStatic.NEIGHBORHOODS)){
				sql.append(" t_neighborhoods ");
			}else if(obj[1].equals(SysStatic.BUILDING)){
				sql.append("t_building ");
			}else if(obj[1].equals(SysStatic.UNIT)){
				sql.append("t_unit ");
			}else if(obj[1].equals(SysStatic.ROOM)){
				sql.append("t_room ");
			}
			
			sql.append(" t ON t.id=d.fentityid  where d.id = ? ");
			String id = (String)obj[0];
			List<Object[]> list = deviceCountSqlDao.pageFindBySql(sql.toString(), new Object[]{id});
			if(list!=null&&!list.isEmpty()){
				Object[] subObj = list.get(0);
				sb.insert(0, subObj[2]);
				if(subObj[1]!=null){
					getSipNumDetail(subObj, sb);
				}
				return subObj;
			}
		}
		return null;
	}
    /**
     * /**添加截止日期
     * @param endTime
     * @throws BizException 
     * @see com.youlb.biz.countManage.IDeviceCountBiz#update(java.lang.String)
     */
	@Override
	public void updateById(String endTime,String ramdonCode,String orderNum,String qrPath,String id) throws BizException {
		String hql ="update DeviceCount set endTime=?,ramdonCode=?,orderNum=?,qrPath=?,createTime=? where id=?";
		deviceCountSqlDao.update(hql, new Object[]{endTime,ramdonCode,orderNum,qrPath,new Date(),id});
	}
	/**获取地址信息
	 * @param cardInfo
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	private String findAddressByRoomId(String domainId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = deviceCountSqlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}

	@Override
	public String getOrderNum() {
		//生成序号8位数
	    Session session = deviceCountSqlDao.getCurrSession();
		SQLQuery query = session.createSQLQuery("SELECT '1'||substring('00000000'||nextval('tbl_devicecount_ordernum_seq'),length(currval('tbl_devicecount_ordernum_seq')||'')) ");
		List<String> list =  query.list();
		return list.get(0);
	}

	@Override
	public DeviceCount getByCount(String deviceCount) throws BizException {
		String hql ="from DeviceCount where deviceCount=?";
		return deviceCountSqlDao.findObject(hql, new Object[]{deviceCount});
	}
    /**
     * 修改门口机需要更新白名单标志
     */
	@Override
	public void updateNeedUpdate(String domainId) throws BizException {
		String sql = "update t_devicecount set fneed_update=? where fdomainid=?";
		deviceCountSqlDao.updateSQL(sql, new Object[]{"1",domainId});
	}
}
