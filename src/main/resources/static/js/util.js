;
$.fn.dataTable.ext.errMode = function( settings, tn, msg){
//打印msg，和tn来判断，进了这个方法都是ajax走了error才会到这里来
    console.log(msg);
    alert("表格发生错误");
};
function dataTablesInit(p){

    if(p.emptyTableText == undefined ||
        p.emptyTableText == null ||
        p.emptyTableText == ""){
        p.emptyTableText = "没有数据";
    }
    $.each(p.columns,function (i,t) {
        if(t.type == "seN"){
            t.render=function (data, type, full, meta) {
                return meta.settings._iDisplayStart+meta.row+1;
            };
        }
    });
    $('#'+p.id).DataTable( {
        "order": [],//必须要，去掉表格的默认排序，自定义排序由使用者进行参数配置。
        "serverSide": true,
        "autoWidth": false,
        "columns.searchable":false,
        "language": {
            "emptyTable": p.emptyTableText,
            "lengthMenu": "每页 _MENU_ 条记录",
            "paging" : true,//是否分页
            //"search": "搜索",
            //"searchPlaceholder": "请输入",
            /*"sInfo": "显示 _START_ 到 _END_ 记录，总条数 _TOTAL_",
            "infoEmpty": "没有数据",
            "loadingRecords": "数据正在加载中...",
            "zeroRecords": "过滤不到数据",
            "processing": "正在处理...",
            "infoFiltered":"",*/
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _MAX_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",

            "paginate": {
                "first": "首页",
                "last": "尾页",
                "next": "下一页",
                "previous": "上一页"
            },
        },
        "searching":false,//关闭搜索功能
        "search": {
            "smart": false
        },
        "deferRender": true,//控制表格的延迟渲染，可以提高初始化的速度。
        "columns":p.columns,
        "ajax": {
            "url": p.api,
            "type":"POST",
            "data": function ( d ) {
                var t = {};
                if(typeof  p.queryParaFn == "function"){
                    t = p.queryParaFn();
                }
                return $.extend({}, {
                    "offset":d.start,
                    "pageSize":d.length,
                    "draw":d.draw,
                },t);
            },
            "dataSrc": function ( json ) {
                return json.data;
            }
        }
    });
}