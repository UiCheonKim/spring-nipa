<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/title_.png" type="image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/style.js"></script>
</head>
<body>
	<jsp:include page="${pageContext.request.contextPath}/header"></jsp:include>
	<div id="container">
        <div class="error">
            <div class="error_box">
                <h3>죄송합니다. 요청하신 페이지를 찾을 수 없습니다.</h3>
                <span>페이지의 주소가 잘못되었거나, 변경 혹은 삭제되었을 수 있습니다.</span>
                <p><a href="${pageContext.request.contextPath}/">메인 페이지로 돌아가기</a></p>
            </div>
        </div>
    </div>
    <jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
</body>
</html>