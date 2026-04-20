package com.stone.manage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stone.manage.domain.VO.SkuVO;
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
import com.stone.manage.domain.Sku;
import com.stone.manage.service.ISkuService;
import com.stone.common.utils.poi.ExcelUtil;
import com.stone.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品管理Controller
 * 
 * @author stone
 * @date 2026-04-17
 */
@RestController
@RequestMapping("/manage/sku")
public class SkuController extends BaseController
{
    @Autowired
    private ISkuService skuService;

    /**
     * 查询商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sku sku)
    {
        startPage();
        List<SkuVO> list = skuService.selectSkuVOList(sku);
        return getDataTable(list);
    }

    /**
     * 查询商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:list')")
    @GetMapping("/all")
    public TableDataInfo listAllBy(Sku sku)
    {
        List<SkuVO> list = skuService.selectSkuVOList(sku);
        return getDataTable(list);
    }

    /**
     * 导出商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:export')")
    @Log(title = "商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sku sku)
    {
        List<SkuVO> list = skuService.selectSkuVOList(sku);
        ExcelUtil<SkuVO> util = new ExcelUtil<SkuVO>(SkuVO.class);
//        util.exportExcel(response, list, "商品管理数据");
        util.exportEasyExcel(response, list, "商品管理数据");
    }

    /**
     * 导入商品管理列表
     * @param file
     * @return
     * @throws IOException
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:add')")
    @Log(title = "商品管理", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importExcel(MultipartFile file) throws Exception {
        ExcelUtil<Sku> util = new ExcelUtil<Sku>(Sku.class);
//        List<Sku> skus = util.importExcel(file.getInputStream());
        List<Sku> skus = util.importEasyExcel(file.getInputStream());
        return toAjax(skuService.insertSkuBatch(skus));
    }

    /**
     * 获取商品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:query')")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId)
    {
        return success(skuService.selectSkuBySkuId(skuId));
    }

    /**
     * 新增商品管理
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:add')")
    @Log(title = "商品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sku sku)
    {
        return toAjax(skuService.insertSku(sku));
    }

    /**
     * 修改商品管理
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:edit')")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sku sku)
    {
        return toAjax(skuService.updateSku(sku));
    }

    /**
     * 删除商品管理
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:remove')")
    @Log(title = "商品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{skuIds}")
    public AjaxResult remove(@PathVariable Long[] skuIds)
    {
        return toAjax(skuService.deleteSkuBySkuIds(skuIds));
    }
}
