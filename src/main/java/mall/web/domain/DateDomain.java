package mall.web.domain;

import java.io.Serializable;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Package	: mall.web.domain
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * @Desc	: 기본 DTO
 * @Company	: YT Corp.
 * @Author	: Tae-seok Choi (tschoi@youngthink.co.kr)
 * @Date	: 2016-07-11 (오후 11:09:33)
 * @Version	: 1.0
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/
@Alias("dateDomain")
@XmlRootElement(name="dateDomain")

public class DateDomain extends DefaultDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1025780798422070579L;
	
	private String DAY1;
	private String DAY2;
	private String DAY3;
	private String DAY4;
	private String DAY5;
	private String DAY6;
	private String DAY7;
	private String DAY8;
	private String DAY9;
	private String DAY10;
	private String DAY11;
	private String DAY12;
	private String DAY13;
	private String DAY14;
	private String DAY15;
	private String DAY16;
	private String DAY17;
	private String DAY18;
	private String DAY19;
	private String DAY20;
	private String DAY21;
	private String DAY22;
	private String DAY23;
	private String DAY24;
	private String DAY25;
	private String DAY26;
	private String DAY27;
	private String DAY28;
	private String DAY29;
	private String DAY30;
	private String DAY31;
	
	
	public String getDAY1() {
		return DAY1;
	}
	public void setDAY1(String dAY1) {
		DAY1 = dAY1;
	}
	public String getDAY2() {
		return DAY2;
	}
	public void setDAY2(String dAY2) {
		DAY2 = dAY2;
	}
	public String getDAY3() {
		return DAY3;
	}
	public void setDAY3(String dAY3) {
		DAY3 = dAY3;
	}
	public String getDAY4() {
		return DAY4;
	}
	public void setDAY4(String dAY4) {
		DAY4 = dAY4;
	}
	public String getDAY5() {
		return DAY5;
	}
	public void setDAY5(String dAY5) {
		DAY5 = dAY5;
	}
	public String getDAY6() {
		return DAY6;
	}
	public void setDAY6(String dAY6) {
		DAY6 = dAY6;
	}
	public String getDAY7() {
		return DAY7;
	}
	public void setDAY7(String dAY7) {
		DAY7 = dAY7;
	}
	public String getDAY8() {
		return DAY8;
	}
	public void setDAY8(String dAY8) {
		DAY8 = dAY8;
	}
	public String getDAY9() {
		return DAY9;
	}
	public void setDAY9(String dAY9) {
		DAY9 = dAY9;
	}
	public String getDAY10() {
		return DAY10;
	}
	public void setDAY10(String dAY10) {
		DAY10 = dAY10;
	}
	public String getDAY11() {
		return DAY11;
	}
	public void setDAY11(String dAY11) {
		DAY11 = dAY11;
	}
	public String getDAY12() {
		return DAY12;
	}
	public void setDAY12(String dAY12) {
		DAY12 = dAY12;
	}
	public String getDAY13() {
		return DAY13;
	}
	public void setDAY13(String dAY13) {
		DAY13 = dAY13;
	}
	public String getDAY14() {
		return DAY14;
	}
	public void setDAY14(String dAY14) {
		DAY14 = dAY14;
	}
	public String getDAY15() {
		return DAY15;
	}
	public void setDAY15(String dAY15) {
		DAY15 = dAY15;
	}
	public String getDAY16() {
		return DAY16;
	}
	public void setDAY16(String dAY16) {
		DAY16 = dAY16;
	}
	public String getDAY17() {
		return DAY17;
	}
	public void setDAY17(String dAY17) {
		DAY17 = dAY17;
	}
	public String getDAY18() {
		return DAY18;
	}
	public void setDAY18(String dAY18) {
		DAY18 = dAY18;
	}
	public String getDAY19() {
		return DAY19;
	}
	public void setDAY19(String dAY19) {
		DAY19 = dAY19;
	}
	public String getDAY20() {
		return DAY20;
	}
	public void setDAY20(String dAY20) {
		DAY20 = dAY20;
	}
	public String getDAY21() {
		return DAY21;
	}
	public void setDAY21(String dAY21) {
		DAY21 = dAY21;
	}
	public String getDAY22() {
		return DAY22;
	}
	public void setDAY22(String dAY22) {
		DAY22 = dAY22;
	}
	public String getDAY23() {
		return DAY23;
	}
	public void setDAY23(String dAY23) {
		DAY23 = dAY23;
	}
	public String getDAY24() {
		return DAY24;
	}
	public void setDAY24(String dAY24) {
		DAY24 = dAY24;
	}
	public String getDAY25() {
		return DAY25;
	}
	public void setDAY25(String dAY25) {
		DAY25 = dAY25;
	}
	public String getDAY26() {
		return DAY26;
	}
	public void setDAY26(String dAY26) {
		DAY26 = dAY26;
	}
	public String getDAY27() {
		return DAY27;
	}
	public void setDAY27(String dAY27) {
		DAY27 = dAY27;
	}
	public String getDAY28() {
		return DAY28;
	}
	public void setDAY28(String dAY28) {
		DAY28 = dAY28;
	}
	public String getDAY29() {
		return DAY29;
	}
	public void setDAY29(String dAY29) {
		DAY29 = dAY29;
	}
	public String getDAY30() {
		return DAY30;
	}
	public void setDAY30(String dAY30) {
		DAY30 = dAY30;
	}
	public String getDAY31() {
		return DAY31;
	}
	public void setDAY31(String dAY31) {
		DAY31 = dAY31;
	}

	
}
