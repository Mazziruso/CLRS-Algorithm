package Charpter4;
import java.util.Random;  
import java.util.Scanner;  
/* 
 * @Author:唐波 
 * Strassen矩阵乘法 
 * 2014.10.31 
 * 程序对比了传统方法和Strassen算法计算的结果是否相等 
 * 算法来源：1969年，德国的一位数学家Strassen证明O（N^3）的解法并不是矩阵乘法的最优算法，他做了一系列工作使得最终的时间复杂度降低到了O(n^2.80) 
 */  
public class SquareMatrixMultiply {  
    static Random random = new Random();  
    static Scanner in;  
    public static void main(String[] args)   
    {   
        int matrixLength=0;  
        in = new Scanner(System.in);     
        System.out.print("输入矩阵的阶数: ");     
        matrixLength = in.nextInt();  
        if(isEven(matrixLength)==0)  
        {  
            int [][]x=productMatrix(matrixLength);  
            int [][]y=productMatrix(matrixLength);  
//            System.out.println("x矩阵:");  
//            printMatrix(x);  
//            System.out.println("y矩阵:");  
//            printMatrix(y);
            
            //时间记录
            long startTime;
            long endTime;
            int times = 20;//循环次数
            
            
            int [][]strassenResult; 
            
            startTime = System.currentTimeMillis();
            for(int i=0; i<times; i++) {
            	strassenResult=strassenMatrixMultiply(x,y);//Strassen计算结果  
            }
            endTime = System.currentTimeMillis();
//            System.out.println("Strassen计算结果:");  
//            printMatrix(strassenResult);  
            System.out.println("Strassen运行时间平均：  " + (endTime-startTime)/times + " ms");
            
            
            int [][] forceResult; 
            startTime = System.currentTimeMillis();
            for(int i=0; i<times; i++) {
            	forceResult = forceMatrixMultiply(x, y);//传统方法计算结果 
            }
            endTime = System.currentTimeMillis();
//            System.out.println("传统计算结果:");  
//            printMatrix(forceResult);
            System.out.println("传统算法运行时间平均：  " + (endTime-startTime)/times + " ms");
            
            strassenResult = strassenMatrixMultiply(x,y);
            forceResult = forceMatrixMultiply(x, y);
            boolean isEqual = isEqual(forceResult, strassenResult);//比较两种计算结果是否相等  
            if(isEqual)  
            {  
                System.out.println("两个计算结果相等！");  
            }else  
            {  
                System.err.println("两个计算结果不相等！");  
                System.exit(0);//程序退出  
            }  
        }else  
        {  
            System.out.println("矩阵不是2^k方阵，无法计算！");  
        }  
    }  
    static boolean isEqual(int [][]x,int [][]y)//遍历判断两个矩阵是否相等  
    {  
        boolean equal=true;  
        for(int i =0;i<x.length;i++)  
        {  
            for(int j=0;j<x[0].length;j++)  
            {  
                if(x[i][j]!=y[i][j])  
                {  
                    equal=false;  
                }  
            }  
        }  
        return equal;  
    }  
    static int isEven(int n)//判断输入矩阵阶数是否为2^k  
    {     
        int a = 1,temp=n;     
        while(temp%2==0)     
        {     
            if(temp%2==0)      
                temp/=2;   
        }    
        if(temp==1)      
            a=0;     
        return a;  
    }     
    static int[][] productMatrix(int matrixLength)//自动生成矩阵  
    {  
        int matrix[][] = new int[matrixLength][matrixLength];  
        //初始化矩阵  
        for(int i=0;i<matrixLength;i++)  
        {  
            for(int j=0;j<matrixLength;j++)  
            {  
                matrix[i][j] = (int)(Math.random()*10);  
            }  
        }  
        System.out.println();  
        return matrix;  
    }  
    static void printMatrix(int matrix[][])//矩阵打印函数  
    {  
        int matrixRowLength = matrix.length;//获取矩阵的行数  
        int matrixColumnLength = matrix[0].length;//获取矩阵的列数  
        for(int i=0;i<matrixRowLength;i++)  
        {  
            for(int j=0;j<matrixColumnLength;j++)  
            {  
                System.out.print(matrix[i][j]+" ");  
            }  
            System.out.println();  
        }  
    }  
    static int[][] matrixPlus(int[][] x,int[][] y) //矩阵加法      
    {     
        int matrixXRowLength = x.length;//获取矩阵的行长度  
        
        int matrixXColumnLength = x[0].length;  
        int matrixYRowLength = x.length;//获取矩阵的行长度  
        int matrixYColumnLength = x[0].length;  
        if(matrixXColumnLength!=matrixYColumnLength || matrixXRowLength!=matrixYRowLength)//判断矩阵是否同型  
        {  
            throw new RuntimeException("矩阵不同型，无法进行加法计算！");    
        }  
        int[][] result = new int[matrixXRowLength][matrixXColumnLength];  
        for(int i=0;i<matrixXColumnLength;i++)  
        {  
            for (int j = 0; j < matrixXColumnLength; j++)   
            {  
                result[i][j] = x[i][j]+y[i][j];   
            }  
        }  
        return result;  
    }     
  
