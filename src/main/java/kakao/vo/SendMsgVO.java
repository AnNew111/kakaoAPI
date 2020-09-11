package kakao.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SendMsgVO  {

	@Valid
	@SerializedName("k")
	private List<KVO> k;
	
	@Valid
	@SerializedName("m")
	private LinkedHashMap<String,String> m;
	
	
	
}
