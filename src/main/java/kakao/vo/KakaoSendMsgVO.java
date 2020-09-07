package kakao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class KakaoSendMsgVO {

	private String phone;
	
	@JsonProperty("callback")
	private String callBack;
	
	private String msg;
	
	@JsonProperty("template_code")
	private String templateCode;
	
	@JsonProperty("faled_type")
	private String faledType;
	
//	private String faled_msg;
	
	@JsonProperty("btn_types")
	private String btnTypes;
	
	@JsonProperty("btn_txts")
	private String btnTxts;
	
	@JsonProperty("btn_urls1")
	private String btnUrls1;
	
	@JsonProperty("btn_urls2")
	private String btnUrls2;	
	
	/*
	@Override
	public String toString() {
		return "phone:"+phone+",callback:"+callBack+",msg:"+msg+",template_code:"+templateCode+",faled_type:"
				+ faledType+",btn_types:"+btnTypes+",btn_txts:"+btnTxts+",btn_urls1:"+btnUrls1;
	}
	*/

}
