<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
	a:hover{cursor: pointer;}
	.document_img, .video_img{width: 295px; height: 416px;}
	.nonHover{cursor: default !important;}
</style>
<header>
	<div class="content_center">
		<div class="header clearFix">
			<h1 class="logo">
				<a href="${pageContext.request.contextPath}/">
					<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="NIPA정보통신산업진흥원로고">
				</a>
			</h1>
			<nav>
				<ul class="nav clearFix">
					<li class="menu" id="main"><a href="${pageContext.request.contextPath}/">NFT 콘텐츠 목록</a></li>
					<li class="menu" id="nft"><a href="${pageContext.request.contextPath}/nft">NFT 전체 현황</a></li>
					<li class="menu" id="his"><a href="${pageContext.request.contextPath}/blockList">NFT 상세 이력</a></li>
					<li class="menu" id="intro"><a href="${pageContext.request.contextPath}/intro">사이트 소개</a></li>
				</ul>
				<div class="search clearFix">
					<div class="searchBar">
						<form id="search_form" onsubmit="return false">
							<input type="text" id="search_word" name="searchWord" placeholder="NFT ID를 입력해주세요." onkeypress="keyPress(event)">
						</form>
						<div class="searching clearFix" onclick="search()">
							<a><img src="${pageContext.request.contextPath}/resources/images/search.png" alt="돋보기"></a>
						</div>
					</div>
				</div>
			</nav>
		</div>
	</div>
</header>
<script type="text/javascript">
	
	function keyPress(e){
		if (e.keyCode == 13) {
			search();
		}
	}

	/** 기존 코드 */
	// function search(){
	// 	var inputTag = document.querySelector('#search_word');
	// 	const inputWord = inputTag.value;
	// 	const searchWord = inputWord.replace(/(\s*)/g, "");
	//
	// 	if(searchWord == "" || searchWord.length == 0){
	// 		alert("검색어를 입력해주세요");
	// 		inputTag.value = "";
	// 		inputTag.focus();
	//
	// 	}else{
	//
	// 		if (searchWord.length == 64){
	//
	// 			var xmlhttp = new XMLHttpRequest();
	// 			xmlhttp.onreadystatechange = function(){
	// 				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
	// 					var obj = JSON.parse(xmlhttp.responseText);
	// 					if(obj.result == 1){
	// 						const form =  document.querySelector('#search_form')
	// 						form.setAttribute('action', '../searchByBlHash');
	// 						form.submit();
	// 					}else if(obj.result == 2){
	// 						const form =  document.querySelector('#search_form')
	// 						form.setAttribute('action', '../searchByTrHash');
	// 						form.submit();
	// 					}else{
	// 						alert("검색결과가 없습니다.");
	// 						inputTag.value = "";
	// 						inputTag.focus();
	// 					}
	// 				}
	// 			};
	// 			xmlhttp.open("post","/testSearchHash" , true);
	// 			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	// 			xmlhttp.send("searchWord="+searchWord);
	//
	// 		} else if (searchWord.length == 40){
	//
	// 			var xmlhttp = new XMLHttpRequest();
	// 			xmlhttp.onreadystatechange = function(){
	// 				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
	// 					var obj = JSON.parse(xmlhttp.responseText);
	// 					if(obj.result == 1){
	// 						const form =  document.querySelector('#search_form')
	// 						form.setAttribute('action', '../searchById');
	// 						form.submit();
	// 					}else{
	// 						alert("원본증명에 실패하였습니다.\nNFT ID를 확인해주세요.");
	// 						inputTag.value = "";
	// 						inputTag.focus();
	// 					}
	// 				}
	// 			};
	// 			xmlhttp.open("post","/testSearchId" , true);
	// 			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	// 			xmlhttp.send("searchWord="+searchWord);
	//
	// 		} else {
	// 			alert("올바른 NFT ID 또는 해시값이 아닙니다.\n확인 후 다시 입력해주세요.");
	// 			inputTag.value = "";
	// 			inputTag.focus();
	// 		}
	//
	// 	} //  공백 필터링 (if end)
	// } // search() end

	/** 수정 코드 */
	function search(){
		var inputTag = document.querySelector('#search_word');
		const inputWord = inputTag.value;
		const searchWord = inputWord.replace(/(\s*)/g, "");

		if(searchWord == "" || searchWord.length == 0){
			alert("검색어를 입력해주세요");
			inputTag.value = "";
			inputTag.focus();

		}else{
			if (searchWord.startsWith("0x")){ // Block, Transaction

				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function(){
					if(xmlhttp.readyState==4 && xmlhttp.status == 200){
						var obj = JSON.parse(xmlhttp.responseText);
						if(obj.result == 1){
							const form =  document.querySelector('#search_form')
							form.setAttribute('action', '../searchByBlHash');
							form.submit();
						}else if(obj.result == 2){
							const form =  document.querySelector('#search_form')
							form.setAttribute('action', '../searchByTrHash');
							form.submit();
						}else{
							alert("검색결과가 없습니다.");
							inputTag.value = "";
							inputTag.focus();
						}
					}
				};
				xmlhttp.open("post","/testSearchHash" , true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("searchWord="+searchWord);

			} else if (searchWord.length == 40){ // NFT ID

				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function(){
					if(xmlhttp.readyState==4 && xmlhttp.status == 200){
						var obj = JSON.parse(xmlhttp.responseText);

						if(obj.result == 1){
							const form =  document.querySelector('#search_form')
							form.setAttribute('action', '../searchById');
							form.submit();
						}else{
							alert("원본증명에 실패하였습니다.\nNFT ID를 확인해주세요.");
							inputTag.value = "";
							inputTag.focus();
						}
					}
				};
				xmlhttp.open("post","/testSearchId" , true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("searchWord="+searchWord);

			} else if (searchWord.length == 32){ // NFT ID

				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function(){
					if(xmlhttp.readyState==4 && xmlhttp.status == 200){
						var obj = JSON.parse(xmlhttp.responseText);

						if(obj.result == 1){
							const form =  document.querySelector('#search_form')
							form.setAttribute('action', '../searchById');
							form.submit();
						}else{
							alert("원본증명에 실패하였습니다.\nNFT ID를 확인해주세요.");
							inputTag.value = "";
							inputTag.focus();
						}
					}
				};
				xmlhttp.open("post","/testSearchId" , true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("searchWord="+searchWord);

			} else {
				alert("올바른 NFT ID 또는 해시값이 아닙니다.\n확인 후 다시 입력해주세요.");
				inputTag.value = "";
				inputTag.focus();
			}

		} //  공백 필터링 (if end)
	} // search() end

	function openGuide(){
    	const open = document.querySelector(".quick_menu");
		open.click();
		//console.log(open);
	}
	
</script>