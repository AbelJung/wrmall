package mall.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.domain
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 관심상품
 * @Company	: YT Corp.
 * @Author	: Young-dae Kim (sjie1638@youngthink.co.kr)
 * @Date	: 2016-07-25 (오후 3:21:06)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Alias("tb_ipinfoxm")
@XmlRootElement(name="tb_ipinfoxm")
public class TB_IPINFOXM extends TB_PDINFOXM {

	private static final long serialVersionUID = -178717921067680136L;

	private String INTPD_REGNO;//	VARCHAR2(15)			관심상품 등록번호
	private String INTPD_REGDT;//	DATE			관심상품 등록일시
	private String MEMB_ID;//	VARCHAR2(50)			회원ID
	private String PD_CODE;//	VARCHAR2(20)			제품코드
	private String PD_NAME;//	VARCHAR2(30)			제품명
	private String PD_QTY;//	NUMBER(5)			제품 수량
	private String PD_PRICE;//	NUMBER(15)			제품가격
	private String PDDC_GUBN;//	VARCHAR2(35)			제품할인 구분
	private String PDDC_VAL;//	NUMBER(15)			제품할인 값
	private String OCCUR_SVMN;//	NUMBER(15)	Y		발생적립금
	
	private String REAL_PRICE;		//실제값
	
	//파일관련
	private String FILE_ID;//파일ID
	private String RPIMG_SEQ;//대표이미지 순번
	private String ATFL_ID;//대표이미지 순번
	
	
	private String INTPD_REGNO_LIST; //장바구니 & 삭제 이동 항목
	private String STATE_GUBUN; //장바구니 & 삭제 구분
	private String SALE_CON;
	
	private String PD_CUT_SEQ;
	private String OPTION_CODE;
	
	private String FILEPATH_FLAG;
		
	public String getOPTION_CODE() {
		return OPTION_CODE;
	}
	public void setOPTION_CODE(String oPTION_CODE) {
		OPTION_CODE = oPTION_CODE;
	}
	public String getINTPD_REGNO() {
		return INTPD_REGNO;
	}
	public void setINTPD_REGNO(String iNTPD_REGNO) {
		INTPD_REGNO = iNTPD_REGNO;
	}
	public String getINTPD_REGDT() {
		return INTPD_REGDT;
	}
	public void setINTPD_REGDT(String iNTPD_REGDT) {
		INTPD_REGDT = iNTPD_REGDT;
	}
	public String getMEMB_ID() {
		return MEMB_ID;
	}
	public void setMEMB_ID(String mEMB_ID) {
		MEMB_ID = mEMB_ID;
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
	public String getPD_QTY() {
		return PD_QTY;
	}
	public void setPD_QTY(String pD_QTY) {
		PD_QTY = pD_QTY;
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
	public String getOCCUR_SVMN() {
		return OCCUR_SVMN;
	}
	public void setOCCUR_SVMN(String oCCUR_SVMN) {
		OCCUR_SVMN = oCCUR_SVMN;
	}
	public String getREAL_PRICE() {
		return REAL_PRICE;
	}
	public void setREAL_PRICE(String rEAL_PRICE) {
		REAL_PRICE = rEAL_PRICE;
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
	public String getATFL_ID() {
		return ATFL_ID;
	}
	public void setATFL_ID(String aTFL_ID) {
		ATFL_ID = aTFL_ID;
	}
	public String getSTATE_GUBUN() {
		return STATE_GUBUN;
	}
	public void setSTATE_GUBUN(String sTATE_GUBUN) {
		STATE_GUBUN = sTATE_GUBUN;
	}
	public String getINTPD_REGNO_LIST() {
		return INTPD_REGNO_LIST;
	}
	public void setINTPD_REGNO_LIST(String iNTPD_REGNO_LIST) {
		INTPD_REGNO_LIST = iNTPD_REGNO_LIST;
	}
	public String getSALE_CON() {
		return SALE_CON;
	}
	public void setSALE_CON(String sALE_CON) {
		SALE_CON = sALE_CON;
	}
	public String getPD_CUT_SEQ() {
		return PD_CUT_SEQ;
	}
	public void setPD_CUT_SEQ(String pD_CUT_SEQ) {
		PD_CUT_SEQ = pD_CUT_SEQ;
	}
	public String getFILEPATH_FLAG() {
		return FILEPATH_FLAG;
	}
	public void setFILEPATH_FLAG(String fILEPATH_FLAG) {
		FILEPATH_FLAG = fILEPATH_FLAG;
	}

}
