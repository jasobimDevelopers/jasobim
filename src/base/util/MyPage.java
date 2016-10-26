package base.util;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MyPage<T> implements Page<T> {
	private int pageNum;
	private List<T> list;
	private int pageSize;
	private int totalPages;
	private int rowCount;
	/**
	 * 初始化Page
	 * @param pageNum		页号
	 * @param pageSize		页长
	 * @param list			数据列表
	 * @param rowCount		数据总条数
	 */
	public MyPage(int pageNum, int pageSize, List<T> list, int rowCount){
		this.list = list;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.totalPages = pageSize > 0 ? (int)Math.ceil(((double) rowCount)/pageSize) : 0;
	}
	public List<T> getContent() {
		// TODO Auto-generated method stub
		return list;
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return pageNum;
	}

	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	public Sort getSort() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTotalElements() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	public int getTotalPages() {
		// TODO Auto-generated method stub
		return totalPages;
	}

	public boolean hasContent() {
		if(list != null){
			return true;
		}
		return false;
	}

	public boolean hasNextPage() {
		if(totalPages > 0 && totalPages-1 > pageNum){
			return true;
		}
		return false;
	}

	public boolean hasPreviousPage() {
		if(pageNum > 0){
			return true;
		}
		return false;
	}

	public boolean isFirstPage() {
		if(pageNum > 0){
			return false;
		}
		return true;
	}

	public boolean isLastPage() {
		if(totalPages > 0 && totalPages-1 == pageNum){
			return true;
		}
		return false;
	}

	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pageable nextPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pageable previousPageable() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isLast() {
		// TODO Auto-generated method stub
		return false;
	}

}