    static int[][] matrixMinus(int[][] x,int[][] y)//矩阵减法  
    {  
        int matrixXRowLength = x.length;//获取矩阵的行长度  
        int matrixXColumnLength = x[0].length;  
        int matrixYRowLength = x.length;//获取矩阵的行长度  
        int matrixYColumnLength = x[0].length;  
        if(matrixXColumnLength!=matrixYColumnLength || matrixXRowLength!=matrixYRowLength)  
        {  
            throw new RuntimeException("矩阵不同型，无法进行减法计算！");    
        }  
        int[][] result = new int[matrixXRowLength][matrixXColumnLength];  
        for(int i=0;i<matrixXColumnLength;i++)  
        {  
            for (int j = 0; j < matrixXColumnLength; j++)   
            {  
                result[i][j] = x[i][j]-y[i][j];   
            }  
        }  
        return result;  
    }  
  
    //Strassen二阶矩阵的乘法  
    static int[][] twostrassenMatrixMultiply(int [][]x,int [][]y) //阶数为2的矩阵乘法      
    {     
        int matrixXColumnLength = x[0].length;  
        int matrixYRowLength = x.length;//获取矩阵的行长度  
        if(matrixXColumnLength!=matrixYRowLength)  
        {  
            throw new RuntimeException("matrixXColumnLength!=matrixYRowLength，无法进行乘法计算！");  
        }  
        int p1,p2,p3,p4,p5,p6,p7;//这些都是按照算法定义进行的  
        int [][]result = new int[2][2];  
        p1=(y[0][1] - y[1][1]) * x[0][0];     
        p2=y[1][1] * (x[0][0] + x[0][1]);     
        p3=(x[1][0] + x[1][1]) * y[0][0];     
        p4=x[1][1] * (y[1][0] - y[0][0]);     
        p5=(x[0][0] + x[1][1]) * (y[0][0]+y[1][1]);     
        p6=(x[0][1] - x[1][1]) * (y[1][0]+y[1][1]);     
        p7=(x[0][0] - x[1][0]) * (y[0][0]+y[0][1]);     
        result[0][0] = p5 + p4 - p2 + p6;     
        result[0][1] = p1 + p2;     
        result[1][0] = p3 + p4;  
        result[1][1] = p5 + p1 - p3 - p7;  
        return result;     
    }     
    static void divide(int[][] a,int[][] a11,int[][] a12,int[][] a21,int[][] a22)//分解矩阵  
    {     
        int matrixLength = a.length/2;  
        for(int i=0;i<matrixLength;i++)     
            for(int j=0;j<matrixLength;j++)     
            {  
                a11[i][j]=a[i][j];  
                a12[i][j]=a[i][j+matrixLength];     
                a21[i][j]=a[i+matrixLength][j];     
                a22[i][j]=a[i+matrixLength][j+matrixLength];     
            }     
    }  
  
