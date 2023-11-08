const formatDateString = (dateStr) => {
    const inputDate = new Date(dateStr);
    const today = new Date();
    const optionsWithoutYear = { month: "long", day: "numeric" };
    const optionsWithYear = { year: "numeric", month: "long", day: "numeric" };
    if (inputDate.getFullYear() === today.getFullYear()) {
        return inputDate.toLocaleDateString("ko-KR", optionsWithoutYear);
    } else {
        return inputDate.toLocaleDateString("ko-KR", optionsWithYear);
    }
};

const feeds = document.querySelectorAll(".feed-item");

feeds.forEach((feed) => {
    const date = feed.querySelector(".feed-date");
    date.innerText = formatDateString(date.innerText);
});
