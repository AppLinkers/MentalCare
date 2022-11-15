const authBtn = document.querySelector(".control-box button");

authBtn.addEventListener("click", () => {

    if (authBtn.classList.contains("active")) {
        authBtn.innerText = "권한 없음";
    } else {
        authBtn.innerText = "허가됨";
    }
    authBtn.classList.toggle("active");
})

