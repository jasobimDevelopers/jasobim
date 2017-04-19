package test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.DuctDao;
import com.my.spring.model.Duct;
import com.my.spring.model.DuctPojo;
import com.my.spring.service.DuctService;
import com.my.spring.utils.DataWrapper;

public class RunnableTest {
	private DataWrapper<List<Duct>> test;
	@Autowired
	private DuctDao DuctDao;
	public RunnableTest(DataWrapper<List<Duct>> temp){
		this.test=temp;
	}
	public void run(){
        while(true){
            try {
            	//test=DuctDao.getDuctList(10, 1, null, null);
                
                Thread.sleep(1000);//等待1秒
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
            System.out.println("ceshi");
        }
    }
	
}
