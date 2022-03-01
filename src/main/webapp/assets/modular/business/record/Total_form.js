$(function (){
    layui.use(['element', 'jquery', 'layer', 'form', 'laydate'], function () {
        var form = layui.form
            , element = layui.element
            // , laydate = layui.laydate
            , $ = layui.$;
        var laydate = layui.laydate;
        var table = layui.table;
        table.render({
            elem: '#demo'
            ,height: 312
            ,url: 'layui-v2.4.5/layui/demo.json' //数据接口
            ,page: true //开启分页
            ,cols: [[ //表头
                {field: 'id', type:"numbers",title: 'ID', width:80, sort: true, fixed: 'left'}
                // ,{field: 'aa', type:"numbers",title: '序号',width: 300},
                // ,{field: 'aa', type:"checkbox",width:300}
                ,{field: 'bbb', title: '问题列表'}
            ]]
        });
        laydate.render({
            elem: '#hejieshixian' //指定元素
            ,format: 'yyyy年MM月dd日' //可任意组合
        })
        laydate.render({
            elem: '#filling_time' //指定元素
            ,format: 'yyyy年MM月dd日' //可任意组合
        });
        laydate.render({
            elem: '#court_time'
            ,type: 'datetime'
            ,format: 'yyyy年MM月dd日 HH时mm分' //可任意组合
        });

// 基本信息表单
//动态添加input输入框
        var num_judge_chief=1;
        $("#add_judge_chief").click(function () {
            num_judge_chief++;
            var str =
                '<div class="layui-form-item">\n'+
                '<label class="layui-form-label">审判长'+num_judge_chief+'</label>\n ' +
                '<div class="layui-input-inline">\n'+
                '<input type="text" name="title" id="chief_judge'+num_judge_chief+'" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></input> </div>\n'+
                '<div class="layui-input-inline">\n'+
                '<div class="layui-btn-group">\n'+
                '<button type="button" id="add_judge_chief'+num_judge_chief+'" class="layui-btn layui-btn-primary layui-btn-sm remove_judge_chief">\n'+
                ' <i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>\n'+
                '</div>\n';

            $("#form_judge_chief").append(str);
        });
        //删除动态添加的input输入框 审判长
        $("body").on('click', ".remove_judge_chief", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent();
            parentEle.remove();
            num_judge_chief--;
        });
//审判员动态添加
        var num_judge=1;
        $("#add_judge").click(function () {
            num_judge++;
            var str = '<div class="layui-form-item">\n'+
                '<label class="layui-form-label"> 审判员'+num_judge+' </label>\n ' +
                '<div class="layui-input-inline">\n'+
                '<input type="text" id="judge'+num_judge+'" name="Judge'+num_judge+'" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></div>\n'+
                '<div class="layui-input-inline">\n'+
                '<div class="layui-btn-group">\n'+
                '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm remove_judge">\n'+
                ' <i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>\n'+
                '</div>\n';

            $("#form_judge").append(str);

        });
//删除动态添加的input输入框 审判员
        $("body").on('click', ".remove_judge", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent();
            parentEle.remove();
            num_judge--;
        });

//陪审员动态添加
        var num_juror=1;
        $("#add_juror").click(function () {
            num_juror++;
            var str =
                '<div class="layui-form-item">\n'+
                '<label class="layui-form-label">陪审员'+num_juror+'</label>\n ' +
                '<div class="layui-input-inline">\n'+
                '<input type="text" id="juror+'+num_juror+'" name="Juror'+num_juror+'" lay-verify="required" placeholder="陪审员姓名" autocomplete="on" class="layui-input"></div>\n'+
                '<div class="layui-input-inline">\n'+
                '<div class="layui-btn-group">\n'+
                '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm remove_juror">\n'+
                ' <i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>\n'+
                '</div>\n';

            $("#form_juror").append(str);

        });

        //删除动态添加的input输入框 陪审员
        $("body").on('click', ".remove_juror", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent();
            parentEle.remove();
            num_juror--;
        });
        //原告信息表单

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        //原告信息 单位-value=1 个人-value=2 切换
        form.on('radio(plaintiffType)', function(data){
            var type=data.value;
            var personType1 = "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告姓名</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser\" name=\"Accuser\" required  lay-verify=\"required\" placeholder=\"请输入原告姓名\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告住址</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser_address\" name=\"accuserAddress\" required  lay-verify=\"required\" placeholder=\"请输入原告住址\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";

            var unitType1="<div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告全称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser\" name=\"Accuser\" required  lay-verify=\"required\" placeholder=\"请输入原告全称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告简称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser_short\" name=\"accuseShort\" required  lay-verify=\"required\" placeholder=\"请输入原告简称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人代表</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser_represent\" name=\"accuserRepresent\" required lay-verify=\"required\" placeholder=\"请输入法人代表\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人职务</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"accuser_duty\" name=\"accuserDuty\" required  lay-verify=\"required\" placeholder=\"请输入法人职务\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";
            if (type=="2"){
                $("#mychange_liao").html(personType1);
            }
            else{
                $("#mychange_liao").html(unitType1);
            }
        });

        // 被告信息 单位-value=1 个人-value=2 切换
        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        form.on('radio(defendantType)', function(data){
            var type=data.value;
            var personType2 = "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">被告姓名</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant\" name=\"Defendant\" required  lay-verify=\"required\" placeholder=\"请输入被告姓名\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">被告住址</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant_address\" name=\"defendantAddress\" required  lay-verify=\"required\" placeholder=\"请输入被告住址\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";

            var unitType2="<div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">被告全称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant\" name=\"Defendant\" required  lay-verify=\"required\" placeholder=\"请输入被告全称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">被告简称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant_short\" name=\"defendantShort\" required  lay-verify=\"required\" placeholder=\"请输入被告简称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人代表</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant_representative\" name=\"defendantRepresentative\" required  lay-verify=\"required\" placeholder=\"请输入法人代表\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人职务</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" id=\"defendant_duty\" name=\"defendantDuty\" required  lay-verify=\"required\" placeholder=\"请输入法人职务\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";
            if (type=="2"){
                $("#mychange2").html(personType2);
            }
            else{
                $("#mychange2").html(unitType2);
            }
        });

        // 基本信息陈述 0-简易程序 1-普通程序独任制 2-普通程序合议制
        //监听提交
        form.on('submit(formDemo3)', function (data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        form.on('radio(stateType)', function(data){
            var type=data.value;
            var firstType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__独任审判，书记员__担任法庭记录。";
            var secondType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__担任审判长，与人民陪审员__共同组成合议庭，书记员__担任法庭记录。";
            var thirdType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__担任审判长，与陪审员__，人民陪审员__共同组成合议庭，书记员__担任法庭记录。";
            if (type=="1"){
                $("#state_content").val(firstType);
            }
            else if (type=="2"){
                $("#state_content").val(secondType);
            }
            else if (type="3"){
                $("#state_content").val(thirdType);
            }
        });

        // 法庭调查1

        var counterclaimnum=1;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        //删除诉讼请求项
        form.on('submit(deleteitem)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="claimitem"+num;
            $("#"+myid).remove();
            asknum--;
            return false;
        });
        //删除被告答辩内容
        form.on('submit(delete_reply)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="replyitem"+num;
            $("#"+myid).remove();
            replynum--;
            return false;
        });
        //删除反诉被告答辩内容
        form.on('submit(delete_counterclaim)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="couterclaimitem"+num;
            $("#"+myid).remove();
            counterclaimnum--;
            return false;
        });

        form.on('submit(delete_todayreply)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="todayreplyitem"+num;
            $("#"+myid).remove();
            todayreplynum--;
            return false;
        })
        //是否反诉界面
        form.on('radio(is_counterclaim)',function (data){
            var type=data.value;
            var counterclaim_html="<div id =\"addcounterclaimitem\">\n" +
                "                    <div id=\"counterclaimitem1\">\n" +
                "                        <div class=\"layui-form-item\" style=\"margin-top: 11px;\">\n" +
                "                            <label class=\"layui-form-label\">\n" +
                "                                <input type=\"text\" id=\"lawsuit_counter_accuser1\" name=\"counterAccuserShort1\" placeholder=\"反诉原告简称\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" id=\"lawsuit_counterclaim_item1\" name=\"counterClaimItem1\" placeholder=\"反诉原告诉讼请求项1\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                                </div>\n" +
                "                                <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "                                    <div class=\"layui-btn-group\">\n" +
                "                                        <button id=\"counterclaimitem_add\" type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" data-type=\"text\" style=\"height: 38px;\">\n" +
                "                                            <i class=\"layui-icon\"></i>\n" +
                "                                        </button>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "<div class=\"layui-form-item layui-form-text\">\n" +
                "                    <label class=\"layui-form-label\">事实和理由</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <textarea id=\"lawsuit_counter_factReason1\" name=\"counterFactReason1\" placeholder=\"请输入事实和理由\" class=\"layui-textarea\"></textarea>\n" +
                "                    </div>\n" +
                "                </div>"+
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n";
            var todayreply_html="            <div class=\"layui-form-item\">\n" +
                "                <label class=\"layui-form-label layui-form-required\">反诉被告今</br>天是否答辩</label>\n" +
                "                <div class=\"layui-input-block\">\n" +
                "                    <input type=\"radio\" name=\"today_counter\" lay-filter=\"is_todayreply\" value=\"0\" title=\"答辩\" checked>\n" +
                "                    <input type=\"radio\" name=\"today_counter\" lay-filter=\"is_todayreply\" value=\"1\" title=\"不答辩\" >\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"todayreply\">\n" +
                "                <div id =\"addtodayreplyitem\">\n" +
                "                    <div id=\"todayreplyitem1\">\n" +
                "                        <div class=\"layui-form-item\" style=\"margin-top: 11px;\">\n" +
                "                            <label class=\"layui-form-label\">\n" +
                "                                <input type=\"text\" id=\"lawsuit_defendant1\" name=\"defendantShort1\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" id=\"lawsuit_content1\" name=\"Content1\" placeholder=\"被告答辩内容1\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                                </div>\n" +
                "                                <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "                                    <div class=\"layui-btn-group\">\n" +
                "                                        <button id=\"todayreplyitem_add\" type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" data-type=\"text\" style=\"height: 38px;\">\n" +
                "                                            <i class=\"layui-icon\"></i>\n" +
                "                                        </button>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>";
            if(type=="2"){
                $("#counterclaim").html(counterclaim_html+todayreply_html);
            }else {
                $("#counterclaim").html("");
            }
            form.render();
        });


        form.on('radio(is_todayreply)',function (data){
            var type=data.value;
            var todayreply_html="<div id =\"addtodayreplyitem\">\n" +
                "                    <div id=\"todayreplyitem1\">\n" +
                "                        <div class=\"layui-form-item\" style=\"margin-top: 11px;\">\n" +
                "                            <label class=\"layui-form-label\">\n" +
                "                                <input type=\"text\" id=\"lawsuit_name_counter1\" name=\"nameCounter1\" placeholder=\"反诉被告姓名\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" id=\"lawsuit_content_counter1\" name=\"contentCounter1\" placeholder=\"反诉被告答辩内容1\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                                </div>\n" +
                "                                <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "                                    <div class=\"layui-btn-group\">\n" +
                "                                        <button id=\"todayreplyitem_add\" type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" data-type=\"text\" style=\"height: 38px;\">\n" +
                "                                            <i class=\"layui-icon\"></i>\n" +
                "                                        </button>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>";
            if(type=="2"){
                $("#todayreply").html(todayreply_html);
            }else {
                $("#todayreply").html("");
            }
            form.render();
        });
        //添加诉讼请求
        var asknum=1;
        $("#courtinves_add").on('click',function () {
            asknum++;
            var askhtml="            <div class=\"layui-form-item\" id=\"claimitem"+asknum+"\" style=\"margin-top: 11px;\">\n" +
                "                <label class=\"layui-form-label\">\n" +
                "                    <input type=\"text\" id=\"lawsuit_accuser_short"+asknum+"\" name=\"accuser_short"+asknum+"\" placeholder=\"原告简称\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                </label>\n" +
                "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                        <input type=\"text\" id=\"lawsuit_claim_item"+asknum+"\" name=\"claimItem"+asknum+"\" placeholder=\"诉讼请求项"+asknum+"\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "<div class=\"layui-btn-group\">\n" +
                "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm \" name=\"deleteitem\" id=\"deleteitem"+asknum+"\" style=\"height: 38px;\" lay-filter=\"deleteitem\" lay-submit>\n" +
                " <i class=\"layui-icon\"></i>\n" +
                "</button>\n" +
                "</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "<div class=\"layui-form-item layui-form-text\">"+
                "<label class=\"layui-form-label\">事实和理由</label>"+
                "<div class=\"layui-input-block\">"+
                "<textarea id=\"lawsuit_fact_reason"+asknum+"\" name=\"factReason"+asknum+"\" placeholder=\"请输入事实和理由\" class=\"layui-textarea\"></textarea>"+
                "</div>"+
                "</div>"+
                "            </div>";
            $("#requestitem").append(askhtml);
        });
        //添加被告答辩内容
        var replynum=1;
        $("#replyitem_add").click(function () {
            replynum++;
            var replyhtml="            <div class=\"layui-form-item\" id=\"replyitem"+replynum+"\" style=\"margin-top: 11px;\">\n" +
                "                <label class=\"layui-form-label\">\n" +
                "                    <input type=\"text\" id=\"lawsuit_defendant"+replynum+"\" name=\"defendantShort"+replynum+"\" placeholder=\"被告姓名\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                </label>\n" +
                "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                        <input type=\"text\" id=\"lawsuit_content"+replynum+"\" name=\"title\" placeholder=\"被告答辩内容"+replynum+"\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "<div class=\"layui-btn-group\">\n" +
                "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" name=\"delete_reply_item\" id=\"delete_reply"+replynum+"\" style=\"height: 38px;\" lay-filter=\"delete_reply\" lay-submit>\n" +
                " <i class=\"layui-icon\"></i>\n" +
                "</button>\n" +
                "</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>";
            $("#myreplyitem").append(replyhtml);
        });

        //添加反诉原告诉讼请求项
        $("#counterclaim").on('click','#counterclaimitem_add',function (){
            counterclaimnum++;
            var counterclaim_html="            <div class=\"layui-form-item\" id=\"couterclaimitem"+counterclaimnum+"\" style=\"margin-top: 11px;\">\n" +
                "                <label class=\"layui-form-label\">\n" +
                "                    <input type=\"text\" id=\"counter_accuser_short"+counterclaimnum+"\" name=\"counterAccuserShort"+counterclaimnum+"\" placeholder=\"反诉被告简称\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                </label>\n" +
                "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                        <input type=\"text\" id=\"lawsuit_counterclaim_item"+counterclaimnum+"\" name=\"title\" placeholder=\"反诉原告诉讼请求项"+counterclaimnum+"\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "<div class=\"layui-btn-group\">\n" +
                "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" name=\"delete_reply_item\" id=\"delete_counterclaim"+counterclaimnum+"\" style=\"height: 38px;\" lay-filter=\"delete_counterclaim\" lay-submit>\n" +
                " <i class=\"layui-icon\"></i>\n" +
                "</button>\n" +
                "</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "<div class=\"layui-form-item layui-form-text\">"+
                "<label class=\"layui-form-label\">事实和理由</label>"+
                "<div class=\"layui-input-block\">"+
                "<textarea id=\"lawsuit_counter_factReason"+counterclaimnum+"\" name=\"counterFactReason"+counterclaimnum+"\" placeholder=\"请输入事实和理由\""+
                " class=\"layui-textarea\"></textarea>"+
                " </div>"+
                "</div>"+
                "</div>";
            $("#addcounterclaimitem").append(counterclaim_html);
        });
        //添加反诉被告答辩内容
        var todayreplynum=1;
        $("#counterclaim").on('click','#todayreplyitem_add',function (){
            todayreplynum++;
            var todayreply_html="            <div class=\"layui-form-item\" id=\"todayreplyitem"+todayreplynum+"\" style=\"margin-top: 11px;\">\n" +
                "                <label class=\"layui-form-label\">\n" +
                "                    <input type=\"text\" id=\"lawsuit_name_counter"+todayreplynum+"\" name=\"nameCounter"+todayreplynum+"\" placeholder=\"反诉被告姓名\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                </label>\n" +
                "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                        <input type=\"text\" id=\"lawsuit_content_counter"+todayreplynum+"\" name=\"contentCounter"+todayreplynum+"\" placeholder=\"反诉被告答辩内容"+todayreplynum+"\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "<div class=\"layui-btn-group\">\n" +
                "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" name=\"delete_reply_item\" id=\"delete_todayreply"+todayreplynum+"\" style=\"height: 38px;\" lay-filter=\"delete_todayreply\" lay-submit>\n" +
                " <i class=\"layui-icon\"></i>\n" +
                "</button>\n" +
                "</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>";
            $("#addtodayreplyitem").append(todayreply_html);
        });

        // 法庭调查2 原告举证质证

        //原告举证的增加和删除
        var  accuser_evidencenum=1;
        $("#button_ofAccuserEvidence").click(function () {
            accuser_evidencenum++;
            var str =
                '<table class="layui-table">'+
                '<tr>\n'+
                '<td>\n'+
                '<div class="layui-card-header">\n'+
                '<input type="text" id="accshow_evidence'+accuser_evidencenum+'" name="evidenceQuery'+accuser_evidencenum+'"  placeholder="证据名称'+accuser_evidencenum+'" autoComplete="off"\n'+
                ' class="layui-input">\n'+
                '</div>\n'+
                ' </td>\n'+
                ' <td>\n'+
                '<div class="layui-input-row">\n'+
                '<div class="layui-col-md9">\n'+
                '<div class="layui-card-header">\n'+
                '<input type="text" id="accshow_content'+accuser_evidencenum+'" name="contentQuery'+accuser_evidencenum+'" placeholder="证明事项"\n'+
                'autoComplete="off" class="layui-input">\n'+
                ' </div>\n'+
                ' </div>\n'+
                '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm accuser_evidenceremove">\n'+
                ' <i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>\n'+
                '</td>\n'+
                '</tr>\n'+
                '<tr>'+
                '<td style="text-align: center" width="30%">'+
                '<div class="layui-col-md2">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">事实和理由</label>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td style="text-align: center" width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="accshow_fact_reason'+accuser_evidencenum+'" name="factReason'+accuser_evidencenum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '</table>'
            $("#form_ofAccuserEvidence").append(str);
        });
        $("body").on('click', ".accuser_evidenceremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent().parent().parent();
            parentEle.remove();
            accuser_evidencenum--;
        });

        //被告质证的增加和删除
        var defendant_Questioning_evidencenum=1;
        $("#button_ofAttendantQuestion").click(function () {
            defendant_Questioning_evidencenum++;
            form.render('radio','text1');
            var str=
                '<table class="layui-table" >'+
                '<tr>'+
                '<td style="text-align: center">审判员</td>'+
                ' <td>'+
                '<textarea type="text" class="layui-input ">被告对原告提交的证据进行质证</textarea>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="accshow_defendant'+defendant_Questioning_evidencenum+'" id="nameAttendant'+defendant_Questioning_evidencenum+'" placeholder="被告姓名" autocomplete="off"'+
                ' class="layui-input">'+
                ' </div>'+
                ' </td>'+
                '<td width="70%">'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="accshow_query_evidence'+defendant_Questioning_evidencenum+'" id="evidence_question'+defendant_Questioning_evidencenum+'" placeholder="证据名称1" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="button_ofAttendantQuestion" type="button" class="layui-btn layui-btn-primary layui-btn-sm remove_defendant_Questioning_evidence"'+
                'data-type="text">'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">真实性</label>'+
                '<div class="site-title">'+
                ' <div class="layui-form">'+
                '<input type="radio" name="accshow_facticity'+defendant_Questioning_evidencenum+'" lay-filter="text1" title="是" value="true" checked>'+
                '<input type="radio" name="accshow_facticity'+defendant_Questioning_evidencenum+'" lay-filter="text1" title="否" value="false">'+
                ' </div>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                ' <textarea name="accshow_query_facticity'+defendant_Questioning_evidencenum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">合法性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                '<input type="radio" name="accshow_legality'+defendant_Questioning_evidencenum+'" title="是" value="true" checked>'+
                '<input type="radio" name="accshow_legality'+defendant_Questioning_evidencenum+'" title="否" value="false">'+
                '</div>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea name="accshow_query_legality'+defendant_Questioning_evidencenum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">关联性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                ' <input type="radio" name="accshow_relevance'+defendant_Questioning_evidencenum+'" title="是" value="true" checked>'+
                '<input type="radio" name="accshow_relevance'+defendant_Questioning_evidencenum+'" title="否" value="false">'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea name="accshow_query_relevace'+defendant_Questioning_evidencenum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                ' </table>';
            form.render('radio','text1');
            form.render();
            $("#form_ofAttendantQuestion").append(str);
        });
        $("body").on('click', ".remove_defendant_Questioning_evidence", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent().parent().parent();
            parentEle.remove();
            defendant_Questioning_evidencenum--;
        });

        // 法庭调查3 被告举证
        var attendant_evidencenum=1;
        $("#button_ofAttendantEvidence").click(function () {
            attendant_evidencenum++;
            var str =
                '<table class="layui-table">'+
                '<tr><td><div class="layui-card-header">'+
                '<input type="text" id="evidence_AttendantQuery'+attendant_evidencenum+'" name="defendshow_evidence'+attendant_evidencenum+'"  placeholder="证据名称'+attendant_evidencenum+'" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</td><td>'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text"  id="defendshow_content'+attendant_evidencenum+'" name="contentAttendantQuery'+attendant_evidencenum+'" placeholder="证明事项" autocomplete="off"'+
                'class="layui-input"></div></div>'+
                '<div class="layui-col-md3">'+
                '<button id="button_ofAttendantEvidence" type="button" class="layui-btn layui-btn-primary layui-btn-sm attendant_evidenceremove"'+
                'data-type="text">'+
                '<i class="layui-icon">&#xe640;</i></button></div></div></td></tr><tr>'+
                '<td style="text-align: center" width="30%">'+
                '<div class="layui-col-md2">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">事实和理由</label>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td style="text-align: center" width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="defendshow_fact_reason'+attendant_evidencenum+'" name="factAttendantQuery'+attendant_evidencenum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                ' </div>'+
                '</div>'+
                ' </td>'+
                '</tr>'+
                '</table>';
            $("#form_ofAttendantEvidence").append(str);
        });
        $("body").on('click', ".attendant_evidenceremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent().parent().parent();
            parentEle.remove();
            bg_evidencenum--;
        });
        var AccuserQuestionnum=1;
        $("#button_AccuserQuestion").click(function () {
            AccuserQuestionnum++;
            var str=
                '<table class="layui-table">'+
                '<tr>'+
                ' <td width="30%">'+
                '<div class="layui-card-header">'+
                '<input type="text" id="defendshow_accuser'+AccuserQuestionnum+'" name="nameOthersAccuser1"  placeholder="原告姓名" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" id="defendshow_evidencenum'+AccuserQuestionnum+'" name="evidenceNum1"  placeholder="证据编号'+AccuserQuestionnum+'" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="button_zhizheng" type="button" class="layui-btn layui-btn-primary layui-btn-sm AccuserQuestioneremove"'+
                'data-type="text">'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">真实性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                '<input type="radio" name="defendshow_facticity'+AccuserQuestionnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_facticity'+AccuserQuestionnum+'" title="否" value="false">'+
                ' </div>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="defendshow_query_facticity'+AccuserQuestionnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '<hr>'+
                ' </td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">合法性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                '<input type="radio" name="defendshow_legality'+AccuserQuestionnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_legality'+AccuserQuestionnum+'" title="否" value="false">'+
                '</div>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="defendshow_query_legality'+AccuserQuestionnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                ' </td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">关联性</label>'+
                '<div class="site-title">'+
                ' <div class="layui-form">'+
                ' <input type="radio" name="defendshow_relevance'+AccuserQuestionnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_relevance'+AccuserQuestionnum+'" title="否" value="false">'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea name="defendshow_query_relevace'+AccuserQuestionnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '<hr>'+
                '</td>'+
                '</tr>'+
                '</table>';
            $("#form_AccuserQuestion").append(str);
        });
        $("body").on('click', ".AccuserQuestioneremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent().parent().parent();
            parentEle.remove();
            AccuserQuestionnum--;
        });

        var otherDefendantnum=1;
        $("#button_accuser_query").click(function () {
            otherDefendantnum++;
            var str=
                '<table class="layui-table">'+
                '<tr>'+
                ' <td width="30%">'+
                '<div class="layui-card-header">'+
                '<input type="text" id="defendshow_defendant'+otherDefendantnum+'" name="nameOthersAttendants"  placeholder="被告姓名" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" id="defendshow_numevidence1'+otherDefendantnum+'" name="evidenceNum"  placeholder="证据编号'+otherDefendantnum+'" autocomplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="button_accuser_query" type="button" class="layui-btn layui-btn-primary layui-btn-sm AccuserQuestioneremove"'+
                'data-type="text">'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">真实性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                '<input type="radio" name="defendshow_numfacticity'+otherDefendantnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_numfacticity'+otherDefendantnum+'" title="否" value="false">'+
                ' </div>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="defendshow_reason_facticity1'+otherDefendantnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '<hr>'+
                ' </td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">合法性</label>'+
                '<div class="site-title">'+
                '<div class="layui-form">'+
                '<input type="radio" name="defendshow_numlegality'+otherDefendantnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_numlegality'+otherDefendantnum+'" title="否" value="false">'+
                '</div>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea id="defendshow_reason_legality'+otherDefendantnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                ' </td>'+
                '</tr>'+
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">关联性</label>'+
                '<div class="site-title">'+
                ' <div class="layui-form">'+
                ' <input type="radio" name="defendshow_numrelevance'+otherDefendantnum+'" title="是" value="true" checked>'+
                '<input type="radio" name="defendshow_numrelevance'+otherDefendantnum+'" title="否" value="false">'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '<td width="70%">'+
                '<div class="layui-col-md10">'+
                '<div class="layui-input-row">'+
                '<textarea name="defendshow_reason_relevace'+otherDefendantnum+'" placeholder="理由" class="layui-textarea"></textarea>'+
                '</div>'+
                '</div>'+
                '<hr>'+
                '</td>'+
                '</tr>'+
                '</table>';
            $("#form_accuser_query").append(str);
        });
        // 法庭调查4-法庭询问
        var questionnum=1;
        $("#button_inquiry").click(function () {
            questionnum++;
            var str=
                '<div class="layui-input-row" id="form_inquiry">'+
                '<div class="layui-col-md11">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="inquiry_question'+questionnum+'" placeholder="问题'+questionnum+'" autoComplete="off" class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md1">'+
                '<button id="button_inquiry" type="button" class="layui-btn layui-btn-primary layui-btn-sm questionremove" data-type="text">'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '<table class="layui-table" id="bb">'+
                '<tr>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="inquiry_accuser'+questionnum+'" placeholder="原告姓名" autoComplete="off" class="layui-input">'+
                ' </div>'+
                '</td>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="inquiry_accuser_answer'+questionnum+'" placeholder="回答" autoComplete="off" class="layui-input">'+
                '</div>'+
                '</td>'+
                '</tr><tr>'+
                '<td style="text-align: center" width="30%">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="inquiry_defendant'+questionnum+'" placeholder="被告姓名" autoComplete="off" class="layui-input">'+
                '</div></td>'+
                ' <td width="70%">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="defendant_answer'+questionnum+'" placeholder="回答" autoComplete="off" class="layui-input">'+
                '</div></td> </tr></table> </div>'
            $("#form_inquiry").append(str);
        });
        $("body").on('click', ".questionremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent();
            parentEle.remove();
            questionnum--;
        });
        // 法庭辩论
        //原告辩论意见增加与删除
        var accuser_debatenum=1;
        $("#argue_accuser_button").on('click',function () {
            accuser_debatenum++;
            var str =
                '<tr>' +
                '<td>' +
                '<div class="layui-card-header">' +
                '<input type="text" name="title" id="argue_accuser'+accuser_debatenum+'" placeholder="原告" autocomplete="off" class="layui-input"  >' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="layui-input-row" >' +
                '<div class="layui-col-md9">' +
                '<div class="layui-card-header">' +
                '<input type="text" id="argue_accuser_argue'+accuser_debatenum+'"  placeholder="原告辩论意见'+accuser_debatenum+'" autocomplete="off" class="layui-input"  >' +
                '</div>' +
                '</div>' +
                '<div class="layui-col-md3">' +
                '<button id="argue_accuser_button" type="button" class="layui-btn layui-btn-primary layui-btn-sm accuser_debateremove data-type="text" >' +
                '<i class="layui-icon">&#xe640;</i>' +
                '</button>' +
                ' </div>' +
                '</div>' +
                '</td>' +
                '</tr>'
            $("#form_argue").append(str);
        });
        $("body").on('click', ".accuser_debateremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            accuser_debatenum--;
        });
        //被告辩论意见的增加与删除
        var defendant_debatenum=1;
        $("#argue_defendant_button").click(function () {
            defendant_debatenum++;
            var str =
                '<tr>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" id="argue_defendant'+defendant_debatenum+'" placeholder="被告" autocomplete="off" class="layui-input"  >'+
                '</div>'+
                '</td>'+
                '<td>'+
                '<div class="layui-input-row" >'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="argue_defendant_argue1'+defendant_debatenum+'" placeholder="被告辩论意见'+defendant_debatenum+'" autocomplete="off" class="layui-input"  >'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="argue_defendant_button" type="button" class="layui-btn layui-btn-primary layui-btn-sm defendant_debateremove" data-type="text" >'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#form_argue").append(str);
        });
        $("body").on('click', ".defendant_debateremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            defendant_debatenum--;
        });
        //原告反诉辩论意见的增加与删除
        var accuser_debatefan=1;
        $("#argue_counter_defendant_button").on('click', function () {
            accuser_debatefan++;
            var str =
                '<tr>'+
                '<td width="30%">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="argue_counter_defendant'+accuser_debatefan+'" placeholder="原告(反诉被告)" autoComplete="off" class="layui-input">'+
                '</div>'+
                '</td>'+
                ' <td width="70%">'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="argue_counter_defendant_debate'+accuser_debatefan+'" placeholder="原告(反诉被告)辩论意见'+accuser_debatefan+'" autoComplete="off" class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button  id="argue_defendant_debate_button" type="button"'+
                'class="layui-btn layui-btn-primary layui-btn-sm accuser_debatefanremove" >'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#argue_counter_defendant_form").append(str);
        });
        $("body").on('click', ".accuser_debatefanremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            accuser_debatefan--;
        });

        //被告反诉辩论意见的增加与删除
        var defendant_debatefan=1;
        $("#argue_defendant_debate_button").on('click', function () {
            defendant_debatefan++;
            var str =
                '<tr>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" id="argue_counter_accuser'+defendant_debatefan+'"  placeholder="被告(反诉原告)"'+
                ' autoComplete="off" class="layui-input">'+
                '</div>'+
                '</td>'+
                '<td>'+
                '<div class="layui-input-row">'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" id="argue_counter_accuser_debate'+defendant_debatefan+'"  placeholder="被告(反诉原告)辩论意见'+defendant_debatefan+'"'+
                'autoComplete="off" class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="argue_defendant_debate_button" type="button" class="layui-btn layui-btn-primary layui-btn-sm defendant_debatefanremove"  data-type="text">'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#argue_counter_defendant_form").append(str);
        });
        $("body").on('click', ".defendant_debatefanremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            defendant_debatefan--;
        });

