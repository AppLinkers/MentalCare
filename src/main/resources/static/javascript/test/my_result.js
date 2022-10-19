async function fetchAsText(url){
    return await (await fetch(url)).text();
}

async function importNav(targetDiv) {
    document.querySelector('#' + targetDiv).innerHTML = await fetchAsText("../../nav");
}

importNav('nav');

// my__reult.js
const articles = document.querySelectorAll(".result__list article");

articles.forEach(article => {

    const box = article.querySelector(".result__box");
    const chart_box = article.querySelector(".result__chart");
    const avg_box = box.querySelector(".avg");

    var avg = parseFloat(avg_box.innerText);
    colorByAvg(avg,box,chart_box);

    article.addEventListener('click', ()=> {
        const chart = article.querySelector('.result__chart');
        const icon = article.querySelector('.result__box i');
        chart.classList.toggle('active');
        icon.classList.toggle('active');
    })
})

function colorByAvg(avg,box,chart_box) {
    if(avg >= 4){
        box.style.background = '#FC5230';
        chart_box.style.borderColor = '#FC5230';
    } else if(avg >= 1) {
        box.style.background = '#EFB93A';
        chart_box.style.borderColor = '#EFB93A';
    } else {
        box.style.background = '#1DBA84';
        chart_box.style.borderColor = '#1DBA84';
    }
}