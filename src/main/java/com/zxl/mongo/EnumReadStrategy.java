package com.zxl.mongo;

/**
 * <b>读策略枚举<\b>
 * 
 * @author zhangxl
 * 
 */
public enum EnumReadStrategy
{
    
    Primary, Primary_Preferred, Secondary, Secondary_Preferred, Nearest
    // Primary:默认的读模式，只从 primary(活跃节点)读,如果primary节点无法到达，则抛出错误。
    // Primary_Preferred:primary可获得，从primary读取，否则从secondary(备份节点)读取.
    // Secondary:从secondary读取，否则出错。
    // Secondary_Preferred:secondary可获得，从secondary读取，否则从primary读取。
    // Nearest:从响应最快的节点中读取。
}
