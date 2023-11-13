const tabs = document.querySelectorAll(".label p");
const calendars = document.querySelectorAll(".calendar");

tabs.forEach((tab, idx) => {
    tab.addEventListener("click", () => {
        tabs.forEach((label) => {
            label.classList.remove("active");
        });
        calendars.forEach((calendar) => {
            calendar.classList.remove("active");
        });
        tabs[idx].classList.add("active");
        calendars[idx].classList.add("active");
    });
});
