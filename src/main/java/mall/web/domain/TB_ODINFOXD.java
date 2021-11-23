package mall.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.domain
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 주문정보 상세 DTO
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-13 (오후 08:10:10)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Alias("tb_odinfoxd")
@XmlRootElement(name="tb_odinfoxd")
public class TB_ODINFOXD extends DefaultDomain{
	
	private static final long serialVersionUID = -1471161809434513649L;
	
	private String ORDER_DTNUM;	//NUMBER(5)				주문상세번호
	private String ORDER_NUM;	//VARCHAR2(50)			주문번호
	private String PD_CODE;		//VARCHAR2(20)			제품코드
	private String PD_NAME;		//VARCHAR2(30)			제품명
	private String PD_PRICE;	//NUMBER(15)			제품가격
	private String REAL_PRICE;	//NUMBER(15)			제품판매가격
	private String PDDC_GUBN;	//VARCHAR2(35)			제품할인 구분
	private String PDDC_VAL;	//NUMBER(15)			제품할인 값
	private String BUNDLE_CNT;	//VARCHAR2(30)			상품묶음단위
	private String ORDER_QTY;	//NUMBER(5)				주문 수량
	private String ORDER_AMT;	//NUMBER(15)			주문금액
	private String DLVY_AMT;	//NUMBER(15)	Y		배송비
	private String USE_SVMN;	//NUMBER(15)	Y		사용적립금
	private String OCCUR_SVMN;	//NUMBER(15)	Y		발생적립금
	private String ORDER_CON;	//VARCHAR2(35)	Y		주문상태
	private String PAY_METD;	//VARCHAR2(35)	Y		결제수단
	private String PAY_MDKY;	//VARCHAR2(50)	Y		결제모듈키
	private String DLVY_CON;	//VARCHAR2(35)	Y		배송상태
	private String DLVY_COM;	//VARCHAR2(35)	Y		배송업체
	private String DLVY_TDN;	//VARCHAR2(50)	Y		배송운송장번호
	private String CNCL_CON;	//VARCHAR2(35)	Y		취소상태
	private String RFND_CON;	//VARCHAR2(35)	Y		환불상태
	private String BSKT_REGNO;	//VARCHAR2(35)	Y		환불상태
	
	private String PD_CUT_SEQ;		// VARCHAR2(10) 	제품세절방식
	private String PD_CUT_SEQ_UNIT;		// VARCHAR2(10) 	제품세절방식
	private String OPTION_CODE;		// VARCHAR2(35) 	선택옵션
	private String OPTION_NAME;		// VARCHAR2(35) 	선택옵션이름
	
	private String BOX_PDDC_GUBN;
	private String BOX_PDDC_VAL;
	private String INPUT_CNT;
	

