package com.zzh.controller;

import com.zzh.dao.entity.Category;
import com.zzh.service.CategoryService;
import com.zzh.vo.baseVo.PageResult;
import com.zzh.vo.baseVo.R;
import com.zzh.vo.reqVo.PageQueryReqVo;
import com.zzh.vo.respVo.PageQueryRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String>  addCategory(@RequestBody Category category)
    {
        return categoryService.addCategory(category);
    }

    @RequestMapping("/page")
    @GetMapping
    public R<PageQueryRespVo<Category>> getPage(PageQueryReqVo pageQueryReqVo)
    {
        return categoryService.getPage(pageQueryReqVo);
    }

    @DeleteMapping
    public R<String> delete(Long ids)
    {
       return categoryService.delete(ids);
    }

    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
       return categoryService.update(category);
    }

    @GetMapping("/list")
    public R<List<Category>> getAllCategory(Category category)
    {
        return categoryService.getAllCategory(category);
    }



}
