package kakao.vo;

import lombok.Data;
@Data
public class KakaoSendMsgVO {
	
//	@JsonProperty("PHONE")//
	//카카오 알림톡 전송
	private String phone;
	private String callback;
	private String msg;
	private String template_code;
	private String faled_type;
	private String faled_msg;
	private String btn_types;
	private String btn_txts;
	private String btn_urls1;
	private String btn_urls2;	
	

}
