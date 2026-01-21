// 공지사항 Axios 삭제

function deleteNotice() {

    const noticeId = document.getElementById('noticeId').value;

    if (confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
        axios.delete('/api/notice/' + noticeId)
            .then(res => {
                alert("공지사항이 삭제되었습니다.");
                location.href = '/notice';
            })
            .catch(err => {
                console.error(err);
                alert("삭제 권한이 없거나 오류가 발생했습니다.");
            });
    }
}