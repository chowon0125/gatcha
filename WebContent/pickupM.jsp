<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <style>
        .disable {
            font-style: italic;
            font: bold;
        }
    </style>
    <script src="http://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script>
        var size;

        $(document).ready(function () {
            a('grade');
            $('button#b').click(function () {
                b();
            })
            $('button#log').click(function () {
                makeJSON();
            })
            $('button#update').click(function () {
                update();
            })
            $('button#abcB').click(function () {
                a('abc');
            })
            $('button#gradeB').click(function () {
                a('grade');
            })
            $('button#pickupB').click(function () {
                a('pickup');
            })
            $('button#pickableB').click(function () {
                a('pickable');
            })
        })

        function a(p_comporator) {
            var comporator = p_comporator;
            $.ajax({
                type: "post",
                async: true,
                url: "http://localhost:8090/gatcha/charServlet",
                dataType: "text",
                data: "kyarl=pickupM&comporator="+comporator,
                success: function (data, textStatus) {
                    if (data != "no_data") {
                        size = 0;
                        var jsonStr = data;
                        var jsonInfo = JSON.parse(jsonStr);
                        var output = "";
                        output += "<table border='solid'><tr align='center' bgcolor='lightblue'><th>가능</th><th>픽업</th><th>이미지</th><th>이름</th><th>등급</th></tr>";
                        for (var i in jsonInfo.character) {
                            size++;
                            if (jsonInfo.character[i].pickable == 1) {
                                output += "<tr id='idx_" + i + "' align='center'><td><input class='pickable' type='checkbox' checked='checked'></td>";
                            } else {
                                output += "<tr id='idx_" + i + "' class='disable' align='center'><td><input class='pickable' type='checkbox'></td>";
                            }
                            if (jsonInfo.character[i].pickup == 1) {
                                output += "<td><input class='pickup' type='checkbox' checked='checked'></td>";
                            } else {
                                output += "<td><input class='pickup' type='checkbox'></td>";
                            }
                            output += "<td class='imgpath'>" + jsonInfo.character[i].imgpath + "</td>";
                            output += "<td class='name'>" + jsonInfo.character[i].name + "</td>";
                            output += "<td class='grade'>" + jsonInfo.character[i].grade + "</td></tr>";
                        }
                        output += "</table>";
                        console.log(size);
                        $('#view').html(output);
                    } else {
                        $('#view').text("결과가 없습니다.");
                    }
                },
                error: function (data, textStatus) {
                    alert("에러가 발생했습니다.");
                },
                complete: function (data, textStatus) { }
            });
        }

        function b() {
            for (let i = 0; i < size; i++) {
                if ($('#idx_' + i + ' .pickable').is(":checked")) {
                    $('#test').append('1');
                } else {
                    $('#test').append('0');
                }
                if ($('#idx_' + i + ' .pickup').is(":checked")) {
                    $('#test').append('1');
                } else {
                    $('#test').append('0');
                }
                $('#test').append($('#idx_' + i + ' .name').text());
                $('#test').append($('#idx_' + i + ' .grade').text());
                $('#test').append($('#idx_' + i + ' .imgpath').text());
                $('#test').append('<br>');
            }
        }

        function makeJSON() {
            var result = '';
            for (let i = 0; i < size; i++) {
                if ($('#idx_' + i + ' .pickup').is(":checked")) {
                    result += '{"pickup":"1",';
                } else {
                    result += '{"pickup":"0",';
                }
                if ($('#idx_' + i + ' .pickable').is(":checked")) {
                    result += '"pickable":"1",';
                } else {
                    result += '"pickable":"0",';
                }
                result += '"name":"' + $('#idx_' + i + ' .name').text() + '",';
                result += '"grade":"' + $('#idx_' + i + ' .grade').text() + '",';
                result += '"imgpath":"' + $('#idx_' + i + ' .imgpath').text() + '"}#';
            }
            result = result.substr(0, result.length - 1);
            console.log(result);
            return result;
        }

        function update(){
            var jsondata = makeJSON();
            $.ajax({
                type: "post",
                async: true,
                url: "http://localhost:8090/gatcha/charServlet",
                dataType: "text",
                data: "kyarl=update&jsondata="+jsondata,
                success: function (data, textStatus) {
                    if (data = "success") {
                        alert('성공');
                    } else {
                        alert('실패');
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
    <div id='menu'>
        <button id='abcB'>이름순</button>
        <button id='gradeB'>등급순</button>
        <button id='pickupB'>픽업순</button>
        <button id='pickableB'>한정순</button>
    </div>
    <div id='view'>

    </div>
    <div id='test'>

    </div>
    <button id='b'>b함수</button>
    <button id='log'>로그</button>
    <button id='update'>업데이트</button>
</body>

</html>