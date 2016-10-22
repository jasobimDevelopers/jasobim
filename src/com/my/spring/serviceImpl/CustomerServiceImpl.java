package com.my.spring.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.spring.model.Customer;
import com.my.spring.service.CustomerService;
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	@SuppressWarnings("unused")
	@Override
	public boolean batchImport(String name, org.springframework.web.multipart.MultipartFile file) {
		boolean b = false;
        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取客户信息集合。
       // List<Customer> customerList = readExcel.getExcelInfo(name ,file);

        /*if(customerList != null){
            b = true;
        }
        
        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
        for(Customer customer:customerList){
            //customerDoImpl.addCustomers(customer);
        }*/
        return b;
	}

}
