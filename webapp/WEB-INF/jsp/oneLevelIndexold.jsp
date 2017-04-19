<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理系统</title>
<%--      <script src="${path}/js/common/jquery-2.1.1.min.js"></script> IE不支持2.x版本--%>
<%--           <script src="${path}/js/common/jquery-2.1.3.min.js"></script> --%>
     
     <script src="${path}/js/common/jquery-1.12.3.js"></script>
     <link href="${path}/css/common/bootstrap/bootstrap.css" rel="stylesheet">
<%--      <link href="${path}/css/common/bootstrap/fileinput.min.css" rel="stylesheet"> --%>
     <link href="${path}/css/common/bootstrap/bootstrap-table.css" rel="stylesheet">
     <link href="${path}/css/common/bootstrap/bootstrap-datepicker.css" rel="stylesheet">
     <link href="${path}/css/common/bootstrap/bootstrap-treeview.css" rel="stylesheet">
<%--      <link href="${path}/css/common/bootstrap/bootstrap-multiselect.css" rel="stylesheet"> --%>
     <link href="${path}/css/common/peng.css" rel="stylesheet">
     <link href="${path}/css/common/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet">
     <link href="${path}/css/common/scojs.css" rel="stylesheet">
     <link href="${path}/css/qiniu/highlight.css" rel="stylesheet">
     <link href="${path}/css/qiniu/main.css" rel="stylesheet">
     
<%--        <link rel="stylesheet" href="${path}/css/common/prettify.css" type="text/css"> --%>
<!--         <script type="text/javascript" src="docs/js/jquery-2.1.3.min.js"></script> -->
<!--         <script type="text/javascript" src="docs/js/bootstrap-3.3.2.min.js"></script> -->
<%--         <script type="text/javascript" src="${path}/js/common/prettify.js"></script> --%>

<!--         <link rel="stylesheet" href="dist/css/bootstrap-multiselect.css" type="text/css"> -->
<!--         <script type="text/javascript" src="dist/js/bootstrap-multiselect.js"></script> -->
<%--     <script type="text/javascript" src="${path}/js/common/bootstrap/bootstrap-multiselect-collapsible-groups.js"></script> --%>
	<script src="${path}/js/common/ajaxfileupload.js"></script>
    <script src="${path}/js/common/init.js"></script>
    <script src="${path}/js/qiniu/dist/qiniu.js"></script>
<%--     <script src="${path}/js/qiniu/scripts/main.js"></script> --%>
    <script src="${path}/js/qiniu/scripts/ui.js"></script>
    <script type="text/javascript" src="${path}/js/common/spark-md5.js"></script>
    <script type="text/javascript" src="${path}/js/common/qunit-1.16.0.js"></script>
    <script src="${path}/js/plupload/js/plupload.full.min.js"></script>
    <script src="${path}/js/plupload/js/moxie.js"></script>
    <script src="${path}/js/plupload/js/plupload.full.min.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap.js"></script>
<%--     <script src="${path}/js/common/bootstrap/fileinput.min.js"></script> --%>
    <script src="${path}/js/common/bootstrap/respond.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-table-all.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-table-locale-all.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-table-zh-CN.min.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-datepicker.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-treeview.js"></script>
