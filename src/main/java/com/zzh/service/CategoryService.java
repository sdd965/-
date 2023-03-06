package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.dao.entity.Category;
import com.zzh.vo.baseVo.PageResult;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    R<String> addCategory(Category category);

    R<PageQueryRespVo<Category>> getPage(PageQueryReqVo pageQueryReqVo);

    R<String> delete(Long id);

    R<String> update(Category category);

    R<List<Category>> getAllCategory(Category category);
}
