package com.zxl.util;


public class RunTimeCalculator
{
    private long starttime = System.currentTimeMillis();
    
    private TimeOutLayout layout = new DefaultTimeOutLayout();
    
    public void setLayout(TimeOutLayout layout)
    {
        this.layout = layout;
    }
    
    /**
     * 开始计时
     */
    public void startTiming()
    {
        starttime = System.currentTimeMillis();
    }
    
    /**
     * 停止计时，并获得计时运算结果
     * 
     * @return
     */
    public String stop()
    {
        if(starttime <= 0)
            throw new IllegalArgumentException("please start time first!");
        
        String calculator = layout.formatTime(starttime, System.currentTimeMillis());
        starttime = 0;
        return calculator;
    }
    
    /**
     * 观察计时
     * 
     * @return
     */
    public String watch()
    {
        if(starttime <= 0)
            throw new IllegalArgumentException("please start time first!");
        
        return layout.formatTime(starttime, System.currentTimeMillis());
    }
    
    /**
     * 计时输出显示控制
     * 
     * @author zhangxl
     * 
     */
    public interface TimeOutLayout
    {
        public String formatTime(long startTime, long watchTime);
    }
    
    /**
     * 默认的输出显示控制
     * 
     * @author zhangxl
     * 
     */
    private class DefaultTimeOutLayout implements TimeOutLayout
    {
        
        @Override
        public String formatTime(long startTime, long watchTime)
        {
            long reduce = watchTime - startTime;
            int hour = (int) (reduce / (1000 * 60 * 60));
            int minute = (int) ((reduce - hour * 1000 * 60 * 60) / (1000 * 60));
            int second = (int) ((reduce - hour * 1000 * 60 * 60 - minute * 1000 * 60) / 1000);
            
            return "--执行耗时:" + hour + " 时 " + minute + " 分 " + second + " 秒。";
        }
        
    }
}
