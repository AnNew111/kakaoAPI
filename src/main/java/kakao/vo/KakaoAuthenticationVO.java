package kakao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoAuthenticationVO {

	@JsonProperty("sendnumber")
	private String sendNumber;
	
	private String comment;

	@JsonProperty("pintype")
	private String pinType;

	@JsonProperty("pincode")
	private String pinCode;
}
