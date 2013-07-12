
package keter.persistence.base;


/**
 * <p>Class       : com.neusoft.mea.persistence.Pagination
 * <p>Descdription: 类功能描述
 * @author  顾力行-gulixing@msn.com
 * @version 1.0.0
 */
public class Pagination {
	public final static int DEFAULT_PAGE_SIZE = 10;
    private Integer pageNo = 1;  
    private Integer pageSize = DEFAULT_PAGE_SIZE;  
	private Long recordTotal = 0L;
	private Integer pageTotal;  

	
	/**
	 * <p>Method ：init
	 * <p>Description : 分页初始化：采用系统默认分页大小
	 * 
	 * @author  顾力行-gulixing@msn.com
	 */
	public void init(){
		pageNo = 1;
	}
	
	/**
	 * <p>Method ：init
	 * <p>Description : 分页初始化
	 *
	 * @param pageSize 分页大小
	 * @author  顾力行-gulixing@msn.com
	 */
	public void init(Integer pageSize){
		pageNo = 1;
		this.pageSize = pageSize;
	}
	
	public Long getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(Long recordTotal) {
		this.recordTotal = recordTotal;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageTotal() {
		pageTotal = (int) Math.ceil((double) recordTotal / (double) pageSize);  
		pageTotal = pageTotal==0?1:pageTotal;
		return pageTotal;
	}
	
	public void init(String paginExp){
		if(paginExp.contains("|")){
			String arr[] = paginExp.split("|");
			setPageNo(Integer.valueOf(arr[0]));
			setPageSize(Integer.valueOf(arr[1]));
		}
		else{
			setPageNo(Integer.valueOf(paginExp));
		}
	}
}
