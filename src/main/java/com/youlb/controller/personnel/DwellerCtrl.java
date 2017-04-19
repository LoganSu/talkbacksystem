package com.youlb.controller.personnel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.personnel.IDwellerBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: DwellerCtrl.java 
 * @Description: 住户信息管理 
 * @author: Pengjy
 * @date: 2015-10-26
 * 
 */

@Controller
@RequestMapping("/mc/dweller")
public class DwellerCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(DwellerCtrl.class);

	@Autowired
    private IDwellerBiz dwellerBiz;
	public void setDwellerBiz(IDwellerBiz dwellerBiz) {
		this.dwellerBiz = dwellerBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Dweller dweller){
		List<Dweller> list = new ArrayList<Dweller>();
		try {
			list = dwellerBiz.showList(dweller,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Dweller dweller,Model model){
    	if(ids!=null&&ids.length>0){
    		try {
				dweller = dwellerBiz.get(ids[0]);
				List<String> parentIds = dwellerBiz.getParentIds(ids[0]);
				model.addAttribute("parentIds",parentIds);
			} catch (BizException e) {
				log.error("获取单个数据失败");
				e.printStackTrace();
			}
    		//查询所有的父节点

    	}
    	model.addAttribute("dweller",dweller);
   		return "/dweller/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Dweller dweller,Model model){
    	try {
    		Operator loginUser = getLoginUser();
    		if(dweller.getTreecheckbox()==null||dweller.getTreecheckbox().isEmpty()){
    			return  "请选择地址！";
    		}
    		if(StringUtils.isBlank(dweller.getFname())){
	    		return  "姓名不能为空！";
    	    }else{
    	    	if(dweller.getFname().length()>10){
    	    		return   "姓名长度超长！";
    	    	}
    	    }
    	    //检查身份证号码
    	    if(StringUtils.isNotBlank(dweller.getIdNum())){
    	    	if(dweller.getIdNum().length()!=18){
    	    		return  "身份证号码不正确！";
    	    	}
    	    }else{
	    		return  "身份证号码不能为空！";
    	    }
    	   
    	    //顶级运营商如果指定房间 修改运营商为被指定的域的运营商
//    		if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
//    			if(dweller.getTreecheckbox()!=null&&!dweller.getTreecheckbox().isEmpty()){
//    				List<String> carrierId = dwellerBiz.getCarrierByDomainId(dweller.getTreecheckbox());
//    				//获取两个运营商 不允许一个用户绑定跨域地址，一个域的用户智能绑定一个运营商的房间
//    				if(carrierId!=null&&carrierId.size()>1){
//    					return "用户绑定房间不能跨运营商，请创建新的用户绑定！";
//    				}else if(carrierId!=null&&carrierId.size()==1){
//    					dweller.setCarrierId(carrierId.get(0));
//    				}
//    			}else{
//    				dweller.setCarrierId(loginUser.getCarrier().getId());//设置运营商id
//    			}
//    		}else{
//    			dweller.setCarrierId(loginUser.getCarrier().getId());//设置运营商id
//    		}
    		//判断电话的正确性
    		if(StringUtils.isNotBlank(dweller.getPhone())){
    			if(!RegexpUtils.checkMobile(dweller.getPhone())&&!RegexpUtils.checkPhone(dweller.getPhone())){
    				return  "请填写正确的联系电话";
    			}
    		}
    		//验证邮箱
    		if(StringUtils.isNotBlank(dweller.getEmail())){
    			if(!RegexpUtils.checkEmail(dweller.getEmail())){
    				return  "请填写正确的邮箱";
    			}
    		}
    		 //检查手机号码是否已经在同一个运营商里面注册
    	    if(StringUtils.isNotBlank(dweller.getPhone())){
    	    	String b = dwellerBiz.checkPhoneExistWebShow(dweller);
    	    	if(StringUtils.isNotBlank(b)){
    	    		return  "该手机号码已经绑定该"+b+"房产";
    	    	}
    	    }
    		
				dwellerBiz.saveOrUpdate(dweller,loginUser);
			} catch (BizException e) {
				if(RegexpUtils.checkChinese(e.getMessage())){
					return  e.getMessage();

				}else{
					return  "操作失败";
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	 return  null;
    }
    /**
     * 删除
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				dwellerBiz.delete(ids,getLoginUser());
			} catch (Exception e) {
				e.printStackTrace();
				return "删除出错";
			}
		}
		return null;
	}
	/**
	 * 关联房间
	 * @param id
	 * @param hostInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toJoinRoom.do")
	public String toJoinRoom(String id,Dweller dweller,Model model){
		if(StringUtils.isNotBlank(id)){
//    		hostInfo = hostInfoBiz.get(id);
    	}
//		Operator loginUser = getLoginUser();
//    	List<Domain> domainList = domainBiz.getDomainList(loginUser);
//    	model.addAttribute("domainList", domainList);
		model.addAttribute("dweller", dweller);
		return "/dweller/joinRoom";
	}
}
