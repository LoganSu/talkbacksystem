<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
	            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
<%-- 	            <r:role auth="角色管理/添加"> --%>
<%-- 	                  <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/role/toSaveOrUpdate.do" saveUrl="${path}/mc/role/saveOrUpdate.do">添加</button></li> --%>
<%--                 </r:role> --%>
                <r:role auth="运营商角色管理/修改">
                      <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/role/toSaveOrUpdate.do?isCarrier=a" saveUrl="${path}/mc/role/saveOrUpdate.do">分配修改权限</button></li>
	            </r:role>
	            <!--delete类 公共删除
	            <r:role auth="运营商角色管理/删除">
	                   <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/role/delete.do">删除</button></li>
	            </r:role>  -->
         </ul>
	 </div>	
	 <!-- 查询form div-->     
    <div class="searchInfoDiv">
          <form id="carrierRoleSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">角色名称：</div></td>
                <td><div><input name="roleName" class="form-control"/></div	></td>
<!--                 <td><div class="leftFont">姓名：</div></td> -->
<!--                 <td><div><input name="realName" class="form-control"/></div></td> -->
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/role/carrierShowList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
    
</body>
