const articles = document.querySelectorAll(".member-list");

for (var i = 0; i < articles.length; i++) {
    const article = articles[i];
    const input = article.querySelector("input");
    const label = article.querySelector("label");

    input.id = "switch" + i;
    label.htmlFor = "switch" + i;
}