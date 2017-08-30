package com.zxl.test.concurrency.example;

public interface TaskReturnResultHandler
{
    /**
     * 由异步框架处理任务的返回结果
     * 
     * @param returnResult
     *            提交task的返回结果
     */
    public void process(Object returnResult);
}
