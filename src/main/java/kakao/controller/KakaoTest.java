package kakao.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONArray;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.google.gson.JsonArray;

import kakao.KakaoApiSettng;
import kakao.KakaoConfig;
import kakao.ReportMsgRsltCode;
import kakao.ReportRsltCode;
import kakao.vo.AuthenticationVO;
import kakao.vo.CallingNumberVO;
import kakao.vo.KakaoAuthenticationVO;
import kakao.vo.KakaoSendMsgVO;
import kakao.vo.ReportVO;
import kakao.vo.SendMsgVO;
import kakao.vo.TemplateVO;
import lombok.var;


@RestController
@RequestMapping(value="/kakaoapi")
public class KakaoTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Map<String, String> msgList = new HashMap<String, String>();
	
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
	public String  kakaotalkSend(@RequestBody @Valid SendMsgVO sendVO ,BindingResult bindingResult) {
		String jsonInString = "a";
		String sendMsgUri = kakaoConfig.getSendMsgUri();
		String clientId = kakaoConfig.getClientId();
		logger.info(msgList.toString());
		
		String templateCode = sendVO.getK().get(0).getTemplateCode();
		logger.info(templateCode);
		String aa = sendVO.getM().get("time");
		String bb = Integer.toString(sendVO.getM().size());
		logger.info("시간"+bb);
		logger.info("맵 변수 개수"+aa);

		if(bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for(ObjectError e : list) {
				logger.info(e.getDefaultMessage());
			}
		}else if(!msgList.containsKey(templateCode)) {
			return "템플릿 코드 "+ templateCode+"을 조회해 주새요" ;
		}else if(!msgList.get(templateCode).equals(bb)) {
			return "템플릿 코드 "+templateCode+"의 메세지에 포함된 변수의 개수 : "+msgList.get(templateCode)+"\r\n 입력한 변수의 개수 : "+bb;
		}
		else if(sendVO.getK().get(0).getPhone().equals("01040392705")){
			jsonInString = "b";
			 	try {
			 	  List<String> key = new ArrayList<String>(sendVO.getM().keySet());
			 	  for(int i = 0; i<key.size();i++) {
			 	  	logger.info(key.get(i));
			 	  }
			 	
			 	  StringBuffer msgInVar = new StringBuffer(sendVO.getK().get(0).getMsg());
			 	  int count = 0;
			 	  for(int i = 0; (count=msgInVar.indexOf("#", count))>=0;i++) {
			 		  int end = count+3+key.get(i).length();
			 		  msgInVar.replace(count, end, sendVO.getM().get(key.get(i)));
			 		  logger.info("key"+sendVO.getM().get(key.get(i)));
			 	  }
			 	  logger.info("value ="+sendVO.getM().get(key.get(2)));
			 	  logger.info(msgInVar.toString());
			 	  sendVO.getK().get(0).setMsg(msgInVar.toString());
				  Gson gson = new Gson();
				  String mappedValue = gson.toJson(sendVO.getK().get(0));
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
			  		  
			  //String rslt = "A"+jsonObj.get("RSLT").toString();
			 // String msgRslt = "B"+jsonObj.get("msg_rslt").toString();
			  String rslt = jsonObj.get("RSLT").toString();
			  logger.info(rslt);
			  String descriptionA = "";
			  for(ReportRsltCode reportRsltCode : ReportRsltCode.values()) {
				  if(rslt.equals(reportRsltCode.getCode())){
					  descriptionA = ReportRsltCode.valueOf(reportRsltCode.toString()).getValue().toString();
					  break;
				  }
			  }
			  String msgRslt = jsonObj.get("msg_rslt").toString();
			  logger.info(msgRslt);
			  String descriptionB = "";
			  for(ReportMsgRsltCode reportMsgRsltCode : ReportMsgRsltCode.values()) {
				  if(rslt.equals(reportMsgRsltCode.getCode())){
					  descriptionB = ReportMsgRsltCode.valueOf(reportMsgRsltCode.toString()).getValue().toString();
					  break;
				  }
			  }
			
			  logger.info(descriptionA);
			 
			  jsonObj.remove("RSLT");
			  jsonObj.put("RSLT",descriptionA);  
			  jsonObj.remove("msg_rslt");
			  jsonObj.put("msg_rslt",descriptionB);
			  
			  jsonInString = jsonObj.toString();
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		
		return jsonInString;
	}

	
	//템플릿 조회
	@PostMapping(value="/template")
	public String templateSearch(@RequestBody @Valid TemplateVO templateVO, BindingResult bindingResult) {
		logger.info(templateVO.getTempateCode());
		String template_code = templateVO.getTempateCode();
		String status = templateVO.getStatus();
		String jsonInString = "";
		String templateUri = kakaoConfig.getTemplateUri();
		String clientId = kakaoConfig.getClientId();
		String msgCount = "";
		String resTemplateCode = "";
		String resMsg = "";
		
		if(bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for(ObjectError e : list) {
				logger.info(e.getDefaultMessage());
			}
		}else {
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
			
				  logger.info(jsonObj.get("templateList").toString());
				  JSONParser parser = new JSONParser();
				  JSONObject templateList = (JSONObject)parser.parse(jsonObj.toString());
				  JSONArray arr = (JSONArray)templateList.get("templateList");
				  JSONObject tmp = (JSONObject)arr.get(0);
				  resTemplateCode = tmp.get("template_code").toString();
				  resMsg = tmp.get("template_msg").toString();
				  logger.info(resMsg+"  "+resTemplateCode);
				  int count = 0;
				  int index = -1;
				  do {
					  if((index = resMsg.indexOf("#",index+1))>=0) {
						  count++;
						 msgCount= Integer.toString(count);
						 logger.info(msgCount);
					  }
				  }while(!(index==-1));
				  msgList.put(resTemplateCode,msgCount);
				  logger.info(msgList.toString());
				  jsonObj.put(resTemplateCode,"변수개수"+ msgCount);
				  jsonInString = jsonObj.toString(); 
					
				 // String result = kakaoMsgInfo.setMsg(resTemplateCode, msgCount);
				  //logger.info(result);
				  
			  }catch(HttpClientErrorException|HttpServerErrorException e) {
				  logger.info(e.toString()); 
			  }catch(Exception e) {
				  logger.info(e.toString()); 
			  }
		}
		return jsonInString;
	}
	

	//발신번호 인증/등록
	@PostMapping(value="/authentication")
	public String phoneNumberAuthenticate(@RequestBody @Valid AuthenticationVO authenticationVO, BindingResult bindingResult) {
		logger.info(authenticationVO.getComment());
		String sendNumberSaveUri = kakaoConfig.getSendNumberSaveUri();
		String jsonInString = "";
		String clientId = kakaoConfig.getClientId();
		logger.info(authenticationVO.getSendNumber());

		if(bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for(ObjectError e : list) {
				logger.info(e.getDefaultMessage());
			}
		}else {
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
			  URI uri = URI.create(sendNumberListUri+"/"+clientId);
			  
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
