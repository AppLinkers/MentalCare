const navigation = document.getElementById('navigation');

fetch('/nav')
    .then(res => res.text())
    .then(data => navigation.innerHTML = data);