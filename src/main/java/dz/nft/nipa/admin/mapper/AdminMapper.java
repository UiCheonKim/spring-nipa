package dz.nft.nipa.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dz.nft.nipa.dto.DataImgDto;
import dz.nft.nipa.dto.DataNftDto;
import dz.nft.nipa.dto.UserDto;

@Mapper
public interface AdminMapper {
	
	public int createNftKey();
	
	public void insertUserData(UserDto dto);
	public UserDto getUserDataById(String id);
	public UserDto getUserDataByIdAndPw(@Param("id") String userId, @Param("pw") String userPw);
	public void insertNftData(DataNftDto dto);
	public void insertThumbnailImg(DataImgDto dto);

	public int testTitle(String nftTitle);

	public void updateLogFailCntByUserNum(int userNum);
	public void updateUserLockByUserNum(int userNum);
	public void resetLogFailCntByUserNum(int userNum);

}
