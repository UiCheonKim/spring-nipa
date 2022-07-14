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
.noneHover:hover {
	cursor: default;
	text-decoration: none !important;
	text-decoration-line: none;
}
.onHover:hover {
	cursor: pointer;
}
</style>
<script type="text/javascript">
   	function changeListNum(se){
   		const indexNo = se.selectedIndex;
   		var selectedOption = se.options[indexNo];
   		var optionNo = selectedOption.value;
   		location.href='/admin/searchList?typeNum=${type}&listNum='+optionNo+'&searchType=${searchType}&searchWord=${searchWord}';
   	}
   	
   	function move2update(num){
   		const form = document.getElementById("sendForm");
   		form.setAttribute("method","post");
   		form.setAttribute("action","${pageContext.request.contextPath}/admin/update");
   		const param = document.createElement("input");
   		param.setAttribute("type", "hidden");
   		param.setAttribute("name", "nftNum");
   		param.setAttribute("value", num);
   		form.appendChild(param);
   		form.submit();
   	}
   	
   	function updateStatus(e) {
   	    
   		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				// var obj = JSON.parse(xmlhttp.responseText);
			}
		};
		xmlhttp.open("post","/admin/updateHideYn" , true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("nftNum="+e.value);
   		
   	}
   	
   	function makeHiddenNumVal(num){
   		const hiddenNum = document.getElementById('hiddenNum');
   		hiddenNum.setAttribute('value', num);
   	}
   	
   	function removeHiddenNumVal(){
   		const hiddenNum = document.getElementById('hiddenNum');
   		hiddenNum.removeAttribute('value');
   	}
   	
	function deleteNft() {
		
		const numVal = document.getElementById('hiddenNum').value;
		
   		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				// var obj = JSON.parse(xmlhttp.responseText);
			}
		};
		xmlhttp.open("post","/admin/updatedelYn" , true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("nftNum="+numVal);
   		
   	}
	
	function changePlaceHolder(e){
		
		const selectIndexNo = e.selectedIndex;
		const selectedOption = e.options[selectIndexNo];
		const changeTypeName = selectedOption.value;
		const changeArea = document.getElementById('searchCase');
		// const searchInput = document.getElementById('admin_search_word');
		
		for (let i=0; i<changeArea.childElementCount; i++){
			changeArea.children[i].style.display='none';
		}
		
		const titleArea = document.querySelector('.search_icon_title');
		const idArea = document.querySelector('.search_icon_id');
		const yearArea = document.querySelector('.search_icon_year');
		const statusArea = document.querySelector('.search_icon_status');
		
		if(changeTypeName == 'searchTitle'){
			titleArea.style.display='block';
			
		} else if (changeTypeName == 'searchId'){
			idArea.style.display='block';
			
		} else if (changeTypeName == 'searchYear'){
			yearArea.style.display='block';
			
		} else if (changeTypeName == 'searchStatus'){
			statusArea.style.display='block';
		}
		
	}
	
	function doSearch(){
		
		const selectedType = document.getElementById('selectedSearchType');
		const selectIndexNo = selectedType.selectedIndex;
		const selectedOption = selectedType.options[selectIndexNo];
		const searchType = selectedOption.value;
		const typeNum = '${type}';
		let searchWord;
		
		if(searchType == 'searchTitle'){
			searchWord = document.querySelector('.search_icon_title>.admin_search_word').value;
			console.log(searchWord);
			
		} else if (searchType == 'searchId'){
			searchWord = document.querySelector('.search_icon_id>.admin_search_word').value;
			console.log(searchWord);
			
		} else if (searchType == 'searchYear'){
			const searchTag = document.querySelector('.search_icon_year>.admin_search_word');
			const searchTagIndexNo = searchTag.selectedIndex;
			const searchTagSelectedOption = searchTag.options[searchTagIndexNo];
			searchWord = searchTagSelectedOption.value;
			console.log(searchWord);
			
		} else if (searchType == 'searchStatus'){
			const searchTag = document.querySelector('.search_icon_status>.admin_search_word');
			const searchTagIndexNo = searchTag.selectedIndex;
			const searchTagSelectedOption = searchTag.options[searchTagIndexNo];
			searchWord = searchTagSelectedOption.value;
			console.log(searchWord);
		}		
		
		const param = 'typeNum='+typeNum+'searchType='+searchType+'searchWord='+searchWord;
		const form = document.getElementById('searchForm');
		form.setAttribute('method', 'post');
		form.setAttribute('action', '/admin/searchList');
		
		const input1 = document.createElement('input');
		input1.setAttribute('type', 'hidden');
		input1.setAttribute('name', 'typeNum');
		input1.setAttribute('value', typeNum);
		const input2 = document.createElement('input');
		input2.setAttribute('type', 'hidden');
		input2.setAttribute('name', 'searchType');
		input2.setAttribute('value', searchType);
		const input3 = document.createElement('input');
		input3.setAttribute('type', 'hidden');
		input3.setAttribute('name', 'searchWord');
		input3.setAttribute('value', searchWord);
		
		form.appendChild(input1);
		form.appendChild(input2);
		form.appendChild(input3);
	
		form.submit();
		
	}
	
	function keyPress(e){
		if (e.keyCode == 13) {
			doSearch();
		}
	}
