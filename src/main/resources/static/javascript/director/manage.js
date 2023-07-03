const authContent = document.querySelector(".auth-content");
const switchBox = authContent.querySelector(".switch-box");

authContent.addEventListener("click", () => {

    switchBox.classList.toggle("active");

});