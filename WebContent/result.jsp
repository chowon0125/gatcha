<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored='false'%>
<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script>
        $(document).ready(function(){
           lookUp("recent");
           $('button#recent').click(function(){
                lookUp("recent");
           })
           $('button#grade').click(function(){
                lookUp("grade");
           })
           $('button#pickup').click(function(){
                lookUp("pickup");
           })
           
        })

        function lookUp(comporator){
            $.ajax({
                type:"post",
                async:true,
                url:"http://localhost:8090/gatcha/charServlet",
                dataType:"text",
                data:"kyarl=result&comporator="+comporator,
                success: function(data, textStatus){
                    if(data!="no_data"){
                        var jsonStr = data;
                        var jsonInfo = JSON.parse(jsonStr);
                        var output = "";
                        output += "<table border='solid'><tr align='center' bgcolor='lightblue'><th>이미지</th><th>이름</th><th>등급</th></tr>";
                        for(var i in jsonInfo.character){
                            output += "<tr align='center'><td>" + jsonInfo.character[i].imgpath + "</td>";
                            output += "<td>" + jsonInfo.character[i].name + "</td>";
                            output += "<td>" + jsonInfo.character[i].grade + "</td></tr>";
                        }
                        output += "</table>";
                        $('#view').html(output);
                    }else{
                        $('#view').text("결과가 없습니다.");
                    }
                },
                error:function(data,textStatus){
                    alert("에러가 발생했습니다.");
                },
                complete:function(data,textStatus){}
	        });
        }
    </script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <button id='recent'>최근순</button><button id='grade'>등급순</button><button id='pickup'>픽업캐릭터</button>
    <div id='view'>

    </div>
</body>
</html>