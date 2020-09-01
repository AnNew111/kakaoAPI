package kakao.controller;


import java.net.URI;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


import com.fasterxml.jackson.databind.DeserializationFeature;

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
	
	
	//īī�� �˸��� ����
	@PostMapping(value = "/kakaoSendMsg")
	public String  kakaotalkSend(@RequestBody SendMsgVO sendVO) {
		String jsonInString = "";
		String sendMsgUri = kakaoConfig.getSendMsgUri();
		String clientId = kakaoConfig.getClientId();
		try { 
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			  String objToJson = mapper.writeValueAsString(sendVO);
			  KakaoSendMsgVO kakaoSendMsgVO = mapper.readValue(objToJson, KakaoSendMsgVO.class);
			
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  //RequestBody�� �����͸� �����ϴ� HTTP.POST�� ��� VO �״�� ������ ���� ����
			  HttpEntity<KakaoSendMsgVO> entity = new HttpEntity<>(kakaoSendMsgVO,header);
			  
			  //create(String) : URI ��ü ������
			  URI url = URI.create(sendMsgUri+"/"+clientId);
			  
			  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  
			  jsonInString = jsonObj.toString();
			  logger.info("cmid="+jsonObj.get("cmid")); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info("�Ф�");
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }  
		return jsonInString;	
	}
	//����Ʈ ��ȸ
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
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  //RequestBody�� �����͸� �����ϴ� HTTP.POST�� ��� VO �״�� ������ ���� ����
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(reportUri+"/"+clientId+"?"+"cmid="+cmid);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();
			  
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info("�Ф�");
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	
	//���ø� ��ȸ
	@PostMapping(value="/template")
	public String templateSearch(@RequestBody TemplateVO templateVO) {
		logger.info(templateVO.getTempateCode());
		String template_code = templateVO.getTempateCode();
		String status = templateVO.getStatus();
		String jsonInString = "";
		String templateUri = kakaoConfig.getTemplateUri();
		String clientId = kakaoConfig.getClientId();
		try { 
		      RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  //RequestBody�� �����͸� �����ϴ� HTTP.POST�� ��� VO �״�� ������ ���� ����
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(templateUri+"/"+clientId+"?"+"template_code="+template_code+"&"+"status="+status);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info("�Ф�");
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	

	//�߽Ź�ȣ ����/���
	@PostMapping(value="/authentication")
	public String phoneNumberAuthenticate(@RequestBody AuthenticationVO authenticationVO) {
		logger.info(authenticationVO.getComment());
		String sendNumberSaveUri = kakaoConfig.getSendNumberSaveUri();
		String jsonInString = "";
		String clientId = kakaoConfig.getClientId();
		try { 
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			  String objToJson = mapper.writeValueAsString(authenticationVO);
			  KakaoAuthenticationVO kakaoAuthenticationVO = mapper.readValue(objToJson, KakaoAuthenticationVO.class);
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			    
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.

			  HttpEntity<KakaoAuthenticationVO> entity = new HttpEntity<>(kakaoAuthenticationVO,header);
			  
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(sendNumberSaveUri+"/"+clientId);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info("�Ф�");
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	
	//�߽Ź�ȣ ����Ʈ ��ȸ
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
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(sendNumberListUri+"/"+clientId+"?"+"sendnumber="+sendnumber);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();   
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  logger.info("�Ф�");
			  logger.info(e.toString()); 
		  }catch(Exception e) {
			  logger.info(e.toString()); 
		  }
		return jsonInString;
	}
	
	@GetMapping(value = "/test3")
	public String test3() {
		String conToString = kakaoConfig.toString();
		logger.info("����");
		return conToString;
	}
	
	
	
	
}
