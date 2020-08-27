<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
	<script>
		$(document).ready(function(){
			init();
		})
		function init(){
			$('td > img').click(function(){
				var name = $(this).attr('class');
				window.open("./inform/"+name+".html","","status=no,menubar=no,width=500,height=500");
			})
		}
	</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border=1>
		<tr align='center'>
			<c:forEach var="member" items="${result }">
				<td width='100' height='100'><img
					src='./img/${member.imgPath }.png' class='${member.imgPath }'></td>
			</c:forEach>
		</tr>
		<tr align='center'>
			<c:forEach var="member" items="${result }">
				<c:choose>
					<c:when test="${member.grade == 1 }">
						<td width='100' height='20' bgcolor='lightblue'>★☆☆</td>
					</c:when>
					<c:when test="${member.grade == 2 }">
						<td width='100' height='20' bgcolor='yellow'>★★☆</td>
					</c:when>
					<c:when test="${member.grade == 3 }">
						<td width='100' height='20' bgcolor='red'>★★★</td>
					</c:when>
				</c:choose>
			</c:forEach>
		</tr>
		<tr align='center'>
			<c:forEach var="member" items="${result }">
				<td width='100' height='20'>${member.name }</td>
			</c:forEach>
		</tr>
	</table>
	<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='./main.jsp'><img src='./img/cancel.png'></a>
	&nbsp;&nbsp;&nbsp;
	<a href='./charServlet?kyarl=playGatcha'><img src='./img/onemore.png'></a>
</body>
</html>