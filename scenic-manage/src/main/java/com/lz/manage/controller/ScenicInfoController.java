package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.manage.model.vo.scenicInfo.ScenicInfoDetailVo;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.ScenicInfo;
import com.lz.manage.model.vo.scenicInfo.ScenicInfoVo;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoQuery;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoInsert;
import com.lz.manage.model.dto.scenicInfo.ScenicInfoEdit;
import com.lz.manage.service.IScenicInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 景区信息Controller
 *
 * @author YY
 * @date 2026-02-28
 */
@RestController
@RequestMapping("/manage/scenicInfo")
public class ScenicInfoController extends BaseController
{
    @Resource
    private IScenicInfoService scenicInfoService;

    /**
     * 查询景区信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:scenicInfo:list,manage:scenicInfo:query')")
    @GetMapping("/list")
    public TableDataInfo list(ScenicInfoQuery scenicInfoQuery)
    {
        ScenicInfo scenicInfo = ScenicInfoQuery.queryToObj(scenicInfoQuery);
        startPage();
        List<ScenicInfo> list = scenicInfoService.selectScenicInfoList(scenicInfo);
        List<ScenicInfoVo> listVo= list.stream().map(ScenicInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出景区信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:export')")
    @Log(title = "景区信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScenicInfoQuery scenicInfoQuery)
    {
        ScenicInfo scenicInfo = ScenicInfoQuery.queryToObj(scenicInfoQuery);
        List<ScenicInfo> list = scenicInfoService.selectScenicInfoList(scenicInfo);
        ExcelUtil<ScenicInfo> util = new ExcelUtil<ScenicInfo>(ScenicInfo.class);
        util.exportExcel(response, list, "景区信息数据");
    }

    /**
     * 获取景区信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ScenicInfo scenicInfo = scenicInfoService.selectScenicInfoById(id);
        return success(ScenicInfoVo.objToVo(scenicInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:query')")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id) {
        ScenicInfoDetailVo scenicInfo = scenicInfoService.selectScenicInfoDetailById(id);
        return success(scenicInfo);
    }

    /**
     * 新增景区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:add')")
    @Log(title = "景区信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScenicInfoInsert scenicInfoInsert)
    {
        ScenicInfo scenicInfo = ScenicInfoInsert.insertToObj(scenicInfoInsert);
        return toAjax(scenicInfoService.insertScenicInfo(scenicInfo));
    }

    /**
     * 修改景区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:edit')")
    @Log(title = "景区信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScenicInfoEdit scenicInfoEdit)
    {
        ScenicInfo scenicInfo = ScenicInfoEdit.editToObj(scenicInfoEdit);
        return toAjax(scenicInfoService.updateScenicInfo(scenicInfo));
    }

    /**
     * 删除景区信息
     */
    @PreAuthorize("@ss.hasPermi('manage:scenicInfo:remove')")
    @Log(title = "景区信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scenicInfoService.deleteScenicInfoByIds(ids));
    }
}
