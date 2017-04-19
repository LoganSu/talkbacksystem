package com.youlb.controller.houseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IAreaBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Address;
import com.youlb.entity.houseInfo.Area;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AreaCtrller.java 
 * @Description: 地区逻辑控制类 
 * @author: Pengjy
 * @date: 2015年6月23日
 * 
 */
@Controller
@RequestMapping("/mc/area")
public class AreaCtrl extends BaseCtrl {
	
	@Autowired
	private IAreaBiz areaBiz;
	@Autowired
    private IRoomBiz roomBiz;
	@Autowired
	private IDomainBiz domainBiz;
	public void setAreaBiz(IAreaBiz areaBiz) {
		this.areaBiz = areaBiz;
	}
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Area area){
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaBiz.showList(area,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
    		
			try {
				Area area = areaBiz.get(ids[0]);
				model.addAttribute("area", area);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	List<Address> provinceList = getAddressByParentId(null);
    	model.addAttribute("provinceList", provinceList);
   		return "/area/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Area area,Model model){
    	try {
    		String province = area.getProvince();
    		if(StringUtils.isNotBlank(province)){
    			area.setProvince(province.split("_")[1]);
    		}
    		if(StringUtils.isBlank(area.getProvince())){
    			return "请选择省份！";
    		}
    		if(StringUtils.isBlank(area.getCity())){
    			return "请选择城市！";
    		}
    		if(StringUtils.isBlank(area.getAreaNum())||area.getAreaNum().length()!=3){
    			return "呼叫号码不能为空且为3个数字，请填写区号！";
    		}else{
    			boolean b = areaBiz.checkAreaNumFromArea(area);
    			if(b){
    				return "呼叫号码已经存在！";
    			}
    		}
    		
			List<Area> areaList = areaBiz.getAreaList(area);
			if(areaList!=null&&!areaList.isEmpty()){
				return "城市已存在！";
			}
			
			//更新操作
			if(StringUtils.isNotBlank(area.getId())){
				Area a = areaBiz.get(area.getId());
				//编号有更新操作
				if(!a.getAreaNum().equals(area.getAreaNum())){
					roomBiz.updateSipNum(a.getAreaNum(),area.getAreaNum(),1);
				}
			}
			
    		areaBiz.saveOrUpdate(area,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log
			return "操作失败！";
		}
    	 return  null;
    }
    
    
    	
    	 /**
         * 查询编号是否存在
         * @param ids
         * @param model
         * @return
         */
    	@RequestMapping("/checkAreaNum.do")
    	@ResponseBody
    	public String checkAreaNum(String areaNum){
    		if(StringUtils.isNotBlank(areaNum)){
    			try {
    				 String news = areaBiz.checkAreaNum(areaNum);
    				 if(StringUtils.isNotBlank(news)){
    					 news =  news + "编号已经存在";
    				 }
    				 return news;
    			} catch (Exception e) {
    			}
    		}
    		return "编号不能为空！";
    	}
    /**
     * 修改
     * @param user
     * @param model
     * @return
     
    @RequestMapping("/update.do")
    @ResponseBody
    public String update(Area area,Model model){
    	try {
    		areaBiz.upate(area);
		} catch (Exception e) {
			return "修改失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return null;
    }*/
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
				//检查是否有子节点
				String remark= domainBiz.hasChild(ids);
				if(StringUtils.isNotBlank(remark)){
					return "请先删除"+remark+"的子域";
				}
				areaBiz.delete(ids);
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
	
	
	/**
     * 通过省份获取城市集合字符串
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/getAreaList.do")
	@ResponseBody
	public List<Area> getAreaList(String province,Model model){
			List<Area> cityList = new ArrayList<Area>();
			if(StringUtils.isNotBlank(province)){
				try {
					cityList = areaBiz.getAreaList(province);
				} catch (Exception e) {
					// TODO
				}
			}
			return cityList;
	}
	@RequestMapping("/getAddressByParentId.do")
	@ResponseBody
	public List<Address> getAddressByParentId(String parentId){
		try {
		List<Address> list = areaBiz.getAddressByParentId(parentId);
		return list;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取社区编号
	 * @param city
	 * @return
	 */
	@RequestMapping("/getAreaCode.do")
	@ResponseBody
	public String getAreaCode(String city){
		try {
			String areaCode = areaBiz.getAreaCode(city);
			return areaCode;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
