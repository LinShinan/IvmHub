package com.stone.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stone.manage.domain.Node;
import com.stone.manage.domain.VO.RegionVO;
import com.stone.manage.service.INodeService;
import lombok.extern.slf4j.Slf4j;
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
import com.stone.manage.domain.Region;
import com.stone.manage.service.IRegionService;
import com.stone.common.utils.poi.ExcelUtil;
import com.stone.common.core.page.TableDataInfo;

/**
 * 区域管理Controller
 * 
 * @author stone
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/manage/region")
@Slf4j
public class RegionController extends BaseController
{
    @Autowired
    private IRegionService regionService;


    /**
     * 查询区域管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:region:list')")
    @GetMapping("/list")
    public TableDataInfo list(Region region)
    {
        startPage();
        List<RegionVO> list = regionService.selectRegionVOList(region);
        return getDataTable(list);
    }

    /**
     * 查询所有区域列表
     */
    @PreAuthorize("@ss.hasPermi('manage:region:list')")
    @GetMapping("/list/all")
    public TableDataInfo listAll()
    {
        List<RegionVO> list = regionService.selectRegionVOList(null);
        log.info("查询所有区域{}",list);
        return getDataTable(list);
    }




    /**
     * 导出区域管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:region:export')")
    @Log(title = "区域管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Region region)
    {
        List<RegionVO> list = regionService.selectRegionVOList(region);
        ExcelUtil<RegionVO> util = new ExcelUtil<RegionVO>(RegionVO.class);
        util.exportExcel(response, list, "区域管理数据");
    }

    /**
     * 获取区域管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:region:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(regionService.selectRegionById(id));
    }

    /**
     * 新增区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:add')")
    @Log(title = "区域管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Region region)
    {
        return toAjax(regionService.insertRegion(region));
    }

    /**
     * 修改区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:edit')")
    @Log(title = "区域管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Region region)
    {
        return toAjax(regionService.updateRegion(region));
    }

    /**
     * 删除区域管理
     */
    @PreAuthorize("@ss.hasPermi('manage:region:remove')")
    @Log(title = "区域管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(regionService.deleteRegionByIds(ids));
    }
}
