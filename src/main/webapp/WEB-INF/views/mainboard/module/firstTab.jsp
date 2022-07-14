<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach items="${dataList}" var="list">
	<c:if test="${!empty list.sortedList}">
		<div>
		    <h3 class="tabList_title">${list.detailDto.detailName}</h3>
		</div>
	</c:if>
	<c:forEach items="${list.sortedList}" var="sList">
		<li>
		    <a class="nftLinkList clearFix" onclick="removeLink(${sList.nft_num})">
		    	<img src="${pageContext.request.contextPath}/file?id=${sList.img_num}" alt="${sList.img_num}" class="document_img">
		    	<div class="main_textbox">
		    		<span class="number clearFix">NFT ID : ${fn:toUpperCase(fn:substring(sList.nft_id,0,12))}&middot;&middot;&middot;</span>
		    		<p class="name">${sList.nft_title}</p>
		    		<div class="hits clearFix">
			        	<img src="${pageContext.request.contextPath}/resources/images/eyes.png" alt="조회수">
			        	<span>${sList.nft_cnt}</span>
			        </div>
			        <span class="day">
						<fmt:parseDate var="parseRegDate" value="${sList.nft_original_date}" pattern="yyyy-MM-dd"/>
						<fmt:formatDate var="resultRegDt" value="${parseRegDate}" pattern="yyyy.MM.dd"/>
						${resultRegDt}
					</span>
		    	</div>
		    </a>
		</li>
	</c:forEach>
</c:forEach>