//  상태
const state = {
    step: 1,
    sido: null,
    gu: null,
    dong: null
};

//  DOM
const container = document.getElementById("regionButtons");
const result = document.getElementById("result");

// 데이터 시/도
const regions = [
    { name: "서울", enabled: false },
    { name: "부산", enabled: true },
    { name: "대구", enabled: false },
    { name: "인천", enabled: false },
    { name: "광주", enabled: false },
    { name: "대전", enabled: false },
    { name: "울산", enabled: false },
    { name: "세종", enabled: false },
    { name: "경기", enabled: false },
    { name: "강원", enabled: false },
    { name: "충북", enabled: false },
    { name: "충남", enabled: false },
    { name: "전북", enabled: false },
    { name: "전남", enabled: false },
    { name: "경북", enabled: false },
    { name: "경남", enabled: false },
    { name: "제주", enabled: false }
];

function renderSido() {
    const title = document.createElement("h3");
    title.innerText = "시/도를 선택해주세요";
    container.appendChild(title);

    const fragment = document.createDocumentFragment();

    regions.forEach(region => {
        const btn = document.createElement("button");
        btn.innerText = region.name;

        if (!region.enabled) {
            btn.disabled = true;
            btn.classList.add("disabled");
        }

        btn.onclick = () => {
            if (!region.enabled) return;
            state.sido = region.name;
            state.step = 2;
            render();
        };

        fragment.appendChild(btn);
    });

    container.appendChild(fragment);
}

function renderGu() {
    const title = document.createElement("h3");
    title.innerText = "구를 선택해주세요";
    container.appendChild(title);

    const backBtn = document.createElement("button");
    backBtn.innerText = "← 시/도 다시 선택";
    backBtn.onclick = () => {
        state.step = 1;
        state.sido = null;
        state.gu = null;
        state.dong = null;
        result.innerText = "";
        render();
    };
    container.appendChild(backBtn);

    const fragment = document.createDocumentFragment();

    busanGuList.forEach(gu => {
        const btn = document.createElement("button");
        btn.innerText = gu;

        btn.onclick = () => {
            state.gu = gu;
            state.step = 3;
            render();
        };

        fragment.appendChild(btn);
    });

    container.appendChild(fragment);
}

function renderDong() {
    const title = document.createElement("h3");
    title.innerText = `${state.gu}의 동을 선택해주세요`;
    container.appendChild(title);

    const backBtn = document.createElement("button");
    backBtn.innerText = "← 구 다시 선택";
    backBtn.onclick = () => {
        state.step = 2;
        state.gu = null;
        state.dong = null;
        render();
    };
    container.appendChild(backBtn);

    const dongList = busanDongMap[state.gu] || [];
    if (dongList.length === 0) {
        const empty = document.createElement("p");
        empty.innerText = "등록된 동 정보가 없습니다.";
        container.appendChild(empty);
        return;
    }

    const fragment = document.createDocumentFragment();
    dongList.forEach(dong => {
        const btn = document.createElement("button");
        btn.innerText = dong;
        btn.onclick = () => {
            state.dong = dong;
            result.innerText = `선택된 지역: ${state.sido} ${state.gu} ${state.dong}`;
            console.log("최종 선택:", state);
        };
        fragment.appendChild(btn);
    });

    container.appendChild(fragment);
}

const viewMap = { 1: renderSido, 2: renderGu, 3: renderDong };

function render() {
    container.innerHTML = "";
    viewMap[state.step]?.();
}

render();
