const tabs = document.querySelectorAll(".label p");
const tabScreens = document.querySelectorAll(".target-grid");

tabs.forEach((tab, idx) => {

    tab.addEventListener("click", () => {

        tabs.forEach(label => {
            label.classList.remove("active");
        });

        tabScreens.forEach(screen => {
            screen.classList.remove("active");
        });

        tabs[idx].classList.add("active");
        tabScreens[idx].classList.add("active");

    });

});