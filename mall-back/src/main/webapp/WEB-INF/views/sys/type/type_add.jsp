<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../includes/importer.jsp"%>
<!doctype html>
<html>
<head>
    <ht:head/>
    <script type="text/javascript">
    $().ready(function() {
    	//获取来源地址
	 	var url = document.referrer;
	 	$("#backUrl").val(url);
	});
    </script>
</head>
<body>


<div id="content">
<!-- forms -->
<div class="box">
    <!-- box / title -->
    <div class="title">
        <h5><c:if test="${method == 'add'}">添加</c:if><c:if test="${method == 'edit'}">修改</c:if><c:if test="${sysType.type == 1}">商户分类</c:if><c:if test="${sysType.type == 2}">商品分类</c:if><c:if test="${pName == 0}">节点类型</c:if></h5>
    </div>
    <!-- end box / title -->
    <form:form method="post" id="fm" commandName="sysType" htmlEscape="true" acceptCharset="utf-8" cssClass="required-validate">
        <form:hidden path="pId" />
		<form:hidden path="type" />
		 <input type="hidden" id="backUrl" name="backUrl" />
        <div class="form">
            <div class="fields">
            	<c:if test="${method == 'add'}">
                <div class="field">
                    <div class="label noinput">ID：</div>
                    <div class="input">自动生成</div>
                </div>
                </c:if>
                <c:if test="${method == 'edit'}">
                <div class="field">
                    <div class="label noinput">ID：</div>
                    <div class="input">${sysType.id}</div>
                </div>
                </c:if>
                <div class="field">
                    <div class="label noinput">类型：</div>
                    <div class="input"><c:if test="${empty sysType.type}"><input type="text" name="type" id="type" value="${type}" /></c:if><c:if test="${sysType.type == 1}">商户</c:if><c:if test="${sysType.type == 2}">商品</c:if></div>
                </div>	
                <div class="field">
                    <div class="label noinput">上级<c:if test="${sysType.type == 1}">商户</c:if><c:if test="${sysType.type == 2}">商品</c:if>：</div>
                    <div class="input"><c:if test="${pName == 0}">无</c:if><c:if test="${pName != 0}">${pName}</c:if></div>
                </div>			
                <div class="field">
                    <div class="label">
                        <label for="name" class="req">名称：</label>
                    </div>
                    <div class="input"><!-- small medium large -->
                        <form:input path="name" cssClass="small required min-length-0 max-length-20" maxlength="20" />
                    	<span class="error" id="advice-required-name" style="display:none"></span>
                        <span class="error" id="advice-min-length-name" style="display:none"></span>
                        <span class="error" id="advice-max-length-name" style="display:none"></span>
                        <span class="error" id="advice-server-name" style="display:none"></span>
                    </div>
                </div>
                
                <div class="field">
                    <div class="label">
                        <label for="isValid" class="req">是否显示:</label>
                    </div>
                    <div class="select">
                        <select name="isValid" class="required">
			                <c:forEach items="${isValidMap}" var="item">
			                	    <option value="${item.key}" <c:if test="${sysType.isValid == item.key}">selected="selected"</c:if>>${item.value }</option>
			                </c:forEach>
		                </select>
                        <span class="error" id="advice-required-isValid" style="display:none"></span>
                    </div>
                </div>
                
                <div class="buttons">
                    <div class="highlight">
                        <input type="submit" name="submit.highlight" value="提交" />
                    </div>
                    <input type="reset" name="reset" value="重置" />
                    <a href="javascript:history.go(-1)" class="btnAnchor">返回</a>
                </div>
            </div>
        </div>
    </form:form>
</div>
<!-- end forms -->
</div>


</body>
</html>