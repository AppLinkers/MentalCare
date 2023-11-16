const forms = document.querySelectorAll(".sign_up_form");


function selectChange() {

    const select = document.getElementById("role").options[document.getElementById("role").selectedIndex].value;

    forms.forEach(form => {
        form.classList.remove("active");
    });

    if (select === "player_personal" || select === "player_team") {
        forms[0].classList.add("active");
    }

    if(select === "player_personal") forms[0].querySelector(".team_code").style.display = "none";

    if (select === "director") {
        forms[1].classList.add("active");
    }

    if(select == "consultant") {
        forms[2].classList.add("active");
    }

}