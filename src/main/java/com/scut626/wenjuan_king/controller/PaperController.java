package com.scut626.wenjuan_king.controller;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.view.PaperPageView;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/paper")
public class PaperController {

    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping("/delete-paper")
    public Result deletePaper(@RequestBody Map<String, Object> request) {
        List<Integer> pidList = (List<Integer>) request.get("pidList");
        if (pidList == null || pidList.isEmpty()) {
            return Result.error("删除问卷失败，缺少问卷ID参数");
        }
        //输出日志
        log.info("正在删除id为" + pidList + "的问卷");
        //
        int resultCode = paperService.deletePapers(pidList);
        switch (resultCode) {
            case 0:
                return Result.success("删除问卷成功");
            case 1:
                return Result.error("删除问卷失败");
            case 2:
                return Result.error("删除问卷失败，问卷ID非法（无此问卷）");
            default:
                return Result.error("删除问卷失败，未知错误");
        }
    }

    /**
     * 增加一个新问卷
     * @param paperUpdateInfo 传入的问卷信息
     */
    @PostMapping("/update-paper")
    public Result insertPaper(@RequestBody UpdateViewPaper paperUpdateInfo)
    {
        log.info("正在增加问卷："+paperUpdateInfo.getTitle());
        paperService.insertPaper(paperUpdateInfo);
        return Result.success();
    }

    /**
     * 更新问卷
     * @param paperUpdateInfo 传入的问卷信息
     * @return
     */
    @PutMapping("/update-paper")
    public Result updatePaper(@RequestBody UpdateViewPaper paperUpdateInfo)
    {
        log.info("正在修改问卷："+paperUpdateInfo.getTitle());
        paperService.updatePaper(paperUpdateInfo);
        return Result.success();
    }

    /**
     * 查看问卷（供修改使用）
     * @param pid
     * @return
     */
    @PostMapping("/view-paper")
    public Result adminViewPaper(Integer pid)
    {
        log.info("正在获取问卷编号为："+ pid);
        UpdateViewPaper paperView = paperService.viewPaper(pid);
        if(paperView == null)
        {
            log.info("要查看的问卷不存在");
            return Result.error("paperID not exist");
        }
        else
        {
            return Result.success(paperView);
        }
    }

    @RequestMapping("/paper-lists")
    public Result viewPaperList(String name, Integer page, Integer pageSize)
    {
        log.info("查找问卷...");
        PaperPageView paperPageView = paperService.getPaperList(name, page, pageSize);
        return Result.success(paperPageView);
    }

}
