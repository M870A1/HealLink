// 건강정보 Axios 글쓰기

function submitData() {
    const category = document.querySelector('input[name="category"]:checked').value;
    const sourceType = document.querySelector('input[name="sourceType"]:checked').value;

    const postData = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        category: category,
        sourceType: sourceType
    };

    if(!postData.title || !postData.content) {
        alert("제목과 내용을 모두 입력해 주세요.");
        return;
    }

    axios.post('/api/healthinfo', postData)
        .then(res => {
            alert("새로운 정보가 성공적으로 등록되었습니다!");
            location.href = "/healthinfo";
        })
        .catch(err => {
            console.error(err);
            alert("저장 중 오류가 발생했습니다. 입력을 확인해 주세요.");
        });
}