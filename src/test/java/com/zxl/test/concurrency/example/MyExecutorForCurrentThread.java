package com.zxl.test.concurrency.example;

import java.util.concurrent.Executor;

public class MyExecutorForCurrentThread implements Executor
{
    
    @Override
    public void execute(Runnable command)
    {
        command.run();
    }
    
}
