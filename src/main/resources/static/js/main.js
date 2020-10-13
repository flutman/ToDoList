$(function(){

    const appendAction = function(data){
        var actionCode = "<a href='#' class='action-link' data-id='" + data.id
        + "'>" + data.name + "</a>";
        var deleteLink = "<a href='#' class='action-delete' data-id='"+data.id+"'> Удалить</a>";
        var updateLink = "<a href='#' class='action-update' data-id='"+data.id+"'> Изменить</a>";
        $("#action-list").append("<div>" + actionCode + " " + deleteLink + " " + updateLink +"</div>");
    };

    //Delete action
    $(document).on("click",".action-delete",function(){
        var link = $(this);
        var actionId = link.data("id");
        $.ajax({
            method: "DELETE",
            url: "/actions/" + actionId,
            success: function(response){
                link.parent().remove();
            }
        });
    });

    //Update action
    $(document).on("click",".action-update",function(){

        $("#edit-form").removeAttr("style").show();
        var link = $(this);
        var actionId = link.data("id");
        $.get('/actions/'+ actionId, function(response){
            $("#edit-form form input[name=name]").attr("data-id",response.id);
            $("#edit-form form input[name=name]").val(response.name);
            $("#edit-form form input[name=date]").val(response.date);
        });
    });

    $("#edit-form form").submit(function(e){
        e.preventDefault();
    });

    $("#update-action").click(function(){
        var action = new Object();
        action.id = $("#edit-form form input[name=name]").data("id");
        action.name = $("#edit-form form input[name=name]").val();
        action.date = $("#edit-form form input[name=date]").val();
        var jsonArr = JSON.stringify(action);

        var id = $("#edit-form form input[name=name]").data("id");
        $.ajax({
            method: "PUT",
            contentType: "application/json",
            data: jsonArr,
            url: "/actions/update",
            success: function(response){
                $("#edit-form").hide();
            }
        });
        return false;
    });

    //Get Action
    $(document).on("click",".action-link",function(){
        var link = $(this);
        var actionId = link.data("id");
        $.ajax({
            method: "GET",
            url: "/actions/" + actionId,
            success: function(response){
                    if (!link.siblings().is("span")) {
                        var code = " <span>Срок: " + response.date + "</span>";
                        link.parent().append(code);
                    } else {
                        link.siblings().detach("span");
                    }
            },
            error: function (response){
                if(response.status == 404){
                    alert("Страница не найдена");
                }
            }
        });
    return false;
    });

    //Adding action
    $("#save-action").click(function(){
        if ($("#action-form form input[name=name]").val() == "") {
            console.log("Not filled");
            return false;
        }
        var action = new Object();
        action.id = $("#action-form form input[name=name]").data("id");
        action.name = $("#action-form form input[name=name]").val();
        action.date = $("#action-form form input[name=date]").val();
        data = JSON.stringify(action);

        $.ajax({
            method: 'POST',
            contentType: "application/json",
            url: '/actions/',
            data: data,
            success: function(response){
                appendAction(response);
                var actionCount = $("#action-list>div").length;
                $("h1>span").text(actionCount);

                $("#action-form form input[name=name]").val("");
                $("#action-form form input[name=date]").val("");

            }
        });
        return false;
    });

});