    static int[][] merge(int [][]a11,int [][]a12,int [][]a21,int [][]a22)//合并矩阵      
    {     
        int n=a11.length;  
        int [][] result = new int[2*n][2*n];  
        for(int i=0;i<n;i++)  
        {  
            for(int j=0;j<n;j++)  
            {  
                result[i][j]=a11[i][j];     
                result[i][j+n]=a12[i][j];     
                result[i+n][j]=a21[i][j];     
                result[i+n][j+n]=a22[i][j];     
            }  
        }  
        return result;  
    }  
    static int[][] strassenMatrixMultiply(int [][]x,int [][]y) //矩阵乘法方法      
    {     
        if(x.length==2)     
        {     
            return twostrassenMatrixMultiply(x,y);  
        }     
        else     
        {     
            int matrixLength = x.length/2;  
            int[][] a11,a12,a21,a22;  
            a11 = new int[matrixLength][matrixLength];  
            a12 = new int[matrixLength][matrixLength];  
            a21 = new int[matrixLength][matrixLength];  
            a22 = new int[matrixLength][matrixLength];  
            int[][] b11,b12,b21,b22;  
            b11 = new int[matrixLength][matrixLength];  
            b12 = new int[matrixLength][matrixLength];  
            b21 = new int[matrixLength][matrixLength];  
            b22 = new int[matrixLength][matrixLength];  
            int[][] c11,c12,c21,c22,c;     
            c11 = new int[matrixLength][matrixLength];  
            c12 = new int[matrixLength][matrixLength];  
            c21 = new int[matrixLength][matrixLength];  
            c22 = new int[matrixLength][matrixLength];  
            c = new int[2*matrixLength][2*matrixLength];  
            int[][] m1,m2,m3,m4,m5,m6,m7;  
            divide(x,a11,a12,a21,a22); //拆分A、B、C矩阵      
            divide(y,b11,b12,b21,b22);     
            divide(c,c11,c12,c21,c22);  
            m1=strassenMatrixMultiply(a11,matrixMinus(b12,b22));     
            m2=strassenMatrixMultiply(matrixPlus(a11,a12),b22);  
            m3=strassenMatrixMultiply(matrixPlus(a21,a22),b11);  
            m4=strassenMatrixMultiply(a22,matrixMinus(b21,b11));     
            m5=strassenMatrixMultiply(matrixPlus(a11,a22),matrixPlus(b11,b22));     
            m6=strassenMatrixMultiply(matrixMinus(a12,a22),matrixPlus(b21,b22));     
            m7=strassenMatrixMultiply(matrixMinus(a11,a21),matrixPlus(b11,b12));     
            c11=matrixPlus(matrixMinus(matrixPlus(m5,m4),m2),m6);     
            c12=matrixPlus(m1,m2);     
            c21=matrixPlus(m3,m4);     
            c22=matrixMinus(matrixMinus(matrixPlus(m5,m1),m3),m7);  
            c=merge(c11,c12,c21,c22); //合并C矩阵      
            return c;     
        }      
    }  
    static int[][] forceMatrixMultiply(int [][]x,int [][]y)  
    {  
        int matrixXRowLength = x.length;//获取矩阵的行长度  
        int matrixXColumnLength = x[0].length;  
        int matrixYRowLength = x.length;//获取矩阵的行长度  
        int matrixYColumnLength = x[0].length;  
        if(matrixXColumnLength!=matrixYRowLength)  
        {  
            throw new RuntimeException("matrixXColumnLength!=matrixYRowLength，无法进行乘法计算！");  
        }  
        int [][] result = new int[matrixXRowLength][matrixYColumnLength];  
        for(int i=0;i<matrixXRowLength;i++)  
        {  
            for(int j=0;j<matrixYColumnLength;j++)  
            {  
                result[i][j]=0;  
                for(int k=0;k<matrixYRowLength;k++)  
                {  
                    result[i][j] = result[i][j]+x[i][k]*y[k][j];  
                }  
            }  
        }  
        return result;  
    }  
  
}  
