<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	function setTabCookie(num){
		let todayDate = new Date();
	    // todayDate.setDate(todayDate.getDate() + 1); // 현재 시각 + 일 단위로 쿠키 만료 날짜 변경
	    todayDate.setTime(todayDate.getTime() + 5*60*1000); // 5분
	    document.cookie = "tabNum=" + escape(num) + "; expires=" + todayDate.toUTCString() + ";";
		console.log(document.cookie);
	}
	
	function getTabCookie(){
		//const value = document.cookie.match("tabNum");
		//console.log(value);
		//console.log(document.cookie);
		const name = "tabNum"
		let matches = document.cookie.match(new RegExp(
			"(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
		));
		return matches ? decodeURIComponent(matches[1]) : undefined;
	}
	
	function changeTab(){
		
		const tabBtn = document.querySelector(".tabBtn");
		
		if(getTabCookie() == undefined){
			const childLi = tabBtn.children[0];
			childLi.setAttribute('class', 'active');
			const tab1 = document.querySelector(".tab1");
			const tab2 = document.querySelector(".tab2");
			tab1.style.display = 'block';
			tab2.style.display = 'none';
			
		} else if(getTabCookie() == 1){
			const childLi = tabBtn.children[0];
			childLi.setAttribute('class', 'active');
			const tab1 = document.querySelector(".tab1");
			const tab2 = document.querySelector(".tab2");
			tab1.style.display = 'block';
			tab2.style.display = 'none';
			
		} else if(getTabCookie() == 2){
			const childLi = tabBtn.children[1];
			childLi.setAttribute('class', 'active');
			const tab1 = document.querySelector(".tab1");
			const tab2 = document.querySelector(".tab2");
			tab1.style.display = 'none';
			tab2.style.display = 'block';
			
		}
		
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
            <div class="tooltip" style="top: 83px; left: 938px;">
                <p class="tooltiptext tooltiptext_top tooltiptext_main_page">
                    <span>NFT ID를 검색하여 원본증명 서비스를 체험할 수 있습니다.</span>
                </p>
            </div>
        </div>
    </div>
	
	<div id="loading" class="loadging_back" style="display: none;">
		<div class="loading-container">
			<div class="loading"></div>
			<div id="loading-text">Loading...</div>
		</div>
	</div>
	
	<jsp:include page="${pageContext.request.contextPath}/mainHeader"></jsp:include>
	<script type="text/javascript">
		function aboutHeader(){
			var child = document.getElementById("main");
			child.setAttribute("class","menu bold");
		}
		aboutHeader();
	</script>
	<div class="mainbanner">
		<div class="no_position_content_center">
			<div class="bannerbox clearFix">
				<div class="bannerbox_text">
					<p>국내 공공기관 최초</p>
					<h2>NIPA 블록체인 보고서<br>원본증명(NFT) 체험 사이트</h2>
					<div class="underline"></div>
					<p class="banner_box_bottom_text">
						<br>우측 하단의 "이용 가이드"를 활용하여 원본증명 서비스를 직접 체험해보세요.
					</p>
				</div>
			</div>
			<!-- .bannerBox -->
		</div>
	</div>
	<div id="main_container">
		<div class="content_center">
			<div class="main_content">
				<ul id="typeUl" class="tabBtn clearFix">
					<c:forEach items="${typeList}" var="list">
						<li onclick="setTabCookie(${list.typeNum})" class="depth1Tabs"><a>${list.typeName}</a></li>
					</c:forEach>
				</ul>
				<div class="tabWrap">
					<div class="tabList_box">
						<div class="tabList">
							<c:forEach items="${tabList}" var="tabs">
								<c:set var="num" value="${tabs.typeDto.typeNum}"></c:set>
								<div class="tab${num}">
									<div>
										<ul class="tabBtn_sub dateForTab${num} clearFix">
											<li class="active"><a>ALL</a></li>
											<c:forEach items="${tabs.dateList}" var="dates">
												<li class=""><a>${dates}</a></li>
											</c:forEach>
										</ul>
									</div>
									<div class="npf_exBox">
	                                    <p><span class="blue bold">NFT란?</span> Non-Fungible Token의 약자로, 디지털 자산에 대한 소유증명서를 뜻합니다. <br>이는 블록체인에 디지털파일 형태로 저장되며, 각기 고유성을 갖고 있어 다른 것으로 대체할 수 없다는 특징이 있습니다.</p>
	                                </div>
	                                
	                                
	                                
									<!-- 연도별 버튼 -->
									<ul class="document y_ALL">
										<jsp:include page="${pageContext.request.contextPath}/tabs">
											<jsp:param value="ALL" name="dateType"/>
											<jsp:param value="${num}" name="typeNum"/>
										</jsp:include>
									</ul>
									<!-- 전체보기 -->
									<c:forEach items="${tabs.dateList}" var="dates">
										<ul class="document y_${dates}">
											<jsp:include page="${pageContext.request.contextPath}/tabs">
												<jsp:param value="${dates}" name="dateType"/>
												<jsp:param value="${num}" name="typeNum"/>
											</jsp:include>
										</ul>
									</c:forEach>
									<!-- 연도별 클릭 ul 생성 -->
								</div>
							</c:forEach>
							<script type="text/javascript">
								changeTab();
							</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    	function removeLink(n){
    		const popup_contents = document.getElementById("loading");
    		popup_contents.style.display = 'block';
   			location.href = './nftDetail?nftNum='+n;
    		const nftLinkList = document.getElementsByClassName('nftLinkList');
    		for (let link of nftLinkList) {
    			link.removeAttribute('onclick');
    		}
    	}
	</script>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
</body>
</html>