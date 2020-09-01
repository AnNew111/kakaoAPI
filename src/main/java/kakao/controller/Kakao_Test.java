package kakao.controller;

import java.awt.List;
import java.net.URI;


import org.json.simple.JSONObject;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kakao.KakaoApiSettng;
import kakao.KakaoConfig;
import kakao.vo.AuthenticationVO;
import kakao.vo.CallingNumberVO;
import kakao.vo.KakaoAuthenticationVO;
import kakao.vo.KakaoSendMsgVO;
import kakao.vo.ReportVO;
import kakao.vo.SendMsgVO;
import kakao.vo.TemplateVO;


@RestController
@RequestMapping(value="/kakaoapi")
public class Kakao_Test {
	
	@Autowired
	KakaoApiSettng kakaoApiSetting;
	
	@Autowired
	private KakaoConfig kakaoCofig;
	
	String clientId = kakaoCofig.getClient_id();
//	public void setKakaoConfig(KakaoConfig kakaoCofig) {
//		this.kakaoCofig = kakaoCofig;
//	}
	
	
	//朝朝神 硝顕套 穿勺
	@PostMapping(value = "/kakaoSendMsg")
	public String  kakaotalkSend(@RequestBody SendMsgVO sendVO) {
		String jsonInString = "";
		String sendMsgUri = kakaoCofig.getSendMsgUri();
		try { 
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			  String objToJson = mapper.writeValueAsString(sendVO);
			  KakaoSendMsgVO kakaoSendMsgVO = mapper.readValue(objToJson, KakaoSendMsgVO.class);
			
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
			  HttpEntity<KakaoSendMsgVO> entity = new HttpEntity<>(kakaoSendMsgVO,header);
			  
			  //create(String) : URI 梓端 持失敗
			  URI url = URI.create(sendMsgUri+"/"+clientId);
			  
			  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  
			  jsonInString = jsonObj.toString();
			  System.out.println("cmid="+jsonObj.get("cmid")); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("ばば");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }  
		return jsonInString;	
	}



	
	
	//軒匂闘 繕噺
	@PostMapping(value = "/report")
	public String reportSearch(@RequestBody ReportVO reportVO) {
		System.out.println(reportVO.getCmid());
		String cmid = reportVO.getCmid();
		String jsonInString = "";
		String reportUri = kakaoCofig.getReportUri();
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			 
			  //create(String) : URI 梓端 持失敗
			  URI uri = URI.create(reportUri+"/"+clientId+"?"+"cmid="+cmid);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();
			  
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("ばば");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	//奴巴鹸 繕噺
	@PostMapping(value="/template")
	public String templateSearch(@RequestBody TemplateVO templateVO) {
		System.out.println(templateVO.getTempateCode());
		String template_code = templateVO.getTempateCode();
		String status = templateVO.getStatus();
		String jsonInString = "";
		String templateUri = kakaoCofig.getTemplateUri();
		try { 
		      RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI 梓端 持失敗
			  URI uri = URI.create(templateUri+"/"+clientId+"?"+"template_code="+template_code+"&"+"status="+status);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("ばば");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	

	//降重腰硲 昔装/去系
	@PostMapping(value="/authentication")
	public String phoneNumberAuthenticate(@RequestBody AuthenticationVO authenticationVO) {
		System.out.println(authenticationVO.getComment());
		String sendNumberSaveUri = kakaoCofig.getSendNumberSaveUri();
		String jsonInString = "";
		try { 
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			  String objToJson = mapper.writeValueAsString(authenticationVO);
			  KakaoAuthenticationVO kakaoAuthenticationVO = mapper.readValue(objToJson, KakaoAuthenticationVO.class);
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			    
			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.

			  HttpEntity<KakaoAuthenticationVO> entity = new HttpEntity<>(kakaoAuthenticationVO,header);
			  
			  //create(String) : URI 梓端 持失敗
			  URI uri = URI.create(sendNumberSaveUri+"/"+clientId);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("ばば");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	//降重腰硲 軒什闘 繕噺
	@PostMapping(value = "/calling-number")
	public String callingNumberSearch(@RequestBody CallingNumberVO callingNumberVO) {
		System.out.println(callingNumberVO.getSendNumber());
		String sendnumber = callingNumberVO.getSendNumber();
		String jsonInString = "";
		String sendNumberListUri = kakaoCofig.getSendNumberListUri();
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI 梓端 持失敗
			  URI uri = URI.create(sendNumberListUri+"/"+clientId+"?"+"sendnumber="+sendnumber);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();   
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("ばば");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	@GetMapping(value = "/test3")
	public String test3() {
		String conToString = kakaoCofig.toString();	
		return conToString;
	}
	
	
	
	
}
