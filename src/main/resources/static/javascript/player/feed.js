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

function deleteFeed(feedId){
    const request = new XMLHttpRequest();
    request.open("DELETE", "/player/feed/delete/"+feedId, true);

    request.onreadystatechange = function (){
        if(request.readyState === 4){
            if(request.status === 200 || request.status === 201){
                console.log((request.status));
            }else{
                console.log("failed : "+ request.status);
            }
        }
    }
    request.send(null);
}


function deleteComment(commentId){
    const request = new XMLHttpRequest();
    request.open("DELETE", "/player/feed/delete/comment/"+commentId, true);

    request.onreadystatechange = function (){
        if(request.readyState === 4){
            if(request.status === 200 || request.status === 201){
                console.log((request.status));
            }else{
                console.log("failed : "+ request.status);
            }
        }
    }
    request.send(null);
}

document.addEventListener("DOMContentLoaded", function () {
    const feeds = document.querySelectorAll(".feed-item");
    const feedDialog = document.getElementById("feed-dialog");
    let feedId;

    feeds.forEach((feed) => {
        const feedIdEl = feed.querySelector(".feed-id");
        const date = feed.querySelector(".feed-date");
        date.innerText = formatDateString(date.innerText);

        const menu = feed.querySelector(".feed-menu");
        menu.addEventListener("click", () => {
            feedDialog.classList.add("active");
            document.body.style.overflow = "hidden";
            feedId = feedIdEl.id
        });
    });

    feedDialog.addEventListener("click", (event) => {
        const dialogContent = feedDialog.querySelector(".dialog");
        if (!dialogContent.contains(event.target)) {
            feedDialog.classList.remove("active");
            document.body.style.overflow = "";
        }
    });

    const deleteFeedButton = feedDialog.querySelector(".font14");
    deleteFeedButton.addEventListener("click", () => {
        deleteFeed(feedId);
        feedDialog.classList.remove("active");
        document.body.style.overflow = "";
        location.reload(true);
    })

    const comments = document.querySelectorAll(".comments-list .comment-item");
    const commentDialog = document.getElementById("comment-dialog");
    let commentId;
    comments.forEach((comment) => {
        const commentIdEl = comment.querySelector(".comment-id");
        const date = comment.querySelector(".comment-date");
        date.innerText = formatCommentDateString(date.innerText);

        const menu = comment.querySelector(".comment-menu");
        menu.addEventListener("click", () => {
            commentDialog.classList.add("active");
            commentId = commentIdEl.id;
        });
    });

    commentDialog.addEventListener("click", (event) => {
        const dialogContent = commentDialog.querySelector(".dialog");
        if (!dialogContent.contains(event.target)) {
            commentDialog.classList.remove("active");
        }
    });

    const deleteCommentButton = commentDialog.querySelector(".font14");
    deleteCommentButton.addEventListener("click", () => {
        deleteComment(commentId);
        commentDialog.classList.remove("active");
        location.reload(true);
    })
});
