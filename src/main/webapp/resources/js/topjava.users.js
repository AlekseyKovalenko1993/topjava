// $(document).ready(function () {
var userUrl = "ajax/admin/users/";
$(function () {
    makeEditable({
            ajaxUrl: userUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function checkBox(checkbox) {
    var enabled = checkbox.checked;
    var data = {
        "id": checkbox.getAttribute("id"),
        "enabled" : enabled
    };
    if(enabled){
        checkbox.parentElement.classList.add("online");
    }else{
        checkbox.parentElement.classList.add("offline");
    }
    $.ajax({
        type: "POST",
        url: userUrl + "enabled",
        data: data
    }).done(function () {
        successNoty("Update enabled");
    });
    updateTable();
}