<%--     <script src="${path}/js/common/bootstrap/bootstrap-multiselect.js"></script> --%>
    
    <script src="${path}/js/common/bootstrap/bootstrap-progressbar.js" type="text/javascript"></script>
    <script src="${path}/js/common/jquery.validate.js" type="text/javascript"></script>
    <script src="${path}/js/common/messages_zh.js" type="text/javascript"></script>
    <script src="${path}/js/common/jquery.metadata.js" type="text/javascript"></script>
     <script src="${path}/js/common/bootstrap/bootstrapQ.js" type="text/javascript"></script>
     <script src="${path}/js/common/spin.js" type="text/javascript"></script>
     <script src="${path}/js/common/sco.modal.js" type="text/javascript"></script>
     <script src="${path}/js/common/sco.confirm.js" type="text/javascript"></script>
     
     <script src="${path}/js/common/chars/highcharts.js" type="text/javascript"></script>
     <script src="${path}/js/common/chars/chars.js" type="text/javascript"></script>
     
     
    <!-- 公共js 方法 -->
    <script src="${path}/js/common/common.js" ></script>
    <script src="${path}/js/cardInfo/cardInfo.js" ></script>
    <script src="${path}/js/deviceInfo/deviceInfo.js" ></script>
    <script src="${path}/js/users/users.js" ></script>
    <script src="${path}/js/deviceCount/deviceCount.js" ></script>
    <script src="${path}/js/appManage/appManage.js" ></script>
    <script src="${path}/js/infoPublish/infoPublish.js" ></script>
    <script src="${path}/js/infoPublish/adPublish.js" ></script>
    <script src="${path}/js/infoPublish/todayNews.js" ></script>
    <script src="${path}/js/permission/permission.js" ></script>
    <script src="${path}/js/management/management.js" ></script>
    <script src="${path}/js/management/aboutNeighborhoods.js" ></script>
    <script src="${path}/js/guidePage/guidePage.js" ></script>
    <script src="${path}/js/monitor/monitor.js" ></script>
    <script src="${path}/js/IPManage/IPManage.js" ></script>
    <script src="${path}/js/SMSManage/SMSManage.js" ></script>
    <script src="${path}/js/houseInfo/area.js" ></script>
    
    
    
    <!-- table JS -->
<%--     <script src="${path}/js/table/areaTable.js"></script> --%>
    <script src="${path}/js/table/neighborhoodsTable.js"></script>
    <script src="${path}/js/table/buildingTable.js"></script>
    <script src="${path}/js/table/unitTable.js"></script>
    <script src="${path}/js/table/roomTable.js"></script>
    <script src="${path}/js/table/dwellerTable.js"></script>
    <script src="${path}/js/table/roomPermissionTable.js"></script>
    <script src="${path}/js/table/appManageTable.js"></script>
    <script src="${path}/js/table/operatorTable.js"></script>
    <script src="${path}/js/table/carrierOperatorTable.js"></script>
    <script src="${path}/js/table/carrierRoleTable.js"></script>
    <script src="${path}/js/table/roleTable.js"></script>
    <script src="${path}/js/table/privilegeTable.js"></script>
    <script src="${path}/js/table/carrierTable.js"></script>
    <script src="${path}/js/table/areaTable.js"></script>
    <script src="${path}/js/table/permissionTable.js"></script>
    <script src="${path}/js/table/cardTable.js"></script>
    <script src="${path}/js/table/deviceTable.js"></script>
    <script src="${path}/js/table/usersTable.js"></script>
    <script src="${path}/js/table/deviceCountTable.js"></script>
    <script src="${path}/js/table/infoPublishTable.js"></script>
    <script src="${path}/js/table/adPublishTable.js"></script>
    <script src="${path}/js/table/appRecordTable.js"></script>
    <script src="${path}/js/table/cardRecordTable.js"></script>
    <script src="${path}/js/table/todayNewsTable.js"></script>
    <script src="${path}/js/table/repairsTable.js"></script>
    <script src="${path}/js/table/workerTable.js"></script>
    <script src="${path}/js/table/departmentTable.js"></script>
    <script src="${path}/js/table/managementCompanyTable.js"></script>
    <script src="${path}/js/table/staticParamTable.js"></script>
    <script src="${path}/js/table/workerGroupTable.js"></script>
    <script src="${path}/js/table/sipCountTable.js"></script>
    <script src="${path}/js/table/sipCountAllTable.js"></script>
    <script src="${path}/js/table/aboutNeighborhoodsTable.js"></script>
    <script src="${path}/js/table/deviceCountSipTable.js"></script>
    <script src="${path}/js/table/IPManageTable.js"></script>
    <script src="${path}/js/table/SMSManageTable.js"></script>
    <script src="${path}/js/table/billManageTable.js"></script>
    <script src="${path}/js/table/doorMachineTable.js"></script>
    <script src="${path}/js/table/domainNameTable.js"></script>
    
    
    
    <!-- 导入自己的js文件-->
