const tabs = document.querySelectorAll(".label p");

tabs.forEach((tab, idx) => {
    tab.addEventListener("click", () => {
        tabs.forEach((label) => {
            label.classList.remove("active");
        });
        tabs[idx].classList.add("active");
    });
});
