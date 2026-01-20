// 건강정보 Axios 목록

// 1. 페이지 로드 시 목록 불러오기
document.addEventListener('DOMContentLoaded', function() {
    loadList(0); // 첫 페이지(0) 호출
});

function loadList(page) {
    // 1. 체크된 카테고리 모으기 (PUBLIC_CENTER, VACCINATION_CHECKUP)
    const categories = Array.from(document.querySelectorAll('.filter-category:checked'))
        .map(cb => cb.value);

    // 2. 체크된 출처 모으기 (PUBLIC_CENTER, MEDICAL_SITE)
    const sources = Array.from(document.querySelectorAll('.filter-source:checked'))
        .map(cb => cb.value);

    // 추가: 검색어 가져오기
    const keyword = document.getElementById('search-keyword').value;
    console.log("현재 입력된 검색어: [" + keyword + "]"); // 로그 확인용


    axios.get('/api/healthinfo', {
        params: {
            page: page,
            size: 9, // 9개
            categories: categories.join(','),
            sources: sources.join(','),
            keyword: keyword // 서버로 키워드 전달
        }
    })
        .then(response => {
            renderList(response.data.content);
            renderPagination(response.data);
        })
        .catch(error => console.error('검색 에러:', error));

}

// 필터 초기화 함수
function resetFilters() {
    document.querySelectorAll('.form-check-input').forEach(cb => cb.checked = false);
    loadList(0);
}

// 2. 목록 그리기 함수
function renderList(list) {
    const container = document.getElementById('list-container');
    container.innerHTML = '';

    // --- [수정 구간: 매핑 객체 정의] ---
    const categoryMap = {
        'PUBLIC_CENTER': '보건소 소식',
        'VACCINATION_CHECKUP': '예방접종/검진'
    };

    const sourceMap = {
        'PUBLIC_CENTER': '보건소',
        'MEDICAL_SITE': '의료 사이트'
    };
    // -------------------------------

    if (list.length === 0) {
        container.innerHTML = '<p class="col-12 text-center">데이터가 없습니다.</p>';
        return;
    }

    list.forEach(item => {
        const date = item.createdAt ? item.createdAt.replace('T', ' ').substring(0, 16) : '-';

        // --- [수정 구간: 한글 명칭으로 변환] ---
        // 매핑된 값이 없으면 원래 값(item.category)을 보여줌
        const categoryName = categoryMap[item.category] || item.category;
        const sourceName = sourceMap[item.sourceType] || item.sourceType;
        // -----------------------------------


        const html = `
        <div class="notice-item" onclick="location.href='/healthinfo/${item.id}'">
            <div class="item-badges" style="margin-bottom: 12px; display: flex; gap: 8px;">
                <span class="badge" style="background-color: #2f89fc; color: white; padding: 4px 8px; font-size: 11px; border-radius: 4px;">${categoryName}</span>
                <span class="badge" style="background-color: #28a745; color: white; padding: 4px 8px; font-size: 11px; border-radius: 4px;">${sourceName}</span>
            </div>

            <h4 class="info-title">${item.title}</h4>

            <p class="info-summary">
                ${item.summary || '요약 정보가 없습니다.'}
            </p>

            <div class="item-footer" style="margin-top: auto; display: flex; justify-content: space-between; align-items: flex-end;">
                <a class="more-link">자세히 보기 →</a>
                <small style="color: #bbb; font-size: 11px;">${date}</small>
            </div>
        </div>`;

        container.insertAdjacentHTML('beforeend', html);
    });
}

// 3. 페이징 버튼 생성 함수
function renderPagination(pageData) {
    const container = document.getElementById('pagination-container');
    container.innerHTML = '';

    const totalPages = pageData.totalPages;
    const currentPage = pageData.number;

    for (let i = 0; i < totalPages; i++) {
        const activeClass = (i === currentPage) ? 'active' : '';
        const li = `
                <li class="page-item ${activeClass}">
                    <a class="page-link" href="javascript:void(0)" onclick="loadList(${i})">${i + 1}</a>
                </li>`;
        container.insertAdjacentHTML('beforeend', li);
    }
}