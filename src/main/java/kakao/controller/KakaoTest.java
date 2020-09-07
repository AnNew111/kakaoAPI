package kakao.controller;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import kakao.KakaoApiSettng;
import kakao.KakaoConfig;
import kakao.KakaoReportCode;
import kakao.vo.AuthenticationVO;
import kakao.vo.CallingNumberVO;
import kakao.vo.KakaoAuthenticationVO;
import kakao.vo.KakaoSendMsgVO;
import kakao.vo.ReportVO;
import kakao.vo.SendMsgVO;
import kakao.vo.TemplateVO;


@RestController
@RequestMapping(value="/kakaoapi")
public class KakaoTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	KakaoApiSettng kakaoApiSetting;
	
	@Autowired
	@Qualifier("kakaoConfig")
	private KakaoConfig kakaoConfig;
	
//	public void setKakaoConfig(KakaoConfig kakaoCofig) {
//		this.kakaoCofig = kakaoCofig;
//	}

	//카카오 알림톡 전송
	@PostMapping(value = "/kakaoSendMsg")
	public String  kakaotalkSend(@RequestBody SendMsgVO sendVO) {
		String jsonInString = "";
		String sendMsgUri = kakaoConfig.getSendMsgUri();
		String clientId = kakaoConfig.getClientId();
		logger.info(sendVO.getMsg());
		logger.info(sendVO.getCallBack());
		logger.info("템플릿"+sendVO.getTemplateCode());

		try { 
			  Gson gson = new Gson();
			  String mappedValue = gson.toJson(sendVO);
			  logger.info("String mapper"+mappedValue);
			  
			  KakaoSendMsgVO kakaoSendMsgVO = gson.fromJson(mappedValue, KakaoSendMsgVO.class);

			  logger.info("카카오 전체:"+kakaoSendMsgVO.toString());
			  
			  ObjectMapper mapper = new ObjectMapper();
			  String jobj = mapper.writeValueAsString(kakaoSendMsgVO);
			  Map<String,String> map = mapper.readValue(jobj, Map.class);
			  
			  MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
			  result.setAll(map);
			  			  
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(result,header);		  
			  //create(String) : URI 객체 생성함
			  URI url = URI.create(sendMsgUri+"/"+clientId);		  
			 
			  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);			  
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  
			  jsonInString = jsonObj.toString();
			  logger.info("cmid="+jsonObj.get("cmid")); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }  
		return jsonInString;	
	}
	//리포트 조회        
	@PostMapping(value = "/report")
	public String reportSearch(@RequestBody ReportVO reportVO) {
		logger.info(reportVO.getCmid());
		String cmid = reportVO.getCmid();
		String jsonInString = "";
		String reportUri = kakaoConfig.getReportUri();
		String clientId = kakaoConfig.getClientId();
		String r = "";
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();

			  //HttpEntity는 Http프로토콜을 이용하는 통신의  header와 body관련 정보를 저장할 수 있게끔 함.
			  //RequestBody로 데이터를 전달하는 HTTP.POST의 경우 VO 그대로 데이터 전달 가능
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) :URI 객체 생성함
			  URI uri = URI.create(reportUri+"/"+clientId+"?"+"cmid="+cmid);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);

			  String rslt = jsonObj.get("RSLT").toString();
			  logger.info(rslt);
			  String description = "";
			  for(KakaoReportCode code : KakaoReportCode.values()) {
				  if(code.getValue().toString().equals(rslt)) {
					  description = code.toString();
				  }break;
			  }
			  logger.info(description);
			  jsonObj.remove("RSLT");
			  jsonObj.put("RSLT",description);
			  
			  jsonInString = jsonObj.toString();
			  r = response.toString();
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		
		return jsonInString;
	}
	
	//템플릿 조회
	@PostMapping(value="/template")
	public String templateSearch(@RequestBody TemplateVO templateVO) {
		logger.info(templateVO.getTempateCode());
		String template_code = templateVO.getTempateCode();
		String status = templateVO.getStatus();
		String jsonInString = "";
		String templateUri = kakaoConfig.getTemplateUri();
		String clientId = kakaoConfig.getClientId();
		String a = "";
		try { 
		      RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();

			  //HttpEntity는 Http프로토콜을 이용하는 통신의  header와 body관련 정보를 저장할 수 있게끔 함.
			  //RequestBody로 데이터를 전달하는 HTTP.POST의 경우 VO 그대로 데이터 전달 가능
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI 객체 생성함
			  URI uri = URI.create(templateUri+"/"+clientId+"?"+"template_code="+template_code+"&"+"status="+status);
			  logger.info(uri.toString());
			    
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
			  a = response.getBody().toString();
			  logger.info(a);
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	

	//발신번호 인증/등록
	@PostMapping(value="/authentication")
	public String phoneNumberAuthenticate(@RequestBody AuthenticationVO authenticationVO) {
		logger.info(authenticationVO.getComment());
		String sendNumberSaveUri = kakaoConfig.getSendNumberSaveUri();
		String jsonInString = "";
		String clientId = kakaoConfig.getClientId();
		logger.info(authenticationVO.getSendNumber());
		try { 
			 Gson gson = new Gson();
			  String mappedValue = gson.toJson(authenticationVO);
			  logger.info("String mapper"+mappedValue);
			  
			  KakaoAuthenticationVO kakaoAuthenticationVO = gson.fromJson(mappedValue, KakaoAuthenticationVO.class);
			  logger.info("카카오 전체:"+kakaoAuthenticationVO.toString());
			  
			  ObjectMapper mapper = new ObjectMapper();
			  String jobj = mapper.writeValueAsString(kakaoAuthenticationVO);
			  Map<String,String> map = mapper.readValue(jobj, Map.class);
			  
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			    
			  //HttpEntity는 Http프로토콜을 이용하는 통신의  header와 body관련 정보를 저장할 수 있게끔 함.

			  HttpEntity<KakaoAuthenticationVO> entity = new HttpEntity<>(kakaoAuthenticationVO,header);
			  
			  //create(String) : URI 객체 생성함
			  URI uri = URI.create(sendNumberSaveUri+"/"+clientId);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	
	//발신번호 리스트 조회
	@PostMapping(value = "/calling-number")
	public String callingNumberSearch(@RequestBody CallingNumberVO callingNumberVO) {
		logger.info(callingNumberVO.getSendNumber());
		String sendnumber = callingNumberVO.getSendNumber();
		String jsonInString = "";
		String sendNumberListUri = kakaoConfig.getSendNumberListUri();
		String clientId = kakaoConfig.getClientId();
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity는 Http프로토콜을 이용하는 통신의  header와 body관련 정보를 저장할 수 있게끔 함.
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI 객체 생성함
			  URI uri = URI.create(sendNumberListUri+"/"+clientId+"?"+"sendnumber="+sendnumber);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();   
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	
	@GetMapping(value = "/test3")
	public String test3() {
		String conToString = kakaoConfig.toString();
		logger.info("test");
		return conToString;
	}
	
	
	
	
}
