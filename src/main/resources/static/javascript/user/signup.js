const optionMenu = document.querySelector(".select-menu"),
    selectBtn = optionMenu.querySelector(".select-btn"),
    options = optionMenu.querySelectorAll(".option"),
    sBtn_text = optionMenu.querySelector(".sBtn-text");

const role = document.getElementById("role");

selectBtn.addEventListener("click", () => optionMenu.classList.toggle("active"));

options.forEach(option => {
    option.addEventListener("click", () => {
        let selectedOption = option.querySelector(".option-text").innerText;
        role.value= selectedOption;
        optionMenu.classList.remove("active");
    })
})

const pro = document.getElementById("pro");
const dir = document.getElementById("dir");
const form_pro = document.getElementById("form-pro");
const form_dir = document.getElementById("form-dir");

pro.addEventListener("click", () => {
    form_dir.classList.remove("active");
    form_pro.classList.add("active");
})

dir.addEventListener("click", () => {
    form_pro.classList.remove("active");
    form_dir.classList.add("active");
})