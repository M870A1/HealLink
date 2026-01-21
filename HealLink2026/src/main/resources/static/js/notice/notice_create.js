// 공지사항 Axios 글쓰기

function saveNotice() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const isPinned = document.getElementById('isPinned').checked;

    if (!title.trim() || !content.trim()) {
        alert("제목과 내용을 모두 입력해주세요.");
        return;
    }

    const requestData = {
        title: title,
        content: content,
        isPinned: isPinned
    };

    // Axios POST 요청
    axios.post('/api/notice', requestData)
        .then(response => {
            alert("공지사항이 성공적으로 등록되었습니다.");
            location.href = "/notice";
        })
        .catch(error => {
            console.error("저장 실패:", error);
            if (error.response && error.response.data.message) {
                alert("에러 발생: " + error.response.data.message);
            } else {
                alert("공지사항 등록 중 문제가 발생했습니다. 권한을 확인하세요.");
            }
        });
}