package dz.nft.nipa.block.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dz.nft.nipa.dto.EthBlockDto;
import lombok.extern.slf4j.Slf4j;
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
			log.trace("createdt = {}", createdt);
			
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

			map.put("timeDiff", diff);
			resulteList.add(map);
		}
		
		return resulteList;
	}

	public BlockDto getBlDataById(int blNum) {
		return blockMapper.getBlDataById(blNum);
	}
	
	public BlockDto getBlDataByBlocknum(int blocknum) {
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
