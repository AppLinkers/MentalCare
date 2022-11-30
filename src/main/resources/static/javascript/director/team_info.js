const authBtns = document.querySelectorAll(".lead__con button");
const roleInput = document.getElementById('roleInput');

authBtns.forEach(authBtn => {

    authBtn.addEventListener("click", () => {

        if (authBtn.classList.contains("active")) {
            authBtn.innerText = "권한 없음";
            role.value = "PENDING";
        } else {
            authBtn.innerText = "허가됨";
            role.value = "DIRECTOR";
        }
        authBtn.classList.toggle("active");
    })

})