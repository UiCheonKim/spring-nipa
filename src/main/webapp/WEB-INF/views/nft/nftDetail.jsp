<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%  
response.setHeader("Cache-Control","no-store");  
response.setHeader("Pragma","no-cache");  
response.setDateHeader("Expires",0);  
if (request.getProtocol().equals("HTTP/1.1"))
        response.setHeader("Cache-Control", "no-cache");
%>  
<!DOCTYPE html>
<html>
<head>
<title>NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트</title>
<link rel = "icon" href = "${pageContext.request.contextPath}/resources/images/title_.png" type = "image/x-icon">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/style.js"></script>
<script src="https://kit.fontawesome.com/ace68e7c88.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	const $move = document.querySelector(".move2Tran");
	// 열람하기
	function readNft() {
		window.open('${uri}');
	}
	
	function doNotReload(){
	    if((event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116) )
	    {
	    	event.keyCode = 0;
	    	event.cancelBubble = true;
	    	event.returnValue = false;
	 		alert("새로고침 방지");
	    }
	}
	document.onkeydown = doNotReload;
	
	function noEvent() {
	    if (event.keyCode == 116) {
	        event.keyCode= 2;
	        return false;
	    }
	    else if(event.ctrlKey && (event.keyCode == 78 || event.keyCode == 82))
	    {
	        return false;
	    }
	}
	document.onkeydown = noEvent;
	
	function closeModal(){
		const popup = document.getElementById("popup");
		popup.style.display = 'none';
	}
	
	function copyNftID(){
		const obj = document.getElementById("need2Copy");
		window.getSelection().removeAllRanges();
		
		let range = document.createRange();
		range.selectNode(document.getElementById("need2Copy")); // 복사할 이미지 영역 선택 
		window.getSelection().addRange(range); 
		document.execCommand('copy');
		alert('NFT ID 가 복사되었습니다.');
		window.getSelection().removeAllRanges();
	}
	
	function closePop_1(){
		const pop_1 = document.getElementById('popup_1');
		pop_1.style.display = 'none';
	}
	