//是否反诉的切换
        form.on('radio(isCounterclaim)', function (data) {
            var type = data.value;
            if (type == "true") {
                $("#isFansua").show();

            } else {
                $("#isFansua").hide();

            }
            form.render();
        })

        //双方发表陈述意见
        var accuser_statenum=1;
        $("#final_accuser_state").click(function () {
            accuser_statenum++;
            var str =
                '<tr>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="final_accuser'+accuser_statenum+'" placeholder="原告" autoComplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</td>'+
                '<td>'+
                '<div class="layui-input-row" >'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="final_accuser_state'+accuser_statenum+'" placeholder="原告陈述意见'+accuser_statenum+'" autocomplete="off" class="layui-input"  >'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="final_accuser_state" type="button" class="layui-btn layui-btn-primary layui-btn-sm accuser_stateremove" data-type="text" >'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#form_final_accuser").append(str);
        });
        $("body").on('click', ".accuser_stateremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            accuser_statenum--;
        });

        var defendant_statenum=1;
        $("#final_defendant_state").click(function () {
            defendant_statenum++;
            var str =
                '<tr>'+
                '<td>'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="final_defendant'+defendant_statenum+'" placeholder="被告" autoComplete="off"'+
                'class="layui-input">'+
                '</div>'+
                '</td>'+
                '<td>'+
                '<div class="layui-input-row" >'+
                '<div class="layui-col-md9">'+
                '<div class="layui-card-header">'+
                '<input type="text" name="title" id="final_defendant_state'+defendant_statenum+'" placeholder="被告陈述意见'+defendant_statenum+'" autocomplete="off" class="layui-input"  >'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">'+
                '<button id="final_defendant_state" type="button" class="layui-btn layui-btn-primary layui-btn-sm defendant_stateremove" data-type="text" >'+
                '<i class="layui-icon">&#xe640;</i>'+
                '</button>'+
                ' </div>'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#form_final_defendant").append(str);
        });
        $("body").on('click', ".defendant_stateremove", function () {
            //元素移除前校验是否被引用
            var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
            var parentEle = $(this).parent().parent().parent().parent();
            parentEle.remove();
            defendant_statenum--;
        });

