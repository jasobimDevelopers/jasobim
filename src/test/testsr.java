package test;

public class testsr {

	public static void main(String[] args) {
	 	Long userId=127L;
        Long authorId=127L; 
        System.out.println(userId==authorId);//true
        userId=128L;
        authorId=128L;
        System.out.println(userId==authorId);//false
        userId=126L;
        authorId=126L;
        System.out.println(userId==authorId);//true
        Long x=new Long(127);
        Long y=new Long(127);
        System.out.println(x==y);//false
        System.out.println(x.equals(y));//true
        Long userIds=(long)265;
        Long users=(long)265;
        System.out.println(userIds==users);
	}
	public class print{
		int a=0;
		char b = 0;
	}
}
