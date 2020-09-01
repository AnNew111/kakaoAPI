
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

/*   RestTemplate - ���������� �����ϴ� http��ſ� �����ϰ� �� �� �ִ� ���ø��̸�, HTTP�������� ����� �ܼ�ȭ�ϰ� RESTful��Ģ�� ��Ŵ
 * 			���ø����̼��� RestTemplate�� �����ϰ�, URI, HTTP�޼ҵ� ���� ����� ��� ��û�Ѵ�.
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
			factory.setReadTimeout(5000); // �б�ð��ʰ�
			factory.setConnectTimeout(3000); // ������ ������ ���� ���� Ÿ�Ӿƿ� 
			HttpClient httpClient = HttpClientBuilder.create() 
			        .setMaxConnTotal(100) // �ִ� Ŀ�ؼ� ����
			        .setMaxConnPerRoute(5) // IP/domain name�� �ִ� Ŀ�ؼ� ���� 
			        .build(); 
			factory.setHttpClient(httpClient); // ������࿡ ���� HttpClient ����
			HttpClient �� HttpUrlConnection�� ���� ��� �����ڵ� �б� ����(��Ű����,Ÿ�Ӿƿ����� ����)<----
									->������ ���ھ��� RestTemplate�� �����ϰ� �Ǹ� SimpleClientHttpRequestFactory����ü�� ����ϰԵ� �̴� HttpUrlConnection�� �̿��Ѵ�.
					RestTemplate �� ResponseErrorHandler �� ������ Ȯ���ϰ� �ִٸ� ó�������� �¿��.
			 		HttpMessageConverter �� �̿��ؼ� ����޼����� java object(Class responseType) �� ��ȯ�Ѵ�.
					���ø����̼ǿ� ��ȯ�ȴ�.
 
 */
  @RestController
  public class RestAPI_Test {
  
  
  @GetMapping("/test")
  public String calAPI() {
  
	  String jsonInString = "";
	  String a = "";
	  try { 
		  HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(); 
		  factory.setConnectTimeout(5000);//Ÿ�Ӿƿ� ���� 5�� 
		  factory.setReadTimeout(5000);//Ÿ�Ӿƿ� ���� 5��
		  RestTemplate restTemplate = new RestTemplate(factory);
		  
		  HttpHeaders header = new HttpHeaders(); 
		  header.set("Authorization","Bearer 14097621-94af-4a7a-85bd-fac0c1f9f19a");
		  header.setContentType(MediaType.APPLICATION_JSON);
		  
		  //HttpEntity�� Http���������� �̿��ϴ� �����  header�� body���� ������ ������ �� �ְԲ� ��.
		  HttpEntity<?> entity = new HttpEntity<>(header);
		  
		  //create(String) : URI ��ü ������(������ ��� ���ϰ�)
		  URI url = URI.create("https://goqual.io/openapi/devices");
		  
		  //ResponseEntity�� @ResponseBody������̼ǰ� ���� �ǹ̷�, ResponseEntity�� return Type���� �����ϸ� JSON(default)�Ǵ�  xml Format���� ����� �����ش�
		  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		  
		  JSONParser jsonPaser = new JSONParser();
		  JSONArray jsonArray = (JSONArray) jsonPaser.parse(response.getBody().toString());
		  jsonInString = jsonArray.toString();
		  
	  }catch(HttpClientErrorException|HttpServerErrorException e) {
		  System.out.println("�Ф�");
		  System.out.println(e.toString()); 
	  }catch(Exception e) {
		  System.out.println(e.toString()); 
	  }
	  
	  return jsonInString;
  
  	}
  
  
  }
 