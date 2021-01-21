package com.cca.core.generator.supperClass.controller;

import com.cca.core.generator.supperClass.entity.Query;
import com.cca.core.generator.supperClass.service.BaseService;
import com.cca.core.generator.supperClass.entity.BaseDO;
import org.springframework.web.bind.annotation.*;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 15:55
 */
public abstract class BaseController<T extends BaseDO, Q extends Query> {


    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(T entity) {
        this.getService().insertOrUpdate(entity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.getService().deleteByPk(id);
    }

    @GetMapping("/getById/{id}")
    public void getById(@PathVariable("id") Long id) {
        this.getService().getByKey(id);
    }

    @PostMapping("/list")
    public void list(@RequestBody Q query) {
        this.getService().listExample(query);
    }

    @PostMapping("/pageList")
    public void pageList(@RequestBody Q query) {
        // todo 待实现分页查询
    }

    /**
     * 获取一个service执行 控制器逻辑
     *
     * @return
     */
    protected abstract BaseService<T, Q> getService();
}
