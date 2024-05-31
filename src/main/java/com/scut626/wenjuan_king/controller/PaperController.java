package com.scut626.wenjuan_king.controller;

import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper/delete-paper")
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
}