//双方是否调解表格
        $("#button_beigaotiaojie").click(function () {
            var str =
                '<tr>\n'+
                '<td width="30%">\n'+
                '<p>被告<p>\n'+
                '</td>'+
                '<td width="70%">\n'+
                '<div class="layui-input-row">\n'+
                '<div class="layui-col-md9">\n'+
                '<div class="layui-input-block">'+
                '<div class="layui-form-radio">'+
                ' <input type="radio" name="name" value="no">'+
                '<i class="layui-anim layui-icon"></i>'+
                '<div>能</div>'+
                '</div>'+
                '<div class="layui-unselect layui-form-radio">'+
                '<input type="radio" name="name" value="yes">'+
                '<i class="layui-anim layui-icon"></i>'+
                '<div>不能</div>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '<div class="layui-col-md3">\n'+
                '<button id="beigaotiaojie" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text">\n'+
                '<i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>'+
                '</td>'+
                '</tr>'
            $("#form_beigaotiaojie").append(str);
        });
        var ElectronicInstruments=1;
        $("#button_beigaodianziwenshusongda").click(function () {
            ElectronicInstruments++;
            var str =
                '<tr>\n'+
                '<td width="30%">\n'+
                '<p>被告'+ElectronicInstruments+'<p>\n'+
                '</td>'+
                '<td width="70%">\n'+
                '<div class="layui-input-row">\n'+
                '<div class="layui-col-md9">\n'+
                '<textarea type="text" class="layui-input ">同意</textarea>'+
                '</div>'+
                '<div class="layui-col-md3">\n'+
                '<button id="beigaotiaojie" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text">\n'+
                '<i class="layui-icon">&#xe640;</i>\n'+
                '</button>\n'+
                '</div>\n'+
                '</div>'+
                '</td>'+
                '</tr>'

            $("#form_beigaodianziwenshusongda").append(str);
        });
    });
});