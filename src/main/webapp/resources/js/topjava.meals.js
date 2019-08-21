
$(document).ready(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        dataTable: $("#datatable").dataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ]
        })
        }
    );
});
