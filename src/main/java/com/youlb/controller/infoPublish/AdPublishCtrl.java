package com.youlb.controller.infoPublish;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.infoPublish.IAdPublishBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.infoPublish.AdPublish;
import com.youlb.entity.infoPublish.AdPublishPicture;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: InfoPublish.java 
 * @Description: 信息发布 
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
@Controller
@RequestMapping("/mc/adPublish")
public class AdPublishCtrl extends BaseCtrl {
	
	private static Logger log = LoggerFactory.getLogger(AdPublishCtrl.class);
	@Autowired
    private IAdPublishBiz adPublishBiz;
	public void setAdPublishBiz(IAdPublishBiz adPublishBiz) {
		this.adPublishBiz = adPublishBiz;
	}

	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(AdPublish adPublish){
		List<AdPublish> list = new ArrayList<AdPublish>();
		try {
			list = adPublishBiz.showList(adPublish,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,AdPublish adPublish,Model model){
    	String opraterType = adPublish.getOpraterType();
    	if(ids!=null&&ids.length>0){
    		try {
				adPublish = adPublishBiz.get(ids[0]);
				//选择区域发送处理数据回显
				if("2".equals(adPublish.getSendType())){
					List<String> parentIds = adPublishBiz.getParentIds(ids[0]);
					model.addAttribute("parentIds",parentIds);
				}
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    		//如果是全部推送类型不需要返回标签
    		if("1".equals(adPublish.getSendType())){
    			adPublish.setTreecheckbox(null);
    		}
    		adPublish.setOpraterType(opraterType);
    	}
    	model.addAttribute("adPublish",adPublish);
   		return "/adPublish/addOrEdit";
   	}
    
    /**
     * 上传文件
     * @param request
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("/uploadFile.do")
    @ResponseBody
    public AdPublishPicture upoadFile(HttpServletRequest request,HttpServletResponse resp,AdPublishPicture adPic,Model model) throws IOException{
//    	AdPublishPicture adPic = new AdPublishPicture();
//    	System.out.println(adPic);
    	//获取项目根目录
//    	String rootPath = request.getSession().getServletContext().getRealPath("/");
    	try {
//    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
//    		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
//    		if(multipartFile!=null&&!multipartFile.isEmpty()) {
//    			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
//        		String fileName =sd.format(new Date());
        		//按月份分文件夹
//        		String subDir =fileName.substring(0, 6);
//    			String riginalFilename = multipartFile.getOriginalFilename();
//    			BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());   
//    			int width = bufferedImage.getWidth();   
//    			int height = bufferedImage.getHeight();
    			//门口机
//    			if(SysStatic.one.equals(adPic.getTargetDevice())){
//    				//首页
//    				if(SysStatic.one.equals(adPic.getPosition())){
//    					if(width<720||width>1080||(float)width/height!=(float)9/16){
//        					adPic.setMessage("请选择1080*1920~720*1280px比例为9:16的图片");
//        				}
//    				//拨号页3:4
//    				}else if(SysStatic.two.equals(adPic.getPosition())){
//    					if(width<720||width>1080||(float)width/height!=(float)3/4){
//        					adPic.setMessage("请选择1080*1440~720*960px比例为3:4的图片");
//        				}
//    				}
//    			 //手机18:7
//    			}else if(SysStatic.two.equals(adPic.getTargetDevice())){
//    				if(width<720||width>1080||(float)width/height!=(float)18/7){
//    					adPic.setMessage("请选择1080*420~720*280px比例为18:7的图片");
//    				}
//    			}
    			//获取上传文件的后缀
//    			String suffix = riginalFilename.substring(riginalFilename.lastIndexOf("."), riginalFilename.length());
//    			//文件名按时间命名
//    			String relativePath= SysStatic.ADDIR+SysStatic.PICTURE+subDir+"/"+fileName+suffix;
//    			log.info("服务器地址 ： http://" +  SysStatic.FILEUPLOADIP );
//    			//服务器地址 
//    			String strBackUrl = "http://" + SysStatic.FILEUPLOADIP  //192.168.1.222:8080
//                        + request.getContextPath();      //项目名称  
//				File file = new File(relativePath);
//				if(!file.exists()){
//					file.mkdirs();
//				}
//				multipartFile.transferTo(file);
//				adPic.setServerAddr(strBackUrl);
//				adPic.setRelativePath(relativePath.substring(relativePath.indexOf("adDir")-1));//文件相对路径
				adPic.setId(adPublishBiz.addPicture(adPic));
//    		}
    	} catch (BizException e) {
    		adPic.setMessage("操作失败！");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			adPic.setMessage("媒体文件上传失败！");
			e.printStackTrace();
		}
    	resp.setContentType("text/html");
		return adPic;
    }
    
    public static void main(String[] args) {
		int i1 =720;
		int i2=280;
		System.out.println((float)i1/i2==(float)18/7);
	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(HttpServletRequest request,AdPublish adPublish,Model model){
    	
			try {
				if(adPublish.getPicId()==null||adPublish.getPicId().isEmpty()){
					return "请先上传图片！";
		    	}
				if(StringUtils.isBlank(adPublish.getExpDateStr())){
					return "有效期不能为空！";
		    	}
		    	Date expDate = DateHelper.strParseDate(adPublish.getExpDateStr(), "yyyy-MM-dd");
		    	if(new Date().getTime()>expDate.getTime()){
		    		return "有效期要在今天以后！";
		    	}
		    	if("2".equals(adPublish.getSendType())){
		    		List<String> treecheckbox = adPublish.getTreecheckbox();
		    		if(treecheckbox==null||treecheckbox.size()!=1){
		    			return "请选择一个域发布信息！";
		    		}
		    	}
				adPublishBiz.saveOrUpdate(adPublish,getLoginUser());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return "发布出错";
			} catch (InvocationTargetException e) {
 				e.printStackTrace();
				return "发布出错";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "发布出错";
			} catch (ParseException e) {
				e.printStackTrace();
				return "发布出错";
			} catch (JsonException e) {
				e.printStackTrace();
				return "发布出错";
			} catch (IOException e) {
				e.printStackTrace();
				return "发布出错";
			} catch (BizException e) {
				e.printStackTrace();
				return "发布出错";
			}
				 
    	 return null;
    }
    /**
     * 发布
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/publish.do")
	@ResponseBody
	public String publish(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				adPublishBiz.publish(ids,getLoginUser());
			} catch (Exception e) {
				return  "发布出错";
			}
		}
		return null;
	}
    
    
	/**
     * 删除图片
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/deletePic.do")
	@ResponseBody
	public String deletePic(String picId,Model model){
		if(StringUtils.isNotBlank(picId)){
			try {
				adPublishBiz.deletePicture(picId);
			} catch (Exception e) {
				return "删除出错";
			}
		}
		return null;
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
				adPublishBiz.delete(ids);
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
}
