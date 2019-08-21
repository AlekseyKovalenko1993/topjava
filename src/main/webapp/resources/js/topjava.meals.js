var mealUrl = "ajax/profile/meals/";
var formFilter = $("#filter");
$(document).ready(function () {
    makeEditable({
        ajaxUrl: mealUrl,
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

    $("#filterBut").click(function () {
        filter();
    });
});

function filter() {
    $.get(mealUrl + "filter",formFilter.serialize(),function (data) {
        dataTable.clear().rows.add(data).draw();
    });
}

function deleteMealById(id){
    if (confirm('Are you sure?')) {
        deleteMealRow(id);
    }
}

function deleteMealRow(id) {
    $.ajax({
        url:  mealUrl + id,
        type: "DELETE"
    }).done(function () {
        checkUpdateOrFilter();
        successNoty("Deleted");
    });
}


function saveMeal() {
    $.ajax({
        type: "POST",
        url:  mealUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        checkUpdateOrFilter();
        successNoty("Saved");
    });
}

function checkUpdateOrFilter() {
    var empty = true;
    $("#filter").find(":input").each(function (){
        if($(this).val()!=""){
            empty =false;
            return false;
        }
    });
    if(empty){
        updateTable();
    }else {
        filter();
    }
}
