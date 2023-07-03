const authBtns = document.querySelectorAll(".user__con button");

authBtns.forEach(authBtn => {

    authBtn.addEventListener("click", () => {

        const user__role = authBtn.parentElement.getElementsByClassName("user__role")[0];

        if (authBtn.classList.contains("active")) {
            if (user__role.value === "PLAYER") {
                user__role.value = "PLAYER_PENDING";
            } else if (user__role.value === "DIRECTOR"){
                user__role.value = "DIRECTOR_PENDING";
            }
            authBtn.innerText = "권한 없음";
        } else {
            if (user__role.value === "PLAYER_PENDING") {
                user__role.value = "PLAYER";
            } else if (user__role.value === "DIRECTOR_PENDING"){
                user__role.value = "DIRECTOR";
            }
            authBtn.innerText = "허가됨";
        }
        authBtn.classList.toggle("active");
    })

})