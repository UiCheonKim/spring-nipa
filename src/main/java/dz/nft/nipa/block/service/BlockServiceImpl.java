package dz.nft.nipa.block.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import dz.nft.nipa.dto.EthBlockDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.nft.nipa.block.mapper.BlockMapper;
import dz.nft.nipa.dto.BlockDto;

@Service
@Slf4j
public class BlockServiceImpl {

	@Autowired
	private BlockMapper blockMapper;
	
	public int getBlockCnt() {
		return blockMapper.getBlockCnt();
	}
	
	public int getFirstBlockNum() {
//		Integer dataNum = blockMapper.getFirstBlockNum();
		Integer dataNum = blockMapper.getEthFirstBlockNum();
		int resultNum = 0;
		if (dataNum != null) {
			resultNum = (int)dataNum;
		}
		return resultNum;
	}
	
	public ArrayList<HashMap<String, Object>> getRecentBlList() {
		
		ArrayList<HashMap<String, Object>> resulteList = new ArrayList<HashMap<String,Object>>();
		
//		ArrayList<BlockDto> dtoList = blockMapper.getRecentBlList();
		ArrayList<EthBlockDto> dtoList = blockMapper.getEthRecentBlList();
//		for (BlockDto blockDto : dtoList) {
		for (EthBlockDto blockDto : dtoList) {

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("blDto", blockDto);
//			String createdt = blockDto.getCreatedt();
			String createdt = blockDto.getTimestamp();
			log.trace("생성 시간 = {}", createdt);
			log.trace("블록 번호 = {}", blockDto.getNumber());
			log.trace("블록 해시 = {}", blockDto.getHash());
			int ethTransactionCount = blockMapper.getEthTransactionCount(Integer.parseInt(blockDto.getNumber()));
			log.trace("트랜잭션 갯수 = {}", ethTransactionCount);

			map.put("txCnt", ethTransactionCount);
			
			Date today = new Date ();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date formatCreatedt = null;
			try {
				formatCreatedt = new Date(dateFormat.parse(createdt).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			long todayMill = today.getTime();
			long createdtMill = formatCreatedt.getTime();
			long diff = (todayMill-createdtMill)/1000;

			log.trace("시간 차이 = {}", diff);

			map.put("timeDiff", diff);
			resulteList.add(map);
		}
		
		return resulteList;
	}

	// 기존 코드
//	public EthBlockDto getBlDataById(int blNum) {
//		EthBlockDto ethBlockDto = blockMapper.getBlDataById(blNum);
//		int ethTransactionCount = blockMapper.getEthTransactionCount(blNum);
//
//		return blockMapper.getBlDataById(blNum);
//	}

	// 수정 코드 - NFT 상세 이력 - NFT 블록 상세 조회
	public EthBlockDto getBlDataById(int blNum) {
		EthBlockDto ethBlockDto = blockMapper.getBlDataById(blNum);
		ethBlockDto.setTxCount(blockMapper.getEthTransactionCount(blNum));

		return ethBlockDto;
	}
	
	public EthBlockDto getBlDataByBlocknum(int blocknum) {
		return blockMapper.getBlDataByBlocknum(blocknum);
	}

	public ArrayList<BlockDto> getTotalBlList(int pageNum) {
		return blockMapper.getTotalBlList(pageNum);
	}

	public int getTestResult4SearchHash(String searchWord) {
		return blockMapper.getTestResult4SearchHash(searchWord);
	}

	public int getBlNumByHash(String searchWord) {
		Integer dataNum = blockMapper.getBlNumByHash(searchWord);
		int resultNum = 0;
		if (dataNum != null) {
			resultNum = (int)dataNum;
		}
		return resultNum;
	}

}
