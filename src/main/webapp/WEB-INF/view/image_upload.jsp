<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <form action="${pageContext.request.contextPath}/ImageUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="Go!" />
    </form>
    <c:if test="${requestScope.image != null}">
        <hr />
        <img src="data:${requestScope.imageContentType};base64,${requestScope.image}" width="${requestScope.imageWidth}" height="${requestScope.imageHeight}" />
    </c:if>
</div>