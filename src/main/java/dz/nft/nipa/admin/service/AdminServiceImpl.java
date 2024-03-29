package dz.nft.nipa.admin.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dz.nft.nipa.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dz.nft.nipa.admin.mapper.AdminMapper;
import dz.nft.nipa.dto.DataImgDto;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.UserDto;
import dz.nft.nipa.transaction.mapper.TransactionMapper;

@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private TransactionMapper tranMapper;

	// 로그인 관련 기능
	// =========================================================================

	public void createAdminUser() {
		UserDto test = adminMapper.getUserDataById("digitalzonenipanft");
		if (test == null) {
			UserDto dto = new UserDto();
			dto.setUserId("digitalzonenipanft");
			dto.setUserPw(new HashStringUtil().getHashString("digi0808**"));
			dto.setUserRole("ROLE_ADMIN");
			adminMapper.insertUserData(dto);
		}
	}

	@Override
	public void insertUserData(UserDto dto) {
		adminMapper.insertUserData(dto);
	}
	
	@Override
	public void updateLogFailCntByUserNum(int userNum) {
		adminMapper.updateLogFailCntByUserNum(userNum);
	}
	
	@Override
	public void updateUserLockByUserNum(int userNum) {
		adminMapper.updateUserLockByUserNum(userNum);
	}

	@Override
	public UserDto getUserDataById(String userId) {
		return adminMapper.getUserDataById(userId);
	}
	
	@Override
	public void resetLogFailCntByUserNum(int userNum) {
		adminMapper.resetLogFailCntByUserNum(userNum);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserDto dto = adminMapper.getUserDataById(userId);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (dto != null) {
			authorities.add(new SimpleGrantedAuthority(dto.getUserRole()));
		}
		return null;
	}

	@Override
	public UserDto getUserDataByIdAndPw(String userId, String userPw) {
		return adminMapper.getUserDataByIdAndPw(userId, userPw);
	}

	@Override
	public String getUserTokenById(String userId) {
		return new SendUserId2BlockchainApi().sendData(userId);
	}

	// 게시글 등록 관련
	// =========================================================================

	public int createNftKey() {
		return adminMapper.createNftKey();
	}

	public void insertNftData(DataNftDto dto) throws IOException {
		// 이미지를 TXT 파일로 변환 후 인코딩
		// TXT 파일 생성
		Base64EncodeingUtil encoding = new Base64EncodeingUtil();
		MultipartFile file = encoding.send2BlockChainApi(dto.getNftUri());

		// request_date 값 세팅
		String todayUnix = new UnixTimeUtil().makeUnixTime();

		log.trace("dto.getNftId() = {}", dto.getNftId());

		// 블록체인 nft_create api로 보낼 값 세팅
		HashMap<String, Object> requestMap = new HashMap<>();
		// new HashMap<String, Object>();에서 <String, Object> 를 <>로 변경 (자바 1.7 이상부터 가능)
		requestMap.put("nftID", dto.getNftId());
		requestMap.put("nftTimeStamp", todayUnix);
		requestMap.put("tokenURI", dto.getNftUri());
		requestMap.put("title", dto.getNftTitle());
		requestMap.put("category", dto.getDetailNum());

		// 블록체인 nft_create api로 데이터 전송
		new SendBase64File2BlockchainApi().sendData(requestMap);
		String ipfsCid = new SendFileIPFSBlockchainApi().sendData(file);// ipfs txt 파일 저장
		log.trace("db 추가");
		tranMapper.save(dto.getNftId(), ipfsCid);

/*
		String nftId = null;
		int num = 0;
		do {
			num++;
			log.debug("dto.getNftNum() = {}", dto.getNftNum());
			nftId = tranMapper.getNftIdByNftNum(Integer.toString(dto.getNftNum()));
			log.debug("nftId = {}", nftId);
			dto.setNftId(nftId);
			if (nftId!=null) {
				break;
			}
		} while(nftId == null || num <= 500);
		// log.debug("Create_NFT nftId 호출 횟수 - "+num);
*/

		log.trace("nftId = {}", dto.getNftId());
		log.trace("ipfsCid = {}", ipfsCid);

		// dto.getNftId() 값이 null이 아닐 경우에만 db에 저장
		if (dto.getNftId() != null) {
			adminMapper.insertNftData(dto);
		} else {
			log.debug("NFT 발급 중 NFT_ID를 가져오는데 실패 하였습니다."+ dto.getNftId());
		}
		
	}

	public void makeThumbnailImg(int nftNum, MultipartFile file) throws IOException {

		// 업로드 이미지 서버에 저장
		if (file != null) {
			
			// 이미지 저장 파일 이름 /Nft_Img/yyyy-mm-dd/nftid_저장시점unixTime_originalfilename.jpg
			Date today = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todayDateFormat = dateFormat.format(today);
			log.trace("todayDateFormat = {}", todayDateFormat);
			String todayUnix = new UnixTimeUtil().makeUnixTime();
			log.trace("todayUnix = {}", todayUnix);
			String oriFileName = file.getOriginalFilename();
			log.trace("oriFileName = {}", oriFileName);
//			String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
			String hashedOriFileName = new HashStringUtil().getHashString(oriFileName);
			log.trace("hashedOriFileName = {}", hashedOriFileName);
			
			// 서버 배포시 수정해야함
			 String createImgFolderPath = "C:/nipa_upload/Nft_Img/" + todayDateFormat + "/"; // 개발용
//			String createImgFolderPath = "/nipa_upload/Nft_Img/" + todayDateFormat + "/"; // 배포용
			Files.createDirectories(Paths.get(createImgFolderPath)); // 폴더 생성
			
			// 저장할 이미지 이름 설정 및 저장
			String createImgFileName = createImgFolderPath + "NFT_" + nftNum + "_" + todayUnix + "_" + hashedOriFileName;
			file.transferTo(new File(createImgFileName + ".png"));

			log.trace("createImgFileName = {}", createImgFileName);
			
			// 서버 배포시 수정해야함
			// 디비 저장용 경로 설정
			 String saveFileName = createImgFileName.substring(15); // 개발용
//			String saveFileName = createImgFileName.substring(13); // 배포용
			
			DataImgDto dto = new DataImgDto();
			dto.setNftNum(nftNum);
			dto.setImgLocation(saveFileName);
			dto.setImgOriginalName(oriFileName);
			
			// 저장 완료된 이미지 경로 insert
			adminMapper.insertThumbnailImg(dto); // 이미지 경로 디비에 저장
		}

	}

	public int testTitle(String nftTitle) {
		return adminMapper.testTitle(nftTitle);
	}
	
	

}
