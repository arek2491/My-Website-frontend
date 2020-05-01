$(document).ready(function() {

  var apiRoot = 'http://localhost:8080/v1/';
  var datatableRowTemplate = $('[data-datatable-row-template]').children()[0];
  var commentsContainer = $('[data-comments-container]');

  // init
  getAllComments();

  function createElement(data) {
    var element = $(datatableRowTemplate).clone();

    element.attr('data-comment-id', data.id);
    element.find('[data-nick-section] [data-nick-paragraph]').text(data.nick);
    element.find('[data-nick-section] [data-nick-input]').val(data.nick);

    element.find('[data-comment-section] [data-comment-paragraph]').text(data.comment);
    element.find('[data-comment-section] [data-comment-input]').val(data.comment);

    return element;
  }

  function handleDatatableRender(data) {
    commentsContainer.empty();
    data.forEach(function(comment) {
      createElement(comment).appendTo(commentsContainer);
    });
  }

  function getAllComments() {
    var requestUrl = apiRoot + 'comments';

    $.ajax({
      url: requestUrl,
      method: 'GET',
      success: handleDatatableRender
    });
  }

  function handleCommentUpdateRequest() {
    var parentEl = $(this).parent().parent();
    var commentId = parentEl.attr('data-comment-id');
    var nick = parentEl.find('[data-nick-input]').val();
    var comment = parentEl.find('[data-comment-input]').val();
    var requestUrl = apiRoot + 'comments';

    $.ajax({
      url: requestUrl,
      method: "PUT",
      processData: false,
      contentType: "application/json; charset=utf-8",
      dataType: 'json',
      data: JSON.stringify({
        id: commentId,
        nick: nick,
        comment: comment
      }),
      success: function(data) {
        parentEl.attr('data-comment-id', data.id).toggleClass('datatable__row--editing');
        parentEl.find('[data-nick-paragraph]').text(nick);
        parentEl.find('[data-comment-paragraph]').text(comment);
      }
    });
  }

  function handleCommentDeleteRequest() {
    var parentEl = $(this).parent().parent();
    var commentId = parentEl.attr('data-comment-id');
    var requestUrl = apiRoot + 'comments';

    $.ajax({
      url: requestUrl + '/' + commentId,
      method: 'DELETE',
      success: function() {
        parentEl.slideUp(400, function() { parentEl.remove(); });
      }
    })
  }

  function handleCommentSubmitRequest(event) {
    event.preventDefault();

    var nick = $(this).find('[name="nick"]').val();
    var comment = $(this).find('[name="comment"]').val();

    var requestUrl = apiRoot + 'comments';

    $.ajax({
      url: requestUrl,
      method: 'POST',
      processData: false,
      contentType: "application/json; charset=utf-8",
      dataType: 'json',
      data: JSON.stringify({
        nick: nick,
        comment: comment
      }),
      complete: function(data) {
        if(data.status === 200) {
          getAllComments();
        }
      }
    });
  }

  function toggleEditingState() {
    var parentEl = $(this).parent().parent();
    parentEl.toggleClass('datatable__row--editing');

    var nick = parentEl.find('[data-nick-paragraph]').text();
    var comment = parentEl.find('[data-comment-paragraph]').text();

    parentEl.find('[data-nick-input]').val(nick);
    parentEl.find('[data-comment-input]').val(comment);
  }

  $('[data-comment-add-form]').on('submit', handleCommentSubmitRequest);

  commentsContainer.on('click','[data-comment-edit-button]', toggleEditingState);
  commentsContainer.on('click','[data-comment-edit-abort-button]', toggleEditingState);
  commentsContainer.on('click','[data-comment-submit-update-button]', handleCommentUpdateRequest);
  commentsContainer.on('click','[data-comment-delete-button]', handleCommentDeleteRequest);
});