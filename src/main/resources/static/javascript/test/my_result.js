const articles = document.querySelectorAll(".result__list article");

articles.forEach(article => {
    article.addEventListener('click', ()=> {
        const chart = article.querySelector('.result__chart');
        const icon = article.querySelector('.result__box i');
        chart.classList.toggle('active');
        icon.classList.toggle('active');
    })
})