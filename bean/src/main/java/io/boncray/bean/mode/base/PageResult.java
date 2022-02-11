package io.boncray.bean.mode.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<E> implements Serializable {
    private int pageSize;
    private int pageIndex;
    private int total;
    private int totalPage;
    private List<E> list;
}
