// 공지사항 Axios 수정

function updateNotice() {
    const id = document.getElementById('noticeId').value;

    const requestData = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        isPinned: document.getElementById('isPinned').checked
    };

    if (!requestData.title.trim() || !requestData.content.trim()) {
        alert("제목과 내용을 모두 입력해주세요.");
        return;
    }

    // Axios PUT 요청
    axios.put('/api/admin/notice/' + id, requestData)
        .then(response => {
            alert("성공적으로 수정되었습니다.");
            location.href = '/admin/notice/' + id;
        })
        .catch(error => {
            console.error("수정 실패:", error);
            if(error.response && error.response.status === 403) {
                alert("수정 권한이 없습니다.");
            } else {
                alert("수정 처리 중 오류가 발생했습니다.");
            }
        });
}