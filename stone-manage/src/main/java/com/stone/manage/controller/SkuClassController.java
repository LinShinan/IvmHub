package com.stone.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stone.common.annotation.Log;
import com.stone.common.core.controller.BaseController;
import com.stone.common.core.domain.AjaxResult;
import com.stone.common.enums.BusinessType;
import com.stone.manage.domain.SkuClass;
import com.stone.manage.service.ISkuClassService;
import com.stone.common.utils.poi.ExcelUtil;
import com.stone.common.core.page.TableDataInfo;

/**
 * 商品类型Controller
 * 
 * @author stone
 * @date 2026-04-17
 */
@RestController
@RequestMapping("/manage/skuClass")
public class SkuClassController extends BaseController
{
    @Autowired
    private ISkuClassService skuClassService;

    /**
     * 查询商品类型列表
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:list')")
    @GetMapping("/list")
    public TableDataInfo list(SkuClass skuClass)
    {
        startPage();
        List<SkuClass> list = skuClassService.selectSkuClassList(skuClass);
        return getDataTable(list);
    }

    /**
     * 根据条件查询所有商品类型列表
     * @param skuClass
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:list')")
    @GetMapping("/all")
    public TableDataInfo listAllBy(SkuClass skuClass)
    {
        List<SkuClass> list = skuClassService.selectSkuClassList(skuClass);
        return getDataTable(list);
    }

    /**
     * 导出商品类型列表
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:export')")
    @Log(title = "商品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SkuClass skuClass)
    {
        List<SkuClass> list = skuClassService.selectSkuClassList(skuClass);
        ExcelUtil<SkuClass> util = new ExcelUtil<SkuClass>(SkuClass.class);
        util.exportExcel(response, list, "商品类型数据");
    }

    /**
     * 获取商品类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId)
    {
        return success(skuClassService.selectSkuClassByClassId(classId));
    }

    /**
     * 新增商品类型
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:add')")
    @Log(title = "商品类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SkuClass skuClass)
    {
        return toAjax(skuClassService.insertSkuClass(skuClass));
    }

    /**
     * 修改商品类型
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:edit')")
    @Log(title = "商品类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SkuClass skuClass)
    {
        return toAjax(skuClassService.updateSkuClass(skuClass));
    }

    /**
     * 删除商品类型
     */
    @PreAuthorize("@ss.hasPermi('manage:skuClass:remove')")
    @Log(title = "商品类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds)
    {
        return toAjax(skuClassService.deleteSkuClassByClassIds(classIds));
    }
}
