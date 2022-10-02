const detailBoxs = document.querySelectorAll('.detail__box');
const detailInside = document.querySelector('.detail__inside');

// 수정 필요
detailBoxs.forEach(detailBox => {
    detailBox.addEventListener("click", ()=> {
        detailBox.classList.toggle('active');
        detailInside.classList.toggle('active');
    })
})