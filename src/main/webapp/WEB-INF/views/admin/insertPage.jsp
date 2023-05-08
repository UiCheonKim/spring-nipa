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
<script type="text/javascript">
	function selectCategory() {
		
		var dataType = document.getElementById("dataType");
		
		var selectIndexNo = dataType.selectedIndex;
		var selectedOption = dataType.options[selectIndexNo];
		var dataTypeNo = selectedOption.value;
		
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				var obj = JSON.parse(xmlhttp.responseText);
				
				var detailBox = document.getElementById("dataDetailArea");
				detailBox.innerHTML="";
				
				for(data of obj){
					var detailOption = document.createElement("option");
					detailOption.setAttribute("value", data.detailNum);
					detailOption.innerText = data.detailName;
					detailBox.appendChild(detailOption);
				}
			
			}
		};
		
		xmlhttp.open("post","/admin/getDetailList", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("typeNum="+dataTypeNo);
	}
	
	function testTitle(){
		
		const val = document.querySelector("#nftTitle");
		const alertArea = document.querySelector("#titleAlertArea");
		
		if(val.value.replace(/(\s*)/g, "") == ""){
			
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "제목은 필수 입력값입니다."
			val.value = "";
			val.focus();
			
		} else{
			
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState==4 && xmlhttp.status==200){
					var obj = JSON.parse(xmlhttp.responseText);
					if (obj == 1){
						alertArea.setAttribute("class", "alertText");
						alertArea.innerText = "이미 존재하는 제목입니다.";
						val.value = "";
						val.focus();
					} else{
						alertArea.innerText = "등록 가능한 제목입니다.";
						alertArea.removeAttribute("class");
						alertArea.setAttribute("class", "successText");
					}
					
				}
			};
			
			xmlhttp.open("post","../admin/testTitle", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("nftTitle="+val.value);
		}
		
	}
	
	function testUrl(){
		const val = document.querySelector("#nftUri");
		const alertArea = document.querySelector("#urlAlertArea");
		
		if(val.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "URL은 필수 입력값입니다."
			val.value = "";
			val.focus();
		} else {
			alertArea.innerText = "";
			alertArea.removeAttribute("class");
		}
	}
	
	function testPub(){
		const val = document.querySelector("#nftPublisher");
		const alertArea = document.querySelector("#pubAlertArea");
		if(val.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "발행처는 필수 입력값입니다."
			val.value = "";
			val.focus();
		} else {
			alertArea.innerText = "";
			alertArea.removeAttribute("class");
		}
	}
	
	function testOwn(){
		const val = document.querySelector("#nftOwner");
		const alertArea = document.querySelector("#ownAlertArea");
		if(val.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "소유자는 필수 입력값입니다."
			val.value = "";
			val.focus();
		} else {
			alertArea.innerText = "";
			alertArea.removeAttribute("class");
		}
	}
	
	function testDate(){
		const val = document.querySelector("#nftOriginalDate");
		const alertArea = document.querySelector("#dateAlertArea");
		if(val.value.replace(/(\s*)/g, "") == ""){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "원본 게시일은 필수 입력값입니다."
			val.value = "";
			val.focus();
		} else {
			alertArea.innerText = "";
			alertArea.removeAttribute("class");
		}
	}
	
	function testImg(){
		const val = document.querySelector("#input_image");
		const alertArea = document.querySelector("#imgAlertArea");
		const maxSize = 5 * 1024 * 1024;
		
		if(val.value.replace(/(\s*)/g, "") == "" || val == null){
			alertArea.setAttribute("class", "alertText");
			alertArea.innerText = "표지 이미지 첨부는 필수 항목입니다."
			val.value = "";
			val.focus();
		} else {
			fileSize = document.getElementById("input_image").files[0].size;
			if(fileSize >= maxSize) {
		    	alert("파일 사이즈는 5MB까지 가능합니다.");
		    	val.value = "";
		    	readImage(val);
		        return;
		    }
			alertArea.innerText = "";
			alertArea.removeAttribute("class");
		}
	}
	
	/*
	function changeType(e){
		
		const selectIndexNo = e.selectedIndex;
		const selectedOption = e.options[selectIndexNo];
		const changeTypeName = selectedOption.value;
		
		const changeType =  document.getElementById('changeType');
		changeType.innerHTML = '';
		
		if(changeTypeName == "URL"){
			
			const div1 = document.createElement('div');
			div1.setAttribute('class','nft_modify_title');
			const p = document.createElement('p');
			p.innerText = '동영상 URL';
			const span = document.createElement('span');
			span.innerText = '(필수)'
			const inputText = document.createElement('input');
			inputText.setAttribute('type','text');
			inputText.setAttribute('name','nftUri');
			inputText.setAttribute('id','nftUri');
			inputText.setAttribute('placeholder','유효한 URL을 입력하세요');
			inputText.setAttribute('onblur','testUrl()');
			const div2 = document.createElement('div');
			div2.setAttribute('id','urlAlertArea');
			
			changeType.appendChild(div1);
			div1.appendChild(p);
			div1.appendChild(span);
			changeType.appendChild(inputText);
			changeType.appendChild(div2);
			
		}else if(changeTypeName == "FILE"){
			
			const div1 = document.createElement('div');
			div1.setAttribute('class','nft_modify_title');
			const p = document.createElement('p');
			p.innerText = '첨부 파일';
			const span = document.createElement('span');
			span.innerText = '파일은 1개만 업로드 가능합니다. (최대 용량 : 10MB)'
			const inputText = document.createElement('input');
			inputText.setAttribute('type','file');
			// inputText.setAttribute('name','nftOriFile');
			// inputText.setAttribute('id','nftOriFile');
			// inputText.setAttribute('onblur','testOriFile()');
			const div2 = document.createElement('div');
			div2.setAttribute('id','fileAlertArea');
			
			changeType.appendChild(div1);
			div1.appendChild(p);
			div1.appendChild(span);
			changeType.appendChild(inputText);
			changeType.appendChild(div2);
			
		}
	}
	*/
	
	function openPopup4SendForm(){
		
		const titleVal = document.querySelector("#nftTitle").value.replace(/(\s*)/g, "");
		const uriVal = document.querySelector("#nftUri").value.replace(/(\s*)/g, "");
		const pubVal = document.querySelector("#nftPublisher").value.replace(/(\s*)/g, "");
		const ownVal = document.querySelector("#nftOwner").value.replace(/(\s*)/g, "");
		const dateVal = document.querySelector("#nftOriginalDate").value.replace(/(\s*)/g, "");
		const imgVal = document.querySelector("#input_image");
		
		if(titleVal == ""){
			testTitle();
		} else if(uriVal == ""){
			testUrl();
		} else if(pubVal == ""){
			testPub();
		} else if(ownVal == ""){
			testOwn();
		} else if(dateVal == ""){
			testDate();
		} else if(imgVal.value.replace(/(\s*)/g, "") == "" || imgVal == null){
			testImg();
		} else {
			const popupDiv = document.getElementById('popup');
			const popupTitle = document.getElementById('popupTitle');
			const popupSubTitle = document.getElementById('popupSubTitle');
			const pupupActive = document.getElementById('pupupActive');
			const pupupCancel = document.getElementById('pupupCancel');
			
			popupTitle.innerText = 'NFT 발행 확인';
			popupSubTitle.innerText = '정말 발행하시겠습니까?';
			pupupActive.innerText = '발행하기';
			pupupActive.setAttribute('onclick','sendForm(this)');
			pupupCancel.innerText = '취소하기';
			
			popupDiv.style.display = 'block';
		}
		
	}

	function openPopup4GoBack(){
		
		const popupDiv = document.getElementById('popup');
		const popupTitle = document.getElementById('popupTitle');
		const popupSubTitle = document.getElementById('popupSubTitle');
		const pupupActive = document.getElementById('pupupActive');
		const pupupCancel = document.getElementById('pupupCancel');
		
		popupTitle.innerText = 'NFT 발행 중단';
		popupSubTitle.innerText = '발행을 중단하시겠습니까?';
		pupupActive.innerText = '확인';
		pupupActive.setAttribute('onclick','location.href="${pageContext.request.contextPath}/admin/nftList"');
		pupupCancel.innerText = '취소';
		
		popupDiv.style.display = 'block';
		
	}
	
	function sendForm(el){
		
		const titleVal = document.querySelector("#nftTitle").value.replace(/(\s*)/g, "");
		const uriVal = document.querySelector("#nftUri").value.replace(/(\s*)/g, "");
		const pubVal = document.querySelector("#nftPublisher").value.replace(/(\s*)/g, "");
		const ownVal = document.querySelector("#nftOwner").value.replace(/(\s*)/g, "");
		const dateVal = document.querySelector("#nftOriginalDate").value.replace(/(\s*)/g, "");
		const imgVal = document.querySelector("#input_image");
		
		if(titleVal == ""){
			testTitle();
		} else if(uriVal == ""){
			testUrl();
		} else if(pubVal == ""){
			testPub();
		} else if(ownVal == ""){
			testOwn();
		} else if(dateVal == ""){
			testDate();
		} else if(imgVal.value.replace(/(\s*)/g, "") == "" || imgVal == null){
			testImg();
		} else {
			var form = document.getElementById("sendForm");
			form.submit();
			el.removeAttribute("onclick");
			loading();
		}
		
	}
	
	function loading(){
		const location = document.querySelector(".content_half_center");
		location.innerHTML = "";
		const divTag = document.createElement("div");
		divTag.setAttribute("class", "content");
		divTag.setAttribute("style", "height: 790px;");
		const imgTag = document.createElement("img");
		imgTag.setAttribute("src", "${pageContext.request.contextPath}/resources/images/loading.gif");
		imgTag.setAttribute("class", "loadingImg");
		location.appendChild(divTag);
		divTag.appendChild(imgTag);
	}