<%--     <script src="${path}/js/room/room.js" ></script> --%>
    <script src="${path}/js/common/tree.js"></script>
    <script type="text/javascript">
       $(function(){
    	   $("#showRightArea").load($path+"/mc/guidePage/toGuidePage.do");
       })
    </script>
</head>
  <body>
  <%@include file="common/model.jsp" %>
   <div class="container-fluid f-main">
      <div class="row" style="margin-right: -15px">
        <div class="header">
          <!-- <div class="wrap"> -->
              <i class="logo"></i>
              <h1>赛翼智慧社区云平台</h1>
              
            
              <div class="btnBox">
                <a class="goHome" href="${path}/mc/user/index.do">
                  <i class=""></i>
                  <span class="" rel="${path}/mc/guidePage/toGuidePage.do?1=1">返回首页</span>
                </a>
                <a  class="goExit" href="${path}/mc/user/loginOut.do">
                  <i></i>
                  <span class="">安全退出</span>
                </a>
              </div>
              <span class="welcome">欢迎： ${loginUser.carrier.carrierName}  ${loginUser.loginName} </span>
          <!-- </div> -->
        </div>
  		<!-- 	<div class="panel panel-primary">
  			    <div class="panel-heading" >
  				      <label style="font-size: 30px;padding-left: 100px"><span class="glyphicon glyphicon-home" aria-hidden="false">&nbsp;赛翼智慧社区云平台</span></label>
  				           <label style="padding-left: 10px;"><button class="btn btn-primary btn-sm li_a" rel="${path}/mc/guidePage/toGuidePage.do?1=1">返回首页</button></label>
  					       <label style="padding-left: 700px">欢迎： ${loginUser.carrier.carrierName}  ${loginUser.loginName} </label>
  				       <a style="padding-left: 100px" href="${path}/mc/user/loginOut.do" style="color: white;"><button type="button" class="btn btn-xs btn-warning">退出</button></a>
  			    </div>
  			</div> -->
      </div>
       <div class="row">
           <div class="col-md-2 f-navList">
           <div class="rightLine-i" style=""></div>
           <!-- <div class="jjjj" style="position: fixed;width: 17px;height: 26px;background: url(/imgs/Triangle.png);background-size: 100%;display: none;"></div> -->
               <div class="panel-group" id="accordion2">
                   <div class="toggleBox">
                      <!--  <span class="glyphicon glyphicon-king" aria-hidden="true"></span>
                       <strong style="font-size: 24px;">菜单选项</strong> -->
                       <div class="toggleTag"></div>
                   </div>
           <%--            <r:role auth="房产管理">      --%>
