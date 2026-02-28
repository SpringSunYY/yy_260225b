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
import com.lz.manage.model.domain.EvaluateInfo;
import com.lz.manage.model.vo.evaluateInfo.EvaluateInfoVo;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoQuery;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoInsert;
import com.lz.manage.model.dto.evaluateInfo.EvaluateInfoEdit;
import com.lz.manage.service.IEvaluateInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 评价信息Controller
 *
 * @author YY
 * @date 2026-02-28
 */
@RestController
@RequestMapping("/manage/evaluateInfo")
public class EvaluateInfoController extends BaseController
{
    @Resource
    private IEvaluateInfoService evaluateInfoService;

    /**
     * 查询评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluateInfoQuery evaluateInfoQuery)
    {
        EvaluateInfo evaluateInfo = EvaluateInfoQuery.queryToObj(evaluateInfoQuery);
        startPage();
        List<EvaluateInfo> list = evaluateInfoService.selectEvaluateInfoList(evaluateInfo);
        List<EvaluateInfoVo> listVo= list.stream().map(EvaluateInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 查询评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:list')")
    @GetMapping("/list/scenic")
    public TableDataInfo listByScenic(EvaluateInfoQuery evaluateInfoQuery) {
        EvaluateInfo evaluateInfo = EvaluateInfoQuery.queryToObj(evaluateInfoQuery);
        startPage();
        List<EvaluateInfo> list = evaluateInfoService.selectEvaluateInfoListByScenic(evaluateInfo);
        List<EvaluateInfoVo> listVo = list.stream().map(EvaluateInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:export')")
    @Log(title = "评价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluateInfoQuery evaluateInfoQuery)
    {
        EvaluateInfo evaluateInfo = EvaluateInfoQuery.queryToObj(evaluateInfoQuery);
        List<EvaluateInfo> list = evaluateInfoService.selectEvaluateInfoList(evaluateInfo);
        ExcelUtil<EvaluateInfo> util = new ExcelUtil<EvaluateInfo>(EvaluateInfo.class);
        util.exportExcel(response, list, "评价信息数据");
    }

    /**
     * 获取评价信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        EvaluateInfo evaluateInfo = evaluateInfoService.selectEvaluateInfoById(id);
        return success(EvaluateInfoVo.objToVo(evaluateInfo));
    }

    /**
     * 新增评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:add')")
    @Log(title = "评价信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluateInfoInsert evaluateInfoInsert)
    {
        EvaluateInfo evaluateInfo = EvaluateInfoInsert.insertToObj(evaluateInfoInsert);
        return toAjax(evaluateInfoService.insertEvaluateInfo(evaluateInfo));
    }

    /**
     * 修改评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:edit')")
    @Log(title = "评价信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluateInfoEdit evaluateInfoEdit)
    {
        EvaluateInfo evaluateInfo = EvaluateInfoEdit.editToObj(evaluateInfoEdit);
        return toAjax(evaluateInfoService.updateEvaluateInfo(evaluateInfo));
    }

    /**
     * 删除评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluateInfo:remove')")
    @Log(title = "评价信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(evaluateInfoService.deleteEvaluateInfoByIds(ids));
    }
}
