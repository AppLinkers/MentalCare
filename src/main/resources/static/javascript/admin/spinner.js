const optionMenu = document.querySelector(".select-menu"),
    selectBtn = optionMenu.querySelector(".select-btn"),
    options = optionMenu.querySelectorAll(".option");

const users = document.querySelectorAll(".user__con");

const teamName = document.getElementById("teamName");

selectBtn.addEventListener("click", () => optionMenu.classList.toggle("active"));

options.forEach(option => {
    option.addEventListener("click", () => {
        let selectedOption = option.querySelector(".option-text").innerText;
        teamName.value = selectedOption;
        optionMenu.classList.remove("active");

        if (teamName.value === '전체 보기') {
            users.forEach(user => {
                user.style.display = "";
            });
        } else {
            users.forEach(user => {
                if (user.querySelector(".user__team").innerText !== teamName.value) {
                    user.style.display = "none";
                } else {
                    user.style.display = "";
                }
            })
        }
    })
})