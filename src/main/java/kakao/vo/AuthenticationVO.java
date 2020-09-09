package kakao.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthenticationVO{

	@NotBlank(message = "발신번호를 입력햊세요")
	@Pattern(regexp = "(^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$)|(^1(?:5|6|8)\\d{6}$)",message = "발신자 번호를 정확히 입력하세요")
	private String sendNumber;
	
	@NotBlank(message = "코멘트를 입력햊세요")
	@Size(max = 200)
	private String comment;
	
	@JsonProperty("pintype")
	private String pinType;
	
	@JsonProperty("pincode")
	private String pinCode;

	
}
