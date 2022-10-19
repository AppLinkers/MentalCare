const articles = document.querySelectorAll(".result__list article");


async function fetchAsText(url){
    return await (await fetch(url)).text();
}

async function importNav(targetDiv) {
    document.querySelector('#' + targetDiv).innerHTML = await fetchAsText("../../nav");
}

importNav('nav');

articles.forEach(article => {
    article.addEventListener('click', ()=> {
        const chart = article.querySelector('.result__chart');
        const icon = article.querySelector('.result__box i');
        chart.classList.toggle('active');
        icon.classList.toggle('active');
    })
})