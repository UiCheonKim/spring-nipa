<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트 관리자 페이지</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/title_.png" type="image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/a_common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/a_style.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/a_style.js"></script>
<style type="text/css">
	a:hover{cursor: pointer;}
	.alertText{font-size: 14px; color: red; font-weight: normal; margin-top: 10px; text-align: left;}
</style>
<script type="text/javascript">
	function keyPress(e){
		if (e.keyCode == 13) {
			login();
		}
	}
	
	function login(){
		const form = document.getElementById("loginForm");
		const id = document.getElementById("id_input");
		const pw = document.getElementById("pw_input");
		const alertArea = document.querySelector("#loginAlertArea");
		
		if(id.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "아이디를 입력해주세요.";
			id.value = "";
			id.focus();
		} else if(pw.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "비밀번호를 입력해주세요.";
			pw.value = "";
			pw.focus();
		} else{
			form.submit();
		}
	}
</script>
</head>
<body>

	<div class="login_page">
        <div class="login">
            <div class="login_wrap">
                <div class="login_title">
                    <p>NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트</p>
                    <p>관리자 로그인</p>
                </div>
                <form action="${pageContext.request.contextPath}/admin/loginProc" method="POST" id="loginForm">
                    <legend> 로그인</legend>
                    <div class="input_box">
                        <label for="id_input" class="id_box">
                            <input type="text" name="userId" id="id_input" placeholder="ID">
                        </label>
                        <label for="pw_input" class="pw_box">
                            <input type="password" name="userPw" id="pw_input" placeholder="PW" onkeypress="keyPress(event)">
                        </label>
                    </div>
                    <div class="input_box_btn" onclick="login()">
                        <a class="login_btn">로그인</a>
                    </div>
                </form>
                <div id="loginAlertArea"></div>
            </div>
        </div>
    </div>
	
</body>
</html>