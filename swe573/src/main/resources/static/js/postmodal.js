const postBtn = document.querySelector(".post-btn");
const modal = document.querySelector(".modal");
const postModalX = document.querySelector(".modal-header i");
const modalPostBtn = document.querySelector('.modal-header button');
const modalInput = document.querySelector('.modal-input');
const modalFooterPlus = document.querySelector('.modal-footer span');
const modalWrapper = document.querySelector(".modal-wrapper");


postBtn.addEventListener('click', () => {
    modal.style.display = 'block';
    modalWrapper.classList.add('modal-wrapper-display');
});

const changeOpacity = x => {
    modalPostBtn.style.opacity = x;
    modalFooterPlus.style.opacity = x;
}

postModalX.addEventListener('click', () => {
    modal.style.display = 'none'
    modalWrapper.classList.remove("modal-wrapper-display");

    if(modalInput.value !== "") {
        modalInput.value = "";
        changeOpacity(.5)
    }
});


modalInput.addEventListener('keypress', e => {
    if (e.target.value !== "") {
        changeOpacity(1);
    }
});

modalInput.addEventListener('blur', e => {
    if (e.target.value === "") {
        changeOpacity(.5)
    }
});