</script>
<style type="text/css">
 a:hover {cursor: pointer;}
 .alertText{font-size: 14px; color: red; font-weight: normal; margin-top: 5px;}
 .successText{font-size: 14px; color: green; font-weight: normal; margin-top: 5px;}
 .loadingImg{width:100px; height:100px; position:absolute;  left:50%; top:50%; margin-left:-50px; margin-top:-50px;}
</style>
</head>
<body onload="selectCategory()">
    <header>
        <div class="content_center">
            <div id="header" class="clearFix">
                <h2 class="header_title">NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트 관리자 페이지</h2>
                <a href="${pageContext.request.contextPath}/admin/logoutProc" class="log_out">로그아웃</a>
            </div>
        </div>
    </header>
    <div id="container">
        <div class="content_half_center">
        	<div id="popup">
		        <div class="popup_contents">
		            <div class="popup_box">
		                <p id="popupTitle"></p>
		                <span id="popupSubTitle"></span>
		            </div>
		            <div class="popup_add_btn_two clearFix">
		                <a class="close" id="pupupCancel"></a>
		                <a class="delete_btn" id="pupupActive"></a>
		            </div>
		        </div>
		    </div>
            <div class="sub_title clearFix">
                <p>NFT 신규 발행</p>
                <a onclick="openPopup4GoBack()" class="prev_page">이전</a>
            </div>
            <div class="content">
            	<form id="sendForm" action="${pageContext.request.contextPath}/admin/insertNftProc" method="post" enctype="multipart/form-data">
	                <ul class="nft_modify">
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>제목</p>
	                            <span>(필수)</span>
	                        </div>
	                        <input type="text" name="nftTitle" id="nftTitle" placeholder="제목을 입력해주세요" onblur="testTitle()">
	                        <div id="titleAlertArea"></div>
	                    </li>
						<li>
							<div class="nft_modify_title">
								<p>NFT ID</p>
								<span>(선택)</span>
							</div>
							<input type="text" name="nftId" id="nftId" placeholder="유효한 NFT ID를 입력하세요">
						</li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>NFT 항목 선택</p>
	                            <span>(필수)</span>
	                        </div>
	                        <select id="dataType" onchange="selectCategory()">
								<c:forEach items="${type}" var="list">
									<option value="${list.typeNum}">${list.typeName}</option>
								</c:forEach>
							</select>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>NFT 세부 항목 선택</p>
	                            <span>(필수)</span>
	                        </div>
	                        <select name="detailNum" id="dataDetailArea"></select>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>URL</p>
	                            <span>(필수)</span>
	                        </div>
	                        <input type="text" name="nftUri" id="nftUri" placeholder="유효한 URL을 입력하세요" onblur="testUrl()">
	                        <div id="urlAlertArea"></div>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>발행처</p>
	                            <span>(필수)</span>
	                        </div>
	                        <input type="text" name="nftPublisher" id="nftPublisher" placeholder="파일을 발행한 기관 이름을 입력하세요" onblur="testPub()">
	                        <div id="pubAlertArea"></div>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>소유자</p>
	                            <span>(필수)</span>
	                        </div>
	                        <input type="text" name="nftOwner" id="nftOwner" placeholder="파일의 소유주를 입력해주세요" onblur="testOwn()">
	                        <div id="ownAlertArea"></div>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>원본 게시일</p>
	                            <span>(필수)</span>
	                        </div>
	                        <input type="date" name="nftOriginalDate" id="nftOriginalDate" onblur="testDate()">
	                        <div id="dateAlertArea"></div>
	                    </li>
	                    <li>
	                        <div class="nft_modify_title">
	                            <p>표지</p>
	                            <span>파일은 1개만 업로드 가능합니다. (최대 용량 : 5MB)</span>
	                        </div>
	                        <div class="preview_file">
	                            <input type="file" name="file" id="input_image" accept="image/*" onblur="testImg()" class="inputImg">
	                            <img alt="" id="preview_image" style="width: 348px; height: 490px;">
	                        </div>
	                        <div id="imgAlertArea"></div>
	                    </li>
	                </ul>
                </form>
                <div class="ntf_more_btn clearFix">
                    <a onclick="openPopup4SendForm()" class="save_btn">발행하기</a>
                </div>
            </div>
        </div>
    </div>
	<script type="text/javascript">
		function readImage(input) {
			const previewImage = document.getElementById("preview_image");
		    // 인풋 태그에 파일이 있는 경우
		    if(input.files && input.files[0]) {
		        // 이미지 파일인지 검사 (생략)
		        // FileReader 인스턴스 생성
		        const reader = new FileReader();
		        // 이미지가 로드가 된 경우
		        reader.onload = e => {
		            previewImage.src = e.target.result;
		        }
		        // reader가 이미지 읽도록 하기
		        reader.readAsDataURL(input.files[0]);
		    } else{
		    	previewImage.removeAttribute("src");
		    }
		}
		// input file에 change 이벤트 부여
		const inputImage = document.getElementById("input_image");
		inputImage.addEventListener("change", e => {
		    readImage(e.target);
		})
	</script>
</body>
</html>