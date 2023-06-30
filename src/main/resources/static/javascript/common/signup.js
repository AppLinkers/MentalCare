const form = document.querySelector(".form__container");
const team_code = document.querySelector(".team_code");
const position = document.querySelector(".position");

function selectChange() {
    team_code.style.display = "block";
    position.style.display = "block";
    var select = document.getElementById("role").options[document.getElementById("role").selectedIndex].value;
    if (select === "player_personal") {
        team_code.style.display = "none";
    }
    if (select === "director" || select === "consultant") {
        position.style.display = "none";
    }
    form.style.display = "block";
}