package kakao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthenticationVO{

	@JsonProperty("sendnember")
	private String sendNumber;
	
	private String comment;
	
	@JsonProperty("pintype")
	private String pinType;

	
}
