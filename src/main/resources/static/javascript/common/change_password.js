const checkPassword = function () {
    if (document.getElementById('pw').value ===
        document.getElementById('pwCheck').value){
        document.querySelector('.pwCheck-message').classList.remove('active');
    }   else {
        document.querySelector('.pwCheck-message').classList.add('active');
    }
}