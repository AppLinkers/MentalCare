const validateURL = (input) => {
    if (input.value) {
        const urlPattern = /^(http|https):\/\/.+/;
        if (urlPattern.test(input.value)) {
            return true;
        } else {
            const errorMsg = document.querySelector(".url-error");
            input.classList.add("error");
            errorMsg.classList.add("active");
            return false;
        }
    }
    return true;
};
