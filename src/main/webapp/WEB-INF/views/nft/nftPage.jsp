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
<script type="text/javascript" defer>

	function startInterval(){
		// var firstInterval = setInterval(updateUpperNav, 10000);
		// var secondInterval = setInterval(updateBlData, 10000);
		// var thirdInterval = setInterval(updateTrData, 10000);
        var firstInterval = setInterval(updateUpperNav, 5000);
        var secondInterval = setInterval(updateBlData, 5000);
        var thirdInterval = setInterval(updateTrData, 5000);

	}
	
	function updateUpperNav(){
		const nft_count =document.querySelector('#nft_count');
		const read_count =document.querySelector('#read_count');
		const bl_count =document.querySelector('#bl_count');
		const tr_count =document.querySelector('#tr_count');
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				var obj = JSON.parse(xmlhttp.responseText);
				nft_count.innerText = obj.nftCnt;
				read_count.innerText = obj.readCnt;
				bl_count.innerText = (obj.blCnt).toLocaleString('ko-KR');
				tr_count.innerText = (obj.trCnt).toLocaleString('ko-KR');
			}
		};
		
		xmlhttp.open("post","/getNftPageUpdateData", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send();
		
	}
	
	function updateBlData(){
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				var obj = JSON.parse(xmlhttp.responseText);
				
				for(let i = 1; i < 6; i++){
					const refBlA_ = document.querySelector('#refBlA_'+i);
					refBlA_.setAttribute("href","${pageContext.request.contextPath}/blockDetail"+obj[i-1].blDto.id);
					refBlA_.innerText = '블록 번호 : '+obj[i-1].blDto.number;
                    // refBlA_.innerText = '블록 번호 : 코기1';
					const refSp1_ = document.querySelector('#refSp1_'+i);
					refSp1_.innerText = ' : '+obj[i-1].blDto.hash;
                    // refSp1_.innerText = ' : 코기2';
					const refSp2_ = document.querySelector('#refSp2_'+i);
					refSp2_.innerText = ' : '+obj[i-1].blDto.minerHash;
                    // refSp2_.innerText= ' : 코기3';
					const refSp3_ = document.querySelector('#refSp3_'+i);
					refSp3_.innerText = ' : '+obj[i-1].txCnt;
                    // refSp3_.innerText = ' : 코기4';

					const refSpTime_ = document.querySelector('#refSpTime_'+i);
					refSpTime_.innerText = obj[i-1].timeDiff+' 초 전';
                    // refSpTime_.innerText = '코기5 초 전';
					
					
				}
			}
		};
		
		xmlhttp.open("post","/getNftPageUpdateBlData", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send();
	}

	
	function updateTrData(){
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				var obj = JSON.parse(xmlhttp.responseText);
				
				for(let i = 1; i < 10; i++){
					
					const refTrA1_ = document.querySelector('#refTrA1_'+i);
					refTrA1_.setAttribute("href","${pageContext.request.contextPath}/tranDetail?trNum="+obj[i-1].id);
					refTrA1_.innerText = obj[i-1].blockNumber;
                    // refTrA1_.innerText = "웰시코기1";
					
					const refTrA2_ = document.querySelector('#refTrA2_'+i);
					refTrA2_.setAttribute("href","${pageContext.request.contextPath}/tranDetail?trNum="+obj[i-1].id);
					refTrA2_.innerText = obj[i-1].hash;
                    // refTrA2_.innerText = "웰시코기2";
					
				}
			}
		};
		
		xmlhttp.open("post","/getNftPageUpdateTrData", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send();
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
            <div class="tooltip" style="top: 268px; left: 185px;">
                <p class="tooltiptext tooltiptext_two_buttom">
                    <span>현재까지 발행된 NFT 수와 조회수입니다.</span>
                </p>
                <span class="tooltiptext_two_buttom_left"></span>
            </div>
            <div class="tooltip" style="top: 268px; left: 878px;">
                <p class="tooltiptext tooltiptext_two_buttom">
                    <span>현재까지 생성된 블록과 트랜잭션 수입니다.</span>
                </p>
                <span class="tooltiptext_two_buttom_left"></span>
            </div>
            <div class="tooltip" style="top: 453px; left: 159px;">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>1분당 생성되는 블록의 수를 확인할 수 있습니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 453px; left: 847px;">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>1분당 생성되는 트랜잭션의 수를 확인할 수 있습니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 722px; left: 137px;">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>가장 최근에 생성된 블록의 정보를 확인할 수 있습니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 983px; left: 34px;">
                <p class="tooltiptext tooltiptext_bottom tooltiptext_block">
                    <span>“블록 번호”를 클릭하면 해당 블록의 상세정보를 조회할 수 있는 페이지로 이동합니다. </span>
                </p>
            </div>
            <div class="tooltip" style="top:722px; left: 823px;">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>가장 최근에 생성된 트랜잭션의 정보를 확인할 수 있습니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 1095px; left: 575px;">
                <p class="tooltiptext tooltiptext_bottom tooltiptext_tran">
                    <span>“트랜잭션 번호”를 클릭하면 해당 트랜잭션의 상세정보를 조회할 수 있는 페이지로 이동합니다.</span>
                </p>
            </div>
        </div>
    </div>

	<jsp:include page="${pageContext.request.contextPath}/header"></jsp:include>
	<script type="text/javascript">
		function aboutHeader(){
			var child = document.getElementById("nft");
			child.setAttribute("class","menu bold");
		}
		aboutHeader();
	</script>
	<div id="container">
		<div class="nav_title">
			<div class="content_center">
            	<h2 class="sub_top_title">NFT 전체 현황</h2>
            </div>
        </div>
        <div class="content_center">
            <div class="content">
                <div class="nft_position_top">
                    <ul class="clearFix">
                        <li>
                            <p>NFT 콘텐츠 발행 수</p>
                            <p class="blue" id="nft_count">${nftCnt}</p>
                        </li>
                        <li>
                            <p>NFT 콘텐츠 조회 수</p>
                            <p class="blue" id="read_count">${totalCnt}</p>
                        </li>
                        <li>
                            <p>NFT 블록 수</p>
                            <p class="blue" id="bl_count">
                            	<fmt:formatNumber value="${blCnt}" pattern="#,###"/>
                            </p>
                        </li>
                        <li>
                            <p>NFT 트랜잭션 수</p>
                            <p class="blue" id="tr_count">
                            	<fmt:formatNumber value="${trCnt}" pattern="#,###"/>
                            </p>
                        </li>
                    </ul>
                </div>
                <div class="nft_position_middle">
                    <ul class="clearFix">
                        <li>
                            <div class="nft_position_title">
                                <p>시간당 NFT 블록(분)</p>
                            </div>
                            <div class="nft_position_content">
                                <p class="example_gra">
                                    <iframe src="https://nipanft.docuchain.kr:3000/d-solo/qQe2JeP7k/new-dashboard?orgId=1&refresh=5s&theme=light&panelId=2" width="100%" height="100%" frameborder="0"></iframe>
                                </p>
                            </div>
                        </li>
                        <li>
                            <div class="nft_position_title">
                                <p>시간당 NFT 트랜잭션(분)</p>
                            </div>
                            <div class="nft_position_content">
                                <p class="example_gra">
                                    <iframe src="https://nipanft.docuchain.kr:3000/d-solo/qQe2JeP7k/new-dashboard?orgId=1&refresh=5s&theme=light&panelId=4" width="100%" height="100%" frameborder="0"></iframe>
                                </p>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="nft_position_bottom">
                    <ul class="clearFix">
                        <li>
                            <div class="nft_position_title">
                                <p>최근 NFT 블록</p>
                                <a href="${pageContext.request.contextPath}/blockList"><img src="${pageContext.request.contextPath}/resources/images/plus.png" alt="더보기"></a>
                            </div>
                            <div class="nft_position_content">
                            	<c:set var="blnum" value="0"/>
                            	<c:forEach items="${recentBlList}" var="blList">
                            		<c:set var="blnum" value="${blnum+1}"/>
									<div class="nft_position_block">
                                        <p><a href="${pageContext.request.contextPath}/blockDetail?blNum=${blList.blDto.number}" id="refBlA_${blnum}">블록 번호 : ${blList.blDto.number}</a></p>
								        <span id="refSpTime_${blnum}">${blList.timeDiff} 초 전</span>
								        <ul>
								            <li><span>블록해시</span><span id="refSp1_${blnum}">: ${blList.blDto.hash}</span></li>
								            <li><span>마이너 해시</span><span id="refSp2_${blnum}">: ${blList.blDto.minerHash}</span></li>
								            <li><span>블록에 담긴 트랜잭션 수</span><span id="refSp3_${blnum}">: ${blList.txCnt}</span></li>
								        </ul>
								    </div>
								</c:forEach>
                            </div>
                        </li>
                        <li>
                            <div class="nft_position_title">
                                <p>최근 NFT 트랜잭션</p>
                                <a href="${pageContext.request.contextPath}/tranList"><img src="${pageContext.request.contextPath}/resources/images/plus.png" alt="더보기"></a>
                            </div>
                            <div class="nft_position_content">
                                <ul class="nft_position_transaction">
                                	<c:set var="trnum" value="0"/>
                                	<c:forEach items="${recentTrList}" var="trList">
                                		<c:set var="trnum" value="${trnum+1}"/>
                                		<li>
	                                        <p>트랜잭션 블록번호 : <a href="${pageContext.request.contextPath}/tranDetail?trNum=${trList.hash}" id="refTrA1_${trnum}">${trList.blockNumber}</a></p>
	                                        <p>트랜잭션 해시 : <a href="${pageContext.request.contextPath}/tranDetail?trNum=${trList.hash}" id="refTrA2_${trnum}">${trList.hash}</a></p>
	                                    </li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
</body>
</html>