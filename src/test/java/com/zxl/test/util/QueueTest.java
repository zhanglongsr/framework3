package com.zxl.test.util;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class QueueTest {

    private DelayQueue<DelayWork> queue = new DelayQueue<QueueTest.DelayWork>();

    @Test
    public void test() {

        DelayExecutorThread t1 = new DelayExecutorThread();
        t1.setName("consumeThread");
        t1.start();

        for (int i = 0; i < 10; i++) {
            DelayWork work = new DelayWork("work-" + i, System.currentTimeMillis() + 10 * 1000 * (i + 1));
            queue.add(work);
        }

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class DelayWork implements Delayed {
        // 任务名称
        private String workName;
        // 任务触发时间
        private long triggerTime;

        public DelayWork(String workName, long triggerTime) {
            this.workName = workName;
            this.triggerTime = triggerTime;
        }

        @Override
        public int compareTo(Delayed o) {
            DelayWork work = (DelayWork) o;
            if (work == null)
                return -1;
            int result = triggerTime > work.getTriggerTime() ? 1 : triggerTime == work.getTriggerTime() ? 0 : -1;
            System.out.println("compareTo's result:" + result + "," + toString());
            return result;
        }

        @Override
        public long getDelay(TimeUnit unit) {

            long result = unit.convert(System.currentTimeMillis() - triggerTime, TimeUnit.MILLISECONDS);
            System.out.println("getDelay's result:" + result + "," + toString());
            return result;
        }

        public long getTriggerTime() {
            return triggerTime;
        }

        public void setTriggerTime(long triggerTime) {
            this.triggerTime = triggerTime;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String toString() {
            return "workName:" + workName + ",triggerTime:" + triggerTime;
        }

        public String triggerInfo() {
            return toString() + ",触发时间差值:" + (System.currentTimeMillis() - triggerTime);
        }

    }

    class DelayExecutorThread extends Thread {

        private volatile boolean stop = false;

        public void run() {
            while (!stop) {
                try {
                    DelayWork work = queue.take();
                    System.out.println(work.triggerInfo());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setStop() {
            stop = true;
        }

        public boolean getStop() {
            return stop;
        }
    }

}