<!--                <div class="panel panel-primary"> -->
<!--                    <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne"> -->
<!--                        <span class=" glyphicon glyphicon-globe" aria-hidden="true"></span> -->
<!--                        <a class="accordion-toggle">房产管理</a> -->
<!--                    </div> -->
<!--                    <div id="collapseOne" class="panel-collapse collapse" style="height: 0px;"> -->
<!--                        <div class="panel-body"> -->
<!--                            <i class="fa fa-files-o fa-fw"></i> -->
<!--                            <ul class="nav nav-pills nav-stacked"> -->
<%--                               <r:role auth="房产信息"> --%>
<%--                                 <li><a tree_id="houseInfoTree" class="li_a tree" href="javascript:void(0)" rel="${path}/mc/area/areaListshowPage.do?module=areaTable&modulePath=/area">房产信息</a></li> --%>
<%--                               </r:role>   --%>
<!--                            </ul> -->
<!--                        </div> -->
<!--                    </div> -->
<!--                </div> -->
<%--            </r:role> --%>
<%--            <r:role auth="运营商管理">      --%>
<!--                <div class="panel panel-primary"> -->
<!--                    <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo"> -->
<!--                        <span class="glyphicon glyphicon-leaf" aria-hidden="true"></span> -->
<!--                        <a class="accordion-toggle">运营商管理</a> -->
<!--                    </div> -->
<!--                    <div id="collapseTwo" class="panel-collapse collapse" style="height: 0px;"> -->
<!--                        <div class="panel-body"> -->
<!--                            <ul class="nav nav-pills nav-stacked"> -->
<%--                              <r:role auth="运营商信息">        --%>
<%--                                <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/carrier/carrierListshowPage.do?module=carrierTable&modulePath=/carrier">运营商信息</a></li> --%>
<%--                              </r:role> --%>
<%--                              <r:role auth="运营商帐号管理">        --%>
<%--                                <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/user/userListshowPage.do?module=carrierOperatorTable&modulePath=/carrierOperator">运营商帐号管理</a></li> --%>
<%--                              </r:role> --%>
<%--                              <r:role auth="运营商角色管理">        --%>
<%--                                <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/role/roleListshowPage.do?module=carrierRoleTable&modulePath=/carrierRole">运营商角色管理</a></li> --%>
<%--                              </r:role> --%>
<!--                            </ul> -->
<!--                        </div> -->
<!--                    </div> -->
<!--                </div> -->
<%--            </r:role> --%>
<%--            <r:role auth="安防监控">      --%>
<!--                <div class="panel panel-primary"> -->
<!--                    <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseThirteen"> -->
<!--                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> -->
<!--                        <a class="accordion-toggle">安防监控</a> -->
<!--                    </div> -->
<!--                    <div id="collapseThirteen" class="panel-collapse collapse" style="height: 0px;"> -->
<!--                        <div class="panel-body"> -->
<!--                            <ul class="nav nav-pills nav-stacked"> -->
<%--                              <r:role auth="实时监控"> --%>
<!--                                module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
<%--                                <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/realTimeMonitor/realTimeMonitor.do?1=1">实时监控</a></li> --%>
<%--                              </r:role> --%>
<!--                            </ul> -->
<!--                        </div> -->
<!--                    </div> -->
<!--                </div> -->
<%--              </r:role> --%>
<%-- 	        <r:role auth="住户管理">      --%>
<!--                <div class="panel panel-primary"> -->
<!--                    <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseEight"> -->
<!--                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> -->
<!--                        <a class="accordion-toggle">住户管理</a> -->
<!--                    </div> -->
<!--                    <div id="collapseEight" class="panel-collapse collapse" style="height: 0px;"> -->
<!--                        <div class="panel-body"> -->
<!--                            <ul class="nav nav-pills nav-stacked"> -->
<%--                              <r:role auth="住户信息"> --%>
<!--                                module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
<%--                                <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/dweller/dwellerListshowPage.do?module=dwellerTable&modulePath=/dweller">住户信息</a></li> --%>
<%--                              </r:role> --%>
<!--                            </ul> -->
<!--                        </div> -->
<!--                    </div> -->
<!--                </div> -->
<%--              </r:role> --%>
<%-- 	        <r:role auth="门禁管理">    --%>
<!-- 	           <div class="panel panel-primary"> -->
<!-- 	                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseFive"> -->
<!-- 	                       <span class="glyphicon glyphicon-gift" aria-hidden="true"></span> -->
<!-- 	                       <a class="accordion-toggle">门禁管理</a> -->
<!-- 	                   </div> -->
<!-- 	                   <div id="collapseFive" class="panel-collapse collapse" style="height: 0px;"> -->
<!-- 	                       <div class="panel-body"> -->
<!-- 		                       <ul class="nav nav-pills nav-stacked"> -->
<%-- 			                       <r:role auth="门禁授权管理"> --%>
<%-- 		                             <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/room/roomCardListshowPage.do?module=roomPermissionTable&modulePath=/roomPermission">门禁授权管理</a></li> --%>
<%-- 		                           </r:role> --%>
<%-- 		                           <r:role auth="门禁卡管理"> --%>
<%-- 		                             <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/permission/cardListshowPage.do?module=cardTable&modulePath=/card">门禁卡管理</a></li> --%>
<%-- 		                           </r:role> --%>
<%-- 		                           <r:role auth="门禁卡刷卡记录"> --%>
<%-- 		                           	   <li><a class="cardInfo li_a" href="javascript:void(0)" rel="${path}/mc/cardRecord/cardrecordshowPage.do?module=cardRecordTable&modulePath=/cardrecord">门禁卡刷卡记录</a></li> --%>
<%-- 	                           	   </r:role> --%>
<%-- 	                           	    <r:role auth="APP开锁记录"> --%>
<%-- 		                           	   <li><a class="cardInfo li_a" href="javascript:void(0)" rel="${path}/mc/appRecord/apprecordshowPage.do?module=appRecordTable&modulePath=/apprecord">APP开锁记录</a></li> --%>
<%-- 	                           	   </r:role> --%>
<!-- 	                           </ul> -->
	                             
