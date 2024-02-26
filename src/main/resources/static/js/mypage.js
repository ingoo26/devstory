
const content_count = document.querySelectorAll('.content').length;
const content_list = document.querySelectorAll('.content');
profile=document.getElementById("profileimg");

for (i=0; i<content_count; i++) {
    content_list[i].innerText = content_list[i].innerText.replace(/(<([^>]+)>)/gi, '');
}

profile.addEventListener("change",async function(){
    try {

        const fileInput = document.getElementById('profileimg');
        const file = fileInput.files[0];

        const formData = new FormData();
        formData.append('image',file);


        const response = await fetch('/tui-editor/upload_profile', {
            method: 'POST',
            body: formData,
        });
        savename = await response.text();
        profileimg = savename.split('\\').pop();


        document.getElementById('profileinput').value = profileimg;
        console.log(profileimg);
        alert('이미지 업로드 완료' );





    } catch (error) {
        console.error('업로드 실패 : ', error);
    }


})
// sign_name = document.getElementById("bio");
// signupform = document.getElementById("mypage-up");
//
// signupform.addEventListener("submit", (event) => {
//     if (sign_name.value.length == 0) {
//         event.preventDefault();
//         Swal.fire(
//             '이름을 입력해주세요',
//         );
//         return false;
//     }
// });