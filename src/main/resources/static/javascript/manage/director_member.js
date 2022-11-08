const articles = document.querySelectorAll(".member-list");

/*
for (var i = 0; i < articles.length; i++) {
    const article = articles[i];
    const input = article.querySelector("input");
    const label = article.querySelector("label");

    input.id = "switch" + i;
    label.htmlFor = "switch" + i;
}*/
articles.forEach(article => {
    const permission = article.querySelector("button");
    permission.addEventListener("click", () => {
        permission.classList.toggle("active");
        if (!permission.classList.contains("active")) {
            permission.innerText = "권한 없음";
        }
        else {
            permission.innerText = "허가됨";
        }
    })
})