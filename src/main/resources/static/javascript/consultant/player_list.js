const headerTabs = document.querySelectorAll(".header-tab");
const typeFilterContainer = document.querySelector(".type-filter__container");

headerTabs.forEach((tab, index) => {
    tab.addEventListener("click", () => {
        headerTabs.forEach((el) => {
            el.classList.remove("active");
        });
        headerTabs[index].classList.add("active");
        if (index === 1) {
            typeFilterContainer.classList.add("active");
        } else {
            typeFilterContainer.classList.remove("active");
        }
    });
});

const typeFilters = typeFilterContainer.querySelectorAll(".type-filter");

typeFilters.forEach((filter, index) => {
    filter.addEventListener("click", () => {
        typeFilters.forEach((el) => {
            el.classList.remove("active");
        });
        typeFilters[index].classList.add("active");
    });
});
