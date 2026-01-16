package org.zerock.obj2026.healthinfo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.zerock.obj2026.healthinfo.domain.HealthInfo;
import org.zerock.obj2026.healthinfo.domain.HealthInfoCategory;
import org.zerock.obj2026.healthinfo.domain.HealthInfoSourceType;
import org.zerock.obj2026.healthinfo.dto.HealthInfoAddRequest;
import org.zerock.obj2026.healthinfo.dto.HealthInfoResponse;
import org.zerock.obj2026.healthinfo.repository.HealthInfoRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthInfoService {
    private final HealthInfoRepository healthInfoRepository;

    // 1. 목록 .findAll... (2026-01-16 어제 다른 패키지를 쓰는 코드(NoticePageResponseDTO)로 꼬여있어서 수정)
    public Page<HealthInfoResponse> findAll(Pageable pageable) {

        return healthInfoRepository
                .findAllByOrderByHealthInfoIdDesc(pageable)
                .map(HealthInfoResponse::new);
    }


      // 1-2. 목록 필터 .findAllWithFilters(이제 이게 쓰임)
      public Page<HealthInfoResponse> findAllWithFilters(
              Pageable pageable,
              List<String> categories,
              List<String> sources,
              String keyword) {

          // 1. Enum 변환 로직 (기존 유지)
          List<HealthInfoCategory> categoryEnums = (categories != null && !categories.isEmpty())
                  ? categories.stream().map(HealthInfoCategory::valueOf).toList() : null;
          List<HealthInfoSourceType> sourceEnums = (sources != null && !sources.isEmpty())
                  ? sources.stream().map(HealthInfoSourceType::valueOf).toList() : null;

          // 2. 키워드 처리
          String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword : null;

          // 3. 리포지토리 호출 및 DTO 변환
          // JPA의 .map()을 사용하면 Page 객체 구조를 유지하면서 엔티티만 DTO로 바꿀 수 있습니다.
          return healthInfoRepository.findAllWithFilters(
                          categoryEnums, sourceEnums, searchKeyword, pageable)
                  .map(HealthInfoResponse::new);
      }



    // 2. 상세 조회  .findById
    public HealthInfoResponse findById(long id){
        HealthInfo healthInfo =  healthInfoRepository.findById(id)
                        // 예외처리
                        .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new HealthInfoResponse(healthInfo);
    }

    // 3. 쓰기
    @Transactional
    public Long createPost(HealthInfoAddRequest dto) {
        HealthInfo entity = dto.toEntity();
        return healthInfoRepository.save(entity).getHealthInfoId();
    }

    // 4. 지우기
    @Transactional
    public void delete(Long id) {
        // 해당 ID의 게시글이 있는지 먼저 확인 (안전장치)
        HealthInfo healthInfo = healthInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 삭제 실행
        healthInfoRepository.delete(healthInfo);
    }

    // 5. 수정
    @Transactional
    public Long update(Long id, HealthInfoAddRequest dto) {
        HealthInfo healthInfo = healthInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 엔티티의 메서드(.update)를 통해 데이터 변경
        healthInfo.update(
                dto.getTitle(),
                dto.getContent(),
                dto.getCategory(),  // DTO에 있는 카테고리
                dto.getSourceType() // DTO에 있는 출처타입
        );
        return id;
    }


}