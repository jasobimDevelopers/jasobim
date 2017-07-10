package test;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component  
public class Shchdual {  
      
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次    
    public void test(){  
        System.out.println("test");  
    }  
	public static void main(String[] arg){
		Shchdual s = new Shchdual();
		s.test();
	}
  
}  