	public String getOPTION_NAME() {
		return OPTION_NAME;
	}
	public void setOPTION_NAME(String oPTION_NAME) {
		OPTION_NAME = oPTION_NAME;
	}
	private String[] PD_CODES;		//VARCHAR2(20)			제품코드
	private String[] PD_NAMES;		//VARCHAR2(30)			제품명
	private String[] PD_PRICES;		//NUMBER(15)			제품가격
	private String[] PDDC_GUBNS;	//VARCHAR2(35)			제품할인 구분
	private String[] PDDC_VALS;		//NUMBER(15)			제품할인 값
	private String[] ORDER_QTYS;	//NUMBER(5)				주문 수량
	private String[] ORDER_AMTS;	//NUMBER(15)			주문금액
	private String[] PD_CUT_SEQS;		// VARCHAR2(10) 	제품세절방식
	private String[] OPTION_CODES;		// VARCHAR2(35) 	선택옵션
	private String[] BOX_PDDC_GUBNS;
	private String[] BOX_PDDC_VALS;
	private String[] INPUT_CNTS;	
	
	
	
	
	public String getORDER_DTNUM() {
		return ORDER_DTNUM;
	}
	public void setORDER_DTNUM(String oRDER_DTNUM) {
		ORDER_DTNUM = oRDER_DTNUM;
	}
	public String getORDER_NUM() {
		return ORDER_NUM;
	}
	public void setORDER_NUM(String oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}
	public String getPD_CODE() {
		return PD_CODE;
	}
	public void setPD_CODE(String pD_CODE) {
		PD_CODE = pD_CODE;
	}
	public String getPD_NAME() {
		return PD_NAME;
	}
	public void setPD_NAME(String pD_NAME) {
		PD_NAME = pD_NAME;
	}
	public String getPD_PRICE() {
		return PD_PRICE;
	}
	public void setPD_PRICE(String pD_PRICE) {
		PD_PRICE = pD_PRICE;
	}
	public String getPDDC_GUBN() {
		return PDDC_GUBN;
	}
	public void setPDDC_GUBN(String pDDC_GUBN) {
		PDDC_GUBN = pDDC_GUBN;
	}
	public String getPDDC_VAL() {
		return PDDC_VAL;
	}
	public void setPDDC_VAL(String pDDC_VAL) {
		PDDC_VAL = pDDC_VAL;
	}
	public String getBUNDLE_CNT() {
		return BUNDLE_CNT;
	}
	public void setBUNDLE_CNT(String bUNDLE_CNT) {
		BUNDLE_CNT = bUNDLE_CNT;
	}
	public String getORDER_QTY() {
		return ORDER_QTY;
	}
	public void setORDER_QTY(String oRDER_QTY) {
		ORDER_QTY = oRDER_QTY;
	}
	public String getORDER_AMT() {
		return ORDER_AMT;
	}
	public void setORDER_AMT(String oRDER_AMT) {
		ORDER_AMT = oRDER_AMT;
	}
	public String getDLVY_AMT() {
		return DLVY_AMT;
	}
	public void setDLVY_AMT(String dLVY_AMT) {
		DLVY_AMT = dLVY_AMT;
	}
	public String getUSE_SVMN() {
		return USE_SVMN;
	}
	public void setUSE_SVMN(String uSE_SVMN) {
		USE_SVMN = uSE_SVMN;
	}
	public String getOCCUR_SVMN() {
		return OCCUR_SVMN;
	}
	public void setOCCUR_SVMN(String oCCUR_SVMN) {
		OCCUR_SVMN = oCCUR_SVMN;
	}
	public String getORDER_CON() {
		return ORDER_CON;
	}
	public void setORDER_CON(String oRDER_CON) {
		ORDER_CON = oRDER_CON;
	}
	public String getPAY_METD() {
		return PAY_METD;
	}
	public void setPAY_METD(String pAY_METD) {
		PAY_METD = pAY_METD;
	}
	public String getPAY_MDKY() {
		return PAY_MDKY;
	}
	public void setPAY_MDKY(String pAY_MDKY) {
		PAY_MDKY = pAY_MDKY;
	}
	public String getDLVY_CON() {
		return DLVY_CON;
	}
	public void setDLVY_CON(String dLVY_CON) {
		DLVY_CON = dLVY_CON;
	}
	public String getDLVY_COM() {
		return DLVY_COM;
	}
	public void setDLVY_COM(String dLVY_COM) {
		DLVY_COM = dLVY_COM;
	}
	public String getDLVY_TDN() {
		return DLVY_TDN;
	}
	public void setDLVY_TDN(String dLVY_TDN) {
		DLVY_TDN = dLVY_TDN;
	}
	public String getCNCL_CON() {
		return CNCL_CON;
	}
	public void setCNCL_CON(String cNCL_CON) {
		CNCL_CON = cNCL_CON;
	}
	public String getRFND_CON() {
		return RFND_CON;
	}
	public void setRFND_CON(String rFND_CON) {
		RFND_CON = rFND_CON;
	}
	public String[] getPD_CODES() {
		return PD_CODES;
	}
	public void setPD_CODES(String[] pD_CODES) {
		PD_CODES = pD_CODES;
	}
	public String[] getPD_NAMES() {
		return PD_NAMES;
	}
	public void setPD_NAMES(String[] pD_NAMES) {
		PD_NAMES = pD_NAMES;
	}
	public String[] getPD_PRICES() {
		return PD_PRICES;
	}
	public void setPD_PRICES(String[] pD_PRICES) {
		PD_PRICES = pD_PRICES;
	}
	public String[] getPDDC_GUBNS() {
		return PDDC_GUBNS;
	}
	public void setPDDC_GUBNS(String[] pDDC_GUBNS) {
		PDDC_GUBNS = pDDC_GUBNS;
	}
	public String[] getPDDC_VALS() {
		return PDDC_VALS;
	}
	public void setPDDC_VALS(String[] pDDC_VALS) {
		PDDC_VALS = pDDC_VALS;
	}
	public String[] getORDER_QTYS() {
		return ORDER_QTYS;
	}
	public void setORDER_QTYS(String[] oRDER_QTYS) {
		ORDER_QTYS = oRDER_QTYS;
	}
	public String[] getORDER_AMTS() {
		return ORDER_AMTS;
	}
	public void setORDER_AMTS(String[] oRDER_AMTS) {
		ORDER_AMTS = oRDER_AMTS;
	}
	public String getBSKT_REGNO() {
		return BSKT_REGNO;
	}
	public void setBSKT_REGNO(String bSKT_REGNO) {
		BSKT_REGNO = bSKT_REGNO;
	}
	public String getREAL_PRICE() {
		return REAL_PRICE;
	}
	public void setREAL_PRICE(String rEAL_PRICE) {
		REAL_PRICE = rEAL_PRICE;
	}
	public String getPD_CUT_SEQ() {
		return PD_CUT_SEQ;
	}
	public void setPD_CUT_SEQ(String pD_CUT_SEQ) {
		PD_CUT_SEQ = pD_CUT_SEQ;
	}
	public String[] getPD_CUT_SEQS() {
		return PD_CUT_SEQS;
	}
	public void setPD_CUT_SEQS(String[] pD_CUT_SEQS) {
		PD_CUT_SEQS = pD_CUT_SEQS;
	}
	public String getPD_CUT_SEQ_UNIT() {
		return PD_CUT_SEQ_UNIT;
	}
	public void setPD_CUT_SEQ_UNIT(String pD_CUT_SEQ_UNIT) {
		PD_CUT_SEQ_UNIT = pD_CUT_SEQ_UNIT;
	}
	public String getOPTION_CODE() {
		return OPTION_CODE;
	}
	public void setOPTION_CODE(String oPTION_CODE) {
		OPTION_CODE = oPTION_CODE;
	}
	public String[] getOPTION_CODES() {
		return OPTION_CODES;
	}
	public void setOPTION_CODES(String[] oPTION_CODES) {
		OPTION_CODES = oPTION_CODES;
	}
	public String getBOX_PDDC_GUBN() {
		return BOX_PDDC_GUBN;
	}
	public void setBOX_PDDC_GUBN(String bOX_PDDC_GUBN) {
		BOX_PDDC_GUBN = bOX_PDDC_GUBN;
	}
	public String getBOX_PDDC_VAL() {
		return BOX_PDDC_VAL;
	}
	public void setBOX_PDDC_VAL(String bOX_PDDC_VAL) {
		BOX_PDDC_VAL = bOX_PDDC_VAL;
	}
	public String getINPUT_CNT() {
		return INPUT_CNT;
	}
	public void setINPUT_CNT(String iNPUT_CNT) {
		INPUT_CNT = iNPUT_CNT;
	}
	public String[] getBOX_PDDC_GUBNS() {
		return BOX_PDDC_GUBNS;
	}
	public void setBOX_PDDC_GUBNS(String[] bOX_PDDC_GUBNS) {
		BOX_PDDC_GUBNS = bOX_PDDC_GUBNS;
	}
	public String[] getBOX_PDDC_VALS() {
		return BOX_PDDC_VALS;
	}
	public void setBOX_PDDC_VALS(String[] bOX_PDDC_VALS) {
		BOX_PDDC_VALS = bOX_PDDC_VALS;
	}
	public String[] getINPUT_CNTS() {
		return INPUT_CNTS;
	}
	public void setINPUT_CNTS(String[] iNPUT_CNTS) {
		INPUT_CNTS = iNPUT_CNTS;
	}
}
