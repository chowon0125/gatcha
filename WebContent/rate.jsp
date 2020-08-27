<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
    <script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script>
        $(document).ready(function () {
            loadData();
        })

        function loadData() {
            $.ajax({
                type: "post",
                async: true,
                url: "http://localhost:8090/gatcha/charServlet",
                dataType: "text",
                data: "kyarl=rate",
                success: function (data, textStatus) {
                    if (data != "no_data") {
                        var jsonStr = data;
                        var jsonInfo = JSON.parse(jsonStr);
                        var suk = jsonInfo.suk;
                        var grade3 = jsonInfo.grade3;
                        var grade2 = jsonInfo.grade2;
                        var grade1 = jsonInfo.grade1;
                        var total = (grade3*1) + (grade2*1) + (grade1*1);
                        var rate3 = grade3/total;
                        var rate2 = grade2/total;
                        var rate1 = grade1/total;
                        var spend = total * 150;
                        $('#view').html('여신석 : '+suk+'<br>가챠횟수 : '+total
                                        +'<br>3성 : '+grade3+'<br>2성 : '+grade2
                                        +'<br>1성 : '+grade1+'<br>쓴 쥬얼 : '+spend
                                        );
                    } else {
                        $('#view').text('저장된 결과가 없습니다.');
                    }
                },
                error: function (data, textStatus) {
                    alert("에러가 발생했습니다.");
                },
                complete: function (data, textStatus) { }
            });
        }

    </script>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>

<body>
    <div id='view'>

    </div>
</body>

</html>