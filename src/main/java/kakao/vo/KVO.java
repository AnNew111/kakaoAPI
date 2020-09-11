package kakao.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class KVO {

	@NotBlank(message = "수신할 번호를 입력하세요")
	@Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",message = "수신할 번호를 정확히 입력하세요")
	private String phone;
	
	@NotBlank(message = "발신자 번호를 입력하세요")
	@Pattern(regexp = "(^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$)|(^1(?:5|6|8)\\d{6}$)",message = "발신자 번호를 정확히 입력하세요")
	private String callBack;
	
	@NotBlank(message = "전송할 메세지를 입력하세요")
	private String msg;
	
	@NotBlank(message = "템플릿 코드를 입력하세요")
	@Size(min = 3,max = 7,message = "템플릿코드를 정확히 임력하세요")
	private String templateCode;
	
	@NotBlank(message = "faledType을 입력하세요")
	private String faledType;
//	private String faledMsg;
	
	@NotBlank(message = "버튼타입을 입력하세요")
	private String btnTypes;
	
	@NotBlank(message = "버튼이름을 입력하세요")
	@Size(max = 14,message = "버튼이름을 정확히 입력하세요")
	private String btnTxts;
	
	@NotBlank(message = "btnUrls1 링크주소를 입력하세요")
	@URL(message = "btnUrls1 링크주소를 정확히 입력하세요")
	private String btnUrls1;
	
	@NotBlank(message = "btnUrls2 링크주소를 입력하세요")
	@URL(message = "btnUrls2 링크주소를 정확히 입력하세요")
	private String btnUrls2;	
}
