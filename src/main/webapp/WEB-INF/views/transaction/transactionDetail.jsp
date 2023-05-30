<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/title_.png" type="image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/style.js"></script>
<script type="text/javascript">
	function move(num){
		var blank = document.getElementById("blankF");
		var form = document.createElement("form");
		form.setAttribute("method","post");
		form.setAttribute("action","/nftDetail");
		blank.appendChild(form);
		var input = document.createElement("input");
		input.setAttribute("type","text");
		input.setAttribute("name","nftNum");
		input.setAttribute("value", num);
		form.appendChild(input);
		form.submit();
	}
	
	function copyTrID(){
		const obj = document.getElementById("need2Copy");
		window.getSelection().removeAllRanges();
		
		let range = document.createRange();
		range.selectNode(document.getElementById("need2Copy")); // 복사할 이미지 영역 선택 
		window.getSelection().addRange(range); 
		document.execCommand('copy');
		alert('트랜잭션 해시가 복사되었습니다.');
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
            <div class="tooltip" style="top: 510px; left: 671px;">
                <p class="tooltiptext tooltiptext_top tooltiptext_tran_sub_page_2">
                    <span>“블록 상세정보 조회하기” 클릭 시 해당 트랜잭션이 담긴 블록의 상세정보 페이지로 이동합니다.</span>
                </p>
            </div>
<%--            nftNum이 없다...--%>
<%--            <c:if test="${data.nftNum!=0 && (data.fcn eq 'CREATE_NFT' || data.fcn eq 'READ_NFT')}">--%>
<%--	            <div class="tooltip" style="top: 774px; left: 790px;">--%>
<%--	                <p class="tooltiptext tooltiptext_top tooltiptext_tran_sub_page_3">--%>
<%--	                    <span>“NFT 상세정보 조회하기” 클릭 시 해당 NFT의 상세정보 페이지로 이동합니다.</span>--%>
<%--	                </p>--%>
<%--	            </div>--%>
<%--	        </c:if>--%>
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
                    <li><a href="${pageContext.request.contextPath}/blockList">NFT 블록</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/tranList">NFT 트랜잭션</a></li>
                </ul>
                <div class="block_tran_list transaction_list">
                    <div class="block_tran_list_box">
                        <p class="block_tran_sub_title"><span>트랜잭션</span>은 더 이상 나눠질 수 없는 작업의 최소 단위이며, 어떠한 상태를 변화시키는 것을 의미합니다. 'NFT'를 '발행'하거나 '조회'하면 'NFT 트랜잭션'이 생성됩니다.</p>
                        <div class="tblwrap_basic">
                            <table>
                                <colgroup>
                                    <col style="width: 30%">
                                    <col style="width: 70%">
                                </colgroup>
                                <tbody>
<%--                                    <tr>--%>
<%--                                        <th>트랜잭션 블록번호</th>--%>
<%--                                        <td>${data.blockNumber}</td>--%>
<%--                                    </tr>--%>
                                    <tr>
                                        <th>트랜잭션이 담긴 블록 번호</th>
                                        <td>${blData.number}
                                            <a href="${pageContext.request.contextPath}/blockDetail?blNum=${blData.number}" class="more_btn">블록 상세정보 조회하기</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>트랜잭션 생성 시각</th>
                                        <td>${data.insertedAt}</td>
                                    </tr>
                                    <tr>
                                        <th>트랜잭션 해시</th>
                                        <td>
                                        	<span id="need2Copy">${data.hash}</span>
                                        	<a class="id_copy_2" onclick="copyTrID()">
                                        		복사
                                        		<div class="tooltip" style="top: 50px; left: 50%; transform: translateX(-50%);">
                                                    <p class="tooltiptext tooltiptext_top tooltiptext_block_sub_page">
                                                        <span>“복사” 버튼 클릭 시 “트랜잭션 해시”를 클립보드에 복사합니다.<br>복사된 “트랜잭션 해시”는 상단의 검색창에서 활용할 수 있습니다.</span>
                                                    </p>
                                                </div>
                                        	</a>
                                        </td>
                                    </tr>
<%--                                    <tr>--%>
<%--                                        <th>트랜잭션이 담긴 블록 번호</th>--%>
<%--                                        <td>${blData.number}--%>
<%--                                        	<a href="${pageContext.request.contextPath}/blockDetail?blNum=${blData.number}" class="more_btn">블록 상세정보 조회하기</a>--%>
<%--                                        </td>--%>
<%--                                    </tr>--%>
                                    <c:if test="${data.fcn eq 'Register_NFT' || data.fcn eq 'Read_NFT'}">
                                    <tr>
                                        <th>트랜잭션 명</th>
                                        <td>${data.fcn}</td>
                                    </tr>
                                    <tr>
                                        <th>NFT ID</th>
                                        <td>${data.nftId}</td>
                                    </tr>
                                    <tr>
                                        <th>
                                            트랜잭션 데이터
                                            <div class="tooltip" style="top: 59%; left: -21px;">
                                                <p class="tooltiptext tooltiptext_top tooltiptext_tran_sub_page_4">
                                                    <span>트랜잭션이 가지고 있는 데이터입니다. 트랜잭션 명에 따라 가지고 있는 데이터는 다를 수 있습니다.</span>
                                                </p>
                                            </div>
                                        </th>
                                        <td>
                                            <pre style="height: auto; width: 920px; overflow-x: auto; line-height: 20px;">${data.writeSet}</pre>
                                        </td>
                                    </tr>
                                    </c:if>
<%--                                    nftNum 관련 컬럼 없음 주석 처리--%>
<%--                                    <c:if test="${data.fcn eq 'Register_NFT' || data.fcn eq 'Read_NFT'}">--%>
<%--	                                    <tr>--%>
<%--	                                        <th>NFT ID</th>--%>
<%--                                       		<td>${fn:toUpperCase(data.nftId)}--%>
<%--                                       			<a href="${pageContext.request.contextPath}/nftDetail?nftNum=${data.nftNum}" class="more_btn popup_btn">NFT 상세정보 조회하기</a>--%>
<%--                                       		</td>--%>
<%--	                                    </tr>--%>
<%--                                    </c:if>--%>
<%--                                    <c:if test="${!empty data.writeSet}">--%>
<%--                                    	<tr>--%>
<%--	                                        <th>--%>
<%--	                                        	트랜잭션 데이터--%>
<%--	                                        	<div class="tooltip" style="top: 59%; left: -21px;">--%>
<%--	                                                <p class="tooltiptext tooltiptext_top tooltiptext_tran_sub_page_4">--%>
<%--	                                                    <span>트랜잭션이 가지고 있는 데이터입니다. 트랜잭션 명에 따라 가지고 있는 데이터는 다를 수 있습니다.</span>--%>
<%--	                                                </p>--%>
<%--	                                            </div>--%>
<%--	                                        </th>--%>
<%--	                                        <td>--%>
<%--	                                            <pre style="height: auto; width: 920px; overflow-x: scroll; line-height: 20px;">${data.writeSet}</pre>--%>
<%--	                                        </td>--%>
<%--	                                    </tr>--%>
<%--                                    </c:if>--%>
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