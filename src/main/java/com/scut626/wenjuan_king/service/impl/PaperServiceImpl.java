package com.scut626.wenjuan_king.service.impl;

import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperMapper paperMapper;

    @Autowired
    public PaperServiceImpl(PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Override
    public int deletePapers(List<Integer> pidList) {
        if (pidList == null || pidList.isEmpty()) {
            return 1; // 删除失败，问卷ID列表为空
        }

        int successCount = 0;
        for (int pid : pidList) {
            int result = paperMapper.deletePaperById(pid);
            if (result > 0) {
                successCount++;
            }
        }

        if (successCount == pidList.size()) {
            return 0; // 删除成功
        } else if (successCount == 0) {
            return 2; // 删除失败，问卷ID非法（无此问卷）
        } else {
            return 1; // 部分问卷删除成功，部分删除失败
        }
    }
}
