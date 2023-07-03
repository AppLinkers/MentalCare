const checkSets = document.querySelectorAll(".check-set");

checkSets.forEach(set => {

    const items = set.querySelectorAll(".select-item");

    items.forEach(item => {

        item.addEventListener("click", () => {

            items.forEach(each => {
                each.classList.remove("active");
            });

            item.classList.add("active");

        });

    });

});