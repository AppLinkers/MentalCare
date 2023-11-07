const tabs = document.querySelectorAll(".label p");
const teamCalendar = document.getElementById("calendar-team");
const personalCalendar = document.getElementById("calendar-personal");

tabs.forEach((tab, idx) => {
    tab.addEventListener("click", () => {
        tabs.forEach((label) => {
            label.classList.remove("active");
        });
        tabs[idx].classList.add("active");
        if (idx === 0) {
            teamCalendar.style.display = "block";
            personalCalendar.style.display = "none";
        } else {
            teamCalendar.style.display = "none";
            personalCalendar.style.display = "block";
        }
    });
});
