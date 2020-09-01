//package kakao.controller;
//
//import java.net.URI;
//
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import kakao.KakaoApiSettng;
//import kakao.vo.AuthenticationVO;
//import kakao.vo.CallingNumberVO;
//import kakao.vo.KakaoAuthenticationVO;
//import kakao.vo.KakaoSendMsgVO;
//import kakao.vo.ReportVO;
//import kakao.vo.SendMsgVO;
//import kakao.vo.TemplateVO;
//
//
//@RestController
//@RequestMapping(value="/kakaoapi")
//public class Kakao_Test2 {
//	
//	@Autowired
//	KakaoApiSettng kakaoApiSetting;
//	
//	//朝朝神 硝顕套 穿勺
//	@PostMapping(value = "/kakaoSendMsg")
//	public String  kakaotalkSend(@RequestBody SendMsgVO sendVO) {
//		String jsonInString = "";
//		KakaoSendMsgVO kakaoSendMsgVO = new KakaoSendMsgVO();
//		kakaoSendMsgVO.setPhone(sendVO.getPhone());
//		kakaoSendMsgVO.setCallback(sendVO.getCallBack());
//		kakaoSendMsgVO.setMsg(sendVO.getMsg());
//		kakaoSendMsgVO.setTemplate_code(sendVO.getTemplateCode());
//		kakaoSendMsgVO.setFaled_type(sendVO.getFaledType());
//		kakaoSendMsgVO.setFaled_msg(sendVO.getFaledMsg());
//		kakaoSendMsgVO.setBtn_types(sendVO.getBtnTypes());
//		kakaoSendMsgVO.setBtn_txts(sendVO.getBtnTxts());
//		kakaoSendMsgVO.setBtn_urls1(sendVO.getBtnUrls1());
//		kakaoSendMsgVO.setBtn_urls2(sendVO.getBtnUrls2());
//		
//		try { 
//			HttpClient httpClient = HttpClients.custom().setMaxConnTotal(120).setMaxConnPerRoute(60).build(); 
//			 HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient); 
//			  factory.setConnectTimeout(5000);//展績焼数 竺舛 5段 
//			  factory.setReadTimeout(5000);//展績焼数 竺舛 5段
//			  RestTemplate restTemplate = new RestTemplate(factory);
//			  
//			  HttpHeaders header = new HttpHeaders(); 
//			  header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
//			  header.setContentType(MediaType.APPLICATION_JSON);
//			  
//			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
//			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
//			  HttpEntity<KakaoSendMsgVO> entity = new HttpEntity<>(kakaoSendMsgVO,header);
//			  
//			  //create(String) : URI 梓端 持失敗
//			  URI url = URI.create("http://api.apistore.co.kr/kko/1/msg/{client_id}");
//			  
//			  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//			  
//			  JSONParser jsonPaser = new JSONParser();
//			  Object obj = jsonPaser.parse(response.getBody().toString());
//			  JSONObject jsonObj = (JSONObject)obj;
//			  jsonInString = jsonObj.toString();
//			  
//			  System.out.println("cmid="+jsonObj.get("cmid"));
//			  
//		  }catch(HttpClientErrorException|HttpServerErrorException e) {
//			  System.out.println("ばば");
//			  System.out.println(e.toString()); 
//		  }catch(Exception e) {
//			  System.out.println(e.toString()); 
//		  }
//		  
//		return jsonInString;
//		
//	}
//
//	
//	
//	//軒匂闘 繕噺
//	@PostMapping(value = "/report")
//	public String reportSearch(@RequestBody ReportVO reportVO) {
//		System.out.println(reportVO.getCmid());
//		String cmid = reportVO.getCmid();
//		String jsonInString = "";
//	
//		try { 
//			  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
//			  factory.setConnectTimeout(5000);//展績焼数 竺舛 5段 
//			  factory.setReadTimeout(5000);//展績焼数 竺舛 5段
//			  RestTemplate restTemplate = new RestTemplate(factory);
//			  
//			  HttpHeaders header = new HttpHeaders(); 
//			  header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
//			  header.setContentType(MediaType.APPLICATION_JSON);
//			  
//			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
//			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
//			  HttpEntity<?> entity = new HttpEntity<>(header);
//			  
//			  String url = "http://api.apistore.co.kr/kko/1/report/{client_id}" ;
//			  //create(String) : URI 梓端 持失敗
//			  URI uri = URI.create(url+"?"+"cmid="+cmid);
//			  
//			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
//			  
//			  JSONParser jsonPaser = new JSONParser();
//			  Object obj = jsonPaser.parse(response.getBody().toString());
//			  JSONObject jsonObj = (JSONObject)obj;
//			  jsonInString = jsonObj.toString();
//			  
//			  
//		  }catch(HttpClientErrorException|HttpServerErrorException e) {
//			  System.out.println("ばば");
//			  System.out.println(e.toString()); 
//		  }catch(Exception e) {
//			  System.out.println(e.toString()); 
//		  }
//		return jsonInString;
//	}
//	
//	//奴巴鹸 繕噺
//	@PostMapping(value="/template")
//	public String templateSearch(@RequestBody TemplateVO templateVO) {
//		System.out.println(templateVO.getTempateCode());
//		String template_code = templateVO.getTempateCode();
//		String status = templateVO.getStatus();
//		String jsonInString = "";
//	
//		try { 
//			  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
//			  factory.setConnectTimeout(5000);//展績焼数 竺舛 5段 
//			  factory.setReadTimeout(5000);//展績焼数 竺舛 5段
//			  RestTemplate restTemplate = new RestTemplate(factory);
//			  
//			  HttpHeaders header = new HttpHeaders(); 
//			  header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
//			  header.setContentType(MediaType.APPLICATION_JSON);
//			  
//			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
//			  //RequestBody稽 汽戚斗研 穿含馬澗 HTTP.POST税 井酔 VO 益企稽 汽戚斗 穿含 亜管
//			  HttpEntity<?> entity = new HttpEntity<>(header);
//			  
//			  String url = " http://api.apistore.co.kr/kko/1/template/list/{client_id}" ;
//			  //create(String) : URI 梓端 持失敗
//			  URI uri = URI.create(url+"?"+"template_code="+template_code+"&"+"status="+status);
//			  
//			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
//			  
//			  JSONParser jsonPaser = new JSONParser();
//			  Object obj = jsonPaser.parse(response.getBody().toString());
//			  JSONObject jsonObj = (JSONObject)obj;
//			  jsonInString = jsonObj.toString(); 
//			  
//		  }catch(HttpClientErrorException|HttpServerErrorException e) {
//			  System.out.println("ばば");
//			  System.out.println(e.toString()); 
//		  }catch(Exception e) {
//			  System.out.println(e.toString()); 
//		  }
//		return jsonInString;
//	}
//	
//
//	//降重腰硲 昔装/去系
//	@PostMapping(value="/authentication")
//	public String phoneNumberAuthenticate(@RequestBody AuthenticationVO authenticationVO) {
//		System.out.println(authenticationVO.getComment());
//		KakaoAuthenticationVO kakaoAuthenticationVO = new KakaoAuthenticationVO();
//		kakaoAuthenticationVO.setSendnumber(authenticationVO.getSendNumber());
//		kakaoAuthenticationVO.setComment(authenticationVO.getComment());
//		kakaoAuthenticationVO.setPintype(authenticationVO.getPinType());
//		String jsonInString = "";
//	
//		try { 
//			  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
//			  factory.setConnectTimeout(5000);//展績焼数 竺舛 5段 
//			  factory.setReadTimeout(5000);//展績焼数 竺舛 5段
//			  RestTemplate restTemplate = new RestTemplate(factory);
//			  
//			  HttpHeaders header = new HttpHeaders(); 
//			  header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
//			  header.setContentType(MediaType.APPLICATION_JSON);
//			  
//			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
//
//			  HttpEntity<KakaoAuthenticationVO> entity = new HttpEntity<>(kakaoAuthenticationVO,header);
//			  
//			  String url = "http://api.apistore.co.kr/kko/2/sendnumber/save/{client_id}";
//			  //create(String) : URI 梓端 持失敗
//			  URI uri = URI.create(url);
//			  
//			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
//			  
//			  JSONParser jsonPaser = new JSONParser();
//			  Object obj = jsonPaser.parse(response.getBody().toString());
//			  JSONObject jsonObj = (JSONObject)obj;
//			  jsonInString = jsonObj.toString(); 
//			  
//		  }catch(HttpClientErrorException|HttpServerErrorException e) {
//			  System.out.println("ばば");
//			  System.out.println(e.toString()); 
//		  }catch(Exception e) {
//			  System.out.println(e.toString()); 
//		  }
//		return jsonInString;
//	}
//	
//	//降重腰硲 軒什闘 繕噺
//	@PostMapping(value = "/calling-number")
//	public String callingNumberSearch(@RequestBody CallingNumberVO callingNumberVO) {
//		System.out.println(callingNumberVO.getSendNumber());
//		String sendnumber = callingNumberVO.getSendNumber();
//		String jsonInString = "";
//	
//		try { 
//			  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
//			  factory.setConnectTimeout(5000);//展績焼数 竺舛 5段 
//			  factory.setReadTimeout(5000);//展績焼数 竺舛 5段
//			  RestTemplate restTemplate = new RestTemplate(factory);
//			  
//			  HttpHeaders header = new HttpHeaders(); 
//			  header.set("x-waple-authorization","MS0xMzY1NjY2MTAyDk0LTA2MWE4ZDgyLTZhZmMtNGU5OS05YThkLTgyNmFmYzVlOTkzZ Q==");
//			  header.setContentType(MediaType.APPLICATION_JSON);
//			  
//			  //HttpEntity澗 Http覗稽塘紬聖 戚遂馬澗 搭重税  header人 body淫恵 舛左研 煽舌拝 呪 赤惟懐 敗.
//
//			  HttpEntity<?> entity = new HttpEntity<>(header);
//			  
//			  String url = "http://api.apistore.co.kr/kko/1/sendnumber/list/{client_id}";
//			  //create(String) : URI 梓端 持失敗
//			  URI uri = URI.create(url+"?"+"sendnumber="+sendnumber);
//			  
//			  ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
//			  
//			  JSONParser jsonPaser = new JSONParser();
//			  Object obj = jsonPaser.parse(response.getBody().toString());
//			  JSONObject jsonObj = (JSONObject)obj;
//			  jsonInString = jsonObj.toString(); 
//			  
//		  }catch(HttpClientErrorException|HttpServerErrorException e) {
//			  System.out.println("ばば");
//			  System.out.println(e.toString()); 
//		  }catch(Exception e) {
//			  System.out.println(e.toString()); 
//		  }
//		
//		return jsonInString;
//	}
//	
//	
//	
//}
