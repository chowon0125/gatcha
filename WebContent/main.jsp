<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script>
        $(document).ready(function(){
            $('a#reset').click(function(){
                alert('결과 초기화가 완료되었습니다.');
            })
        })

    </script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>임시 메인페이지</h1><br>
    메뉴<br><br>
    <a href='./charServlet?kyarl=playGatcha'>뽑기</a><br>
    <a href='./result.jsp'>결과보기</a><br>
    <a id='reset' href='./charServlet?kyarl=reset'>결과 초기화</a><br>
    <a id='rate' href='./rate.jsp'>확률고지</a><br>
    <a id='pickup' href='./pickupM.jsp'>픽업캐릭터 조회</a><br>
</body>
</html>