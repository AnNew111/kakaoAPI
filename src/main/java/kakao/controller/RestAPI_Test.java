
package kakao.controller;
  
import java.net.URI;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/*   RestTemplate - 스프링에서 제공하는 http통신에 유용하게 쓸 수 있는 템플릿이며, HTTP서버와의 통신을 단순화하고 RESTful원칙을 지킴
 * 			어플리케이션이 RestTemplate를 생성하고, URI, HTTP메소드 등의 헤더를 담아 요청한다.
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
			factory.setReadTimeout(5000); // 읽기시간초과
			factory.setConnectTimeout(3000); // 서버에 연결을 맺을 때의 타임아웃 
			HttpClient httpClient = HttpClientBuilder.create() 
			        .setMaxConnTotal(100) // 최대 커넥션 갯수
			        .setMaxConnPerRoute(5) // IP/domain name당 최대 커넥션 갯수 
			        .build(); 
			factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
			HttpClient 는 HttpUrlConnection에 비해 모든 응답코드 읽기 가능(쿠키제어,타임아웃설정 가능)<----
									->별도의 인자없이 RestTemplate를 생성하게 되면 SimpleClientHttpRequestFactory구현체를 사용하게됨 이는 HttpUrlConnection을 이용한다.
					RestTemplate 는 ResponseErrorHandler 로 오류를 확인하고 있다면 처리로직을 태운다.
			 		HttpMessageConverter 를 이용해서 응답메세지를 java object(Class responseType) 로 변환한다.
					어플리케이션에 반환된다.
 
 */
  @RestController
  public class RestAPI_Test {
  
  
  @GetMapping("/test")
  public String calAPI() {
  
	  String jsonInString = "";
	  String a = "";
	  try { 
		  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
		  factory.setConnectTimeout(5000);//타임아웃 설정 5초 
		  factory.setReadTimeout(5000);//타임아웃 설정 5초
		  RestTemplate restTemplate = new RestTemplate(factory);
		  
		  HttpHeaders header = new HttpHeaders(); 
		  header.set("Authorization","Bearer 14097621-94af-4a7a-85bd-fac0c1f9f19a");
		  header.setContentType(MediaType.APPLICATION_JSON);
		  
		  //HttpEntity는 Http프로토콜을 이용하는 통신의  header와 body관련 정보를 저장할 수 있게끔 함.
		  HttpEntity<?> entity = new HttpEntity<>(header);
		  
		  //create(String) : URI 객체 생성함(생성자 사용 안하고)
		  URI url = URI.create("https://goqual.io/openapi/devices");
		  
		  //ResponseEntity는 @ResponseBody어노테이션과 같은 의미로, ResponseEntity를 return Type으로 지정하면 JSON(default)또는  xml Format으로 결과를 내려준다
		  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		  
		  JSONParser jsonPaser = new JSONParser();
		  JSONArray jsonArray = (JSONArray) jsonPaser.parse(response.getBody().toString());
		  jsonInString = jsonArray.toString();
		  
	  }catch(HttpClientErrorException|HttpServerErrorException e) {
		  System.out.println("ㅠㅠ");
		  System.out.println(e.toString()); 
	  }catch(Exception e) {
		  System.out.println(e.toString()); 
	  }
	  
	  return jsonInString;
  
  	}
  
  
  }
 