<!-- 	                       </div> -->
<!-- 	                   </div> -->
<!-- 	               </div> -->
<%-- 	        </r:role> --%>
	         <r:role auth="设备管理">   
	           <div class="panel panel-primary">
	                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwelve">
	                       <span class="glyphicon glyphicon-gift" aria-hidden="true"></span>
	                       <a class="accordion-toggle">设备管理</a>
	                       <i class="icon"></i>
	                   </div>
	                   <div id="collapseTwelve" class="panel-collapse collapse" style="height: 0px;">
	                       <div class="panel-body">
		                       <ul class="nav nav-pills nav-stacked">
		                           <r:role auth="门禁设备管理">
		                             <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/device/deviceListshowPage.do?module=deviceTable&modulePath=/device">门禁设备管理</a></li>
		                           </r:role>
	                           </ul>
	                             
	                       </div>
	                   </div>
	               </div>
	        </r:role>
	         <r:role auth="帐号管理">   
	           <div class="panel panel-primary">
	                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseFour">
	                       <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
	                       <a class="accordion-toggle">帐号管理</a>
	                   </div>
	                   <div id="collapseFour" class="panel-collapse collapse" style="height: 0px;">
	                       <div class="panel-body">
		                       <ul class="nav nav-pills nav-stacked">
			                       <r:role auth="用户帐号管理">
		                             <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/users/usersshowPage.do?module=usersTable&modulePath=/users">用户帐号管理</a></li>
		                           </r:role>
	                           </ul>
	                             
	                       </div>
	                   </div>
	               </div>
	        </r:role>
	        <r:role auth="版本管理">     
               <div class="panel panel-primary">
                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                       <span class=" glyphicon glyphicon-home" aria-hidden="true"></span>
                       <a class="accordion-toggle">版本管理</a>
                   </div>
                   <div id="collapseThree" class="panel-collapse collapse" style="height: 0px;">
                       <div class="panel-body">
                           <ul class="nav nav-pills nav-stacked">
                             <r:role auth="用户端APP管理"> 
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/appManage/appManageshowPage.do?module=phoneAppManageTable&modulePath=/appManage&appType=2">用户端APP管理</a></li>
                             </r:role>
                             <r:role auth="门口机APP管理"> 
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/appManage/appManageshowPage.do?module=phoneAppManageTable&modulePath=/appManage&appType=1">门口机APP管理</a></li>
                             </r:role>
                             <r:role auth="管理机APP管理"> 
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/appManage/appManageshowPage.do?module=phoneAppManageTable&modulePath=/appManage&appType=3">管理机APP管理</a></li>
                             </r:role>
                             <r:role auth="物业APP管理"> 
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/appManage/appManageshowPage.do?module=phoneAppManageTable&modulePath=/appManage&appType=5">物业APP管理</a></li>
                             </r:role>
                             <r:role auth="第三方APP管理">
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/appManage/appManageshowPage.do?module=phoneAppManageTable&modulePath=/appManage&appType=6">第三方APP管理</a></li>
                             </r:role>
                           </ul>
                       </div>
                   </div>
               </div>
           </r:role>
	                             
           <r:role auth="权限管理">     
               <div class="panel panel-primary">
                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseSix">
                       <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                       <a class="accordion-toggle">权限管理</a>
                   </div>
                   <div id="collapseSix" class="panel-collapse collapse" style="height: 0px;">
                       <div class="panel-body">
                           <ul class="nav nav-pills nav-stacked">
                             <r:role auth="帐户管理">
                               <!-- module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/user/userListshowPage.do?module=operatorTable&modulePath=/operator">帐户管理</a></li>
                             </r:role>
                             <r:role auth="角色管理"> 
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/role/roleListshowPage.do?module=roleTable&modulePath=/role">角色管理</a></li>
                             </r:role>
                             <r:role auth="操作权限">
                                <li><a tree_id="authorityTree" class="tree li_a" href="javascript:void(0)" rel="${path}/mc/privilege/privilegeListshowPage.do?module=privilegeTable&modulePath=/privilege">操作权限</a></li>
                              </r:role>
                           </ul>
                       </div>
                   </div>
               </div>
             </r:role>
             <r:role auth="系统管理">     
               <div class="panel panel-primary">
                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseEleven">
                       <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                       <a class="accordion-toggle">系统管理</a>
                   </div>
                   <div id="collapseEleven" class="panel-collapse collapse" style="height: 0px;">
                       <div class="panel-body">
                           <ul class="nav nav-pills nav-stacked">
                             <r:role auth="静态参数">
                               <!-- module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/staticParam/staticParamListshowPage.do?module=staticParamTable&modulePath=/staticParam">静态参数</a></li>
                             </r:role>
                             <r:role auth="服务器IP管理">
                               <!-- module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/IPManage/IPManageListshowPage.do?module=IPManageTable&modulePath=/IPManage">服务器IP管理</a></li>
                             </r:role>
                             <r:role auth="门口机型号定义 ">
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/doorMachine/doorMachineListshowPage.do?module=doorMachineTable&modulePath=/doorMachine">门口机型号定义</a></li>
                             </r:role>
                           </ul>
                       </div>
                   </div>
               </div>
             </r:role>
             <div class="panel panel-primary">
                   <div class="panel-heading" data-toggle="collapse" data-parent="#accordion2" href="#collapseNine">
                       <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                       <a class="accordion-toggle">安全管理</a>
                   </div>
                   <div id="collapseNine" class="panel-collapse collapse" style="height: 0px;">
                       <div class="panel-body">
                           <ul class="nav nav-pills nav-stacked">
                               <!-- module table json对象， modulePath 是在js/table里面定义的 search.jsp路径对象 -->
                               <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/user/toChangePws.do?1=1">修改密码</a></li>
                           </ul>
                       </div>
                   </div>
               </div>
           </div>
          </div>
           <div class="col-md-10 f-article">
              <!-- 查询条件div 包括功能按钮 -->
              <div class="row" style="padding-left: 15px">
                  
                  <div class="col-md-2 treeDiv" style="display: none;padding-top: 10px">
                     <p id="houseInfoTree"></p>
                  </div>
                  <div class="col-md-2 treeDiv" style="display: none;padding-top: 10px">
                     <p id="authorityTree"></p>
                  </div>
                  <div class="col-md-2 treeDiv" style="display: none;padding-top: 10px">
                     <p id="managementDepartmentTree"></p>
                  </div>
                  <div class="col-md-2 treeDiv" style="display: none;padding-top: 10px">
                     <p id="neighborhoodsTree"></p>
                  </div>
                  <div class="col-md-2 treeDiv" style="display: none;padding-top: 10px">
                     <p id="domainNameTree"></p>
                  </div>
                  
                  
                  <div class="col-md-10" id="showRightArea" >
                     
                  </div>
                  <!-- 图片显示div -->
                  <div id="personshowImg"  class="col-md-2" style="display: none;padding-top: 50px; overflow: hidden;">
                     <img alt="" style="margin-top: 0px;margin-left: 0px;" height="200px" src="">
                  </div>
              </div>
               
           </div>
       </div>
       
   </div>
  <script type="text/javascript">
    //2017/1/14
      $('.toggleTag').on('click',function(){

        $('.f-main').toggleClass('toggle');
        var toggle=$('.f-main').hasClass('toggle');
        var tagList=$('.f-main .panel-group .panel-heading');
        if(toggle){
          //收缩tag
          $('.panel-collapse.collapse,.f-main .panel i.icon').hide();
          $('.f-main .panel-group .toggleBox').css({width:'46px'});
          for(var i=0,len=tagList.length;i<len;i++){
            $('.f-main.toggle .panel.panel-primary')[i].addEventListener("mouseover", hoverTagList);
            $('.f-main.toggle .panel.panel-primary')[i].addEventListener("mouseout", hoverTagListOut);
          }
          $('.jjjj').hide();
          $('.f-navList').css({'border-right':'1px solid #c7c7c7'});
        }else{
          //展开tag
          $('.f-navList').css({'border-right':0});
          $('.f-main .panel i.icon').show();
          $('.f-main .panel-group .toggleBox').css({width:'249px'});
          $('.panel-collapse.collapse.in .panel-body').css({
             'border-top':'1px solid #428bca'
          });
          $('.panel-collapse.collapse').css({
            position:'static',
            background:'#fff',
            top:0,
            left:0,
            width:'249px',
            height:'0',
            display:'inherit',
            'border-top':0,
            'border-bottom':0,
            'border-left':0

          }).find('.panel-body').css({
            'border-top':'1px solid #cdcdcd'
          });
          
          for(var i=0,len=tagList.length;i<len;i++){
            $('.f-main .panel-group .panel.panel-primary')[i].removeEventListener("mouseover", hoverTagList);
            $('.f-main .panel-group .panel.panel-primary')[i].removeEventListener("mouseout", hoverTagListOut);
          }
        }
      });

      $('.f-main .panel-body li a').on('click',function(){
        $('.panel.panel-primary').find('.panel-heading').removeClass('current');
        $('.panel,.panel-heading').css({width:'249px'});
        $(this).parents('.panel').css({width:'260px'}).find('.panel-heading').css({width:'260px'}).addClass('current');

        var jiantouT=$(this).parents('.panel').offset().top;
        var jiantouL=$(this).parents('.panel').offset().left;
        $('.jjjj').show();
         $('.f-main .panel-body li a').removeClass('cur');
          $(this).addClass('cur');
      });
      
    
      function hoverTagList(e){
        var top=$(this).offset().top;
        var taglistCSS={
              position:'fixed',
              background:'#f5f5f5',
              top:top+'px',
              left:'45px',
              width:'200',
              height:'auto',
              border:'1px solid #C7C7C7',
              'border-top':'0'
        }
        $(this).find('.panel-collapse').show().css(taglistCSS);
        // $(this).find('.panel-body').css({'border-top':'none'})
        $(this).find('.panel-body ul').css({'background':'#fff'});
        // $(this).find('.panel-body ul').css({'background':'#fff'}).find('li').css({'margin-top':'2px'})
      }
      function hoverTagListOut(e){
        $(this).find('.panel-collapse').hide();
      }
      //
      $('.f-main .panel-group .panel').on('click',function(){
        var expand=$(this).find('.collapse').hasClass('in');
        $('.panel-heading').removeClass('cur');
        if(!expand){
          $(this).find('.panel-heading').addClass('cur');
        }
      })

  </script>
</body>
</html>