const reservedTimes = {
    "2026-01-14": ["07:30", "09:00", "11:00"]
};

function renderTimeSlots(dateStr) {
    const container = document.getElementById("timeSlots");
    container.innerHTML = "";

    for (let h = 7; h < 18; h++) {
        ["00", "30"].forEach(min => {
            const time = `${String(h).padStart(2, '0')}:${min}`;
            const btn = document.createElement("button");
            btn.textContent = time;

            const isReserved = reservedTimes[dateStr]?.includes(time);

            if (isReserved) {
                btn.disabled = true;
            } else {
                btn.addEventListener("click", () => {
                    alert(`예약 선택: ${dateStr} ${time}`);
                });
            }

            container.appendChild(btn);
        });
    }
}

flatpickr("#datePicker", {
    inline: true,
    locale: "ko",
    dateFormat: "Y-m-d",
    defaultDate: "today",
    disableMobile: true,
    onChange: function(_, dateStr) {
        renderTimeSlots(dateStr);
    }
});
