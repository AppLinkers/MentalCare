const hiddenButton = document.getElementById("selectImg");
const pickImage = document.querySelector(".pick-img");
const profileImage = document.querySelector(".profile-img__container img");

function changeImage() {
    hiddenButton.click();
}

hiddenButton.addEventListener("change", function () {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = () => {
            const result = reader.result;
            profileImage.src = result;
        }
        reader.readAsDataURL(file);
    }
});