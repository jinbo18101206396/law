layui.use(['table', 'HttpRequest', 'func', 'form', 'laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var HttpRequest = layui.HttpRequest;
    var func = layui.func;
    var form = layui.form;

    /**
     * 初始化参数re
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
            {field: 'courtNumber', align: "center", sort: true, title: '案号',minWidth: 250},
            {field: 'courtCause', align: "center", sort: true, title: '案由'},
            {field: 'judge', align: "center", sort: true, title: '审判员'},
            {field: 'courtTime', align: "center", sort: true, title: '开庭时间'},
            {field: 'status', align: "center", sort: true, templet: '#statusTpl', title: '案件状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 250}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Record.search = function () {
        var queryData = {};
        queryData['courtNumber'] = $("#court_number").val();
        queryData['courtCause'] = $("#court_cause").val();
        queryData['judge'] = $("#judge").val();
        queryData['courtClerk'] = $("#court_clerk").val();

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
            (result) => {
                let wholeItem = result.data
                let myLocalStorage = {}
                if (wholeItem != null) {
                    //组织数据
                    if ("basicInfo" in wholeItem) {
                        myLocalStorage["BasicInfo"] = wholeItem.basicInfo
                        myLocalStorage["BasicInfo"]["court_number"] = data.courtNumber
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
                        let DefendantItems = []
                        for (j = 0; j < wholeItem.defendantInfo.length; j++) {
                            DefendantItems.push(wholeItem.defendantInfo[j])
                        }
                        myLocalStorage["DefendantItems"] = DefendantItems
                    }

                    //第三人数据
                    if ("thirdPartyInfo" in wholeItem && wholeItem.thirdPartyInfo.length > 0) {
                        let thirdPartyInfo = []
                        for (j = 0; j < wholeItem.thirdPartyInfo.length; j++) {
                            thirdPartyInfo.push(wholeItem.thirdPartyInfo[j])
                        }
                        myLocalStorage["thirdPartyInfo"] = thirdPartyInfo
                    }

                    if ("rightInfo" in wholeItem) {
                        let rightInfo = wholeItem.rightInfo
                        rightInfo["judge_right_duty"]=wholeItem.judge_right_duty
                        rightInfo["judge_avoid"]=wholeItem.judge_avoid
                        myLocalStorage["rightInfo"] = rightInfo
                    }

                    //法庭调查数据，包含原被告举证表，法庭调查表三个表
                    if ("courtInvestigate" in wholeItem) {
                        let courtTemp = wholeItem.courtInvestigate // 临时存储数据
                        let courtInves = {
                            judge_accuser_claim_item:wholeItem.judge_accuser_claim_item,
                            judge_defendant_reply:wholeItem.judge_defendant_reply,
                            accuser_claim_item: courtTemp.accuser_claim_item,// 原告诉讼请求
                            accuser_claim_fact_reason: courtTemp.accuser_claim_fact_reason,// 原告诉讼请求的事实及理由
                            is_counterclaim: courtTemp.is_counterclaim,
                            defendant_reply: courtTemp.defendant_reply,
                            third_party_state:courtTemp.third_party_state,
                            judge_inquiry_after_accuser_claim: courtTemp.judge_inquiry_after_accuser_claim,
                            judge_inquiry_after_defendant_reply: courtTemp.judge_inquiry_after_defendant_reply,
                            is_change_claim_item: courtTemp.is_change_claim_item,
                            accuser_claim_item_after_change:courtTemp.accuser_claim_item_after_change,
                            accuser_claim_fact_reason_after_change: courtTemp.accuser_claim_fact_reason_after_change
                        }
                        myLocalStorage["CourtInves"] = courtInves

                        courtTemp.defendant_and_other_accuser_query.forEach(e => {
                            e.name = e.name ? e.name.split("**") : [] , e.evidence = e.evidence ? e.evidence.split("**") : []
                        })
                        let accuserShowInfo = {
                            //第一个动态生成的json accuser_evidence 包含以下3个信息
                            accuser_evidence: courtTemp.accuser_evidence,
                            //处理空值.forEach( i => {i.defendant=i.defendant==[""]?[]:i.defendant, i.evidence= i.evidence==[""]?[]:i.evidence})
                            defendant_and_other_accuser_query: courtTemp.defendant_and_other_accuser_query,
                            is_defendant_evidence : courtTemp.is_defendant_evidence,
                            judge_accuser_evidence:wholeItem.judge_accuser_evidence,
                            judge_defendant_and_other_accuser_query:wholeItem.judge_defendant_and_other_accuser_query,
                            accuser_evidence_witness:courtTemp.accuser_evidence_witness,
                            accuser_is_witness:courtTemp.accuser_is_witness,
                            question_list:["问题1","问题2"],
                            witness_type:["证人","鉴定人","勘验人"],
                        }

                        myLocalStorage["accuserShowInfo"] = accuserShowInfo

                        courtTemp.defendant_and_third_evidence.forEach(e => {
                            e.name = e.name ? e.name.split("**") : []
                        })
                        courtTemp.accuser_and_other_defendant_query.forEach(e => {
                            e.name = e.name ? e.name.split("**") : [], e.evidence = e.evidence ? e.evidence.split("**") : []
                        })

                        /*courtTemp.counterclaim_accuser_query.forEach(e => {
                            e.counterclaim_accuser = e.counterclaim_accuser ? e.counterclaim_accuser.split("**") : [], e.evidence = e.evidence ? e.evidence.split("**") : []
                        })
                        courtTemp.other_counterclaim_defendant_query.forEach(e => {
                            e.other_counterclaim_defendant = e.other_counterclaim_defendant ? e.other_counterclaim_defendant.split("**") : [], e.evidence = e.evidence ? e.evidence.split("**") : []
                        })
                        courtTemp.counterclaim_defendant_query.forEach(e => {
                            e.counterclaim_defendant = e.counterclaim_defendant ? e.counterclaim_defendant.split("**") : [], e.evidence = e.evidence ? e.evidence.split("**") : []
                        })*/

                        let defendantShowInfo = {
                            //第一个动态生成的json defendant_evidence 包含以下3个信息
                            defendant_and_third_evidence: courtTemp.defendant_and_third_evidence,
                            defendant_evidence_fact_reason: courtTemp.defendant_evidence_fact_reason,   //事实和理由(被告举证)
                            //第二个动态生成的json accuser_query 包含以下6个信息
                            //原告及其他被告质证
                            accuser_and_other_defendant_query: courtTemp.accuser_and_other_defendant_query,
                            judge_defendant_evidence:wholeItem.judge_defendant_evidence,


                            judge_accuser_and_other_defendant_query:wholeItem.judge_accuser_and_other_defendant_query,
                            //反诉后的答辩情况

                            defendant_is_witness:courtTemp.defendant_is_witness,
                            question_list:["问题1","问题2"],
                            witness_type:["证人","鉴定人","勘验人"],
                            defendant_and_third_evidence_witness:courtTemp.defendant_and_third_evidence_witness,

                        }

                        myLocalStorage["defendantShowInfo"] = defendantShowInfo
                    }


                    //法庭询问表
                    // let inquiryInfo=[]
                    if ("inquiryInfo" in wholeItem) {
                        let inquiryInfoItem = wholeItem.inquiryInfo

                        myLocalStorage["inquiryInfo"] = {}
                        myLocalStorage["inquiryInfo"]["inquiry_info"] = inquiryInfoItem
                        myLocalStorage["inquiryInfo"]["judge_inquiry"]=wholeItem.judge_inquiry

                        //临时添加
                        myLocalStorage["inquiryInfo"]["question_list"] = ["问题列表项1", "问题列表项2"]
                    }

                    //法庭辩论表
                    if ("argueInfo" in wholeItem) {
                        let argueInfo = wholeItem.argueInfo
                        argueInfo["judge_argue"]=wholeItem.judge_argue
                        myLocalStorage["argueInfo"] = argueInfo
                    }

                    //最后陈述表
                    if ("finalStatementInfo" in wholeItem) {
                        let finalStatementInfoItem = wholeItem.finalStatementInfo
                        myLocalStorage["finalStatementInfo"] = {}
                        myLocalStorage["finalStatementInfo"]["judge_finalstatement"] = wholeItem.judge_finalstatement
                        myLocalStorage["finalStatementInfo"]["final_statement_info"] = finalStatementInfoItem
                    }

                    //是否调解表
                    let mediateInfo = {}
                    if ("mediateInfo" in wholeItem) {
                        mediateInfo = wholeItem.mediateInfo
                        mediateInfo["judge_mediate"]=wholeItem.judge_mediate
                        myLocalStorage["mediateInfo"] = mediateInfo
                    }

                    //电子文书送达表
                    if ("deliveryInfo" in wholeItem) {
                        let deliveryInfoItem = wholeItem.deliveryInfo
                        let delivery_info = []
                        for (let i = 0; i < deliveryInfoItem.length; i++) {
                            let delivery = deliveryInfoItem[i]
                            if (JSON.stringify(delivery) != '{}') {
                                delivery_info.push(delivery)
                            }
                        }
                        myLocalStorage["deliveryInfo"] = {}
                        myLocalStorage["deliveryInfo"]["judge_delivery"] = wholeItem.judge_delivery

                        myLocalStorage["deliveryInfo"]["delivery_info"] = delivery_info
                    }

                    //审判员最后陈述
                    if ("summarize" in wholeItem) {
                        let summarize={}
                        summarize["summarize"]=wholeItem.summarize
                        summarize["summarize_inquiry"] = wholeItem.judge_inquiry_before_summarize

                        myLocalStorage["summarize"] = summarize
                    }

                    localStorage.setItem(wholeItem.basicInfo.court_number, JSON.stringify(myLocalStorage))
                    //获取笔录信息，传到前台
                    $(location).attr('href', '/view/record/detail' + "/?CourtNum=" + data.courtNumber);
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

    //生成笔录
    Record.generateRecord = function (data) {
        Feng.confirm("生成【" + data.courtNumber + "】的笔录?", function(){
            $(location).attr('href', '/record/download?courtNumber='+data.courtNumber);
            Feng.success("生成成功!");
        });
    }

    // 工具条点击事件
    table.on('tool(' + Record.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == 'detail') {
            Record.onDetailItem(data);
        } else if (layEvent == 'delete') {
            Record.onDeleteItem(data);
        } else if (layEvent == 'generate') {
            $.get('/record/list?courtNumber='+data.courtNumber,function(result){
                recordList(result.data);
            })
        }
    });

    // 笔录列表弹框工具条点击事件
    table.on('tool(templateTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if(layEvent == 'download'){
            $(location).attr('href', '/record/download?recordPath='+encodeURI(data.recordPath));
        }else if(layEvent == 'delete'){
            var operation = function () {
                var httpRequest = new HttpRequest(Feng.ctxPath + "/record/word/delete", 'post', function (data) {
                    Feng.success("删除成功!");
                    location.reload();
                }, function (data) {
                    Feng.error("删除失败!" + data.message + "!");
                });
                httpRequest.set(data);
                httpRequest.start(true);
            };
            Feng.confirm("确定删除笔录【" + data.recordName + "】?", operation);
        }
    });

    function recordList(result) {
        layui.use('table', function () {
            var table = layui.table;
            layer.open({
                type: 1,
                area: ['50%', '50%'],
                title: "笔录列表",
                content: '<div><table id="templateTable" lay-filter="templateTable"></table></div>', //先定义一个数据表格的div框
                success: function (index, layero) {
                    table.render({
                        elem: '#templateTable',
                        data: result,
                        cols: [[
                            {field: 'recordName', align:'center', sort: true, title: '笔录名称'},
                            {field: 'recordPath', align:'center',sort: true, title: '笔录路径',hide:true},
                            {align: 'center', toolbar: '#recordBar', title: '操作',width: 150}
                        ]]
                    });
                },
                end: function () {
                    location.reload();
                }
            });
        });
    }

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
