<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>

<div>
	   <div>
		 <form id="areasaveForm" action="">
		   <input type="hidden" name="id" value="${area.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>省份：</div></td>
                <td><div>
                  <select name="province" class="form-control province">
                     <option value="">请选择省份</option>
                    <c:forEach items="${provinceList}" var="province">
                       <option value="${province.id}_${province.fshortname}" <c:if test="${province.fshortname==area.province}">selected="selected"</c:if>>${province.fshortname}</option>
                    </c:forEach>
                  </select>
                
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>城市：</div></td>
                <td><div>
                   <select name="city" class="form-control city">
                    <option value="">请选择城市</option>
                    <c:if test="${area.id!=null}">
                      <option value="${area.city }" selected="selected">${area.city }</option>
                    </c:if>
                  </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>呼叫号码：</div></td>
                <td><div><input name="areaNum" class="form-control areaNum required number" title="呼叫号码不能为空且为3个数字，请填写区号" readonly="readonly" maxlength="3" value="${area.areaNum}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">是否启用网关：</div></td>
                 <td><div>
                    <select name="createSipNum" class="form-control">
                      <option value="" <c:if test="${area.createSipNum==null or building.createSipNum==''}">selected="selected"</c:if> >否</option>
                      <option value="2" <c:if test="${area.createSipNum=='2'}">selected="selected"</c:if>>是</option>
                    </select>
                     
                 </div></td>
                 <td><span style="color: red;">注：地区下面没有社区不能启用网关</span></td>
              </tr>
              <tr>
                <td><div>备注：</div></td>
                <td colspan="5"><div><input style="width: 740px" name="remark" class="form-control" value="${area.remark}"/></div></td>
              </tr>
           </table>
         </form>
               <!-- 详情不显示按钮 -->
	           <div class="modal-footer" id="areasaveFormSubmit">
		         <!--操作按钮 -->
		            <input type="button" class="btn btn-primary sure" value="确定"/> 
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
	   </div>
 </div>
</body>
</html>