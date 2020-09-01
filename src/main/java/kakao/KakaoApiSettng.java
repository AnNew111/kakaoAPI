package kakao;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class KakaoApiSettng {

	@Autowired
	@Qualifier("kakaoConfig")
	private KakaoConfig kakaoConfig;
	
	public RestTemplate restTemplateSet() {
		HttpClient httpClient = HttpClients.custom().setMaxConnTotal(120).setMaxConnPerRoute(60).build(); 
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient); 
		factory.setConnectTimeout(5000);//타임아웃 설정 5초 
		factory.setReadTimeout(5000);//타임아웃 설정 5초
		return new RestTemplate(factory);
	}

	public HttpHeaders headerSet() {
		HttpHeaders header = new HttpHeaders(); 
		String key = kakaoConfig.getKey();
		String value =kakaoConfig.getValue();
		header.set(key,value);
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}

	public JSONObject jsonParser(ResponseEntity<String> response) throws ParseException {
		JSONParser jsonPaser = new JSONParser();
		Object obj = jsonPaser.parse(response.getBody().toString());
		return  (JSONObject)obj;
	}
	
	

}