</script>
</head>
<body oncontextmenu="return false" onpageshow="openGuide()">
	
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
            <div class="tooltip" style="top: 415px; left: 432px;">
                <p class="tooltiptext tooltiptext_top tooltiptext_page_search">
                    <span>“NFT 발행 이력 조회하기” 버튼을 클릭하면 해당 NFT가 언제, 어떤 내용으로 발행되었는지 조회할 수 있는 페이지로 이동합니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 798px; left: 676px;">
                <p class="tooltiptext tooltiptext_bottom tooltiptext_page_search_2">
                    <span>“해당 페이지로 이동하기” 버튼을 클릭하면 원본 콘텐츠가 게시된 NIPA 홈페이지로 이동합니다.</span>
                </p>
            </div>
            <div class="tooltip" style="top: 915px; left: 400px;">
                <p class="tooltiptext tooltiptext_top">
                    <span>각 아이콘을 클릭하면 현재 페이지를 공유할 수 있습니다.</span>
                </p>
            </div>
        </div>
    </div>
    
    <c:if test="${!empty sYn && sYn eq 'Y'}">
    	<div id="popup_1" style="display: block;">
	        <div class="popup_contents">
	            <div class="popup_title clearFix">
	            	<span>NFT ID: ${fn:toUpperCase(fn:substring(data.nft_id,0,12))}&middot;&middot;&middot;</span>
	                <button type="button" class="close" onclick="closePop_1()"> <span>×</span></button>
	            </div>
	            <div class="popup_box">
	                <p>원본 NFT입니다.</p>
	                <span>
                      본 NFT는 &lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;에서만 유효합니다.
                      NFT 조회 이력은 블록체인에 등록되며<br>
                      이는 [NFT 상세 이력]을 통해 확인할 수 있습니다.
                  </span>
	            </div>
	            <div class="popup_add_btn close"><a onclick="closePop_1()">확인</a></div>
	        </div>
	    </div>
    </c:if>

	<jsp:include page="${pageContext.request.contextPath}/header"></jsp:include>
	<script type="text/javascript">
		function aboutHeader(){
			var child = document.getElementById("main");
			child.setAttribute("class","menu bold");
		}
		aboutHeader();
	</script>
	<div id="popup">
        <div class="popup_contents">
            <div class="popup_title clearFix">
                <span>NFT ID: ${fn:toUpperCase(fn:substring(data.nft_id,0,12))}&middot;&middot;&middot;</span>
                <button type="button" class="close"> <span>×</span></button>
            </div>
            <div class="popup_box">
                <p>${data.nft_title}</p>
                <span>아래 버튼을 클릭하여 원본 콘텐츠를 확인하세요.</span>
            </div>
            <div class="popup_add_btn"><a id="getLink" onclick="readNft(), closeModal()">NFT 콘텐츠 열람하기</a></div>
        </div>
    </div>
	<div id="container">
		<div class="nav_title">
			<div class="content_center">
            	<h2 class="sub_top_title">NFT 상세정보</h2>
            </div>
        </div>
        <div class="content_center">
            <div class="content clearFix">
            	<h2 class="sub_title">${data.nft_title}</h2>
                <div class="nft_subpage clearFix">
                    <div class="nft_left">
                        <img src="${pageContext.request.contextPath}/file?id=${data.img_num}" alt="${data.nft_title}" style="width: 400px; height: 543px;">
                    </div>
                    <div class="nft_right">
                        <div class="nft_right_box clearFix">
                        	<p class="empty_area">공공기관 최초! NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트, &lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;에서 다양한 NFT를 검색 및 열람해보세요!
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        	</p>
                            <div class="nft_rigth_info clearFix">
                                <ul class="nft_list">
                                	<fmt:parseDate var="parseRegDate" value="${data.nft_original_date}" pattern="yyyy-MM-dd" />
									<fmt:formatDate var="resultRegDt" value="${parseRegDate}" pattern="yyyy.MM.dd" />
									<li>
										<span>NFT ID</span><span>: </span>
										<span id="need2Copy" style="padding-left: 10px;">${fn:toUpperCase(data.nft_id)}</span>
										<span>
											<a class="id_copy" onclick="copyNftID()">
												ID 복사
												<div class="tooltip" style="top: -75px; left: 50%; transform: translateX(-50%);">
		                                            <p class="tooltiptext tooltiptext_bottom">
		                                                <span class="tooltip_max_width">“ID 복사” 버튼을 클릭하면 “NFT ID”를 클립보드에 복사합니다.<br>복사된 “NFT ID”는 상단의 검색창에서 활용할 수 있습니다.</span>
		                                            </p>
		                                        </div>
											</a>
										</span>
									</li>
									<li><span>NFT 소유권</span><span>: </span><span>${data.nft_owner}</span></li>
                                    <li><span>NFT 발행처</span><span>: </span><span>${data.nft_publisher}</span></li>
                                    <li><span>NFT 발행일</span><span>: </span><span>${data.createdt}</span></li>
                                    <li><span>원본 게시일</span><span>: </span><span>${resultRegDt}</span></li>
									<li><span>조회수</span><span>: </span><span>${data.nft_cnt}</span></li>
                                </ul>
                                <div class="nft_search_btn">
                                	<a href="${pageContext.request.contextPath}/tranDetail?trNum=${data.hash}" class="nft_list_search">NFT 발행 이력 조회하기</a>
                                </div>
                            </div>
                            <div class="nft_notice">
                            	<c:choose>
                            		<c:when test="${data.detail_attach eq 'uri'}">
                            			<p>
		                                    본 NFT의 원본 콘텐츠는 NIPA의 YouTube 채널에서 확인하실 수 있습니다.
		                                </p>
                            		</c:when>
                            		<c:otherwise>
                            			<p>
		                                    본 NFT의 원본 게시글은 <span class="blue bold">NIPA 홈페이지 → 지식마당 → NIPA발간자료 → 2020년 이전 → 산업백서 및 연차보고서</span>에서 <span class="blue bold">‘블록체인 기술검증 지원사업 사례집(NFT ID 적용)’</span>을 검색하여 확인하실 수 있습니다.
		                                </p>
                            		</c:otherwise>
                            	</c:choose>
                            </div>
                            <div class="share_button">
                                <ul class="share_box">
                                    <li>
                                        <a onclick="copyUrl()"><img src="${pageContext.request.contextPath}/resources/images/clip.png" alt=""></a>
                                    </li>
                                    <li>
                                        <a onclick="kakaoShare()"><img src="${pageContext.request.contextPath}/resources/images/kakao_2.png" alt=""></a>
                                    </li>
                                    <li>
                                        <a onclick="shareSns('share_band')"><img src="${pageContext.request.contextPath}/resources/images/band.png" alt=""></a>
                                    </li>
                                    <li>
                                        <a onclick="shareSns('share_twt')"><img src="${pageContext.request.contextPath}/resources/images/twitter.png" alt=""></a>
                                    </li>
                                    <li>
                                        <a onclick="shareSns('share_fb')"><img src="${pageContext.request.contextPath}/resources/images/facebook.png" alt=""></a>
                                    </li>
                                </ul>
                            </div>
                            <ul class="nft_button clearFix">
                                <li><a class="popup_btn">해당 페이지로 이동하기</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
	<!-- 공유 -->
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script type="text/javascript">
		// 공유하기
		const shareTitle = '${data.nft_title}';
		const testURL = window.location.href;
		
		// 카카오 공유
		Kakao.init('c2a3fc79270df36dfdaa485f51b886d1') // kakaolink key
		// http://nipanft.docuchain.kr:8008/resources/images/kakao_2.png
		function kakaoShare() {
		    Kakao.Link.sendDefault({
		    	objectType: 'feed',
		    	content: {
		    	title: 'NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트',
		    	description: shareTitle,
		    	imageUrl: '',
		    	link: {
		    		webUrl: testURL,
		    	},
		    },
		    buttons: [
		    	{
		    		title: 'NFT 확인하기',
		    		link: {
		    			webUrl: testURL,
		    			mobileWebUrl: testURL,
		        	},
		    	},
		    ],
		    	// 카카오톡 미설치 시 카카오톡 설치 경로이동
		    	installTalk: true,
		    })
		}
		
		// 페북, 트위터, 밴드 공유
		const shareSns = (sns) => {
			const snsStyle = "width=480, height=540";
			if( sns == 'share_fb' ) {
		    	let url = "http://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent(testURL)+"&text="+encodeURIComponent(shareTitle);
		    	window.open(url, "shareFB", snsStyle);
			} else if( sns == 'share_twt' ) {
		    	let url = "http://twitter.com/share?url="+encodeURIComponent(testURL)+"&text="+encodeURIComponent(shareTitle);
		    	window.open(url, "tweetPop", snsStyle);
			} else if( sns == 'share_band' ) {
				let targetUrl = encodeURIComponent(shareTitle+'\n'+testURL)
		    	let url = 'http://www.band.us/plugin/share?body='+targetUrl;
		    	window.open(url, "shareBand", snsStyle);
			}
		}
		
		// 클립보드 복사
		const copyUrl = () => {
			navigator.clipboard.writeText(testURL); // 클립보드에 접근하는 새로운 메서드
			try { // .then callback 형태로 자주 쓰이나, try/catch로 작성하였다.
				alert("클립보드에 복사에 성공했습니다.")
			} catch(e) {
				alert("클립보드 복사에 실패하였습니다.")
			}
		}
	</script>
</body>
</html>