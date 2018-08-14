<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
<body>
<h2>Hello World!</h2>

SpringMVC上传图片
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="springmvc上传图片" />
</form>

富文本上传图片
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本上传图片" />
</form>

跨域测试:
<div>
    name : <input id = "name" value = "name">
    pwd : <input id = "password" value = "password">
    <br>
    <button onclick = "login()">submit</button>
</div>
</body>
<script>
    function login(){
        var xmlhttp = new XMLHttpRequest();
        var userneme = document.getElementById('name').value;
        var password = document.getElementById('password').value;
        xmlhttp.open('post', 'http://localhost:8080/user/login.do?username=' + userneme + "&password=" + password, true);
        xmlhttp.send();
    }
</script>
</html>
