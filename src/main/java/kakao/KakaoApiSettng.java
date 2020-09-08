package kakao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;


@Component
public class KakaoApiSettng {

	@Autowired
	@Qualifier("kakaoConfig")
	private KakaoConfig kakaoConfig;
	
	public RestTemplate restTemplateSet() {
		//HttpClient httpClient = HttpClients.custom().setMaxConnTotal(120).setMaxConnPerRoute(60).build(); 
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
		factory.setConnectTimeout(60000);//Ÿ�Ӿƿ� ���� 5�� 
		factory.setReadTimeout(60000);//Ÿ�Ӿƿ� ���� 5��
		RestTemplate restTemplate = new RestTemplate(factory);
//		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
//		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return restTemplate;
	}

	public HttpHeaders headerSet() {
		HttpHeaders header = new HttpHeaders(); 
		String key = kakaoConfig.getKey();
		String value = kakaoConfig.getValue();
		header.set(key,value);
		header.set("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		return header;
	}

	public JSONObject jsonParser(ResponseEntity<String> response) throws ParseException {
		JSONParser jsonPaser = new JSONParser();
		Object obj = jsonPaser.parse(response.getBody().toString());
		return  (JSONObject)obj;
	}
	
	




}
