package com.hust.hui.alarm.common.test;

import com.hust.hui.alarm.common.concurrent.forkjoin.DefaultForkJoinDataLoader;
import com.hust.hui.alarm.common.concurrent.forkjoin.ForkJoinPoolFactory;
import com.hust.hui.alarm.common.concurrent.forkjoin.IDataLoader;
import lombok.Data;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by yihui on 2018/4/8.
 */
public class ForJoinTest {

    @Data
    static class Context {
        public int addAns;

        public int mulAns;

        public String concatAns;

        public Map<String, Object> ans = new ConcurrentHashMap<>();
    }


    @Test
    public void testForkJoinFramework() {
        ForkJoinPool forkJoinPool = new ForkJoinPoolFactory().getObject();

        Context context = new Context();
        DefaultForkJoinDataLoader<Context> loader = new DefaultForkJoinDataLoader<>(context);
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                context.addAns = 100;
                System.out.println("add thread: " + Thread.currentThread());
            }
        });
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.mulAns = 50;
                System.out.println("mul thread: " + Thread.currentThread());
            }
        });
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                context.concatAns = "hell world";
                System.out.println("concat thread: " + Thread.currentThread());
            }
        });


        DefaultForkJoinDataLoader<Context> subTask = new DefaultForkJoinDataLoader<>(context);
        subTask.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                System.out.println("sub thread1: " + Thread.currentThread() + " | now: " + System.currentTimeMillis());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.ans.put(Thread.currentThread().getName(), System.currentTimeMillis());

            }
        });
        subTask.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                System.out.println("sub thread2: " + Thread.currentThread() + " | now: " + System.currentTimeMillis());
                context.ans.put(Thread.currentThread().getName(), System.currentTimeMillis());
            }
        });

        loader.addTask(subTask);


        long start = System.currentTimeMillis();
        System.out.println("------- start: " + start);

        // 提交任务，同步阻塞调用方式
        forkJoinPool.invoke(loader);


        System.out.println("------- end: " + (System.currentTimeMillis() - start));

        // 输出返回结果，要求3s后输出，所有的结果都设置完毕
        System.out.println("the ans: " + context);
    }


    @Test
    public void testForkJoinFramework2() {
        ForkJoinPool forkJoinPool = new ForkJoinPoolFactory().getObject();

        Context context = new Context();
        DefaultForkJoinDataLoader<Context> loader = new DefaultForkJoinDataLoader<>(context);
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                context.addAns = 100;
                System.out.println("add thread: " + Thread.currentThread());
            }
        });
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                context.mulAns = 50;
                System.out.println("mul thread: " + Thread.currentThread());
            }
        });
        loader.addTask(new IDataLoader<Context>() {
            @Override
            public void load(Context context) {
                context.concatAns = "hell world";
                System.out.println("concat thread: " + Thread.currentThread());
            }
        });


        long start = System.currentTimeMillis();
        System.out.println("------- start: " + start);

        // 如果暂时不关心返回结果，可以采用execute方式，异步执行
        forkJoinPool.execute(loader);

        // .... 这里可以做其他的事情 此时，不会阻塞，addAns不会被设置
        System.out.println("context is: " + context);
        System.out.println("------- then: " + (System.currentTimeMillis() - start));


        loader.getContext(); // 主动调用这个，表示会等待所有任务执行完毕后，才继续下去
        System.out.println("context is: " + context);
        System.out.println("------- end: " + (System.currentTimeMillis() - start));
    }

}
