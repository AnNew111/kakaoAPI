package kakao;

public enum ReportMsgRsltCode {

	B00("성공","00"),
	B01("전송시간_초과","01"),
	B02("잘못된_전화번호or비가입자","02"),
	B05("통신사_결과_미수신","05"),
	B08("단말기_BUSY","08"),
	B09("음영지역","09"),
	B0a("건수_부족","0a"),
	B0b("전송_권한_없음","0b"),
	B0c("중복된_키_접수_차단","0c"),
	B0e("서버_내부_에러","0e"),
	B0o("TIME_OUT_처리","0o"),
	B0p("메시지본문_중복차단","0p"),
	B0q("메시지_중복키_체크","0q"),
	B0t("잘못된_동보_전송_수신번호_리스트카운트","0t"),
	B0A("단말기_메시지_저장개수_초과","0A"),
	B0B("단말기_일시_서비스_정지","0B"),
	B0C("기타_단말기_문제","0c"),
	B0D("착신_거절","0D"),
	B0E("전원_꺼짐","0E"),
	B0F("기타","0F"),
	B0G("내부_포맷_에러","0G"),
	B0H("통신사_포맷_에러","0H"),
	B0I("SMS/MMS서비스_불가_단말기","0I"),
	B0J("착신_측_호출_불가상태","0J"),
	B0L("통신사에서_메시지_처리불가_상태","0L"),
	B0M("무선망단_전송_실패","0M"),
	B0S("스팸","0S"),
	B0V("컨텐츠_사이즈_초과","0V"),
	B0U("잘못된_컨텐츠","0U");
	
	
	private final String msg;
	private final String code;
	
	ReportMsgRsltCode(String msg,String code){
		this.msg = msg;
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getValue() {
		return msg+code;
	}
	
	
}
