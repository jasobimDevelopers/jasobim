 package test;

import java.math.BigInteger;

public class DataTypeTest {
	public static void main(String[] arg){
		/*BigInteger test=new BigInteger("2111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		BigInteger test1=new BigInteger("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		BigInteger test2=test.add(test1);
		BigInteger test3=test.subtract(test1);
		BigInteger test4=test.multiply(test1);
		System.out.println(test2+"/n"+test3+"/n"+test4);
		///////斐波拉契数列
		int num = 100;  
        //整型数组  
        int[] resultInt = new int[100];  
        resultInt[0] = 0;  
        resultInt[1] = 1;  
        //长整型数组  
        long[] resultLong = new long[100];  
        resultLong[0] = 0L;  
        resultLong[1] = 1L;  
        //BigInteger数组  
        BigInteger[] resultBigInteger = new BigInteger[100];  
        resultBigInteger[0] = BigInteger.ZERO;  
        resultBigInteger[1] = BigInteger.ONE;  
        for (int i = 2; i < num; i++) {  
            resultInt[i] = resultInt[i - 1] + resultInt[i - 2];  
            resultLong[i] = resultLong[i - 1] + resultLong[i - 2];  
            resultBigInteger[i] = resultBigInteger[i - 1].add(resultBigInteger[i - 2]);  
        }  
        System.out.println("--------------------用整型来存储数值的结果！---------------------");  
        for (int i = 0; i < resultInt.length; i++) {  
            System.out.println("resultInt[" + i + "] = "+ resultInt[i]);  
        }  
        System.out.println("--------------------用长整型来存储数值的结果！---------------------");  
        for (int i = 0; i < resultLong.length; i++) {  
            System.out.println("resultLong[" + i + "] = "+ resultLong[i]);  
        }  
        System.out.println("--------------------用BigInteger来存储数值的结果！---------------------");  
        for (int i = 0; i < resultBigInteger.length; i++) {  
            System.out.println("resultBigInteger[" + i + "] = "+ resultBigInteger[i]);  
        }  */
		BigInteger[] resultBigInteger = new BigInteger[100];  
        resultBigInteger[0] = BigInteger.ZERO;  
        resultBigInteger[1] = BigInteger.ONE;  
        BigInteger test = new BigInteger("222222222222222222222222222222");
        System.out.println(test);
        System.out.println("resultBigInteger[0]="+resultBigInteger[0]);
        System.out.println("resultBigInteger[1]="+resultBigInteger[1]);
        resultBigInteger[2] = resultBigInteger[1].add(resultBigInteger[1]);
        System.out.println("resultBigInteger[2]="+resultBigInteger[2]);
        for(int i=3;i<21;i++){
        	resultBigInteger[i]=resultBigInteger[i-1].multiply(resultBigInteger[i-1]);
        	System.out.println(resultBigInteger[i]);
        }
	}
}
