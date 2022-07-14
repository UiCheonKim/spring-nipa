<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <div class="tooltip" style="top: 888px; left: 50%; transform: translateX(-50%);">
                <p class="tooltiptext tooltiptext_bottom">
                    <span>아래 탭을 클릭하여 각각의 적용 기술에 대한 상세한 내용을 확인하실 수 있습니다.</span>
                </p>
            </div>
        </div>
    </div>

	<jsp:include page="${pageContext.request.contextPath}/header"></jsp:include>
	<script type="text/javascript">
		function aboutHeader(){
			var child = document.getElementById("intro");
			child.setAttribute("class","menu bold");
		}
		aboutHeader();
	</script>
	<div id="container" class="introduce_page_container">
        <div class="nav_title">
			<div class="content_center">
            	<h2 class="sub_top_title">사이트 소개</h2>
            </div>
        </div>
        <div class="content">
            <div class="introduce_background_story">
                <div class="content_center">
                    <h3 class="introduce_title"><span class="blue">구축</span> 배경</h3>
                    <p>정보통신산업진흥원에서는 공공기관 최초 민관협력 NFT 공공적용 시범 사례 발굴을 위하여</p>
                    <p>‘2019-2021 블록체인 기술검증(PoC) 지원사업’ 홍보물(사례집, 유튜브 영상 등)을 NFT로 발행하고</p>
                    <p class="introduce_bottom">이를 실시간으로 확인할 수 있는 &lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;를 구축하였습니다.</p>
                    <p>㈜디지털존은 블록체인 기술검증(PoC) 지원사업의 졸업기업으로 3년간의 지원사업 동안 우수한 성과를 보여주었으며,</p>
                    <p class="introduce_bottom">이를 통해 보유하게 된 자체적인 블록체인 기술을 바탕으로 &lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;를 구축 및 운영하고 있습니다.</p>
                    <p>‘NFT(Non Fungible Token, 대체 불가능 토큰)’란, 디지털 자산에 대한 소유증명서를 뜻합니다.</p>
                    <p>이는 블록체인에 디지털파일 형태로 저장되며, 각기 고유성을 갖고 있어 다른 것으로 대체할 수 없다는 특징이 있습니다.</p>
                    <p class="introduce_bottom">본원은 이러한 NFT 기술을 활용하여 블록체인 기술검증(PoC) 지원사업에 참여한 기업들의 이야기를 더 많은 이들과 공유하고자 합니다.</p>
                    <p>공공기관 최초! 블록체인과 NFT를 활용한 원본증명 체험 서비스,</p>
                    <p>&lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;에서 다양한 NFT를 검색 및 열람해보세요!</p>
                </div>
            </div>
            <div class="introduce_skill clearFix">
                <div class="content_center clearFix">
                    <h3 class="introduce_title"><span class="blue">적용</span> 기술</h3>
                    <ul class="skill_list_btn clearFix">
                        <li class="active"><a href="#">Blockchain</a></li>
                        <li><a href="#">NFT Protocol</a></li>
                        <li><a href="#">Smart Contract</a></li>
                        <li><a href="#">On/Off - Chain</a></li>
                        <li><a href="#">Blockchain Monitoring</a></li>
                    </ul>
                </div>
                <div class="skill_container content_center clearFix" id="real">
                    <div class="skill1 clearFix">
                        <p class="skill_info">
                            <span class="blue bold">Blockchain</span>은 데이터들을 모아 블록 단위로 구성하여 체인 형태로 연결시킨 뒤, 다수의 사람들이 복사하여 분산 저장하는 데이터 저장 기술입니다. <br>Blockchain은 데이터 위변조가 불가능하고, 투명하며, 신뢰할 수 있습니다.
                        </p>
                        <div class="skill_img">
                            <img src="${pageContext.request.contextPath}/resources/images/Blockchain.png" alt="">
                        </div>
                        <div class="skill_sub_info">
                            <ul>
                                <li>디지털존이 개발한 블록체인, &lt;Docuchain&gt;의 특징은 다음과 같습니다.</li>
                                <li>자체 프라이빗 환경을 구축하여 강력한 블록체인 노드 권한 관리와 효율적 자원 활용이 가능합니다.</li>
                                <li>노드 서버 장애 대응 및 관리에 유연한 합의 알고리즘을 적용하여 Liveness를 보장합니다.</li>
                                <li>전체 노드가 분산원장을 통해 동일한 데이터를 저장함으로써 투명성을 확보합니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="menu2">
                        <div class="skill2">
                            <p class="skill_info">
                                <span class="blue bold">NFT Protocol</span>은 블록체인 네트워크에서 NFT를 발행하는 방법이자 NFT 발행 시 반드시 따라야 하는 규약입니다. <br>동일한 블록체인 네트워크 안에서 표준화된 규약을 따라 발행된 NFT는 다양한 어플리케이션에서 안정적으로 통용될 수 있습니다.
                            </p>
                            <div class="skill_img">
                                <img src="${pageContext.request.contextPath}/resources/images/NFT_Protocol.png" alt="">
                            </div>
                            <div class="skill_sub_info">
                                <ul>
                                    <li>디지털존이 개발한 블록체인, &lt;Docuchain&gt;에서 구현된 NFT Protocol의 특징은 다음과 같습니다.</li>
                                    <li>프라이빗 블록체인 환경에서 구현되었기 때문에 기밀성과 빠른 속도를 자랑합니다.</li>
                                    <li>토큰에 소유자를 명시하여 소유권 및 대체불가성/유일성을 보장합니다.</li>
                                    <li>이더리움의 ERC-721, 클레이튼의 KIP-17 등 다양한 NFT Protocol을 연동하여 확장할 수 있습니다.</li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="menu3">
                        <div class="skill3">
                            <p class="skill_info">
                                <span class="blue bold">Smart Contract</span>는 코드로 구현된 계약으로 특정 조건이 충족되면 계약이 자동으로 성사됩니다. <br>자동으로 계약이 성사되기 때문에 중개자가 필요 없어 비용을 절감할 수 있으며, 계약 내용이 블록체인에 기록되어 투명하고 신뢰할 수 있습니다.
                            </p>
                            <div class="skill_img">
                                <img src="${pageContext.request.contextPath}/resources/images/Smart_Contract.png" alt="">
                            </div>
                            <div class="skill_sub_info">
                                <ul>
                                    <li>디지털존이 개발한 블록체인, &lt;Docuchain&gt;에 구현된 SmartContract의 특징은 다음과 같습니다.</li>
                                    <li>NFT 발행, NFT 조회, NFT 삭제 총 3개의 SmartContract를 통해 NFT의 Lifecycle을 관리합니다.</li>
                                    <li>프라이빗 네트워크에서 구현된 SmartContract 실행 내역과 상세정보는 블록체인에 투명하게 저장됩니다.</li>
                                    <li>NFT 상세 이력 페이지에서 누구나 블록/트랜잭션을 통해 상세정보를 조회할 수 있습니다.</li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="menu4">
                        <div class="skill4">
                            <p class="skill_info">
                                <span class="blue bold">On-Chain</span>은 블록체인 네트워크 내부를, <span class="blue bold">Off-chain</span>은 블록체인 네트워크 외부를 의미합니다. <br>블록체인은 확장성과 속도에 제약이 있기 때문에 용량이 적은 데이터는 블록체인 내부에 저장하고 용량이 큰 데이터는 외부에 저장하는 방식으로 확장성 문제를 해결합니다.
                            </p>
                            <div class="skill_img">
                                <img src="${pageContext.request.contextPath}/resources/images/On_Off_Chain.png" alt="">
                            </div>
                            <div class="skill_sub_info">
                                <ul>
                                    <li>디지털존이 개발한 블록체인, &lt;Docuchain&gt;에 적용된 Off-Chain의 특징은 다음과 같습니다.</li>
                                    <li>대용량의 원본 데이터를 분산 환경의 Off-Chain에 저장하여 블록체인의 성능과 처리속도를 빠르게 유지합니다.</li>
                                    <li>원본 데이터가 저장된 주소(해시값)와 NFT 발행 이력을 On-Chain에서 관리합니다.</li>
                                    <li>On-Chain에 저장된 원본 데이터의 해시값을 활용하여 Off-Chain에서 원본 데이터를 언제든지 조회할 수 있습니다.</li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="menu5">
                        <div class="skill5">
                            <p class="skill_info">
                                <span class="blue bold">Blockchain Monitoring</span>은 NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트의 블록체인 내부에서 발생하는 모든 이력을 조회하고 추적할 수 있는 기능을 말합니다. <br>모니터링을 통하여 모든 기록을 실시간으로 조회할 수 있으며 블록체인에 저장되기 때문에 데이터가 삭제되거나 위변조되지 않습니다.
                            </p>
                            <div class="skill_img">
                                <img src="${pageContext.request.contextPath}/resources/images/Blockchain_Monitoring.png" alt="">
                            </div>
                            <div class="skill_sub_info">
                                <ul>
                                    <li>디지털존이 개발한 블록체인, &lt;Docuchain&gt;이 제공하는 Blockchain Monitoring의 특징은 다음과 같습니다.</li>
                                    <li>NFT ID, 블록 해시, 트랜잭션 해시를 활용하여 최적화된 검색 및 조회 기능을 제공합니다.</li>
                                    <li>블록체인에 특화된 시각화 툴을 활용하여 대시보드의 가독성과 가시성을 극대화하였습니다.</li>
                                    <li>블록과 트랜잭션에 담긴 로우 데이터(Raw Data)를 사용자 친화적인 언어와 인터페이스로 제공합니다.</li>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="introduce_extensibility">
                <div class="content_center">
                    <div class="extensibility_box clearFix">
                        <div class="left_extensibility">
                            <h3 class="introduce_title"><span class="blue">확장</span> 가능성</h3>
                            <p>&lt;NIPA 블록체인 보고서 원본증명(NFT) 체험 사이트&gt;에 적용된 블록체인 및 NFT 기술은 다양한 분야로 확장 가능합니다. </p>
                            <p>그 예시로는 디지털 아트 분야부터, 스포츠, 엔터테인먼트, 게임 아이템, 부동산, 금융상품, 지적 재산권 그리고 메타버스 등이 있습니다.</p>
                        </div>
                        <div class="right_extensibility">
                            <img src="${pageContext.request.contextPath}/resources/images/NTF_intro.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="${pageContext.request.contextPath}/footer"></jsp:include>
</body>
</html>