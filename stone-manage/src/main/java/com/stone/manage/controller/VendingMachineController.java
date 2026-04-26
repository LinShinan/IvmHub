package com.stone.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stone.manage.domain.VO.VmDetailsVO;
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
import com.stone.manage.domain.VendingMachine;
import com.stone.manage.service.IVendingMachineService;
import com.stone.common.utils.poi.ExcelUtil;
import com.stone.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 * 
 * @author stone
 * @date 2026-04-12
 */
@RestController
@RequestMapping("/manage/machine")
public class VendingMachineController extends BaseController
{
    @Autowired
    private IVendingMachineService vendingMachineService;

    /**
     * 查询设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:list')")
    @GetMapping("/list")
    public TableDataInfo list(VendingMachine vendingMachine)
    {
        startPage();
        List<VendingMachine> list = vendingMachineService.selectVendingMachineList(vendingMachine);
        return getDataTable(list);
    }

    /**
     * 查询设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:list')")
    @GetMapping("/all")
    public TableDataInfo listAllBy(VendingMachine vendingMachine)
    {
        List<VendingMachine> list = vendingMachineService.selectVendingMachineList(vendingMachine);
        return getDataTable(list);
    }


    /**
     * 导出设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:export')")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VendingMachine vendingMachine)
    {
        List<VendingMachine> list = vendingMachineService.selectVendingMachineList(vendingMachine);
        ExcelUtil<VendingMachine> util = new ExcelUtil<VendingMachine>(VendingMachine.class);
        util.exportExcel(response, list, "设备管理数据");
    }

    /**
     * 获取设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vendingMachineService.selectVendingMachineById(id));
    }

    /**
     * 新增设备管理
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VendingMachine vendingMachine)
    {
        return toAjax(vendingMachineService.insertVendingMachine(vendingMachine));
    }

    /**
     * 修改设备管理
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:edit')")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VendingMachine vendingMachine)
    {
        return toAjax(vendingMachineService.updateVendingMachine(vendingMachine));
    }

    /**
     * 删除设备管理
     */
    @PreAuthorize("@ss.hasPermi('manage:machine:remove')")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vendingMachineService.deleteVendingMachineByIds(ids));
    }

    /**
     * 根据innerCode查询设备详情
     * @param innerCode
     * @return
     */
    @GetMapping("/details/{innerCode}")
    public AjaxResult getVmDetails(@PathVariable("innerCode") String innerCode){
        VmDetailsVO vmDetailsVO = vendingMachineService.getVmDetails(innerCode);
        return success(vmDetailsVO);
    }

}
