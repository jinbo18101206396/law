layui.use(['table', 'HttpRequest', 'func', 'form','laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var HttpRequest = layui.HttpRequest;
    var func = layui.func;
    var form = layui.form;

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
            {field: 'courtNumber', align: "center", sort: true, title: '案号'},
          /*  {field: 'filingTime', align: "center", sort: true, title: '立案时间'},
            {field: 'courtTime', align: "center", sort: true, title: '开庭时间'},*/
            {field: 'judge', align: "center", sort: true, title: '审判员'},
            {field: 'courtClerk', align: "center", sort: true, title: '书记员'},
            {field: 'courtCause', align: "center", sort: true, title: '案由'},
            {field: 'status', align: "center", sort: true, templet: '#statusTpl', title: '案件状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 250}
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
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    Record.onDetailItem = function (data) {
        //
        $.get(
            "/record/detail",
            {courtNumber: data.courtNumber},
            (result) =>{
                let wholeItem = JSON.parse(result.data)
                let myLocalStorage={}
                if (wholeItem != null) {
                    //组织数据
                    if ("basicInfo" in wholeItem){
                        myLocalStorage["BasicInfo"] = wholeItem.basicInfo
                        myLocalStorage["BasicInfo"]["court_number"]=data.courtNumber
                    }

                    //基本信息陈述
                    if ("stateInfo" in wholeItem)
                        myLocalStorage["BasicState"] = wholeItem.stateInfo

                    //原告数据
                    if ("accuserInfo" in wholeItem && wholeItem.accuserInfo.length > 0) {
                        let PlaintiffItems = []
                        for (var j = 0; j < wholeItem.accuserInfo.length; j++) {
                            PlaintiffItems.push(wholeItem.accuserInfo[j])
                        }
                        myLocalStorage["PlaintiffItems"] = PlaintiffItems
                    }

                    //被告数据
                    if ("defendantInfo" in wholeItem && wholeItem.defendantInfo.length > 0) {
                        let  DefendantItems= []
                        for (j = 0; j < wholeItem.defendantInfo.length; j++) {
                            DefendantItems.push(wholeItem.defendantInfo[j])
                        }
                        myLocalStorage["DefendantItems"] = DefendantItems
                    }

                    if ("rightInfo" in wholeItem) {
                        let rightInfo = wholeItem.rightInfo
                        myLocalStorage["rightInfo"] = rightInfo
                    }

                    //法庭调查数据，包含原被告举证表，法庭调查表三个表
                    if ("courtInvestigate" in wholeItem) {
                        let courtTemp= wholeItem.courtInvestigate // 临时存储数据
                        let courtInves={
                            accuser_claim_item: courtTemp.accuser_claim_item,// 原告诉讼请求
                            accuser_claim_fact_reason:courtTemp.accuser_claim_fact_reason ,// 原告诉讼请求的事实及理由
                            is_counterclaim: courtTemp.is_counterclaim,
                            defendant_reply: courtTemp.defendant_reply,
                            counterclaim_accuser_claim_item: courtTemp.counterclaim_accuser_claim_item,
                            counterclaim_accuser_fact_reason: courtTemp.counterclaim_accuser_fact_reason,
                            counterclaim_defendant_reply:courtTemp.counterclaim_defendant_reply,
                            counterclaim_defendant_today_is_reply: courtTemp.counterclaim_defendant_today_is_reply,
                        }
                        myLocalStorage["CourtInves"] = courtInves

                        courtTemp.defendant_query.forEach(e => {e.defendant=e.defendant.split("**"),e.evidence = e.evidence.split("**")})
                        let accuserShowInfo={
                            //第一个动态生成的json accuser_evidence 包含以下3个信息
                            accuser_evidence: courtTemp.accuser_evidence,
                            accuser_evidence_fact_reason: courtTemp.accuser_evidence_fact_reason, //事实和理由(原告举证)

                            defendant_query:courtTemp.defendant_query
                        }
                        myLocalStorage["accuserShowInfo"] = accuserShowInfo

                        courtTemp.accuser_query.forEach(e => {e.accuser=e.accuser.split("**"),e.evidence = e.evidence.split("**")})
                        courtTemp.other_defendant_query.forEach(e => {
                            e.defendant = e.defendant.split("**"), e.evidence = e.evidence.split("**")
                        })
                        courtTemp.counterclaim_accuser_query.forEach(e => {
                            e.counterclaim_accuser = e.counterclaim_accuser.split("**"), e.evidence = e.evidence.split("**")
                        })
                        courtTemp.other_counterclaim_defendant_query.forEach(e => {
                            e.other_counterclaim_defendant = e.other_counterclaim_defendant.split("**"), e.evidence = e.evidence.split("**")
                        })
                        courtTemp.counterclaim_defendant_query.forEach(e => {
                            e.counterclaim_defendant = e.counterclaim_defendant.split("**"), e.evidence = e.evidence.split("**")
                        })

                        let defendantShowInfo={
                            //第一个动态生成的json defendant_evidence 包含以下3个信息
                            is_defendant_give_evidence: "1",//被告是否提供证据
                            is_counterclaim_give_defendant_evidence: "1",//反诉被告是否提供证据
                            defendant_evidence: courtTemp.defendant_evidence,
                            defendant_evidence_fact_reason: courtTemp.defendant_evidence_fact_reason,   //事实和理由(被告举证)
                            //第二个动态生成的json accuser_query 包含以下6个信息
                            accuser_query: courtTemp.accuser_query,
//其他被告质证环节
                            other_defendant_query: courtTemp.other_defendant_query,

                            //反诉后的答辩情况
                            //反诉后第一个部分 反诉被告（原告）进行举证
                            counterclaim_defendant_evidence: courtTemp.counterclaim_defendant_evidence,
                            counterclaim_defendant_evidence_fact_reason: courtTemp.counterclaim_defendant_evidence_fact_reason ,  //事实和理由(反诉被告提出，事实和理由 不是动态添加的)
                            //反诉后第二个动态生成的部分 反诉原告质证
                            counterclaim_accuser_query: courtTemp.counterclaim_accuser_query,
                            other_counterclaim_defendant_query: courtTemp.other_counterclaim_defendant_query,
                            counterclaim_accuser_evidence_fact_reason:courtTemp.counterclaim_accuser_evidence_fact_reason, //反诉原告的事实与理由
                            //反诉后第三个生成部分 反诉原告 (原告) 进行举证
                            counterclaim_accuser_evidence:courtTemp.counterclaim_accuser_evidence,
                            //反诉后第四个生成部分 反诉被告 (原告) 进行质证
                            counterclaim_defendant_query: courtTemp.counterclaim_defendant_query,
                        }
                        myLocalStorage["defendantShowInfo"] = defendantShowInfo
                    }


                    //法庭询问表
                    // let inquiryInfo=[]
                    if ("inquiryInfo" in wholeItem) {
                        let inquiryInfoItem = wholeItem.inquiryInfo
                        myLocalStorage["inquiryInfo"]={}
                        myLocalStorage["inquiryInfo"]["inquiry_info"] = inquiryInfoItem
                    }

                    //法庭辩论表
                    if ("argueInfo" in wholeItem) {
                        let argueInfo = wholeItem.argueInfo
                        myLocalStorage["argueInfo"] = argueInfo
                    }

                    //最后陈述表
                    if ("finalStatementInfo" in wholeItem) {
                        let finalStatementInfoItem = wholeItem.finalStatementInfo
                        myLocalStorage["finalStatementInfo"]={}
                        myLocalStorage["finalStatementInfo"]["final_statement_info"] = finalStatementInfoItem
                    }

                    //是否调解表
                    let mediateInfo = {}
                    if ("mediateInfo" in wholeItem) {
                        mediateInfo = wholeItem.mediateInfo
                        myLocalStorage["mediateInfo"] = mediateInfo
                    }

                    //电子文书送达表
                    if ("deliveryInfo" in wholeItem) {
                        let deliveryInfoItem = wholeItem.deliveryInfo
                        let delivery_info=[]
                        for (let i=0;i<deliveryInfoItem.length;i++){
                            let delivery=deliveryInfoItem[i]
                            if(JSON.stringify(delivery) != '{}'){
                                delivery_info.push(delivery)
                            }
                        }
                        myLocalStorage["deliveryInfo"]={}
                        myLocalStorage["deliveryInfo"]["delivery_info"] = delivery_info
                    }

                    localStorage.setItem( wholeItem.basicInfo.court_number,JSON.stringify(myLocalStorage))
                    //获取笔录信息，传到前台
                    $(location).attr('href', '/view/record/detail'+"/?CourtNum="+data.courtNumber);
                }
            },
            "json"
        );
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

    // 新建笔录按钮点击事件
    $('#btnAdd').click(function () {
        $(location).attr('href', '/view/record/add');
    });

    /* 点击删除 */
    Record.onDeleteItem = function (data) {
        var operation = function () {
            var httpRequest = new HttpRequest(Feng.ctxPath + "/record/delete", 'post', function (data) {
                Feng.success("删除成功!");
                table.reload(Record.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            httpRequest.set(data);
            httpRequest.start(true);
        };
        Feng.confirm("是否删除案号为（" + data.courtNumber + "）的笔录?", operation);
    };

    // 工具条点击事件
    table.on('tool(' + Record.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent == 'detail'){
            Record.onDetailItem(data);
        }else if(layEvent == 'delete'){
            Record.onDeleteItem(data);
        }
    });

    /* 修改案件状态 */
    Record.changeCourtStatus = function (basicId, checked) {
        new HttpRequest(Feng.ctxPath + "/record/changeStatus", 'post', function (data) {
            table.reload(Record.basicId);
            Feng.success("修改成功!");
        }, function (data) {
            table.reload(Record.basicId);
            Feng.error("修改失败!" + data.message);
        }).set({"basicId": basicId, "status": checked}).start(true);
    };

    // 修改案件状态
    form.on('switch(status)', function (obj) {
        var basicId = obj.elem.value;
        var checked = obj.elem.checked ? 1 : 2;
        Record.changeCourtStatus(basicId, checked);
    });

});
