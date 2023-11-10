const today = new Date();

const formatDateString = (dateString) => {
    const inputDate = new Date(dateString);
    const optionsWithoutYear = { month: "long", day: "numeric" };
    const optionsWithYear = { year: "numeric", month: "long", day: "numeric" };
    if (inputDate.getFullYear() === today.getFullYear()) {
        return inputDate.toLocaleDateString("ko-KR", optionsWithoutYear);
    } else {
        return inputDate.toLocaleDateString("ko-KR", optionsWithYear);
    }
};

const formatCommentDateString = (dateString) => {
    today.setHours(0, 0, 0, 0);
    const date = new Date(dateString);
    const timeDiff = today - date;
    const oneDay = 24 * 60 * 60 * 1000;
    const daysDiff = Math.round(timeDiff / oneDay);
    if (daysDiff === 0) {
        return "오늘";
    } else if (daysDiff > 0 && daysDiff <= 31) {
        return `${daysDiff}일 전`;
    } else {
        return formatDateString(dateString);
    }
};

document.addEventListener("DOMContentLoaded", function () {
    const feeds = document.querySelectorAll(".feed-item");
    const feedDialog = document.getElementById("feed-dialog");

    feeds.forEach((feed) => {
        const date = feed.querySelector(".feed-date");
        date.innerText = formatDateString(date.innerText);

        const menu = feed.querySelector(".feed-menu");
        menu.addEventListener("click", () => {
            feedDialog.classList.add("active");
        });
    });

    feedDialog.addEventListener("click", (event) => {
        const dialogContent = feedDialog.querySelector(".dialog");
        if (!dialogContent.contains(event.target)) {
            feedDialog.classList.remove("active");
        }
    });

    const comments = document.querySelectorAll(".comments-list .comment-item");
    const commentDialog = document.getElementById("comment-dialog");
    comments.forEach((comment) => {
        const date = comment.querySelector(".comment-date");
        date.innerText = formatCommentDateString(date.innerText);

        const menu = comment.querySelector(".comment-menu");
        menu.addEventListener("click", () => {
            commentDialog.classList.add("active");
        });
    });

    commentDialog.addEventListener("click", (event) => {
        const dialogContent = commentDialog.querySelector(".dialog");
        if (!dialogContent.contains(event.target)) {
            commentDialog.classList.remove("active");
        }
    });
});
