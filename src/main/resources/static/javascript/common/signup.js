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

const password = document.getElementById('password');
const re_password = document.getElementById('re-password');
const dir_password = document.getElementById('password-dir');
const dir_re_password = document.getElementById('re-password-dir');

function passwordCheck(){
    if(password.value == re_password.value){
        re_password.setCustomValidity('');
    }else{
        re_password.setCustomValidity("비밀번호를 확인해주세요.");
    }
}

function passwordDirCheck(){
    if(dir_password.value == dir_re_password.value){
        dir_re_password.setCustomValidity('');
    }else{
        dir_re_password.setCustomValidity("비밀번호를 확인해주세요.");
    }
}
