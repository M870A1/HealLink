package org.zerock.obj2026.mainpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.obj2026.notice.repository.NoticeRepository;
import org.zerock.obj2026.notice.NoticeMainPageDTO;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MainpageService {

    private final NoticeRepository noticeRepository;
    public List<NoticeMainPageDTO> getLatestNotices() {
        List<NoticeMainPageDTO> list = noticeRepository.findTop3ByOrderByCreatedAtDesc().stream()
                .map(NoticeMainPageDTO::new)
                .toList();
        return list;
    }
}
