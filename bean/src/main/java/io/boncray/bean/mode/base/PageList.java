package io.boncray.bean.mode.base;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/11 14:55
 */
@Data
public class PageList<E> extends ArrayList<E> implements Serializable {

    private int pageSize;
    private int pageIndex;
    private int total;
    private int totalPage;
    private List<E> data;


    public PageList(Collection<? extends E> c) {
        super(c);
    }

    /**
     * 构造器.
     *
     * @param pageSize  页大小
     * @param pageIndex 页码
     * @param total     总计路数
     * @param data      数据
     */
    public PageList(int pageSize, int pageIndex, int total, List<E> data) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.total = total;
        this.data = data;
    }

}