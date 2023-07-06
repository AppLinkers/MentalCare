const navigation = document.getElementById('navigation');

fetch('/page/navigation.html')
    .then(res => res.text())
    .then(data => navigation.innerHTML = data);