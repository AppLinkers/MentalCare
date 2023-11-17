document.addEventListener("DOMContentLoaded", function () {
    const consultants = document.querySelectorAll(".consultant-item");
    consultants.forEach((consultant) => {
        const requestBtnEl = consultant.querySelector(".btn-enabled");
        requestBtnEl.addEventListener("click", () => {
            requestBtnEl.classList.add("btn-disabled");
            requestBtnEl.innerText = "요청됨";
            requestBtnEl.disabled = true;
        });
    });
});
