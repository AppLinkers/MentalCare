const menuBtn = document.getElementById("menu-btn");
const menu = document.getElementById("menu");
const closeBtn = document.getElementById("close");

menuBtn.addEventListener('click',()=>{menu.classList.add("active")});
closeBtn.addEventListener('click',()=>{menu.classList.remove("active")});


const admin = document.getElementById("admin");
const adminList = document.getElementById("adminList");
// const adminIc = document.getElementById('admin-ic');

admin.addEventListener('click', ()=>{

    adminList.classList.toggle("active");
    admin.classList.toggle("active");
})

