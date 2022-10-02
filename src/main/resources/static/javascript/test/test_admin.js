const types = document.querySelectorAll('.type__radio');

types.forEach (type => {
    type.addEventListener("click", () => {
        document.getElementById('detail__title').innerText
            =   type.innerHTML + " 경기 관리";
    })
})
