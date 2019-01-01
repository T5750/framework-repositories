package t5750.pay.common.core.page;

import java.io.Serializable;

/**
 * @类功能说明： 分页参数传递工具类 . @版本：V1.0
 */
public class PageParam implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6297178964005032338L;
	/**
	 * 默认为第一页.
	 */
	public static final int DEFAULT_PAGE_NUM = 1;
	/**
	 * 默认每页记录数(15).
	 */
	public static final int DEFAULT_NUM_PER_PAGE = 15;
	/**
	 * 最大每页记录数(100).
	 */
	public static final int MAX_PAGE_SIZE = 10000;
	private int pageNum = DEFAULT_PAGE_NUM; // 当前页数
	private int numPerPage; // 每页记录数

	/**
	 * 默认构造函数
	 */
	public PageParam() {
	}

	/**
	 * 带参数的构造函数
	 * 
	 * @param pageNum
	 * @param numPerPage
	 */
	public PageParam(int pageNum, int numPerPage) {
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}

	/** 当前页数 */
	public int getPageNum() {
		return pageNum;
	}

	/** 当前页数 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/** 每页记录数 */
	public int getNumPerPage() {
		return numPerPage > 0 ? numPerPage : DEFAULT_NUM_PER_PAGE;
	}

	/** 每页记录数 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
}
