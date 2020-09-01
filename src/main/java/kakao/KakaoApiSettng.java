package kakao;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kakao.vo.KakaoSendMsgVO;
import kakao.vo.SendMsgVO;

@Component
public class KakaoApiSettng {
	
	public RestTemplate restTemplateSet() {
		HttpClient httpClient = HttpClients.custom().setMaxConnTotal(120).setMaxConnPerRoute(60).build(); 
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient); 
		factory.setConnectTimeout(5000);//타임아웃 설정 5초 
		factory.setReadTimeout(5000);//타임아웃 설정 5초
		return new RestTemplate(factory);
	}

	public HttpHeaders headerSet() {
		HttpHeaders header = new HttpHeaders(); 
		header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}

	public JSONObject jsonParser(ResponseEntity<String> response) throws ParseException {
		JSONParser jsonPaser = new JSONParser();
		Object obj = jsonPaser.parse(response.getBody().toString());
		return  (JSONObject)obj;
	}
	
	

}
