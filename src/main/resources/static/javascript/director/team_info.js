const authBtns = document.querySelectorAll(".lead__con button");

authBtns.forEach(authBtn => {

    authBtn.addEventListener("click", () => {

        const lead__role = authBtn.parentElement.getElementsByClassName("lead__role")[0];

        if (authBtn.classList.contains("active")) {
            authBtn.innerText = "권한 없음";
            lead__role.value = "PENDING";
        } else {
            authBtn.innerText = "허가됨";
            lead__role.value = "DIRECTOR";
        }
        authBtn.classList.toggle("active");
    })

})