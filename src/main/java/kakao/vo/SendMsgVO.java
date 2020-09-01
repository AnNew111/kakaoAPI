package kakao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SendMsgVO  {

	private String phone;
	
	@JsonProperty("callback")
	private String callBack;
	
	private String msg;
	
	@JsonProperty("template_code")
	private String templateCode;
	
	@JsonProperty("faled_type")
	private String faledType;
	
	@JsonProperty("faled_msg")
	private String faledMsg;
	
	@JsonProperty("btn_types")
	private String btnTypes;
	
	@JsonProperty("btn_txts")
	private String btnTxts;
	
	@JsonProperty("btn_urls1")
	private String btnUrls1;
	
	@JsonProperty("btn_urls2")
	private String btnUrls2;	
	

}
