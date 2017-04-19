<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
   $(function(){
	   //省联动
// 	   $("#neighborhoodssaveForm .province").on("change",function(){
// 		   var province = $(this).val();
// 		   var url =$path+"/area/getAreaList.do";
// 		   $.post(url,"province="+province,function($data){
// 			   $(".city").children().remove();
// 			   $.each($data,function(i,obj){
// 				   $("#neighborhoodssaveForm  .city").append("<option areaNum='"+obj.areaNum+"' value='"+obj.id+"'>"+obj.city+"</option>");
// 				   $("#neighborhoodssaveForm  .areaNum").val($data[0].areaNum);
// 			   })
// 		   });
// 	   })
	   //市联动
	   $("#neighborhoodssaveForm .neibName").on("change",function(){
		  $("#neighborhoodssaveForm [name='neiborFlag']").val( $("#neighborhoodssaveForm .neibName option:selected").attr("title"));
	   })
	    $("#neighborhoodssaveForm [name='neiborFlag']").val($("#neighborhoodssaveForm .neibName option:selected").attr("title"));
	   //时间控件
	     $(".datepicker").datepicker();
   })
   
  
</script>
<div>
	   <div>
		 <form id="neighborhoodssaveForm" action="">
		 <input type="hidden" name="id" value="${neighborhoods.id}"/>
		 <input type="hidden" name="parentId" value="${neighborhoods.parentId}"/>
		 <input type="hidden" name="neiborFlag" value=""/>
           <table>
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>社区名称：</div></td>
                <td><div>
                          <select class="form-control neibName" name="neibName">
	                             <option value="">--请选择-- </option>
	                             <c:forEach items="${listMap}" var="map">
<%-- 	                                 <c:forEach items="${map.value}" var="keyMap"> --%>
	                                    <option title="${map.fneibor_flag}" <c:if test="${map.fneib_name eq neighborhoods.neibName}">selected="selected"</c:if> value="${map.fneib_name}">
	                                      ${map.fplatform_name}&nbsp;&nbsp;${map.fneib_name}&nbsp;&nbsp;${map.fneibor_flag}
	                                    </option>
<%-- 	                                 </c:forEach> --%>
	                             </c:forEach>
	                     </select>
<%--                     <input name="neibName" value="${neighborhoods.neibName}" maxlength="20" <c:if test="${neighborhoods.id!=null}">readonly="readonly"</c:if> placeholder="添加成功不能修改"  title="社区名称不能为空" class="form-control required"/> --%>
                    </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>呼叫号码：</div></td>
                <td><div><input name="neibNum" value="${neighborhoods.neibNum}" maxlength="5" title="呼叫号码不能为空且为5位整数" class="form-control required number"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>社区地址：</div></td>
                <td><div><input name="address" value="${neighborhoods.address}" title="社区地址不能为空" class="form-control required"/></div></td>
              </tr>
               <tr>
                <td><div class="firstFont">开工日期：</div></td>
                <td><div class="input-append date">
                     <input name="startBuildDateStr" value="${neighborhoods.startBuildDateStr}" class="form-control datepicker" readonly="readonly"/>
                 </div></td>
                <td><div class="leftFont">竣工日期：</div></td>
                <td><div><input name="endBuildDateStr" value="${neighborhoods.endBuildDateStr}" class="form-control datepicker" readonly="readonly"/></div></td>
                <td><div class="leftFont">使用日期：</div></td>
                <td><div><input name="useDateStr" value="${neighborhoods.useDateStr}" class="form-control datepicker" readonly="readonly"/></div></td>
              </tr>
               <tr>
                <td><div class="firstFont">总占地面积(㎡)：</div></td>
                <td><div><input name="totalArea" value="${neighborhoods.totalArea}" title="总占地面积输入为数字类型" class="form-control"/></div></td>
                <td><div class="leftFont">总建筑面积(㎡)：</div></td>
                <td><div><input name="totalBuildArea" value="${neighborhoods.totalBuildArea}"  title="总建筑面积输入为数字类型" class="form-control"/></div></td>
                <td><div class="leftFont">总商业面积(㎡)：</div></td>
                <td><div><input name="totalBussnisArea" value="${neighborhoods.totalBussnisArea}" title="总商业面积输入为数字类型" class="form-control"/></div></td>
              </tr>
               <tr>
                <td><div class="firstFont">承建商：</div></td>
                <td><div><input name="contractor" value="${neighborhoods.contractor}" class="form-control"/></div></td>
                <td><div class="leftFont">绿化率（%）：</div></td>
                <td><div><input name="greeningRate" value="${neighborhoods.greeningRate}" title="绿化率为数字类型" class="form-control number"/></div></td>
                <td><div class="leftFont">容积率（%）：</div></td>
                <td><div><input name="plotRatio" value="${neighborhoods.plotRatio}" title="容积率为数字类型" class="form-control number"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">备注：</div></td>
                <td colspan="5"><div><input maxlength="100" style="width: 720px" name="remark" class="form-control" value="${neighborhoods.remark}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">是否启用网关：</div></td>
                 <td><div>
                    <select name="createSipNum" class="form-control">
                      <option value="" <c:if test="${neighborhoods.createSipNum==null or neighborhoods.createSipNum==''}">selected="selected"</c:if> >否</option>
                      <option value="2" <c:if test="${neighborhoods.createSipNum=='2'}">selected="selected"</c:if>>是</option>
                    </select>
                 </div></td>
	                 <td><div class="leftFont">配置秘钥：</div></td>
	                 <td><div>
	                     <select name="useKey" class="form-control">
	                         <option value="0" <c:if test="${neighborhoods.useKey=='0'}">selected="selected"</c:if> >不启用</option>
	                          <option value="2" <c:if test="${neighborhoods.useKey=='2'}">selected="selected"</c:if>>启用</option>
	                    </select>
	                 </div></td>
	                 <td><div class="leftFont">电话：</div></td>
                     <td><div><input name="phone" value="${neighborhoods.phone}"  placeholder="格式：020-88888888" class="form-control"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>