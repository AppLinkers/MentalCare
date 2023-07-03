const passwordInput = document.getElementById('password');
const rePasswordInput = document.getElementById('re-password');
const error = document.querySelector(".error");
const button = document.querySelector(".btn-basic");

rePasswordInput.addEventListener('input', () => {

    if (rePasswordInput.value !== passwordInput.value) {
        error.classList.add('active');
        button.disabled = true;
    }

    else {
        error.classList.remove('active');
        button.disabled = false;
    }

});