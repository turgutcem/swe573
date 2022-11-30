const user = document.querySelector(".user");
const sideBar = document.querySelector('.sidebar')
const sideBarWrapper = document.querySelector(".sidebar-wrapper");
const xBtn = document.querySelector('.sidebar-header i')

user.addEventListener('click', () => {
    sideBar.classList.add('sidebar-display');
    sideBarWrapper.classList.add('sidebar-wrapper-display')
});

xBtn.addEventListener('click', () => {
    sideBar.classList.remove('sidebar-display');
    sideBarWrapper.classList.remove('sidebar-wrapper-display')
})