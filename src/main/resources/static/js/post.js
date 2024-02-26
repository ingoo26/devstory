const comment = document.getElementById("comment_form");
const commentBtn = document.getElementById("comment_submit")
const commentText = document.getElementById("comment_text")
const likeForm = document.querySelector(".like_form")
const followbtn = document.querySelector(".follow")

comment.addEventListener("submit", (event) => {
    if(commentText.value.length == 0) {
        event.preventDefault();
        Swal.fire(
            '댓글 내용을 입력해주세요'
        )
    }
    return false;
})

let addPostLike = {
    modifyPostLike: function (postId) {
        // 서버에 넘겨줄 데이터 : 게시글 아이디만 전달
        let param = {
            postNum: postId
        };
        $.ajax({
            type: "PUT",
            url: "/post/like",
            data : param,
            success: function (flag) {
                // 데이터 요청에 성공할 경우
                // id가 post-ajax-form인 div만 새로고침 해준다.
                $('.like_box').load(location.href+' .like_box');
            },
        })
    },
}

let addFollow = {
    modifyFollow: function (userId) {
        let param = {
            userId: userId
        };
        $.ajax({
            type: "PUT",
            url: "/follow",
            data: param,
            success: function(flag) {
                $('.follow').load(location.href+' .follow');
            },
        })
    },
}



// thumbs up animation

var $iconWrapper2 = $(".icon-wrapper-2");
$iconWrapper2.on('click', function() {
    var _this = $iconWrapper2;
    if (_this.hasClass('anim')) _this.removeClass('anim');
    else {
        _this.addClass('anim');
        _this.css('pointer-events', 'none');
        setTimeout(function() {
            _this.css('pointer-events', '');
        }, 1200);
    }
})

followbtn.addEventListener("click", () => {
    if (followbtn.innerText == "팔로우") {
        followbtn.innerText = "언팔로우"
    } else {
        followbtn.innerText = "팔로우"
    }
})
