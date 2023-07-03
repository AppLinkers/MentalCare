const authBtn = document.querySelector(".control-box button");
const authInput = document.getElementById("playerAuth");

authBtn.addEventListener("click", () => {

    if (authBtn.classList.contains("active")) {
        authBtn.innerText = "권한 없음";
        authInput.value = "PENDING";
    } else {
        authBtn.innerText = "허가됨";
        authInput.value = "PLAYER";
    }
    authBtn.classList.toggle("active");
})

