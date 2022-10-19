// const detailBoxs = document.querySelectorAll('.detail__box');
// const detailInside = document.querySelector('.detail__inside');

// // 수정 필요
// detailBoxs.forEach(detailBox => {
//     detailBox.addEventListener("click", () => {
//         detailBox.classList.toggle('active');
//         detailInside.classList.toggle('active');
//     })
// })

const inputId = ["worst", "high", "soso", "low", "none"];

const groups = document.querySelectorAll(".form-group");

for (var i = 0; i < groups.length; i++) {
    const group = groups[i];
    const checks = group.querySelectorAll(".check");
    const result = group.querySelector(".check__result input");
    for (var j = 0; j < 5; j++) {
        const check = checks[j];
        const input = check.querySelector("input");
        const label = check.querySelector("label");

        input.name = "check" + i;
        input.id = inputId[j] + i;
        label.htmlFor = inputId[j] + i;

        check.addEventListener('click', () => {
            result.value = input.value;
        })
    }
}