package kakao;

public enum KakaoReportCode {
	성공("0"),
	발신_프로필_키가_유효하지_않음("1"),
	서버와_연결되어있지않은_사용자("2"),
	메시지_발송_후_수신여부_불투명("5"),
	메시지_전성결과를_찾을_수_없음("6");
	
	private final String value;
	
	KakaoReportCode(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
