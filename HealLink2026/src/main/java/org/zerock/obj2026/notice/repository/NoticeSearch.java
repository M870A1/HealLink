package org.zerock.obj2026.notice.repository;

import org.springframework.data.domain.Page;
import org.zerock.obj2026.notice.domain.Notice;
import org.zerock.obj2026.notice.dto.NoticePageRequestDTO;

public interface NoticeSearch {
    // QueryDSL용 : 검색어와 페이징 정보를 담은 DTO를 받아서 결과를 반환
    Page<Notice> searchAll(NoticePageRequestDTO pageRequestDTO);
}