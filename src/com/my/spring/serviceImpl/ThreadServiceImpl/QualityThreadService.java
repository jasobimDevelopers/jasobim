package com.my.spring.serviceImpl.ThreadServiceImpl;
import com.my.spring.DAO.BaseDao;
import com.my.spring.model.QualityQuestion;

public class QualityThreadService extends BaseDao<QualityQuestion>{
	private QualityQuestion quality;
	
	public QualityThreadService(QualityQuestion quality){
		this.quality=quality;
	}
	public void run() {
		if(save(this.quality)){
			System.out.println("添加质量问题完成！");
		}
	}
	public QualityQuestion getQuality() {
		return quality;
	}
	public void setQuality(QualityQuestion quality) {
		this.quality = quality;
	}
	
}
