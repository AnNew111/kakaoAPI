package kakao.controller;

import java.net.URI;


import org.json.simple.JSONObject;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;


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
	
	//īī�� �˸��� ����
	@PostMapping(value = "/kakaoSendMsg")
	public String  kakaotalkSend(@RequestBody SendMsgVO sendVO) {
		String jsonInString = "";
	
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
			  URI url = URI.create("http://api.apistore.co.kr/kko/1/msg/{client_id}");
			  
			  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  
			  jsonInString = jsonObj.toString();
			  System.out.println("cmid="+jsonObj.get("cmid")); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("�Ф�");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }  
		return jsonInString;	
	}



	
	
	//����Ʈ ��ȸ
	@PostMapping(value = "/report")
	public String reportSearch(@RequestBody ReportVO reportVO) {
		System.out.println(reportVO.getCmid());
		String cmid = reportVO.getCmid();
		String jsonInString = "";
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  //RequestBody�� �����͸� �����ϴ� HTTP.POST�� ��� VO �״�� ������ ���� ����
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  String url = "http://api.apistore.co.kr/kko/1/report/{client_id}" ;
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(url+"?"+"cmid="+cmid);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();
			  
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("�Ф�");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	//���ø� ��ȸ
	@PostMapping(value="/template")
	public String templateSearch(@RequestBody TemplateVO templateVO) {
		System.out.println(templateVO.getTempateCode());
		String template_code = templateVO.getTempateCode();
		String status = templateVO.getStatus();
		String jsonInString = "";
		try { 
		      RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  //RequestBody�� �����͸� �����ϴ� HTTP.POST�� ��� VO �״�� ������ ���� ����
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  String url = " http://api.apistore.co.kr/kko/1/template/list/{client_id}" ;
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(url+"?"+"template_code="+template_code+"&"+"status="+status);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("�Ф�");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	

	//�߽Ź�ȣ ����/���
	@PostMapping(value="/authentication")
	public String phoneNumberAuthenticate(@RequestBody AuthenticationVO authenticationVO) {
		System.out.println(authenticationVO.getComment());
		
		String jsonInString = "";
		try { 
			  ObjectMapper mapper = new ObjectMapper();
			  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			  String objToJson = mapper.writeValueAsString(authenticationVO);
			  KakaoAuthenticationVO kakaoAuthenticationVO = mapper.readValue(objToJson, KakaoAuthenticationVO.class);
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			    
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.

			  HttpEntity<KakaoAuthenticationVO> entity = new HttpEntity<>(kakaoAuthenticationVO,header);
			  
			  String url = "http://api.apistore.co.kr/kko/2/sendnumber/save/{client_id}";
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(url);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString(); 
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("�Ф�");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	//�߽Ź�ȣ ����Ʈ ��ȸ
	@PostMapping(value = "/calling-number")
	public String callingNumberSearch(@RequestBody CallingNumberVO callingNumberVO) {
		System.out.println(callingNumberVO.getSendNumber());
		String sendnumber = callingNumberVO.getSendNumber();
		String jsonInString = "";
		try { 
			  RestTemplate restTemplate = kakaoApiSetting.restTemplateSet();
			  
			  HttpHeaders header = kakaoApiSetting.headerSet();
			  
			  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
			  HttpEntity<?> entity = new HttpEntity<>(header);
			  
			  String url = "http://api.apistore.co.kr/kko/1/sendnumber/list/{client_id}";
			  
			  //create(String) : URI ��ü ������
			  URI uri = URI.create(url+"?"+"sendnumber="+sendnumber);
			  
			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			  
			  JSONObject jsonObj = kakaoApiSetting.jsonParser(response);
			  jsonInString = jsonObj.toString();   
		  }catch(HttpClientErrorException|HttpServerErrorException e) {
			  System.out.println("�Ф�");
			  System.out.println(e.toString()); 
		  }catch(Exception e) {
			  System.out.println(e.toString()); 
		  }
		return jsonInString;
	}
	
	
	
}
