<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
	<ht:head/>
</head>
<body>
<br/>
<div id="search-menu">
    <ul>
        <ht:menu-btn type="search"/>
        <ct:display model="typeitemparam_list" btn="add_btn">
        <ht:menu-btn text="添加参数" url="/websys/typeitemparam/add" type="add"/>
        <!-- 
        <ht:menu-btn text="添加商户参数" url="/websys/typeitemparam/shopAdd" type="add"/>
         -->
		</ct:display>
	</ul>
    <br style="clear: left" />
</div>
<div class="queryContainer" >
    <form name="queryForm" id="queryForm" action="?" method="get">
        <table>
            <tr>
            	<td width="70">分类名称：</td>
                <td width="300"><input type="text" name="typeName" value="${param.typeName}" class="txt" style="width:206px"/></td>
                <td width="100">规格参数名：</td>
                <td width="300"><input type="text" name="paramKey" value="${param.paramKey}" class="txt" style="width:206px"/></td>
            </tr>
            <tr>
            	<td width="70">商户名称：</td>
                <td width="300"><input type="text" name="storeName" value="${param.storeName}" class="txt" style="width:206px"/></td>
                 <td width="70">商户编号：</td>
                <td width="300"><input type="text" name="shopId" value="${param.shopId}" class="txt validate-number" style="width:206px"/></td>
                <td colspan="2">
                    <ct:btn type="search" />
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="container">
    <br/>
    <h3>商品分类参数列表</h3>

    <div class="mainbox">
        <c:if test="${not empty sysTypeItemParam}">

        <table class="datalist fixwidth">
            <tr>
               <!--  <th nowrap="nowrap" width="150">所属商户</th>  -->
                <th nowrap="nowrap" width="80">分类名称</th>
                <th nowrap="nowrap" width="100">参数名</th>
                <th nowrap="nowrap" width="100">参数值</th>
                <th nowrap="nowrap" width="80">参数类型</th>
                <th nowrap="nowrap" width="40">必填</th>
                <th nowrap="nowrap" width="40">参与检索</th>
                <th nowrap="nowrap" width="120">操作</th>
            </tr>

            <c:forEach items="${sysTypeItemParam.data}" var="item">
            <tr>
               <!--  <td nowrap="nowrap"  class="ellipsis">${item.storeName}</td>  -->
                <td nowrap="nowrap">${item.typeName}</td>
                <td nowrap="nowrap">${item.paramKey}</td>
                <td nowrap="nowrap">${item.paramValue}</td>
                <td nowrap="nowrap">${item.paramTypeName}</td>
                <td nowrap="nowrap">${item.requiredFlagName}</td>
                <td nowrap="nowrap">${item.searchFlagName}</td>
                <td nowrap="nowrap">
                <ct:display model="typeitemparam_list" btn="mod_btn">
                   	<a href="./edit?id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </ct:display>
                <ct:display model="typeitemparam_list" btn="del_btn">
                   	<a href="#" onclick="deleteItem('./delete/${item.id}');">删除</a>
                </ct:display>
                </td>
            </tr>
            </c:forEach>
        </table>

        <ht:page pageData="${sysTypeItemParam}" />

        </c:if>
        <c:if test="${empty sysTypeItemParam}">
        <div class="note">
            <p class="i">目前没有相关记录!</p>
        </div>
        </c:if>
    </div>

</div>

</body>
</html>