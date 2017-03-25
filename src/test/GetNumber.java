package test;

public class GetNumber {

	public static void main(String[] args) {
		 String str1 = "179,12,123,789";
		 String [] stringArr= str1.split(",");
		 String str="7";
		 boolean test=str1.contains(str);
		 System.out.println(test);  
		 for(int i=0;i<stringArr.length;i++){
			 System.out.println(stringArr[i]);  
		 }
		 
	}
	
}
