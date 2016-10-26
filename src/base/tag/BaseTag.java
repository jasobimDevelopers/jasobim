/**
 * 
 */
package base.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author nijie
 *
 */
public class BaseTag implements Tag {
	public final static String CLASS_PACKAGE = "com.my.spring.controller.,com.my.spring.baseController.";
	
	private PageContext pageContext;
	private Tag parent;

	private String key;
	
	private String id;
	private String name;
	private String clazz;
	private String title;
	private String idprefix;
	
	
	
	private String titletag;
	private String titletagclass;
	private String outtag;
	private String outtagstyle;
	private String datarule;
	
	
	
	public String getDatarule() {
		return datarule;
	}

	public void setDatarule(String datarule) {
		this.datarule = datarule;
	}

	public String getTitletag() {
		return titletag;
	}

	public void setTitletag(String titletag) {
		this.titletag = titletag;
	}

	public String getTitletagclass() {
		return titletagclass;
	}

	public void setTitletagclass(String titletagclass) {
		this.titletagclass = titletagclass;
	}

	public String getOuttag() {
		return outtag;
	}

	public void setOuttag(String outtag) {
		this.outtag = outtag;
	}

	public String getOuttagstyle() {
		return outtagstyle;
	}

	public void setOuttagstyle(String outtagstyle) {
		this.outtagstyle = outtagstyle;
	}

	public String getIdprefix() {
		return idprefix;
	}

	public void setIdprefix(String idprefix) {
		this.idprefix = idprefix;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		return this.SKIP_BODY;// 返回SKIP_BODY，表示不计算标签体
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#getParent()
	 */
	@Override
	public Tag getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.jsp.tagext.Tag#setPageContext(javax.servlet.jsp.PageContext
	 * )
	 */
	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
	 */
	@Override
	public void setParent(Tag parent) {
		this.parent = parent;
	}


}
