<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
</head>
<script type="text/javascript"></script>
<%-- <script type="text/javascript" src="${path}/js/common/jquery-2.1.1.min.js"></script> --%>
<script src="${path}/js/common/jquery-1.12.3.js"></script>
<script type="text/javascript" src="${path}/js/common/jquery.md5.js"></script>
<script type="text/javascript" src="${path}/js/common/bootstrap/bootstrap.min.js"></script>
<script src="${path}/js/common/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/js/common/messages_zh.js" type="text/javascript"></script>
<script src="${path}/js/common/jquery.metadata.js" type="text/javascript"></script>
<script src="${path}/js/common/login.js" type="text/javascript"></script>
    <script src="${path}/js/common/common.js" ></script>


<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap-admin-theme.css" type="text/css">
<style type="text/css">
    .alert{
        margin: 0 auto 20px;
    }
    .container{
    margin-top: 200px
    }
</style>
<body class="bootstrap-admin-without-padding" style="background-position: center;background-repeat: no-repeat;background-size:cover;background-image: url('${path}/imgs/login.png');">
        <div style="display: none;" class="alert alert-warning alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		  <strong>警告!</strong>&nbsp;&nbsp;&nbsp;<span class="warnWorld"></span>
		</div>
        
        <div class="container" >
            <div class="row">
                <div class="col-lg-12" style="text-align: center;">
                 <div style="position: relative;margin: 0 auto;margin-bottom: 30px">
                        <label><font size="50px" style="color:#09f;font-style: !important;" >&nbsp;赛翼智慧社区云平台</font></label>
                 
                 </div>
                    
                    <form method="post" id="loginForm" action="${path}/mc/user/loginForm.do" class="bootstrap-admin-login-form alert-info">
                        <input type="hidden" class="carrierNum" name="carrier.carrierNum" value="${carrierNum}"/>
                        <table style="width: 100%;padding-top: 10px;text-align: left;">
                           <tr>
                             <td><label>用户名：</label></td>
                             <td>
	                              <div style="margin-top: 20px"><input class="form-control required loginName" type="text" name="loginName" placeholder="用户名 ">
	                              </div>
                              </td>
                           </tr>
                            <tr>
                             <td><div style="margin-top: 20px"><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></div></td>
                             <td>
                                 <div style="margin-top: 20px"><input class="form-control required password" type="password" name="password" placeholder="密码">
<%--                                  <label>${password}</label> --%> 
                                 </div>
                             </td>
                           </tr>
                           <tr>
                              <td><label>验证码：</label></td>
                              <td>
                                <div style="margin-top: 20px">
	                                 <div class="input-group">
									      <input type="text" class="form-control required verificationCode" maxlength="6" value="" name="verificationCode" placeholder="验证码">
									      <span class="input-group-btn">
									        <button class="btn btn-info getVerificationCode" type="button" rel="${path}/mc/user/getVerificationCode.do" style="height: 39px;padding-top: 6px">获取验证码</button>
									      </span>
									  </div>
									      <label class='msg'>${errorMessg}</label>
								 </div>
                              </td>  
                           </tr>
                           <tr>
                             <td></td>
                             <td>
                                <div style="text-align: left ;margin-top: 20px">
                                  <button class="btn btn-xs btn-primary" style="width: 230px" type="submit">登录</button>
                                </div>
                             </td>
                           </tr>
                        </table>
                    </form>
                    <div style="margin-top: 20px;">版本号：${version}</div>
                    <div style="margin-top: 50px;">Copyright © 2016 广东赛翼智能科技股份有限公司</div> 
                </div>
            </div>
        </div>
      
        <script type="text/javascript">
            $(function(){
            	if (!!window.ActiveXObject || "ActiveXObject" in window){
            		}else{
            			 $(".alert-dismissible .warnWorld").html("推荐使用IE浏览器来访问本网站, 网站的部分功能无法在非IE浏览器上获得有效的支持.");
            			 $(".alert-dismissible").show();
            			 return false;
            		}
            	var browser=navigator.appName
            	var b_version=navigator.appVersion
            	var version=b_version.split(";");
            	var trim_Version=version[1].replace(/[ ]/g,"");
            	if((browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0")||(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0")){
            		 $(".alert-dismissible .warnWorld").html("请使用IE8以上版本访问本网站");
        			 $(".alert-dismissible").show();
            		     return false;
                	}
            	
//             	var browser=navigator.appName
//             	var b_version=navigator.appVersion
//             	var version=b_version.split(";");
//             	var trim_Version=version[1].replace(/[ ]/g,"");
//             	alert(browser);
            	
//             	alert(trim_Version);
//             	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0")
//             	{
//             	alert("IE 6.0");
//             	}
//             	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0")
//             	{
//             	alert("IE 7.0");
//             	}
//             	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0")
//             	{
//             	alert("IE 8.0");
//             	}
//             	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0")
//             	{
//             	alert("IE 9.0"); 
//             	}
//             	else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE10.0")
//             	{
//             	alert("IE 10.0"); 
//             	}
//             	else if(browser=="Netscape" && trim_Version=="MSIE10.0")
//             	{
//             	alert("IE 10.0"); 
//             	}
            });
        </script>
    </body>
</html>