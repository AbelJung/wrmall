package mall.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.domain
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 상품게시판
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-21 (오후 1:57:40)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Alias("tb_pdbordxm")
@XmlRootElement(name="tb_pdbordxm")
public class TB_PDBORDXM extends DefaultDomain{

	private static final long serialVersionUID = 1733226044024796314L;
	
	private String BRD_NUM;//	VARCHAR2(15)			게시글 번호
	private String BRD_GUBN;//	VARCHAR2(35)			게시글 구분
	private String PD_CODE;//	VARCHAR2(20)	Y		제품코드
	private String BRD_SBJT;//	VARCHAR2(200)	Y		게시글 제목
	private String BRD_CONT;//	CLOB	Y		게시글 내용
	private String PD_PTS;//	NUMBER(5)	Y		상품 별점
	private String BRD_HITS;//	NUMBER(5)	Y		게시글 조회수
	private String WRTR_ID;//	VARCHAR2(50)	Y		작성자ID
	private String WRT_DTM;//	DATE	Y		작성일시
	private String SCWT_YN;//	VARCHAR2(1)	Y		비밀글여부
	private String BRD_PW;//	VARCHAR2(64)	Y		게시글 비밀번호
	private String QNA_REPLY;//	CLOB	Y		QNA답변
	private String REP_ID;//	VARCHAR2(50)	Y		답변자ID
	private String REPLY_DTM;//	DATE	Y		답변일시
	
	private String WRTR_NM;//작성자ID명
	private String REP_NM;//답변자ID명
	
	private String ORDER_GUBUN;//등록일 or 답변일 정렬 구분
	private String WRTR_ORDER; //주문일 정렬 
	private String REPLY_ORDER; //답변일 정렬 
	private String PD_NAME_ORDER; //제품명 정렬 
	
	
	private String REPLY_YN; //답변여부
	private String DEL_YN; //삭제여부
	
	private String PD_NAME; //제품명
	
	private String FILE_ID;//파일ID
	private String RPIMG_SEQ;//대표이미지 순번
	
	private String schCago;//검색 카테고리
	private String schCagoGubun;//검색 카테고리 단계 구분
	private String schCago01;//검색 카테고리 1단계
	private String schCago02;//검색 카테고리 2단계
	private String schCago03;//검색 카테고리 3단계
	private String schCago04;//검색 카테고리 4단계
	
