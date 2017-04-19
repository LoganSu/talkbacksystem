package com.youlb.controller.houseInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.youlb.biz.houseInfo.IBuildingBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.INeighborhoodsBiz;
import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.biz.houseInfo.IUnitBiz;
import com.youlb.controller.SMSManage.SMSManageCtrl;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.houseInfo.RoomInfoDto;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.utils.common.ExcelUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: RoomCtrl.java 
 * @Description: 房间业务控制类 
 * @author: Pengjy
 * @date: 2015年6月30日
 * 
 */
@RequestMapping("/mc/room")
@Controller
public class RoomCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(BaseCtrl.class);

	@Autowired
    private IRoomBiz roomBiz;
	@Autowired
	private IBuildingBiz buildingBiz;
	@Autowired
	private INeighborhoodsBiz neighborBiz;
	@Autowired
	private IUnitBiz unitBiz;
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setUnitBiz(IUnitBiz unitBiz) {
		this.unitBiz = unitBiz;
	}
	public void setNeighborBiz(INeighborhoodsBiz neighborBiz) {
		this.neighborBiz = neighborBiz;
	}

	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
    
	
	public void setBuildingBiz(IBuildingBiz buildingBiz) {
		this.buildingBiz = buildingBiz;
	}
    /**初始化搜索数据
     * @param module
     * @param modulePath
     * @param parentId
     * @param model
     * @return
     * @see com.youlb.controller.common.BaseCtrl#showPage(java.lang.String, java.lang.String, java.lang.String, org.springframework.ui.Model)
     */
    @RequestMapping("/roomCardListshowPage.do")
    public String roomCardListshowPage(Model model) {
		try {
			List<Neighborhoods> neighborList = neighborBiz.getNeighborList(getLoginUser());
			model.addAttribute("neighborList", neighborList);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	if(neighborList!=null&&!neighborList.isEmpty()){
//    		List<Building> buildingList = getBuildingListByNeibId(neighborList.get(0).getId());
//    		model.addAttribute("buildingList", buildingList);
//    		if(buildingList!=null&&!buildingList.isEmpty()){
//    			List<Unit> unitList = getUnitListBybuildingId(buildingList.get(0).getId());
//    			model.addAttribute("unitList", unitList);
//    		}
//    	}
    	return super.showPage(model);
    }
    /**
     * 通过社区id获取楼栋集合
     * @param neighborId
     * @return
     */
    @RequestMapping("/getBuildingListByNeibId.do")
    @ResponseBody
    public List<Building> getBuildingListByNeibId(String neighborId){
    	try {
			return buildingBiz.getBuildingListByNeibId(neighborId);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 通过楼栋id获取单元集合
     * @param neighborId
     * @return
     */
    @RequestMapping("/getUnitListBybuildingId.do")
    @ResponseBody
    public List<Unit> getUnitListBybuildingId(String buildingId){
    	try {
			return unitBiz.getUnitListBybuildingId(buildingId);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Room room){
		List<Room> list = new ArrayList<Room>();
		//处理发卡页面查询参数
		try {
			if(StringUtils.isNotBlank(room.getUnitId())){
				String domainId = domainBiz.getDomainIdByEntityId(room.getUnitId());
				room.setParentId(domainId);
			}
				list = roomBiz.showList(room,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Room room,Model model){
    	if(ids!=null&&ids.length>0){
    		try {
    		    room = roomBiz.get(ids[0]);
				room.setParentId(domainBiz.getParentIdByEntityId(ids[0]));
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	model.addAttribute("room",room);
   		return "/room/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Room room,Model model){
    	
    	try {
    		if(!RegexpUtils.checkNumber(room.getRoomNum())){
    			return "房号不能为空且不大于5个数字";
    		}
    		if(room.getRoomNum().length()>5){
    			return "房号不能超过5个数字";
    		}
    		if(!RegexpUtils.checkNumber(room.getRoomFloor()+"")||room.getRoomFloor()<0){
    			return "楼层不能为空且为正整数";
    		}
    		//判断单元下面房间号是否已经存在
    		String roomNum = domainBiz.getDomainByParentId(room);
    		if(StringUtils.isNotBlank(roomNum)){
    			return "房号"+roomNum+"已经存在";
    		}
    		roomBiz.saveOrUpdate(room,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log
			return "操作失败！";
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
				roomBiz.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
				return  "删除出错";
			}
		}
		return null;
	}
	
 
	
	 /**
     * 绑定户主
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/bindingRoom.do")
	@ResponseBody
	public String bindingRoom(Room room,Model model){
		try {
			roomBiz.bindingRoom(room);
			return  "绑定成功！";
		} catch (Exception e) {
			e.printStackTrace();
			return  "绑定出错！";
		}
	}
	
	
	/**
     * 解除户主绑定
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/unbindingHomeHost.do")
	@ResponseBody
	public String unbindingHomeHost(Room room,Model model){
		try {
			roomBiz.unbindingRoom(room);
			return  "解除绑定成功！";
		} catch (Exception e) {
			e.printStackTrace();
			return  "接触绑定出错！";
		}
	}
	
	@RequestMapping("/getAddressByDomainId.do")
	@ResponseBody
	public String getAddressByDomainId(String domainId){
		try {
			return roomBiz.getAddressByDomainId(domainId);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
     * 下载模板
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDownModel.do")    
    public ModelAndView download(HttpServletRequest request,HttpServletResponse response) {
    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath(); 
    		File file = new File(path+"resource/roomInfo.xls");
    		long fileLength = file.length();  
    		BufferedInputStream bis = null;
    		BufferedOutputStream out = null;
    		try {
    			response = setFileDownloadHeader(request,response, "roomInfo.xls",fileLength);
    			bis = new BufferedInputStream(new FileInputStream(file));
    			out = new BufferedOutputStream(response.getOutputStream());
    			byte[] buff = new byte[3072];
    			int bytesRead;
    			while ((bytesRead = bis.read(buff, 0, buff.length))!=-1) {
    				out.write(buff, 0, bytesRead);
    			}
    		} catch (FileNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}finally{
    			try {
    				if(bis != null){
    					bis.close();
    				}
    				if(out != null){
    					out.flush();
    					out.close();
    				}
    			}
    			catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
		return null;
     
    }  
    
    /**
     * 导入房间信息
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/importRoomInfo.do")
    public String importRoomInfo(HttpServletRequest request,Model model,String parentId){
    	//服务器地址
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile multipartFile = multipartRequest.getFile("roomInfo");
		List<RoomInfoDto> readExcelContent = null;
		if(multipartFile!=null&&!multipartFile.isEmpty()){
			String realName = multipartFile.getOriginalFilename();
			String suffix = realName.substring(realName.lastIndexOf("."));
			if(!".xlsx".equalsIgnoreCase(suffix)&&!".xls".equalsIgnoreCase(suffix)){
    			model.addAttribute("message", "请选择正确的文件类型！");
    			return INPUT;
			}
			try {
				 readExcelContent = ExcelUtils.readExcelContent(multipartFile.getInputStream(), RoomInfoDto.class);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException
					| NoSuchMethodException | SecurityException
					| ParseException | IOException e) {
				e.printStackTrace();
				model.addAttribute("message", "解析excel文件出错！");
				return INPUT;
			}
			if(readExcelContent!=null){
				try {
					roomBiz.saveBatch(readExcelContent,getLoginUser(),parentId);
				} catch (BizException e) {
					log.error(e.getMessage());
					e.printStackTrace();
					model.addAttribute("message",e.getMessage());
			    	return INPUT;

				} catch (IllegalAccessException e) {
					e.printStackTrace();
					model.addAttribute("message", "复制对象出错");
					e.printStackTrace();
					return INPUT;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					model.addAttribute("message", "复制对象出错");
					e.printStackTrace();
					return INPUT;
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.apache.http.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else{
			log.error("请选择文件！");
			model.addAttribute("message","请选择文件！");
	    	return INPUT;
		}
    	return INPUT;
    }
	
	 /**
     * 导出白名单
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDownfile.do")    
    public ModelAndView singleDownfile(HttpServletRequest request,HttpServletResponse response,String parentId,Model model) {
    	 ModelAndView m= new ModelAndView();
    	try {
    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath();
    		path=path.substring(0,path.indexOf("WEB-INF"));
    		//查询所有的设备数据
			List<RoomInfoDto> list = roomBiz.getRoomInfoDto(parentId);
			if(list==null){
				model.addAttribute("message", "获取数据失败！");
	        	return new ModelAndView("/common/input");
			}
			String randomUUID = UUID.randomUUID().toString();
			randomUUID = randomUUID.replace("-", "");
			log.info("path="+path);
			String[] title = new String[]{"房号","楼层","房产证号","房间归属","用途","朝向","装修情况","建筑面积㎡","使用面积㎡","花园面积㎡","是否空置","备注"};
			String[] filds =new String[]{"roomNum","roomFloor","certificateNum","roomType","purpose","orientation","decorationStatus","roomArea","useArea","gardenArea","useStatus","remark"};
			String temPath = ExcelUtils.exportExcel("房间信息", title, filds, list, path+"tems/"+randomUUID+".xls");
			log.info("temPath="+temPath);
			File file = new File(temPath);
	    	//获取项目根目录
//	    	String rootPath = request.getSession().getServletContext().getRealPath("/");
	    	BufferedInputStream bis = null;
	    	BufferedOutputStream out = null;
	    	try {
		        if(!file.exists()){
					model.addAttribute("message", "该文件不存在！");
		        	return new ModelAndView("/common/input");
		        }
		        long fileLength = file.length();  
		            bis = new BufferedInputStream(new FileInputStream(file));
		            out = new BufferedOutputStream(response.getOutputStream());
		            setFileDownloadHeader(request,response, "roomInfo.xls",fileLength);
		            byte[] buff = new byte[1024];
		            while (true) {
		              int bytesRead;
		              if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
		                  break;
		              }
		              out.write(buff, 0, bytesRead);
		            }
		//            file.deleteOnExit();
		            m.addObject("file", file);
		        }
	        catch (IOException e) {
				e.printStackTrace();
	        }
	        finally{
	            try {
	                if(bis != null){
	                    bis.close();
	                }
	                if(out != null){
	                    out.flush();
	                    out.close();
	                }
	            }
	            catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	            }
	        }
			
		} catch (BizException e) {
			e.printStackTrace();
		}
    	
    	return m;
    }
}
