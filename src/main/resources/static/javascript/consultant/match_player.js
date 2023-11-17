document.addEventListener("DOMContentLoaded", function () {
    const players = document.querySelectorAll(".player-item");
    players.forEach((player) => {
        const acceptBtnEl = player.querySelector(".btn-accept");
        const rejectBtnEl = player.querySelector(".btn-reject");
        acceptBtnEl.addEventListener("click", () => {
            acceptBtnEl.classList.add("accepted");
            acceptBtnEl.innerText = "수락됨";
            acceptBtnEl.disabled = true;
            rejectBtnEl.style.display = "none";
        });
    });
});
