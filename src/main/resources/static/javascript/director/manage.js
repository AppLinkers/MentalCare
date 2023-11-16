const authContent = document.querySelector(".auth-content");
const switchBox = authContent.querySelector(".switch-box");
const authInput = document.getElementById("playerAuth");

if (authInput.value === 'PLAYER' || authInput.value === 'DIRECTOR' || authInput.value === 'CONSULTANT') {
    switchBox.classList.add("active");
}

authContent.addEventListener("click", () => {

    switchBox.classList.toggle("active");
    switch(authInput.value) {
        case 'PLAYER':
            authInput.value = 'PLAYER_PENDING';
            break;

        case 'PLAYER_PENDING':
            authInput.value = 'PLAYER';
            break;

        case 'DIRECTOR':
            authInput.value = 'DIRECTOR_PENDING';
            break;

        case 'DIRECTOR_PENDING':
            authInput.value = 'DIRECTOR';
            break;

        case 'CONSULTANT':
            authInput.value = 'CONSULTANT';
            break;

        case 'CONSULTANT_PENDING':
            authInput.value = 'CONSULTANT_PENDING';
            break;
    }


});