package com.my.spring.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import com.my.spring.parameters.Parameters;

public class InstanceUtil {
	
	public static void main(String[] arg){
		System.out.println(Parameters.getDaysByYearMonth(2017, 2));
		double lat1=31.2855400000;
		double lng1=121.2147500000;
		double lat2=31.2879582297;
		double lng2=121.2046051025;
		System.out.println(distanceSimplifyMore(lat1,lng1,lat2,lng2));
	}

	public static double distanceSimplifyMore(double lat1, double lng1, double lat2, double lng2) {
		   double dx = lng1 - lng2; // 经度差值
		   double dy = lat1 - lat2; // 纬度差值
		     double b = (lat1 + lat2) / 2.0; // 平均纬度
		     double Lx = Math.toRadians(dx) * 6367000.0* Math.cos(Math.toRadians(b)); // 东西距离
		     double Ly = 6367000.0 * Math.toRadians(dy); // 南北距离
		     return Math.sqrt(Lx * Lx + Ly * Ly);  // 用平面的矩形对角距离公式计算总距离
	}

}
