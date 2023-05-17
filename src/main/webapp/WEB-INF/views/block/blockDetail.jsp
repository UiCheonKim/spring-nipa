<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script type="text/javascript">
	function copyBlID(){
		const obj = document.getElementById("need2Copy");
		window.getSelection().removeAllRanges();
		
		let range = document.createRange();
		range.selectNode(document.getElementById("need2Copy")); // 복사할 이미지 영역 선택 
		window.getSelection().addRange(range); 
		document.execCommand('copy');
		alert('블록 해시가 복사되었습니다.');
		window.getSelection().removeAllRanges();
	}
</script>
</head>
<body onpageshow="openGuide()">

	<div class="quick_menu">
        <a href="#none" class="quick_menu_icon">
            <span>이용 가이드</span>
            <img src="${pageContext.request.contextPath}/resources/images/quick_menu.png" alt="">
        </a>
    </div>
    <div class="tooltip_close">
        <img src="${pageContext.request.contextPath}/resources/images/tooltip_close.png" alt="">
    </div>
    <div class="info_ballon">
        <div class="content_center">
            <div class="tooltip" style="top: 677px; left: 233px;">
                <p class="tooltiptext tooltiptext_bottom">
<%--                    스팬이 안없어짐 이슈--%>
<%--                    <span>“자세히 보기” 버튼 클릭 시 블록에 담긴 트랜잭션 목록을 하단에 표출합니다.</span>--%>
                </p>
            </div>
        </div>
    </div>

	<jsp:include page="${pageContext.request.contextPath}/header"></jsp:include>
	<script type="text/javascript">
		function aboutHeader(){
			var child = document.getElementById("his");
			child.setAttribute("class","menu bold");
		}
		aboutHeader();
	</script>
	<div id="container">
		<div class="nav_title">
			<div class="content_center">
            	<h2 class="sub_top_title">NFT 상세 이력</h2>
            </div>
        </div>
        <div class="content_center">
            <div class="content">
                <ul class="tabBtn clearFix">
                    <li class="active"><a href="${pageContext.request.contextPath}/blockList">NFT 블록</a></li>
                    <li><a href="${pageContext.request.contextPath}/tranList">NFT 트랜잭션</a></li>
                </ul>
                <div class="block_tran_list">
                    <div class="block_tran_list_box">
                        <p class="block_tran_sub_title"><span>블록</span>은 데이터를 저장하는 단위이며, 일정기간 동안 생성된 트랜잭션을 저장하는 공간입니다. 'NFT 발행' 또는 'NFT 조회' 트랜잭션이 생성되면 'NFT 블록' 에 저장됩니다.</p>
                        <div class="tblwrap_basic">
                            <table>
                                <colgroup>
                                    <col style="width: 30%">
                                    <col style="width: 70%">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>블록 번호</th>
                                        <td>${data.number}</td>
                                    </tr>
                                    <tr>
                                        <th>블록 생성 시각</th>
                                        <td>${data.insertedAt}</td>
                                    </tr>
                                    <tr>
                                        <th>블록 해시</th>
                                        <td>
                                        	<span id="need2Copy">${data.hash}</span>
                                        	<a class="id_copy_2" onclick="copyBlID()">
                                        		복사
                                        		<div class="tooltip" style="top: -76px; left: 50%; transform: translateX(-50%);">
                                                    <p class="tooltiptext tooltiptext_bottom tooltiptext_block_sub_page">
<%--                                                        스팬 주석 처리 사라지지 않은 이슈...--%>
<%--                                                        <span>“복사” 버튼 클릭 시 “블록 해시”를 클립보드에 복사합니다.<br>복사된 “블록 해시”는 상단의 검색창에서 활용할 수 있습니다.</span>--%>
                                                    </p>
                                                </div>
                                        	</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>이전 블록 해시</th>
                                        <td>
                                        	<span id="need2Copy">
                                        		<c:if test="${data.number == 1}">
                                        			이전 블록 데이터가 존재하지 않습니다.
                                        		</c:if>
                                        		<c:if test="${data.number != 1}">
                                        			<a class="prevBlockLink" href="${pageContext.request.contextPath}/prevBlock?h=${data.parentHash}">${data.parentHash}</a>
                                        		</c:if>
                                        	</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>블록 크기</th>
                                        <td>${data.size} KB</td>
                                    </tr>
                                    <tr>
                                        <th>블록에 담긴 트랜잭션 수</th>
                                        <td>${data.txCount}<a class="more_btn block_more_btn">자세히 보기</a></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="tblwrap_basic more_table">
                        	<div class="tooltip" style="top: 442px; left: 638px;">
                                <p class="tooltiptext tooltiptext_bottom tooltiptext_block_sub_page_2">
                                    <span>“트랜잭션 상세정보 조회하기” 클릭 시 해당 블록에 담긴 트랜잭션의 상세정보 페이지로 이동합니다.</span>
                                </p>
                            </div>
                            <table>
                                <colgroup>
                                    <col style="width: 10%">
                                    <col style="width: 19%">
                                    <col style="width: 71%">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>트랜잭션 번호</th>
                                        <th>트랜잭션 생성 시각</th>
                                        <th>트랜잭션 해시</th>
                                    </tr>
                                    <c:forEach items="${trList}" var="list">
                                    	<tr>
	                                        <td>${list.blockNumber}</td>
	                                        <td>${list.insertedAt}</td>
	                                        <td>${list.hash}
	                                        	<a href="${pageContext.request.contextPath}/tranDetail?trNum=${list.hash}" class="another_list_search">트랜잭션 상세정보 조회하기</a>
	                                        </td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
</body>
</html>