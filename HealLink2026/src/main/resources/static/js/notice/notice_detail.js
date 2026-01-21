// 공지사항 Axios 삭제

function deleteNotice() {
    const noticeId = [[${notice.noticeId}]];
    if (confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
        axios.delete('/api/admin/notice/' + noticeId)
            .then(res => {
                alert("공지사항이 삭제되었습니다.");
                location.href = '/admin/notice';
            })
            .catch(err => {
                alert("삭제 권한이 없거나 오류가 발생했습니다.");
            });
    }
}