package dong.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *  执行的是有结果的任务，需要继承RecursiveTask
 *  如果任务不需要返回结果，可以继承RecursiveAction
 * Created by DONGSHILEI on 2017/6/22.
 */
public class ForkJoin extends RecursiveTask {
    private static final int THRESHOLD= 2;//阈值 作为任务切分的标准
    private int start;
    private int end;

    public ForkJoin(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean camCompute = (end-start)<=THRESHOLD;
        if (camCompute){
            for (int i=start;i<=end;i++){
                sum+=i;
            }
        } else {
            //如果任务大于阀值，就分裂成两个子任务计算
            int middle = (start+end)/2;
            ForkJoin leftForkJoin = new ForkJoin(start,middle);
            ForkJoin rightForkJoin = new ForkJoin(middle+1,end);
            //执行子任务
            leftForkJoin.fork();
            rightForkJoin.fork();
            //获取子任务结果
            int left = (int) leftForkJoin.join();
            int right = (int) rightForkJoin.join();
            //合并子任务
            sum = left+right;
        }
        //返回最后结果
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4+5+6
        ForkJoin forkJoin = new ForkJoin(1,6);
        //执行任务
        ForkJoinTask submit = forkJoinPool.submit(forkJoin);
        System.out.println(submit.get());
    }
}
