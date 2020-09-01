package kakao.vo;

import lombok.Data;

@Data
public class KakaoAuthenticationVO {

	//발신번호 인증,등록
	private String sendnumber;
	private String comment;
	private String pintype;

}
