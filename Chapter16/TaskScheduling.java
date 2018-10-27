package Charpter16;

public class TaskScheduling {

    public static void main(String[] args) {

        int[] d = {4, 2, 4, 3, 1, 4, 6};
//        int[] w = {70, 60, 50, 40, 30, 20, 10};
        int[] w = {10, 20, 30, 40, 50, 60, 70};

        int n = 7;
        Task[] S = new Task[7];
        for(int i=0; i<n; i++) {
            S[i] = new Task(i+1, d[i], w[i]);
        }

        printTask(S);
        System.out.println();

        int PrevTaskEnd = TaskScheduingGreedy(S);
        printTask(S, PrevTaskEnd);
    }

    private static void printTask(Task[] S) {
        for(int i=0; i<S.length; i++) {
            System.out.println(S[i]);
        }
    }

    private static void printTask(Task[] S, int end) {
        int punishment = 0;

        System.out.print("{{");
        for(int i=0; i<end; i++) {
            System.out.print("a" + S[i].i + ", ");
        }
        System.out.print("a" + S[end].i);
        System.out.print("}");
        for(int i=end+1; i<S.length; i++) {
            System.out.print(", a" + S[i].i);
            punishment += S[i].w;
        }
        System.out.println("}");

        System.out.println("Punishment: " + punishment);

    }


    //monotonically decreasing
    public static void quickSort(Task[] S, int start, int end) {
        if(start < end) {
            int q = partitionRandomized(S, start, end);
            quickSort(S, start, q-1);
            quickSort(S, q+1, end);
        }
    }

    public static int partitionRandomized(Task[] S, int start, int end) {
        int i = (int)(Math.random() * (end-start)) + start;

        Task temp = S[i];
        S[i] = S[end];
        S[end] = temp;

        return partition(S, start, end);
    }

    public static int partition(Task[] S, int start, int end) {
        Task a = S[end];
        int i = start-1;

        Task temp;
        for(int j=start; j<end; j++) {
            if (S[j].compareTo(a) >= 0) {
                i++;
                temp = S[j];
                S[j] = S[i];
                S[i] = temp;
            }
        }

        temp = S[i+1];
        S[i+1] = S[end];
        S[end] = temp;

        return i+1;
    }

    public static int TaskScheduingGreedy(Task[] S) {
        int n = S.length;
        TaskScheduling.quickSort(S, 0, n-1);

        int end = -1; //代表指向规范化提前任务集的最后一个任务
        int nums = 1; //代表遍历的任务数
        int index;
        int pointer;
        Task key;
        boolean flag;

        while(nums <= n) {
            index = end + 1;
            key = S[index];

            //将新任务合并到提前任务集中，并进行规范化
            while(index>=1 && (S[index-1].d>key.d)) {
                S[index] = S[index-1];
                index--;
            }
            S[index] = key;

            //进行独立性检查
            pointer = end + 1;
            flag = true;
            while(flag && pointer>=0) {
                if(S[pointer].d<pointer+1) {
                    flag = false;
                }
                while(pointer>=1 && S[pointer].d==S[pointer-1].d) {
                    pointer--;
                }
                pointer--;
            }

            //若不是独立子集，则将新任务调至总集合末尾
            if(!flag) {
                while(index < n-1) {
                    S[index] = S[index+1];
                    index++;
                }
                S[index] = key;
            } else {
                end++;
            }

            nums++;
        }

        return end;
    }
}

class Task implements Comparable<Object> {
    int i;
    int d;
    int w;

    public Task(int i, int d, int w) {
        this.i = i;
        this.d = d;
        this.w = w;
    }

    public Task(int i) {
        this(i, 0, 0);
    }

    public Task() {
        this(0);
    }

//    override
    public int compareTo(Object o) {
        if(this == o) {
            return 0;
        } else if((o != null) && (o instanceof Task)) {
            Task A = (Task) o;
            if(this.w > A.w) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public String toString() {
        return "a" + this.i + ", d=" + this.d + ", w=" + this.w;
    }

}
