

const signup = document.getElementById("sign-up");
signin = document.getElementById("sign-in");
loginin = document.getElementById("login-in");
loginup = document.getElementById("login-up");
findpw = document.getElementById("find-pw");
loginfind = document.getElementById("login-find");
signin2 = document.getElementById("sign-in2");
signupform = document.getElementById("login-up");
profile=document.getElementById("profileimg");

sign_name = document.getElementById("sign_name");
sign_email = document.getElementById("sign_email");
sign_pw1 = document.getElementById("sign_pw1");
sign_pw2 = document.getElementById("sign_pw2");
sign_blog = document.getElementById("sign_blog");

profile_img = document.getElementById('profileinput');

signup.addEventListener("click", () => {
    loginin.classList.remove("block");
    loginup.classList.remove("none");
    loginfind.classList.remove("block");
    //
    loginin.classList.add("none");
    loginup.classList.add("block");
    loginfind.classList.add("none");
})

signin.addEventListener("click", () => {
    loginin.classList.remove("none");
    loginfind.classList.remove("block");
    loginup.classList.remove("block");

    loginin.classList.add("block");
    loginup.classList.add("none");
    loginfind.classList.add("none");
})

signin2.addEventListener("click", () => {
    loginin.classList.remove("none");
    loginfind.classList.remove("block");
    loginup.classList.remove("block");

    loginin.classList.add("block");
    loginup.classList.add("none");
    loginfind.classList.add("none");
})

findpw.addEventListener("click", () => {
    loginin.classList.remove("block");
    loginfind.classList.remove("none");
    loginup.classList.remove("block")

    loginin.classList.add("none");
    loginfind.classList.add("block");
    loginup.classList.add("none");
})
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


signupform.addEventListener("submit", (event) => {

    if (sign_name.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '이름을 입력해주세요',
        );
        return false;
    }
    if (sign_email.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '이메일을 입력해주세요',
        );
        return false;
    }
    if (sign_pw1.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '비밀번호를 입력해주세요',
        );
        return false;
    }
    if (sign_pw2.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '비밀번호 확인을 입력해주세요',
        );
        return false;
    }
    if (sign_blog.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '블로그 이름을 입력해주세요',
        );
        return false;
    }


    if (sign_pw1.value != sign_pw2.value) {
        event.preventDefault();
        Swal.fire(
            '비밀번호가 일치하지 않습니다.',
        );
        return false;
    }
    // if(!sign_email.value.includes("@")||(!sign_email.value.includes(".com"))) {
    //     event.preventDefault();
    //     alert("이메일 형식이 틀립니다");
    //     return false;
    // }

    if(profileimg.value.length==0){
        profile_img.value="logo.png"
    }



})