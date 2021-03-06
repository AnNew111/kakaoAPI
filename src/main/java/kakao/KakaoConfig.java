
package kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Configuration
@PropertySource("classpath:application.yml")
public class KakaoConfig {


	@Value("${key}")
	private String key;

	@Value("${value}")
	private String value;

	@Value("${sendMsgUri}")
	private String sendMsgUri;
	
	@Value("${reportUri}")
	private String reportUri;
	
	@Value("${templateUri}")
	private String templateUri;
	
	@Value("${sendNumberSaveUri}")
	private String sendNumberSaveUri;
	
	@Value("${sendNumberListUri}")
	private String sendNumberListUri;
	
	@Value("${clientId}")
	private String clientId;
	
	
	
}
