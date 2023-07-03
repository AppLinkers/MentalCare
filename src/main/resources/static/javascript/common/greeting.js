const greeting = document.getElementById("greeting");
const adjective = ["활기찬", "즐거운", "기분 좋은", "설레는", "신나는", "열정적인"];
const timeText = ["새벽", "아침", "점심", "저녁"];
const endText = ["보내세요!", "보내고 계신가요?"];

const currentHour = new Date().getHours();

greeting.innerHTML = greeting.innerHTML.concat("님, ").concat(makeGreetingText());

function makeGreetingText() {
    const greetingList = [
        adjective[Math.floor(Math.random() * adjective.length)],
        selectTimeText(),
        endText[Math.floor(Math.random() * endText.length)]
    ];
    return greetingList.join(" ");
}

function selectTimeText() {
    if (currentHour <= 15) return timeText[2];
    else if (currentHour <= 11) return timeText[1];
    else if (currentHour <= 6) return timeText[0];
    else return timeText[3];
}