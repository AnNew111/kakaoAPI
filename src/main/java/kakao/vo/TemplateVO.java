package kakao.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TemplateVO {
	
	@NotNull(message = "templateCode는 Null값이 올 수 없습니다.")
	@Size(min = 3,max = 7,message = "템플릿코드를 정확히 입력하세요")
	private String tempateCode;
	
	@NotNull(message = "status는 Null값이 올 수 없습니다.")
	@Pattern(regexp = "^([1-5])$")
	private String status;

	
}
