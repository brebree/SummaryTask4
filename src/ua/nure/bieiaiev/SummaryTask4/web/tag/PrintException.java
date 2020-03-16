package ua.nure.bieiaiev.SummaryTask4.web.tag;

import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag prints exception's Stack Trace on page
 * 
 * @author D.Bieliaiev
 *
 */
public class PrintException extends TagSupport {

	private static final long serialVersionUID = -7918829646931664364L;

	private Throwable exception;

	@Override
	public int doStartTag() throws JspException {
		exception.printStackTrace(new PrintWriter(pageContext.getOut()));
		return SKIP_BODY;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