</script>
</head>
<body>
	<div id="popup">
        <div class="popup_contents">
            <div class="popup_box">
                <p>NFT 삭제 확인</p>
                <span>정말 삭제하시겠습니까?</span>
            </div>
            <div class="popup_add_btn_two clearFix">
                <a href="#none" class="close" onclick="removeHiddenNumVal()">취소하기</a>
                <a href="#none" class="delete_btn" onclick="deleteNft()">삭제하기</a>
            </div>
            <input type="hidden" id="hiddenNum">
        </div>
    </div>
    <header>
        <div class="content_center">
            <div id="header" class="clearFix">
                <h2 class="header_title">NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트 관리자 페이지</h2>
                <a href="${pageContext.request.contextPath}/admin/logoutProc" class="log_out">로그아웃</a>
            </div>
        </div>
    </header>
    <div id="container">
        <div class="content_center">
            <div class="sub_title clearFix">
                <p>NFT 콘텐츠 목록</p>
            </div>
            <div class="content clearFix">
                <ul class="nft_list_button clearFix">
                    <li><a href="${pageContext.request.contextPath}/admin/input">NFT 신규 발행하기</a></li>
                    <%-- <li><a href="${pageContext.request.contextPath}/admin/updateTabs">분류 탭 관리하기</a></li> --%>
                </ul>
                <ul id="typeUl" class="tabBtn clearFix">
                	<c:forEach items="${typeList}" var="typeList">
                		<c:choose>
                			<c:when test="${typeList.typeNum == type}">
                				<li class="active"><a href="${pageContext.request.contextPath}/admin/nftList?typeNum=${typeList.typeNum}">${typeList.typeName}</a></li>
                			</c:when>
                			<c:otherwise>
                				<li><a href="${pageContext.request.contextPath}/admin/nftList?typeNum=${typeList.typeNum}">${typeList.typeName}</a></li>
                			</c:otherwise>
                		</c:choose>
	            	</c:forEach>
                </ul>
                <div class="tabWrap">
                    <div class="tabList">
                    	<div class="tab1">
	                    	<div class="search_btn clearFix">
	                    		<select onchange="changePlaceHolder(this)" id="selectedSearchType">
	                    			<option id="searchTitle" value="searchTitle">제목</option>
	                    			<option id="searchId" value="searchId">NFT ID</option>
	                    			<option id="searchYear" value="searchYear">원본 게시연도</option>
	                    			<option id="searchStatus" value="searchStatus">상태</option>
	                    		</select>
	                    		<div id="searchCase">
	                    			<div class="search_icon_title" ${searchType eq 'searchTitle' ? 'style="display:block;"' : 'style="display:none;"'}>
										<input class="admin_search_word" type="text" placeholder="NFT 제목을 입력해 주세요" onkeypress="keyPress(event)">
										<a onclick="doSearch()" class="onHover">
											<img src="${pageContext.request.contextPath}/resources/images/search.png" alt="돋보기">
										</a>
									</div>
									<div class="search_icon_id" ${searchType eq 'searchId' ? 'style="display:block;"' : 'style="display:none;"'}>
										<input class="admin_search_word" type="text" placeholder="NFT ID를 입력해 주세요" onkeypress="keyPress(event)">
										<a onclick="doSearch()" class="onHover">
											<img src="${pageContext.request.contextPath}/resources/images/search.png" alt="돋보기">
										</a>
									</div>
									<div class="search_icon_year" ${searchType eq 'searchYear' ? 'style="display:block;"' : 'style="display:none;"'}>
										<select class="admin_search_word">
											<c:forEach items="${yearData}" var="ydatas">
												<option value="${ydatas}">${ydatas}</option>
											</c:forEach>
										</select>
										<a onclick="doSearch()" class="onHover" style="left: 160px;">
											<img src="${pageContext.request.contextPath}/resources/images/search.png" alt="돋보기">
										</a>
									</div>
									<div class="search_icon_status" ${searchType eq 'searchStatus' ? 'style="display:block;"' : 'style="display:none;"'}>
										<select class="admin_search_word">
											<option value="발행">발행</option>
											<option value="삭제">삭제</option>
										</select>
										<a onclick="doSearch()" class="onHover" style="left: 160px;">
											<img src="${pageContext.request.contextPath}/resources/images/search.png" alt="돋보기">
										</a>
									</div>
	                    		</div>
	                    		<script type="text/javascript">
	                    			function changeSelectedOne(){
	                    				const selVal = document.getElementById('${searchType}');
	                    				selVal.setAttribute('selected','');
	                    				
	                    				if(selVal.value == 'searchTitle'){
	                    					const needRememberSearchWord = document.querySelector('.search_icon_title>.admin_search_word');
	                    					needRememberSearchWord.value = '${searchWord}';
	                    					
	                    				} else if(selVal.value == 'searchId'){
	                    					const needRememberSearchWord = document.querySelector('.search_icon_id>.admin_search_word');
	                    					needRememberSearchWord.value = '${searchWord}';
	                    					
	                    				} else if(selVal.value == 'searchYear'){
	                    					const needRememberSearchWord = document.querySelector('.search_icon_year>.admin_search_word');
	                    					for(let i=0; i<needRememberSearchWord.childElementCount; i++){
	                    						if(needRememberSearchWord.children[i].value == '${searchWord}'){
	                    							needRememberSearchWord.children[i].setAttribute('selected','');
	                    							break;
	                    						}
	                    					}
	                    					
	                    				} else if(selVal.value == 'searchStatus'){
	                    					const needRememberSearchWord = document.querySelector('.search_icon_status>.admin_search_word');
	                    					for(let i=0; i<needRememberSearchWord.childElementCount; i++){
	                    						if(needRememberSearchWord.children[i].value == '${searchWord}'){
	                    							needRememberSearchWord.children[i].setAttribute('selected','');
	                    							break;
	                    						}
	                    					}
	                    				} 
	                    				
	                    			}
	                    			changeSelectedOne();
	                    		</script>
								<div class="list_choice">
									<select onchange="changeListNum(this)">
										<option id="op10" value="10">10개씩 보기</option>
										<option id="op30" value="30">30개씩 보기</option>
										<option id="op50" value="50">50개씩 보기</option>
									</select>
								</div>
							</div>
							<c:if test="${empty searchList}">
								<div class="no_data">
	                                <div class="no_data_box">
	                                    <img src="${pageContext.request.contextPath}/resources/images/no_data.png" alt="데이터가 없음">
	                                    <p>검색하신 정보가 없습니다.</p>
	                                </div>
	                            </div>
	                        </c:if>
							<input type="hidden" name="pageNum" value="${currentPage}">
							<input type="hidden" id="listNum" name="listNum" value="${listNum}">
							<script type="text/javascript">
						    	function selectList(){
						    		const op10 = document.getElementById("op10");
						    		const op30 = document.getElementById("op30");
						    		const op50 = document.getElementById("op50");
						    		const listNum = document.getElementById("listNum");
						    		if (listNum.value == 10){
						    			op10.setAttribute('selected','');
						    			op30.removeAttribute('selected');
						    			op50.removeAttribute('selected');
						    		} else if(listNum.value == 30){
						    			op10.removeAttribute('selected');
						    			op30.setAttribute('selected','');
						    			op50.removeAttribute('selected');
						    		} else if(listNum.value == 50){
						    			op10.removeAttribute('selected');
						    			op30.removeAttribute('selected');
						    			op50.setAttribute('selected','');
						    		}
						    	}
						    	selectList();
							</script>
							<c:if test="${!empty searchList}">
								<div class="tblwrap_basic tblwrap_basic_center">
									<table>
										<colgroup>
											<col style="width: 8%">
	                                        <col style="width: 27%">
	                                        <col style="width: 27%">
	                                        <col style="width: 14%">
	                                        <col style="width: 8%">
	                                        <col style="width: 8%">
	                                        <col style="width: 8%">
										</colgroup>
										<tbody>
											<tr>
												<th>번호</th>
												<th>제목</th>
												<th>NFT ID</th>
												<th>원본 게시일</th>
												<th>상태</th>
												<th>숨기기</th>
												<th>삭제</th>
											</tr>
											<c:set var="num" value="${(count-(currentPage-1)*listNum)+1}"/>
											<c:forEach items="${searchList}" var="list">
												<c:set var="num" value="${num-1}"/>
												<tr>
													<td>${num}</td>
													<td style="text-align: left; padding-left: 10px;">${list.nft_title}</td>
													<td style="text-align: left; padding-left: 10px;">${list.nft_id}</td>
													<td>${list.nft_original_date}</td>
													<td>${list.del_yn}</td>
													<td>
														<div class="slider">
															<c:choose>
																<c:when test="${list.del_yn eq '삭제'}">
																	<input class="slider__input noneHover" type="checkbox" value="${list.nft_num}">
			                                                    	<label class="slider__label noneHover"></label>
																</c:when>
																<c:when test="${list.hide_yn eq 'N'}">
																	<input checked class="slider__input" type="checkbox" name="radion${num}" id="radio${num}" onchange="updateStatus(this)" value="${list.nft_num}">
			                                                    	<label class="slider__label" for="radio${num}"></label>
																</c:when>
																<c:otherwise>
																	<input class="slider__input" type="checkbox" name="radio${num}" id="radio${num}" onchange="updateStatus(this)" value="${list.nft_num}">
			                                                    	<label class="slider__label" for="radio${num}"></label>
																</c:otherwise>
															</c:choose>
		                                                </div>
													</td>
													<td>
														<c:choose>
															<c:when test="${list.del_yn eq '삭제'}">
																<a class="popup_btn_none noneHover">삭제완료</a>
															</c:when>
															<c:otherwise>
																<a onclick="makeHiddenNumVal(${list.nft_num})" class="popup_btn onHover">삭제하기</a>
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<form id="sendForm"></form>
								</div>
							    <div class="page_wrap">
									<div class="page_nation">
										<c:choose>
											<c:when test="${beginPage <= 1}">
												<a class="page_first" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=1&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
													<img src="${pageContext.request.contextPath}/resources/images/arrow_first.png" alt="첫번째페이지로">
												</a>
												<a class="page_prev">
													<img src="${pageContext.request.contextPath}/resources/images/arrow_left.png" alt="이전페이지로">
												</a>
											</c:when>
											<c:otherwise>
												<a class="page_first" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=1&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
													<img src="${pageContext.request.contextPath}/resources/images/arrow_first.png" alt="첫번째페이지로">
												</a>
												<a class="page_prev" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=${beginPage-1}&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
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
													<a class="pageNum" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=${page}&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">${page}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:choose>
											<c:when test="${endPage >= totalPageCount}">
												<a class="page_next">
				                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_right.png" alt="다음페이지로">
				                                </a>
				                                <a class="page_last" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=${totalPageCount}&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
				                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_last.png" alt="마지막페이지로">
				                                </a>
											</c:when>
											<c:otherwise>
												<a class="page_next" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=${endPage+1}&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
				                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_right.png" alt="다음페이지로">
				                                </a>
				                                <a class="page_last" href="${pageContext.request.contextPath}/admin/searchList?typeNum=${type}&pageNum=${totalPageCount}&listNum=${listNum}&searchType=${searchType}&searchWord=${searchWord}">
				                                	<img src="${pageContext.request.contextPath}/resources/images/arrow_last.png" alt="마지막페이지로">
				                                </a>
											</c:otherwise>
										</c:choose>	
									</div>
								</div>
							</c:if>
						</div> <!-- tab1 -->
					</div>
                </div>
            </div>
        </div>
    </div>
    <form id="searchForm"></form>   
</body>
</html>