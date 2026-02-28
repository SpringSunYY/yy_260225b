package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
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
import com.lz.manage.model.domain.LooksInfo;
import com.lz.manage.model.vo.looksInfo.LooksInfoVo;
import com.lz.manage.model.dto.looksInfo.LooksInfoQuery;
import com.lz.manage.model.dto.looksInfo.LooksInfoInsert;
import com.lz.manage.model.dto.looksInfo.LooksInfoEdit;
import com.lz.manage.service.ILooksInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 浏览信息Controller
 *
 * @author YY
 * @date 2026-02-28
 */
@RestController
@RequestMapping("/manage/looksInfo")
public class LooksInfoController extends BaseController
{
    @Resource
    private ILooksInfoService looksInfoService;

    /**
     * 查询浏览信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(LooksInfoQuery looksInfoQuery)
    {
        LooksInfo looksInfo = LooksInfoQuery.queryToObj(looksInfoQuery);
        startPage();
        List<LooksInfo> list = looksInfoService.selectLooksInfoList(looksInfo);
        List<LooksInfoVo> listVo= list.stream().map(LooksInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出浏览信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:export')")
    @Log(title = "浏览信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LooksInfoQuery looksInfoQuery)
    {
        LooksInfo looksInfo = LooksInfoQuery.queryToObj(looksInfoQuery);
        List<LooksInfo> list = looksInfoService.selectLooksInfoList(looksInfo);
        ExcelUtil<LooksInfo> util = new ExcelUtil<LooksInfo>(LooksInfo.class);
        util.exportExcel(response, list, "浏览信息数据");
    }

    /**
     * 获取浏览信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        LooksInfo looksInfo = looksInfoService.selectLooksInfoById(id);
        return success(LooksInfoVo.objToVo(looksInfo));
    }

    /**
     * 新增浏览信息
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:add')")
    @Log(title = "浏览信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LooksInfoInsert looksInfoInsert)
    {
        LooksInfo looksInfo = LooksInfoInsert.insertToObj(looksInfoInsert);
        return toAjax(looksInfoService.insertLooksInfo(looksInfo));
    }

    /**
     * 修改浏览信息
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:edit')")
    @Log(title = "浏览信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LooksInfoEdit looksInfoEdit)
    {
        LooksInfo looksInfo = LooksInfoEdit.editToObj(looksInfoEdit);
        return toAjax(looksInfoService.updateLooksInfo(looksInfo));
    }

    /**
     * 删除浏览信息
     */
    @PreAuthorize("@ss.hasPermi('manage:looksInfo:remove')")
    @Log(title = "浏览信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(looksInfoService.deleteLooksInfoByIds(ids));
    }
}
