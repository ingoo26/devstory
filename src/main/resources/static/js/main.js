const content = document.querySelector('.content');
const content_count = document.querySelectorAll('.content').length;
const content_list = document.querySelectorAll('.content');

for (i=0; i<content_count; i++) {
    content_list[i].innerText = content_list[i].innerText.replace(/(<([^>]+)>)/gi, '');
}

