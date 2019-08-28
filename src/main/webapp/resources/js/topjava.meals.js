let ajaxUrlMeal = "ajax/profile/meals/";
function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    $( "#startDate" ).datetimepicker({ dateFormat: 'yy-mm-dd' });
    makeEditable({
        ajaxUrl: ajaxUrlMeal,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": ajaxUrlMeal,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "createdRow" : function (row, data, index) {
                if (data.excess) {
                    $(row).addClass('excess');
                }else{
                    $(row).addClass('notexcess');
                }
            },
            "columns": [
                {
                    "data": "dateTime",
                    "render" : function (data, type, row) {
                        if (type === "display") {
                            let d =  new Date(data);
                            var date_format_str = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString())+":00";;
                            return date_format_str;
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
});