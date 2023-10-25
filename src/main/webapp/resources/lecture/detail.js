function makeReplyDiv(replies){
    $(".lecture-comment-container").empty();
    
    let memberId = $("#memberId").val();
    let myComment;
	let hasPrevComment = false;

    if(memberId === ""){
        memberId = -1;
    }

    $.each(replies, function (index, reply) {
        if(reply.memberId == memberId){
            hasPrevComment = true;
        }

        const starRating = reply.lectureReplyScore;
        const replyCard = $("<div>").addClass("card my-3");
        const cardHeader = $("<div>").addClass("card-header");
        const cardBody = $("<div>").addClass("card-body");
        const cardFooter = $('<div class="d-flex flex-row-reverse">').addClass("card-footer"); 
        const starRatingDiv = $("<div>").addClass("star-rating");
  
        // make star icon
        for (let i = 1; i <= 5; i++) {
          const starIcon = $("<i>").addClass("bi");
          if (i <= starRating) {
            starIcon.addClass("bi-star-fill");
          } else if (i - 0.5 <= starRating && starRating < i) {
            starIcon.addClass("bi-star-half");
          } else {
            starIcon.addClass("bi-star");
          }
          starRatingDiv.append(starIcon);
        }
  
        cardHeader.append(starRatingDiv);
        cardHeader.append($("<span>").text(reply.authorName));
        cardBody.append($("<div>").text(reply.lectureReplyContent));
        cardFooter.append($(`<input type="hidden" value=${reply.lectureReplyId}>`))

        // make button
        if(memberId == reply.memberId){
            const editBtn = $(`<button class="me-1 btn btn-primary btn-edit" data-bs-toggle="modal" data-bs-target="#exampleModal" data-replyId="${reply.lectureReplyId}">`).text("수정");
            const deleteBtn = $(`<button class="btn btn-danger btn-delete data-replyId="${reply.lectureReplyId}">`).text("삭제");
            deleteBtn.click(function(){
                deleteReply(reply.lectureReplyId);
            })
            cardFooter.append(deleteBtn).append(editBtn);
        }
        
        replyCard.append(cardHeader);
        replyCard.append(cardBody);
        replyCard.append(cardFooter);

        //내가 쓴 글이면 나중에 추가하기
        if(memberId == reply.memberId){
            myComment = replyCard;
        }else{
            $(".lecture-comment-container").prepend(replyCard);
        }
    }); // end each()

    // 회원이 없거나 쓴 글이 있는 경우, 입력창을 보여주지 않는다.
    if(hasPrevComment || memberId == -1){
        $('.lecture-comment-prompt').hide();
    }else{
        $('.lecture-comment-prompt').show();
    }

    //내가 쓴 글을 맨 위에 올리기
    if(myComment){
        $(".lecture-comment-container").prepend(myComment);
    }
}

function getAllReplies(){
    const lectureId = $("#lectureId").val();
    $.ajax({
        type : "GET",
        url : `/blooming/lecture/${lectureId}/replies`,
        headers : {
            'Content-Type' : 'application/json'
        },
        success : function(result) {
            console.log("getAllReplies 성공");
            makeReplyDiv(result);
        },
    }); // end ajax
}

function addReply(){
    const memberId = $("#memberId").val();
    const lectureId = $("#lectureId").val();
    const lectureReplyScore = $("#review-score").val();
    const lectureReplyContent = $("#review-content").val();

    if(memberId === ''){
        alert("로그인한 유저만 이용가능합니다.");
        return;
    }

    if(lectureReplyContent === ''){
        alert("내용을 입력해주세요");
        return;
    }
    
    const data = {
        memberId: memberId,
        lectureId: lectureId,
        lectureReplyScore: lectureReplyScore,
        lectureReplyContent: lectureReplyContent
    };
    
    $.ajax({
        type : "POST",
        url : `/blooming/lecture/${lectureId}/replies`,
        headers : {
            'Content-Type' : 'application/json'
        },
        data : JSON.stringify(data),
        success : function(result) {
            getAllReplies();
            // 기존 값 지우기
            $("#review-content").val("");
            $("#review-score").val(5);
            SetRatingStar();
            console.log("addReplies 성공 result : " + result);
        },
    }); // end ajax
}

//TODO
function updateReply(replyId){
    const lectureReplyId = $('#lecture-reply-id').val();
    const memberId = $("#memberId").val();
    const lectureId = $("#lectureId").val();
    const lectureReplyScore = $(".modal .review-rating input").val();
    const lectureReplyContent = $(".modal-body textarea").val();

    if(memberId === ''){
        alert("로그인한 유저만 이용가능합니다.");
        return;
    }

    if(lectureReplyContent === ''){
        alert("내용을 입력해주세요");
        return;
    }
    
    const data = {
        lectureReplyId: replyId,
        memberId: memberId,
        lectureId: lectureId,
        lectureReplyScore: lectureReplyScore,
        lectureReplyContent: lectureReplyContent
    };
    
    $.ajax({
        type : "PUT",
        url : `/blooming/lecture/${lectureId}/replies/${lectureReplyId}`,
        headers : {
            'Content-Type' : 'application/json'
        },
        data : JSON.stringify(data),
        success : function(result) {
            getAllReplies();
            // 기존 값 지우기
            $(".modal-body textarea").val("")
            $(".modal .review-rating input").val(5);
            SetRatingStar();

            $('.modal').modal('hide');
            console.log("updateReplies 성공 result : " + result);
        },
    }); // end ajax
}

function deleteReply(replyId){
    const memberId = $("#memberId").val();
    const lectureId = $("#lectureId").val();

    if(memberId === ''){
        alert("로그인한 유저만 이용가능합니다.");
        return;
    }
    
    $.ajax({
        type : "DELETE",
        url : `/blooming/lecture/${lectureId}/replies/${replyId}`,
        headers : {
            'Content-Type' : 'application/json'
        },
        success : function(result) {
            getAllReplies();
            console.log("deleteReplies 성공 result : " + result);
        },
    }); // end ajax
    
}

$(function(){
    // 화면 갱신
    getAllReplies();

    $('#exampleModal').on('show.bs.modal', function (event) {
        //console.log("modal hidden input 값 replyId로 변경")
        const button = $(event.relatedTarget);
        const replyId = button.data('replyid');
        $('#lecture-reply-id').val(replyId);
    })

    $("#modal-edit").click(function(event){
        const replyId = $('#lecture-reply-id').val();
        updateReply(replyId);
    })
})