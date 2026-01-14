const openBtn = document.getElementById("openRegionModal");
const closeBtn = document.getElementById("closeRegionModal");
const modalOverlay = document.getElementById("regionModalOverlay");

//  열기
openBtn.addEventListener("click", () => {
    modalOverlay.classList.add("active");
});

//  닫기 (버튼)
closeBtn.addEventListener("click", closeModal);

//  닫기 (배경 클릭)
modalOverlay.addEventListener("click", (e) => {
    if (e.target === modalOverlay) {
        closeModal();
    }
});

//  ESC 키로 닫기
document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
        closeModal();
    }
});

function closeModal() {
    modalOverlay.classList.remove("active");
}
