const typeItem = document.querySelectorAll(".type-item");

typeItem.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("active");
    });
});

const startButton = document.querySelector(".start-btn");
const dialog = document.getElementById("dialog");
const unselectedText = dialog.querySelector(".unselected");
const dialogNegativeButton = dialog.querySelector(".btn-white");

startButton.addEventListener("click", () => {
    var unselected = checkUnselectedItem();
    if (unselected.length === typeItem.length) return;
    unselectedText.innerHTML = unselected.join(", ");
    dialog.classList.add("active");
});

dialogNegativeButton.addEventListener("click", () => {
    dialog.classList.remove("active");
});

function checkUnselectedItem() {
    const unselectedList = [];
    typeItem.forEach(item => {
        if (!item.classList.contains("active")) {
            unselectedList.push(item.querySelector("p").innerHTML);
        }
    });
    return unselectedList;
}
