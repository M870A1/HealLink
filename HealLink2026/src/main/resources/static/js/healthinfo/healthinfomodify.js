function updateData() {
    const id = document.getElementById('healthinfoId').value;
    const category = document.querySelector('input[name="category"]:checked').value;
    const sourceType = document.querySelector('input[name="sourceType"]:checked').value;

    const postData = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        category: category,
        sourceType: sourceType
    };

    if(!postData.title || !postData.content) {
        alert("제목과 내용을 입력해주세요.");
        return;
    }

    axios.put('/api/healthinfo/' + id, postData)
        .then(res => {
            alert("성공적으로 수정되었습니다!");
            location.href = "/healthinfo/" + id;
        })
        .catch(err => {
            console.error(err);
            alert("수정 중 에러가 발생했습니다.");
        });
}