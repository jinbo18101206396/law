layui.use(['table', 'HttpRequest', 'func', 'form','laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var HttpRequest = layui.HttpRequest;
    var func = layui.func;

    /**
     * 初始化参数
     */
    var Record = {
        tableId: "recordTable"
    };

    /**
     * 初始化表格的列
     */
    Record.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'basicId', hide: true, title: '主键'},
            {field: 'courtNumber',align: "center", sort: true, title: '案号'},
            {field: 'filingTime',align: "center", sort: true, title: '立案时间'},
            {field: 'courtTime',align: "center", sort: true, title: '开庭时间'},
            {field: 'judge',align: "center",sort: true, title: '审判员'},
            {field: 'courtClerk',align: "center", sort: true, title: '书记员'},
            {field: 'courtCause',align: "center", sort: true, title: '案由'},
            {field: 'status',align: "center", sort: true, title: '案件状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth: 250}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Record.search = function () {
        var queryData = {};
        queryData['judge'] = $("#judge").val();
        queryData['courtNumber'] = $("#court_number").val();
        queryData['courtCause'] = $("#court_cause").val();

        table.reload(Record.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 编辑笔录对话框
     *
     * @param data 点击按钮时候的行数据
     */
    Record.openEditDlg = function (data) {
        func.open({
            title: '修改应用',
            content: Feng.ctxPath + '/view/record/editView?recordId=' + data.basicId,
            tableId: Record.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Record.onDeleteItem = function (data) {
        var operation = function () {
            var request = new HttpRequest(Feng.ctxPath + "/record/delete", 'post', function (data) {
                Feng.success("删除成功!");
                table.reload(Record.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("recordId", data.basicId);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Record.tableId,
        url: Feng.ctxPath + '/record/page',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Record.initColumn(),
        request: {pageName: 'pageNo', limitName: 'pageSize'},
        parseData: Feng.parseData
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Record.search();
    });

    // 新建按钮点击事件
    $('#btnAdd').click(function () {
        $(location).attr('href', '/view/index');
        // window.open("/view/index");
        // Feng.newCrontab("/view/index","笔录信息")
        // Feng.ceo
    });


    // 工具条点击事件
    table.on('tool(' + Record.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Record.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Record.onDeleteItem(data);
        }
    });

});
