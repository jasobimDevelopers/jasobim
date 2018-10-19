package com.my.spring.utils;
import java.util.ArrayList;
import java.util.List;
import com.my.spring.model.ContractLofting;
public class TestUtil {
	/**
	 * @param args
	 */
	private static List<Long> idList = new ArrayList<Long>();
	private static List<ContractLofting> plist = new ArrayList<ContractLofting>();
	public TestUtil(){
		
	}
	public TestUtil(List<ContractLofting> plist){
		this.setPlist(plist);
	}
	public static void main(String[] args) {
		/*ContractLofting pitem = new ContractLofting();
		pitem.setId((long)4);
		getClist().add(pitem);
		ContractLofting pitem1 = new ContractLofting();
		pitem1.setId((long)1);
		getPlist().add(pitem);
		ContractLofting pitem2 = new ContractLofting();
		pitem2.setId((long)2);
		getPlist().add(pitem2);
		ContractLofting pitem3 = new ContractLofting();
		pitem3.setId((long)3);
		pitem3.setPid((long)1);
		getPlist().add(pitem3);
		ContractLofting pitem5 = new ContractLofting();
		pitem5.setId((long)4);
		pitem5.setPid((long)1);
		getPlist().add(pitem5);
		ContractLofting pitem6 = new ContractLofting();
		pitem6.setId((long)5);
		pitem6.setPid((long)3);
		getPlist().add(pitem6);
		ContractLofting pitem7 = new ContractLofting();
		pitem7.setId((long)6);
		pitem7.setPid((long)4);
		getPlist().add(pitem7);
		ContractLofting pitem8 = new ContractLofting();
		pitem8.setId((long)7);
		pitem8.setPid((long)4);
		getPlist().add(pitem8);
		ContractLofting pitem9 = new ContractLofting();
		pitem9.setId((long)8);
		pitem9.setPid((long)5);
		getPlist().add(pitem9);
		getList(getClist());
		for(int i=0;i<idList.size();i++){
			System.out.println(idList.get(i));
		}*/
	}
	/*
	 * 查找id=id的所有子级
	 * 
	 * */
	public static List<Long> getList(List<ContractLofting> clist){
		List<ContractLofting> clist2 = new ArrayList<ContractLofting>();
		for(int i=0;i<plist.size();i++){
			for(int j=0;j<clist.size();j++){
				if(clist.get(j).getId().equals(plist.get(i).getPid())){
					clist2.add(plist.get(i));
					idList.add(plist.get(i).getId());
				}
			}
		}
		if(idList.isEmpty() || clist2.isEmpty()){
			return idList;
		}
		return getList(clist2);
		
	}
	public List<ContractLofting> getPlist() {
		return plist;
	}
	public void setPlist(List<ContractLofting> plist) {
		TestUtil.plist = plist;
	}

}
