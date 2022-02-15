package io.boncray.generate.supperClass.controller;

import io.boncray.bean.mode.base.PageList;
import io.boncray.bean.mode.base.PageQuery;
import io.boncray.bean.mode.base.PageResult;
import io.boncray.bean.mode.response.Result;
import io.boncray.generate.supperClass.entity.BaseDO;
import io.boncray.generate.supperClass.entity.Query;
import io.boncray.generate.supperClass.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 15:55
 */
public abstract class BaseController<T extends BaseDO, Q extends Query, page extends PageQuery> {


    @ApiOperation("新增/更新")
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody T entity) {
        int row = this.getService().insertOrUpdate(entity);
        return row == 1 ? Result.success() : Result.failure();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        int row = this.getService().deleteByPk(id);
        return row == 1 ? Result.success() : Result.failure();
    }

    @ApiOperation("单个查询")
    @GetMapping("/getById/{id}")
    public Result<T> getById(@PathVariable("id") Long id) {
        return Result.success(this.getService().getByKey(id));
    }

    @ApiOperation("列表查询")
    @PostMapping("/list")
    public Result<List<T>> list(@RequestBody Q query) {
        return Result.success(this.getService().listExample(query));
    }

    @ApiOperation("分页查询")
    @PostMapping("/pageList")
    public Result<PageResult<T>> pageList(@RequestBody page query) {
        PageList<T> list = this.getService().listPage(query);
        return Result.pageSuccess(list);
    }

    /**
     * 获取一个service执行 控制器逻辑
     *
     * @return
     */
    protected abstract BaseService<T, Q> getService();
}
