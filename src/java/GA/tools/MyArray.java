package com.gkm.GA.tools;

public class MyArray {
	 public static boolean isIn(int a, int[] array) {
	        boolean result = false;
	        for (int i = 0; i < array.length; i++) {
	            if (a == array[i]) {
	                result = true;
	                break;
	            }
	        }
	        return result;
	    }

	    public static boolean isIn(float a, float[] array) {
	        boolean result = false;
	        for (int i = 0; i < array.length; i++) {
	            if (a == array[i]) {
	                result = true;
	                break;
	            }
	        }
	        return result;
	    }

	    public static boolean isIn(double a, double[] array) {
	        boolean result = false;
	        for (int i = 0; i < array.length; i++) {
	            if (a == array[i]) {
	                result = true;
	                break;
	            }
	        }
	        return result;
	    }

	    public static boolean isIn(Object a, Object[] array) {
	        boolean result = false;
	        for (int i = 0; i < array.length; i++) {
	            if (a.equals(array[i])) {
	                result = true;
	                break;
	            }
	        }
	        return result;
	    }

	    public static float getMin(float[] a) {
	        float min = a[0];
	        for (int i = 0; i < a.length; i++) {
	            if (min > a[i]) {
	                min = a[i];
	            }
	        }
	        return min;
	    }

	    public static int getMin(int[] a) {
	        int min = a[0];
	        for (int i = 0; i < a.length; i++) {
	            if (min > a[i]) {
	                min = a[i];
	            }
	        }
	        return min;
	    }
	    public static float getMultiply(float[] a, float[] b) {
	        float tem = 0;
	        if (a.length != b.length) {
	            System.out.println("The length is not equal. Report from MyArray");
	           // System.exit(0);
	        } else {
	            for (int i = 0; i < a.length; i++) {
	                tem += a[i] * b[i];
	            }
	        }
	        return tem;
	    }

	    public static void setUnit(float[][] a) {//set matrix elements 0
	        for (int i = 0; i < a.length; i++) {
	            for (int j = 0; j < a[0].length; j++) {
	                if (i == j) {
	                    a[i][j] = 1f;
	                } else {
	                    a[i][j] = 0f;
	                }
	            }
	        }
	    }

	    public static void setUnit(float[][][] a) {//set matrix elements 0
	        for (int i = 0; i < a.length; i++) {
	            for (int j = 0; j < a[0].length; j++) {
	                for (int k = 0; k < a[0][0].length; k++) {
	                    if (k == j) {
	                        a[i][j][k] = 1f;
	                    } else {
	                        a[i][j][k] = 0f;
	                    }
	                }
	            }
	        }
	    }

	    public static void setZero(float[] a) {//set array elements 0
	        for (int i = 0; i < a.length; i++) {
	            a[i] = 0f;
	        }
	    }

	    public static void setZero(float[][][] a) {//set matrix elements 0
	        for (int i = 0; i < a.length; i++) {
	            for (int j = 0; j < a[0].length; j++) {
	                for (int k = 0; k < a[0][0].length; k++) {
	                    a[i][j][k] = 0f;
	                }
	            }
	        }
	    }

	    public static void setZero(float[][] a) {//set matrix elements 0
	        for (int i = 0; i < a.length; i++) {
	            for (int j = 0; j < a[0].length; j++) {
	                a[i][j] = 0f;
	            }
	        }
	    }

	    public static void copy(float[] from, float[] to) {
	        for (int i = 0; i < from.length; i++) {
	            to[i] = from[i];
	        }
	    }

	    public static void copy(float[][] from, float[][] to) {
	        for (int i = 0; i < from.length; i++) {
	            for (int j = 0; j < from[i].length; j++) {
	                to[i][j] = from[i][j];
	            }
	        }
	    }
	    public static int[] sortNum(float[] array){
	        int[] tem=new int[array.length];
	        float[] tem1=new float[array.length];
	        System.arraycopy(array, 0, tem1, 0, array.length);
	        java.util.Arrays.sort(tem1);
	        for(int i=0;i<tem1.length;i++){
	            for(int j=0;j<array.length;j++){
	                if(tem1[i]==array[j] && !MyArray.isIn(j, tem)){
	                    tem[i]=j;
	                }
	            }
	        }
	        return tem;
	    }
	        public static int[] sortNum(int[] array){
	        int[] tem=new int[array.length];
	        int [] tem1=new int[array.length];
	        System.arraycopy(array, 0, tem1, 0, array.length);
	        java.util.Arrays.sort(tem1);
	        for(int i=0;i<tem1.length;i++){
	            for(int j=0;j<array.length;j++){
	                if(tem1[i]==array[j] && !MyArray.isIn(j, tem)){
	                    tem[i]=j;
	                }
	            }
	        }
	        return tem;
	    }
}