	public String getBRD_NUM() {
		return BRD_NUM;
	}
	public void setBRD_NUM(String bRD_NUM) {
		BRD_NUM = bRD_NUM;
	}
	public String getBRD_GUBN() {
		return BRD_GUBN;
	}
	public void setBRD_GUBN(String bRD_GUBN) {
		BRD_GUBN = bRD_GUBN;
	}
	public String getBRD_SBJT() {
		return BRD_SBJT;
	}
	public void setBRD_SBJT(String bRD_SBJT) {
		BRD_SBJT = bRD_SBJT;
	}
	public String getBRD_CONT() {
		return BRD_CONT;
	}
	public void setBRD_CONT(String bRD_CONT) {
		BRD_CONT = bRD_CONT;
	}
	public String getPD_PTS() {
		return PD_PTS;
	}
	public void setPD_PTS(String pD_PTS) {
		PD_PTS = pD_PTS;
	}
	public String getBRD_HITS() {
		return BRD_HITS;
	}
	public void setBRD_HITS(String bRD_HITS) {
		BRD_HITS = bRD_HITS;
	}
	public String getWRTR_ID() {
		return WRTR_ID;
	}
	public void setWRTR_ID(String wRTR_ID) {
		WRTR_ID = wRTR_ID;
	}
	public String getWRT_DTM() {
		return WRT_DTM;
	}
	public void setWRT_DTM(String wRT_DTM) {
		WRT_DTM = wRT_DTM;
	}
	public String getSCWT_YN() {
		return SCWT_YN;
	}
	public void setSCWT_YN(String sCWT_YN) {
		SCWT_YN = sCWT_YN;
	}
	public String getBRD_PW() {
		return BRD_PW;
	}
	public void setBRD_PW(String bRD_PW) {
		BRD_PW = bRD_PW;
	}
	public String getQNA_REPLY() {
		return QNA_REPLY;
	}
	public void setQNA_REPLY(String qNA_REPLY) {
		QNA_REPLY = qNA_REPLY;
	}
	public String getREP_ID() {
		return REP_ID;
	}
	public void setREP_ID(String rEP_ID) {
		REP_ID = rEP_ID;
	}
	public String getREPLY_DTM() {
		return REPLY_DTM;
	}
	public void setREPLY_DTM(String rEPLY_DTM) {
		REPLY_DTM = rEPLY_DTM;
	}
	public String getWRTR_NM() {
		return WRTR_NM;
	}
	public void setWRTR_NM(String wRTR_NM) {
		WRTR_NM = wRTR_NM;
	}
	public String getREP_NM() {
		return REP_NM;
	}
	public void setREP_NM(String rEP_NM) {
		REP_NM = rEP_NM;
	}
	public String getORDER_GUBUN() {
		return ORDER_GUBUN;
	}
	public void setORDER_GUBUN(String oRDER_GUBUN) {
		ORDER_GUBUN = oRDER_GUBUN;
	}
	public String getWRTR_ORDER() {
		return WRTR_ORDER;
	}
	public void setWRTR_ORDER(String wRTR_ORDER) {
		WRTR_ORDER = wRTR_ORDER;
	}
	public String getREPLY_ORDER() {
		return REPLY_ORDER;
	}
	public void setREPLY_ORDER(String rEPLY_ORDER) {
		REPLY_ORDER = rEPLY_ORDER;
	}
	public String getPD_CODE() {
		return PD_CODE;
	}
	public void setPD_CODE(String pD_CODE) {
		PD_CODE = pD_CODE;
	}
	public String getREPLY_YN() {
		return REPLY_YN;
	}
	public void setREPLY_YN(String rEPLY_YN) {
		REPLY_YN = rEPLY_YN;
	}
	public String getDEL_YN() {
		return DEL_YN;
	}
	public void setDEL_YN(String dEL_YN) {
		DEL_YN = dEL_YN;
	}
	public String getPD_NAME() {
		return PD_NAME;
	}
	public void setPD_NAME(String pD_NAME) {
		PD_NAME = pD_NAME;
	}
	public String getPD_NAME_ORDER() {
		return PD_NAME_ORDER;
	}
	public void setPD_NAME_ORDER(String pD_NAME_ORDER) {
		PD_NAME_ORDER = pD_NAME_ORDER;
	}
	public String getFILE_ID() {
		return FILE_ID;
	}
	public void setFILE_ID(String fILE_ID) {
		FILE_ID = fILE_ID;
	}
	public String getRPIMG_SEQ() {
		return RPIMG_SEQ;
	}
	public void setRPIMG_SEQ(String rPIMG_SEQ) {
		RPIMG_SEQ = rPIMG_SEQ;
	}
	public String getSchCago01() {
		return schCago01;
	}
	public void setSchCago01(String schCago01) {
		this.schCago01 = schCago01;
	}
	public String getSchCago02() {
		return schCago02;
	}
	public void setSchCago02(String schCago02) {
		this.schCago02 = schCago02;
	}
	public String getSchCago03() {
		return schCago03;
	}
	public void setSchCago03(String schCago03) {
		this.schCago03 = schCago03;
	}
	public String getSchCago04() {
		return schCago04;
	}
	public void setSchCago04(String schCago04) {
		this.schCago04 = schCago04;
	}
	public String getSchCago() {
		return schCago;
	}
	public void setSchCago(String schCago) {
		this.schCago = schCago;
	}
	public String getSchCagoGubun() {
		return schCagoGubun;
	}
	public void setSchCagoGubun(String schCagoGubun) {
		this.schCagoGubun = schCagoGubun;
	}

}
