package io.boncray.web.generator.supperClass.controller;

import io.boncray.web.generator.supperClass.entity.BaseDO;
import io.boncray.web.generator.supperClass.entity.Query;
import io.boncray.web.generator.supperClass.service.BaseService;
import io.boncray.bean.mode.response.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 15:55
 */
public abstract class BaseController<T extends BaseDO, Q extends Query> {


    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody T entity) {
        int row = this.getService().insertOrUpdate(entity);
        return row == 1 ? Result.success() : Result.failure();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        int row = this.getService().deleteByPk(id);
        return row == 1 ? Result.success() : Result.failure();
    }

    @GetMapping("/getById/{id}")
    public Result<T> getById(@PathVariable("id") Long id) {
        return Result.success(this.getService().getByKey(id));
    }

    @PostMapping("/list")
    public Result<List<T>> list(@RequestBody Q query) {
        return Result.success(this.getService().listExample(query));
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
