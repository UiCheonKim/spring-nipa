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
	
	function startInterval(){
		var interval = setInterval(updateBlList, 5000);
	}
	
	function updateBlList(){
		
		const pageNum = document.querySelector('#cur_page').value;
		const tb = document.querySelector('#tb');
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				var obj = JSON.parse(xmlhttp.responseText);
				
				tb.innerHTML = "";
				
				const outerTr = document.createElement("tr");
				const th1 = document.createElement("th");
				const th2 = document.createElement("th");
				const th3 = document.createElement("th");
				const th4 = document.createElement("th");
				
				th1.innerText = '블록 번호';
				th2.innerText = '블록 생성 시각';
				th3.innerText = '블록 해시';
				th4.innerText = '블록 크기';
				
				tb.appendChild(outerTr);
				outerTr.appendChild(th1);
				outerTr.appendChild(th2);
				outerTr.appendChild(th3);
				outerTr.appendChild(th4);
				
				for(data of obj){
					
					const tr = document.createElement("tr");
					const td1 = document.createElement("td");
					const td2 = document.createElement("td");
					const td3 = document.createElement("td");
					const td4 = document.createElement("td");
					
					const a1 = document.createElement("a");
					a1.setAttribute("href", "${pageContext.request.contextPath}/blockDetail?blNum="+data.inumberd);
					a1.innerText = data.number;
					td1.appendChild(a1);
					td1.setAttribute("class", "list_link");
					
					td2.innerText = data.insertedAt;
					
					const a2 = document.createElement("a");
					a2.setAttribute("href", "${pageContext.request.contextPath}/blockDetail?blNum="+data.number);
					a2.innerText = data.hash;
					td3.appendChild(a2);
					td3.setAttribute("class", "list_link");
					
					td4.innerText = data.size+" KB";
					
					tb.appendChild(tr);
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					
				} // inner for end
				
			}
		};
		
		xmlhttp.open("post","/updateBlList", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("pageNum="+pageNum);
		
	}
</script>
</head>
<body onpageshow="openGuide(), startInterval()">
	
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
            <div class="tooltip" style="top: 236px; left: 50%; transform: translateX(-50%);">
                <p class="tooltiptext tooltiptext_bottom tooltiptext_block_page">
                    <span>“NFT 블록”과 “NFT 트랜잭션”으로 분류되는 탭입니다.<br>“NFT 블록”을 클릭하면 하단에 블록 목록을 표출하고, “NFT 트랜잭션”을 클릭하면 하단에 트랜잭션 목록을 표출합니다.</span>
                </p>
                <span class="tooltiptext_two_buttom_left tooltiptext_block_page_two_bottom"></span>
            </div>
            <div class="tooltip" style="top: 393px; left: 244px;">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>“블록 해시”란 블록에 담긴 데이터와 이전 블록 등의 정보를 암호화한 값입니다.<br>또한, “블록 해시”는 블록의 고유 식별자 역할을 합니다. </span>
                </p>
            </div>
            <div class="tooltip" style="top: 551px; left: 0px;">
                <p class="tooltiptext tooltiptext_two_buttom_top tooltiptext_block_page_2">
                    <span>“블록 번호” 또는 “블록 해시”를 클릭하면 블록의 상세한 정보를 확인할 수 있는 페이지로 이동할 수 있습니다.</span>
                </p>
                <span class="tooltiptext_two_buttom_top_left"></span>
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
       		<div class="content sub_tab_box">
                <ul class="tabBtn clearFix">
                    <li class="active" onclick='location.href="${pageContext.request.contextPath}/blockList"'><a>NFT 블록</a></li>
                    <li onclick='location.href="${pageContext.request.contextPath}/tranList"'><a>NFT 트랜잭션</a></li>
                </ul>
                <div class="block_tran_list">
                    <div class="block_tran_list_box">
                        <p class="block_tran_sub_title"><span>블록</span>은 데이터를 저장하는 단위이며, 일정기간 동안 생성된 트랜잭션을 저장하는 공간입니다. 'NFT 발행' 또는 'NFT 조회' 트랜잭션이 생성되면 'NFT 블록' 에 저장됩니다.</p>
                        <div class="tblwrap_basic">
                            <table>
                                <colgroup>
                                    <col style="width: 15%">
                                    <col style="width: 20%">
                                    <col style="width: 55%">
                                    <col style="width: 10%">
                                </colgroup>
                                <tbody id="tb">
                                    <tr>
                                        <th>블록 번호</th>
                                        <th>블록 생성 시각</th>
                                        <th>블록 해시</th>
                                        <th>블록 크기</th>
                                    </tr>
                                    <c:forEach items="${list}" var="list">
                                    	<tr>
	                                        <td class="list_link"><a href="${pageContext.request.contextPath}/blockDetail?blNum=${list.number}">${list.number}</a></td>
	                                        <td>${list.insertedAt}</td>
	                                        <td class="list_link"><a href="${pageContext.request.contextPath}/blockDetail?blNum=${list.number}">${list.hash}</a></td>
	                                        <td>${list.size} KB</td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <input type="hidden" value="${currentPage}" id="cur_page">
                        </div>
                        <div class="page_wrap">
                            <div class="page_nation">
                                <c:choose>
									<c:when test="${beginPage <= 1}">
										<a class="page_first" href="${pageContext.request.contextPath}/blockList?pageNum=1">
											<img src="${pageContext.request.contextPath}/resources/images/arrow_first.png" alt="첫번째페이지로">
										</a>
										<a class="page_prev">
											<img src="${pageContext.request.contextPath}/resources/images/arrow_left.png" alt="이전페이지로">
										</a>
									</c:when>
									<c:otherwise>
										<a class="page_first" href="${pageContext.request.contextPath}/blockList?pageNum=1">
											<img src="${pageContext.request.contextPath}/resources/images/arrow_first.png" alt="첫번째페이지로">
										</a>
										<a class="page_prev" href="${pageContext.request.contextPath}/blockList?pageNum=${beginPage - 1}">
											<img src="${pageContext.request.contextPath}/resources/images/arrow_left.png" alt="이전페이지로">
										</a>
									</c:otherwise>
								</c:choose>
								<c:forEach begin="${beginPage}" end="${endPage}" var="page">
									<c:choose>
										<c:when test="${page == currentPage}">
											<a class="pageNum active">${page}</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath}/blockList?pageNum=${page}" class="pageNum">${page}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${endPage >= totalPageCount}">
										<a class="page_next">
		                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_right.png" alt="다음페이지로">
		                                </a>
		                                <a class="page_last" href="${pageContext.request.contextPath}/blockList?pageNum=${totalPageCount}">
		                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_last.png" alt="마지막페이지로">
		                                </a>
									</c:when>
									<c:otherwise>
										<a class="page_next" href="${pageContext.request.contextPath}/blockList?pageNum=${endPage + 1}">
		                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_right.png" alt="다음페이지로">
		                                </a>
		                                <a class="page_last" href="${pageContext.request.contextPath}/blockList?pageNum=${totalPageCount}">
		                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_last.png" alt="마지막페이지로">
		                                </a>
									</c:otherwise>
								</c:choose>	
                            </div>
                        </div>
                    </div>
                </div>
        	</div>
        </div>
    </div>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
	
</body>
</html>