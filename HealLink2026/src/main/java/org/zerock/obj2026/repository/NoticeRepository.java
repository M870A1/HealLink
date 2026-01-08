package org.zerock.obj2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
