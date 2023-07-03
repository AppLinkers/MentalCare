const defaultBtn = document.querySelector("#selectImg");
const profile_img = document.querySelector(".header-img img");

function defaultBtnActive() {
    defaultBtn.click();
}

defaultBtn.addEventListener("change", function () {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function () {
            const result = reader.result;
            profile_img.src = result;
        }
        reader.readAsDataURL(file);
    }
})