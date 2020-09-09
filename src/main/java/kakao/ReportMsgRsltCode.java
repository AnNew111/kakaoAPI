package kakao;

public enum ReportMsgRsltCode {

	B00("성공"),
	B01("전송시간_초과"),
	B02("잘못된_전화번호or비가입자"),
	B05("통신사_결과_미수신"),
	B08("단말기_BUSY"),
	B09("음영지역"),
	B0a("건수_부족"),
	B0b("전송_권한_없음"),
	B0c("중복된_키_접수_차단"),
	B0e("서버_내부_에러"),
	B0o("TIME_OUT_처리"),
	B0p("메시지본문_중복차단"),
	B0q("메시지_중복키_체크"),
	B0t("잘못된_동보_전송_수신번호_리스트카운트"),
	B0A("단말기_메시지_저장개수_초과"),
	B0B("단말기_일시_서비스_정지"),
	B0C("기타_단말기_문제"),
	B0D("착신_거절"),
	B0E("전원_꺼짐"),
	B0F("기타"),
	B0G("내부_포맷_에러"),
	B0H("통신사_포맷_에러"),
	B0I("SMS/MMS서비스_불가_단말기"),
	B0J("착신_측_호출_불가상태"),
	B0L("통신사에서_메시지_처리불가_상태"),
	B0M("무선망단_전송_실패"),
	B0S("스팸"),
	B0V("컨텐츠_사이즈_초과"),
	B0U("잘못된_컨텐츠");
	
	
//	private final Integer code;
	
	private final String value;
	
	ReportMsgRsltCode(String value){
		this.value = value;
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	
	
	
}
