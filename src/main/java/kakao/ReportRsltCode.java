package kakao;

public enum ReportRsltCode {
	A1("발신_프로필_키가_유효하지_않음","1"),
	A2("서버와_연결되어있지않은_사용자","2"),
	A5("메시지_발송_후_수신여부_불투명","5"),
	A0("성공","0"),
	A6("메시지_전성결과를_찾을_수_없음","6"),
	A8("메시지를_전송할_수_없는_상태","8"),
	A9("최근_카카오톡_미사용자","9"),
	Aa("건수_부족","a"),
	Ab("전송_권한_없음","b"),
	Ac("중복된_키_접수_차단","c"),
	Ad("중복된_수신번호_접수_차단","d"),
	Ae("서버_내부_에러","e"),
	Af("메시지_포맷_에러","f"),
	Ak("메시지가_존재하지않음","k"),
	Ao("TIME_OUT처리","o"),
	Ap("메시지본문_중복_차단","p"),
	At("메시지가_비어있음","t"),
	AA("카카오톡_미사용자","A"),
	AB("알림톡_차단을_선택한_사용자","B"),
	AC("메시지_일련번호_중복","C"),
	AD("5초이내_메시지_중복발송","D"),
	AE("미지원_클라이언트_버전","E"),
	AF("기타_오류","F"),
	AH("카카오_시스템_오류","H"),
	AI("전화번호_오류","I"),
	AJ("050_안심번호_발송불가","J"),
	AL("메세지 길이제한 오류","L"),
	AM("템플릿을_찾을_수_없음","M"),
	AS("발신번호_검증_오류","S"),
	AU("메시지가_템플릿과_일치하지않음","U"),
	AV("메시지가_템플릿과_비교실패","V");
	
	private final String msg;
	private final String code;

	ReportRsltCode(String msg,String code){
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
