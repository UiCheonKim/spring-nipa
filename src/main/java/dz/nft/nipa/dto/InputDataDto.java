package dz.nft.nipa.dto;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

@Data
@Slf4j
public class InputDataDto {
    private String input;

    public void setInput(byte[] hash) {
        log.trace("corgi1 : {}", hash);
        log.trace("corgi2 : {}", Hex.encodeHexString(hash));
        this.input = Hex.encodeHexString(hash);
    }
}
