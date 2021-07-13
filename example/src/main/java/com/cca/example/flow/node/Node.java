package com.cca.example.flow.node;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/13 19:49
 */
public interface Node<Context> {


    /**
     * 执行器方法，执行目标逻辑
     *
     * @param context 参数
     */
    void execute(Context context);
    
}
