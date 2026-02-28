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
import com.lz.manage.model.domain.LikesInfo;
import com.lz.manage.model.vo.likesInfo.LikesInfoVo;
import com.lz.manage.model.dto.likesInfo.LikesInfoQuery;
import com.lz.manage.model.dto.likesInfo.LikesInfoInsert;
import com.lz.manage.model.dto.likesInfo.LikesInfoEdit;
import com.lz.manage.service.ILikesInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 点赞信息Controller
 *
 * @author YY
 * @date 2026-02-28
 */
@RestController
@RequestMapping("/manage/likesInfo")
public class LikesInfoController extends BaseController
{
    @Resource
    private ILikesInfoService likesInfoService;

    /**
     * 查询点赞信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(LikesInfoQuery likesInfoQuery)
    {
        LikesInfo likesInfo = LikesInfoQuery.queryToObj(likesInfoQuery);
        startPage();
        List<LikesInfo> list = likesInfoService.selectLikesInfoList(likesInfo);
        List<LikesInfoVo> listVo= list.stream().map(LikesInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出点赞信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:export')")
    @Log(title = "点赞信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LikesInfoQuery likesInfoQuery)
    {
        LikesInfo likesInfo = LikesInfoQuery.queryToObj(likesInfoQuery);
        List<LikesInfo> list = likesInfoService.selectLikesInfoList(likesInfo);
        ExcelUtil<LikesInfo> util = new ExcelUtil<LikesInfo>(LikesInfo.class);
        util.exportExcel(response, list, "点赞信息数据");
    }

    /**
     * 获取点赞信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        LikesInfo likesInfo = likesInfoService.selectLikesInfoById(id);
        return success(LikesInfoVo.objToVo(likesInfo));
    }

    /**
     * 新增点赞信息
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:add')")
    @Log(title = "点赞信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LikesInfoInsert likesInfoInsert)
    {
        LikesInfo likesInfo = LikesInfoInsert.insertToObj(likesInfoInsert);
        return toAjax(likesInfoService.insertLikesInfo(likesInfo));
    }

    /**
     * 修改点赞信息
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:edit')")
    @Log(title = "点赞信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LikesInfoEdit likesInfoEdit)
    {
        LikesInfo likesInfo = LikesInfoEdit.editToObj(likesInfoEdit);
        return toAjax(likesInfoService.updateLikesInfo(likesInfo));
    }

    /**
     * 删除点赞信息
     */
    @PreAuthorize("@ss.hasPermi('manage:likesInfo:remove')")
    @Log(title = "点赞信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(likesInfoService.deleteLikesInfoByIds(ids));
    }
}
