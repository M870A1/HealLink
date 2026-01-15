 // 이미 예약된 시간 (예시)
const reservedTimes = {
    "2026-01-15": ["07:30", "09:00", "11:00"]
};

let selectedDate = null;
let selectedTime = null;

function renderTimeSlots(dateStr) {
    // 날짜는 YYYY-MM-DD로 포맷하여 표기
    const date = new Date(dateStr);
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, "0"); // 0~11 +1 하여 1~12월을 표기함
    const d = String(date.getDate()).padStart(2, "0");
    selectedDate = `${y}-${m}-${d}`;

    selectedTime = null;
    const grid = document.getElementById("timeGrid");
    const confirmBtn = document.getElementById("confirmBtn");

    grid.innerHTML = `<p class="guide-text">${selectedDate} 예약 가능 시간</p>`;
    confirmBtn.style.display = "none";

    for (let h = 7; h <= 18; h++) {
        ["00","30"].forEach(min => {
            if (h === 18 && min === "30") return; // 18:30 이후 예약불가
            const time = `${String(h).padStart(2,"0")}:${min}`;
            const btn = document.createElement("button");
            btn.textContent = time;
            btn.className = "time-btn";

            const isReserved = reservedTimes[selectedDate]?.includes(time);
            if (isReserved) {
                btn.disabled = true;
            } else {
                btn.addEventListener("click", () => {
                    document.querySelectorAll("#timeGrid button").forEach(b => b.classList.remove("selected"));
                    btn.classList.add("selected");
                    selectedTime = time;

                    confirmBtn.textContent = `${time}으로 예약하시겠습니까?`;
                    confirmBtn.style.display = "block";
                });
            }

            grid.appendChild(btn);
        });
    }
}

 // 예약 버튼 클릭
document.getElementById("confirmBtn").addEventListener("click", () => {
    if (!selectedDate || !selectedTime) return;

    alert(`${selectedDate} ${selectedTime} 예약 이 완료되었습니다.`);
    window.location.href = "/index.html";
    // window.location.href = "/";    /표기 가능할시 윗줄 지우고 주석해제
});

// 캘린더
flatpickr(document.getElementById("datePicker"), {
    inline: true,
    locale: "ko",
    dateFormat: "Y-m-d",
    defaultDate: "today",
    minDate: "today",
    disableMobile: true,
    showMonths: 1,
    onChange: (_, dateStr) => renderTimeSlots(dateStr)
});
