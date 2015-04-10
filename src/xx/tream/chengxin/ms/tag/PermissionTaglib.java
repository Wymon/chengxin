package xx.tream.chengxin.ms.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PermissionTaglib extends TagSupport
{
  private static final long serialVersionUID = 1L;
  private String code;

  public int doStartTag()
    throws JspException
  {
    if ((this.code != null) && (!this.code.equals("")))
      return 6;
    return 0;
  }
  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
}