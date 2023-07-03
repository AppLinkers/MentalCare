const checkPassword = function () {
    if (document.getElementById('pw').value ===
        document.getElementById('pwCheck').value){
        document.querySelector('.pwCheck-message').classList.remove('active');
        document.getElementById('submit').disabled = false;
    }   else {
        document.querySelector('.pwCheck-message').classList.add('active');
        document.getElementById('submit').disabled = true;
    }
}