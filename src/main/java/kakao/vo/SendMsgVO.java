package kakao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SendMsgVO  {

	private String phone;
	private String callBack;
	private String msg;
	private String templateCode;
	private String faledType;
//	private String faledMsg;	
	private String btnTypes;
	private String btnTxts;
	private String btnUrls1;
	private String btnUrls2;	
	

}
