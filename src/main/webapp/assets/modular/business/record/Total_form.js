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
            elem: '#lianshijiann' //指定元素
            ,format: 'yyyy年MM月dd日' //可任意组合
        });
        laydate.render({
            elem: '#kaitingshijian'
            ,type: 'datetime'
            ,format: 'yyyy年MM月dd日 HH时mm分' //可任意组合
        });
    });
// 基本信息表单
//动态添加input输入框
    $("#add_shenpanzhang").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label"> &nbsp </label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass2">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#form_shenpanzhang").append(str);
        x++;
    });


    $("#add_shenpanyuan").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label"> &nbsp </label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass2">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#form_shenpanyuan").append(str);
        x++;
    });

    $("#add_peishenyuan").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label">&nbsp</label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="陪审员姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass2">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#form_peishenyuan").append(str);
        x++;
    });
    //删除动态添加的input输入框 removeclass2
    $("body").on('click', ".removeclass2", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent().parent();
        parentEle.remove();
    });

    // 原告信息表单
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
//监听 radio
        form.on('radio(plaintiffType)', function(data){
            var type=data.value;
            var personType1 = "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告姓名</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"6j8z\" required  lay-verify=\"required\" placeholder=\"请输入原告姓名\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告住址</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"m61tp\" required  lay-verify=\"required\" placeholder=\"请输入原告住址\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";

            var unitType1="<div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告全称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"6j8z\" required  lay-verify=\"required\" placeholder=\"请输入原告全称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">原告简称</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"m61tp\" required  lay-verify=\"required\" placeholder=\"请输入原告简称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人代表</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"gjnmk\" required  lay-verify=\"required\" placeholder=\"请输入法人代表\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label layui-form-required\">法人职务</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <input type=\"text\" name=\"ugu\" required  lay-verify=\"required\" placeholder=\"请输入法人职务\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "                </div>";
            if (type=="1"){
                $("#mychange_liao").html(personType1);
            }
            else{
                $("#mychange_liao").html(unitType1);
            }
        });})

    // 被告信息表单

    //监听 radio
    layui.use('form', function(){
        var form = layui.form;
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
            "                        <input type=\"text\" name=\"6j8z\" required  lay-verify=\"required\" placeholder=\"请输入被告姓名\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label layui-form-required\">被告住址</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"m61tp\" required  lay-verify=\"required\" placeholder=\"请输入被告住址\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>";

        var unitType2="<div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label layui-form-required\">被告全称</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"6j8z\" required  lay-verify=\"required\" placeholder=\"请输入被告全称\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label layui-form-required\">被告简称</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"m61tp\" required  lay-verify=\"required\" placeholder=\"请输入被告简称\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label layui-form-required\">法人代表</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"gjnmk\" required  lay-verify=\"required\" placeholder=\"请输入法人代表\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"layui-form-item\">\n" +
            "                    <label class=\"layui-form-label layui-form-required\">法人职务</label>\n" +
            "                    <div class=\"layui-input-block\">\n" +
            "                        <input type=\"text\" name=\"ugu\" required  lay-verify=\"required\" placeholder=\"请输入法人职务\" autocomplete=\"off\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                </div>";
        if (type=="1"){
            $("#mychange2").html(personType2);
        }
        else{
            $("#mychange2").html(unitType2);
        }
    });})
    // 基本信息陈述
    layui.use('form', function() {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo3)', function (data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        form.on('radio(stateType3)', function(data){
            var type=data.value;
            var firstType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__独任审判，书记员__担任法庭记录。";
            var secondType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__担任审判长，与人民陪审员__共同组成合议庭，书记员__担任法庭记录。";
            var thirdType="审判员：当事人身份经核对无误，法庭宣布双方当事人及其诉讼代理人身份符合法律规定，出庭资格合法，可以参加诉讼。现在宣布开庭。北京市海淀区人民法院今天依法适用简易程序，公开审理原告__诉被告__一案，由本院审判员__担任审判长，与陪审员__，人民陪审员__共同组成合议庭，书记员__担任法庭记录。";
            if (type=="0"){
                $("#state").val(firstType);
            }
            else if (type=="1"){
                $("#state").val(secondType);
            }
            else if (type="2"){
                $("#state").val(thirdType);
            }
        });
    });
    // 法庭调查1

    var counterclaimnum=1;
    layui.use('form', function() {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });

        form.on('submit(deleteitem)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="claimitem"+num;
            $("#"+myid).remove();
            return false;
        });

        form.on('submit(delete_reply)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="replyitem"+num;
            $("#"+myid).remove();
            return false;
        });

        form.on('submit(delete_counterclaim)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="couterclaimitem"+num;
            $("#"+myid).remove();
            return false;
        });

        form.on('submit(delete_todayreply)',function (data) {
            var num=this.id.replace(/[^0-9]/ig,"");
            var myid="todayreplyitem"+num;
            $("#"+myid).remove();
            return false;
        })

        form.on('radio(is_counterclaim)',function (data){
            var type=data.value;
            var counterclaim_html="<div id =\"addcounterclaimitem\">\n" +
                "                    <div id=\"counterclaimitem1\">\n" +
                "                        <div class=\"layui-form-item\" style=\"margin-top: 11px;\">\n" +
                "                            <label class=\"layui-form-label\">\n" +
                "                                <input type=\"text\" name=\"title\" placeholder=\"反诉原告简称\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" name=\"title\" placeholder=\"诉讼请求项\" autocomplete=\"on\" class=\"layui-input\">\n" +
                "                                </div>\n" +
                "                                <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
                "                                    <div class=\"layui-btn-group\">\n" +
                "                                        <button id=\"counterclaimitem_add\" type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm\" data-type=\"text\" style=\"height: 38px;\">\n" +
                "                                            <i class=\"layui-icon\"></i>\n" +
                "                                        </button>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"layui-form-item layui-form-text\">\n" +
                "                    <label class=\"layui-form-label\">事实和理由</label>\n" +
                "                    <div class=\"layui-input-block\">\n" +
                "                        <textarea name=\"lji7n\" placeholder=\"请输入事实和理由\" class=\"layui-textarea\"></textarea>\n" +
                "                    </div>\n" +
                "                </div>";
            var todayreply_html="            <div class=\"layui-form-item\">\n" +
                "                <label class=\"layui-form-label layui-form-required\">反诉被告今</br>天是否答辩</label>\n" +
                "                <div class=\"layui-input-block\">\n" +
                "                    <input type=\"radio\" name=\"todayreply\" lay-filter=\"is_todayreply\" value=\"0\" title=\"答辩\" checked>\n" +
                "                    <input type=\"radio\" name=\"todayreply\" lay-filter=\"is_todayreply\" value=\"1\" title=\"不答辩\" >\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"todayreply\">\n" +
                "                <div id =\"addtodayreplyitem\">\n" +
                "                    <div id=\"todayreplyitem1\">\n" +
                "                        <div class=\"layui-form-item\" style=\"margin-top: 11px;\">\n" +
                "                            <label class=\"layui-form-label\">\n" +
                "                                <input type=\"text\" name=\"title\" placeholder=\"被告姓名\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" name=\"title\" placeholder=\"答辩内容\" autocomplete=\"on\" class=\"layui-input\">\n" +
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
            if(type=="0"){
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
                "                                <input type=\"text\" name=\"title\" placeholder=\"被告姓名\" class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
                "                            </label>\n" +
                "                            <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
                "                                <div style=\"float: left;display: inline;width: 80%\">\n" +
                "                                    <input type=\"text\" name=\"title\" placeholder=\"答辩内容\" autocomplete=\"on\" class=\"layui-input\">\n" +
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
            if(type=="0"){
                $("#todayreply").html(todayreply_html);
            }else {
                $("#todayreply").html("");
            }
            form.render();
        });
    });
    var asknum=1;
    $("#courtinves_add").on('click',function () {
        asknum++;
        var askhtml="            <div class=\"layui-form-item\" id=\"claimitem"+asknum+"\" style=\"margin-top: 11px;\">\n" +
            "                <label class=\"layui-form-label\">\n" +
            "                    <input type=\"text\" name=\"title\" placeholder=\"原告简称\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
            "                </label>\n" +
            "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
            "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
            "                        <input type=\"text\" name=\"title\" placeholder=\"诉讼请求项\" autocomplete=\"on\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
            "<div class=\"layui-btn-group\">\n" +
            "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm removeclass\" name=\"deleteitem\" id=\"deleteitem"+asknum+"\" style=\"height: 38px;\" lay-filter=\"deleteitem\" lay-submit>\n" +
            " <i class=\"layui-icon\"></i>\n" +
            "</button>\n" +
            "</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("#requestitem").append(askhtml);
    });

    var replynum=1;
    $("#replyitem_add").click(function () {
        replynum++;
        var replyhtml="            <div class=\"layui-form-item\" id=\"replyitem"+replynum+"\" style=\"margin-top: 11px;\">\n" +
            "                <label class=\"layui-form-label\">\n" +
            "                    <input type=\"text\" name=\"title\" placeholder=\"被告姓名\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
            "                </label>\n" +
            "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
            "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
            "                        <input type=\"text\" name=\"title\" placeholder=\"答辩内容\" autocomplete=\"on\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
            "<div class=\"layui-btn-group\">\n" +
            "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm removeclass\" name=\"delete_reply_item\" id=\"delete_reply"+replynum+"\" style=\"height: 38px;\" lay-filter=\"delete_reply\" lay-submit>\n" +
            " <i class=\"layui-icon\"></i>\n" +
            "</button>\n" +
            "</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("#myreplyitem").append(replyhtml);
    });


    $("#counterclaim").on('click','#counterclaimitem_add',function (){
        counterclaimnum++;
        var counterclaim_html="            <div class=\"layui-form-item\" id=\"couterclaimitem"+counterclaimnum+"\" style=\"margin-top: 11px;\">\n" +
            "                <label class=\"layui-form-label\">\n" +
            "                    <input type=\"text\" name=\"title\" placeholder=\"反诉被告简称\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
            "                </label>\n" +
            "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
            "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
            "                        <input type=\"text\" name=\"title\" placeholder=\"诉讼请求项\" autocomplete=\"on\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
            "<div class=\"layui-btn-group\">\n" +
            "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm removeclass\" name=\"delete_reply_item\" id=\"delete_counterclaim"+counterclaimnum+"\" style=\"height: 38px;\" lay-filter=\"delete_counterclaim\" lay-submit>\n" +
            " <i class=\"layui-icon\"></i>\n" +
            "</button>\n" +
            "</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("#addcounterclaimitem").append(counterclaim_html);
    });

    var todayreplynum=1;
    $("#counterclaim").on('click','#todayreplyitem_add',function (){
        todayreplynum++;
        var todayreply_html="            <div class=\"layui-form-item\" id=\"todayreplyitem"+todayreplynum+"\" style=\"margin-top: 11px;\">\n" +
            "                <label class=\"layui-form-label\">\n" +
            "                    <input type=\"text\" name=\"title\" placeholder=\"被告姓名\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
            "                </label>\n" +
            "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
            "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
            "                        <input type=\"text\" name=\"title\" placeholder=\"答辩内容\" autocomplete=\"on\" class=\"layui-input\">\n" +
            "                    </div>\n" +
            "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
            "<div class=\"layui-btn-group\">\n" +
            "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm removeclass\" name=\"delete_reply_item\" id=\"delete_todayreply"+todayreplynum+"\" style=\"height: 38px;\" lay-filter=\"delete_todayreply\" lay-submit>\n" +
            " <i class=\"layui-icon\"></i>\n" +
            "</button>\n" +
            "</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("#addtodayreplyitem").append(todayreply_html);
    });

    // 法庭调查2 原告举证质证

    $("#button_ofyuangaojuzheng").click(function () {
        var str =
            '<tr>\n'+
            '<td>\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name1" placeholder="证据名称" autoComplete="off"\n'+
            ' class="layui-input">\n'+
            '</div>\n'+
            ' </td>\n'+
            ' <td>\n'+
            '<div class="layui-input-row">\n'+
            '<div class="layui-col-md9">\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name2" placeholder="证明事项"\n'+
            'autoComplete="off" class="layui-input">\n'+
            ' </div>\n'+
            ' </div>\n'+
            '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</td>\n'+
            '</tr>\n'
        $("#form_ofyuangaojuzheng").append(str);
    });
    $("#button_ofbeigaozhizheng").click(function () {
        var str=
            '<tr>\n'+
            '<td>\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="" placeholder="被告姓名" autoComplete="off"\n'+
            ' class="layui-input">\n'+
            '</div>\n'+
            ' </td>\n'+
            ' <td>\n'+
            '<div class="layui-input-row">\n'+
            '<div class="layui-col-md9">\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name2" placeholder="证明名称"\n'+
            'autoComplete="off" class="layui-input">\n'+
            ' </div>\n'+
            ' </div>\n'+
            '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</td>\n'+
            '</tr>\n'
        $("#form_ofbeigaozhizheng").append(str);
    });

    // 法庭调查3 被告举证

    $("#button_beigaojuzheng").click(function () {
        var str =
            '<tr>\n'+
            '<td>\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name1" placeholder="证据名称" autoComplete="off"\n'+
            ' class="layui-input">\n'+
            '</div>\n'+
            ' </td>\n'+
            ' <td>\n'+
            '<div class="layui-input-row">\n'+
            '<div class="layui-col-md9">\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name2" placeholder="证明事项"\n'+
            'autoComplete="off" class="layui-input">\n'+
            ' </div>\n'+
            ' </div>\n'+
            '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</td>\n'+
            '</tr>\n'
        $("#form_beigaojuzheng").append(str);
    });

    $("#button_zhizheng").click(function () {
        var str=
            '<tr>\n'+
            '<td>\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="" placeholder="原告姓名" autoComplete="off"\n'+
            ' class="layui-input">\n'+
            '</div>\n'+
            ' </td>\n'+
            ' <td>\n'+
            '<div class="layui-input-row">\n'+
            '<div class="layui-col-md9">\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name2" placeholder="证明名称"\n'+
            'autoComplete="off" class="layui-input">\n'+
            ' </div>\n'+
            ' </div>\n'+
            '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</td>\n'+
            '</tr>\n'
        $("#form_zhizheng").append(str);
    });
    $("#button_yuangaozhizheng").click(function () {
        var str=
            '<tr>\n'+
            '<td>\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="" placeholder="被告姓名" autoComplete="off"\n'+
            ' class="layui-input">\n'+
            '</div>\n'+
            ' </td>\n'+
            ' <td>\n'+
            '<div class="layui-input-row">\n'+
            '<div class="layui-col-md9">\n'+
            '<div class="layui-card-header">\n'+
            '<input type="text" name="title" id="evid_name2" placeholder="证明名称"\n'+
            'autoComplete="off" class="layui-input">\n'+
            ' </div>\n'+
            ' </div>\n'+
            '<div class="layui-col-md3"><button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</td>\n'+
            '</tr>\n'
        $("#form_yuangaozhizheng").append(str);
    });
    // 法庭调查4-法庭询问
    $("#button_fatingdiaocha").click(function () {
        var str=
            '<div class="layui-input-row" id="form_fatingdiaocha">'+
            '<div class="layui-col-md11">'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="aa" placeholder="问题" autoComplete="off" class="layui-input">'+
            '</div>'+
            '</div>'+
            '<div class="layui-col-md1">'+
            '<button id="button_fatingdiaocha" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass1" data-type="text">'+
            '<i class="layui-icon">&#xe640;</i>'+
            '</button>'+
            '</div>'+
            '<table class="layui-table" id="bb">'+
            '<tr>'+
            '<td>'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="cc" placeholder="原告姓名" autoComplete="off" class="layui-input">'+
            ' </div>'+
            '</td>'+
            '<td>'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="dd" placeholder="回答" autoComplete="off" class="layui-input">'+
            '</div>'+
            '</td>'+
            '</tr><tr>'+
            '<td style="text-align: center" width="30%">'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="bbb" placeholder="被告姓名" autoComplete="off" class="layui-input">'+
            '</div></td>'+
            ' <td width="70%">'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="ccc" placeholder="回答" autoComplete="off" class="layui-input">'+
            '</div></td> </tr></table> </div>'
        $("#form_fatingdiaocha").append(str);
    });

    // 法庭辩论
    $(function () {
        layui.use('form', function () {
            var form = layui.form;
            //监听提交

            $("#bianlunyijian_yuangao").on('click',function () {
                var str =
                    '<tr>' +
                    '<td>' +
                    '<div class="layui-card-header">' +
                    '<input type="text" name="title" id="evid_name1" placeholder="原告" autocomplete="off" class="layui-input"  >' +
                    '</div>' +
                    '</td>' +
                    '<td>' +
                    '<div class="layui-input-row" >' +
                    '<div class="layui-col-md9">' +
                    '<div class="layui-card-header">' +
                    '<input type="text" name="title" id="evid_name2" placeholder="原告辩论意见" autocomplete="off" class="layui-input"  >' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-col-md3">' +
                    '<button id="fansubeigao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >' +
                    '<i class="layui-icon">&#xe640;</i>' +
                    '</button>' +
                    ' </div>' +
                    '</div>' +
                    '</td>' +
                    '</tr>'
                $("#form_bianlunyijian").append(str);
            });
            $("#beigaobianlun").click(function () {
                var str =
                    '<tr>'+
                    '<td>'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="evid_name1" placeholder="被告" autocomplete="off" class="layui-input"  >'+
                    '</div>'+
                    '</td>'+
                    '<td>'+
                    '<div class="layui-input-row" >'+
                    '<div class="layui-col-md9">'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="evid_name2" placeholder="被告辩论意见" autocomplete="off" class="layui-input"  >'+
                    '</div>'+
                    '</div>'+
                    '<div class="layui-col-md3">'+
                    '<button id="fansuyuangaoa" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
                    '<i class="layui-icon">&#xe640;</i>'+
                    '</button>'+
                    ' </div>'+
                    '</div>'+
                    '</td>'+
                    '</tr>'
                $("#form_bianlunyijian").append(str);
            });

            $("#fansubeigaoa").on('click', function () {
                var str =
                    '<tr>'+
                    '<td width="30%">'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="text3_yuangaofansu" placeholder="原告(反诉被告)" autoComplete="off" class="layui-input">'+
                    '</div>'+
                    '</td>'+
                    ' <td width="70%">'+
                    '<div class="layui-input-row">'+
                    '<div class="layui-col-md9">'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="yuangaobianlunyijian" placeholder="原告(反诉被告)辩论意见" autoComplete="off" class="layui-input">'+
                    '</div>'+
                    '</div>'+
                    '<div class="layui-col-md3">'+
                    '<button  id="fansubeigaoa" type="button"'+
                    'class="layui-btn layui-btn-primary layui-btn-sm removeclass" >'+
                    '<i class="layui-icon">&#xe640;</i>'+
                    '</button>'+
                    '</div>'+
                    '</div>'+
                    '</td>'+
                    '</tr>'
                $("#isFansu2a").append(str);
            });

            $("#fansuyuangaoa").on('click', function () {
                var str =
                    '<tr>'+
                    '<td>'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="input_fansuyuangao" placeholder="被告(反诉原告)"'+
                    ' autoComplete="off" class="layui-input">'+
                    '</div>'+
                    '</td>'+
                    '<td>'+
                    '<div class="layui-input-row">'+
                    '<div class="layui-col-md9">'+
                    '<div class="layui-card-header">'+
                    '<input type="text" name="title" id="input_fansuyuan" placeholder="被告(反诉原告)辩论意见"'+
                    'autoComplete="off" class="layui-input">'+
                    '</div>'+
                    '</div>'+
                    '<div class="layui-col-md3">'+
                    '<button id="fansuyuangaoa" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass"  data-type="text">'+
                    '<i class="layui-icon">&#xe640;</i>'+
                    '</button>'+
                    '</div>'+
                    '</div>'+
                    '</td>'+
                    '</tr>'
                $("#isFansu2a").append(str);
            });

            form.on('radio(isCounterclaim)', function (data) {
                var type = data.value;
                if (type == "0") {
                    $("#isFansua").show();

                } else {
                    $("#isFansua").hide();

                }
                form.render();
            })
            $("body").on('click', ".removeclass", function () {
                //元素移除前校验是否被引用
                var parentEle = $(this).parent().parent().parent().parent();
                parentEle.remove();

            });
        });
    })
    // $("#bianlunyijian_yuangao").click(function () {
    //     var str =
    //         '<tr>'+
    //         '<td>'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name1" placeholder="原告" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</td>'+
    //         '<td>'+
    //         '<div class="layui-input-row" >'+
    //         '<div class="layui-col-md9">'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name2" placeholder="原告辩论意见" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</div>'+
    //         '<div class="layui-col-md3">'+
    //         '<button id="fansubeigao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
    //         '<i class="layui-icon">&#xe640;</i>'+
    //         '</button>'+
    //         ' </div>'+
    //         '</div>'+
    //         '</td>'+
    //         '</tr>'
    //     $("#form_bianlunyijian").append(str);
    // });
    // $("#beigaobianlun").click(function () {
    //     var str =
    //         '<tr>'+
    //         '<td>'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name1" placeholder="被告" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</td>'+
    //         '<td>'+
    //         '<div class="layui-input-row" >'+
    //         '<div class="layui-col-md9">'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name2" placeholder="被告辩论意见" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</div>'+
    //         '<div class="layui-col-md3">'+
    //         '<button id="fansuyuangao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
    //         '<i class="layui-icon">&#xe640;</i>'+
    //         '</button>'+
    //         ' </div>'+
    //         '</div>'+
    //         '</td>'+
    //         '</tr>'
    //     $("#form_bianlunyijian").append(str);
    // });
    // $("#fansubeigao").on('click',function () {
    // // $("#fansubeigao").click(function () {
    //     var str =
    //         '<tr>'+
    //         '<td>'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name1" placeholder="原告(反诉被告)" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</td>'+
    //         '<td>'+
    //         '<div class="layui-input-row" >'+
    //         '<div class="layui-col-md9">'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name2" placeholder="原告(反诉被告)辩论意见" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</div>'+
    //         '<div class="layui-col-md3">'+
    //         '<button id="fansubeigao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
    //         '<i class="layui-icon">&#xe640;</i>'+
    //         '</button>'+
    //         ' </div>'+
    //         '</div>'+
    //         '</td>'+
    //         '</tr>'
    //     $("#last1").append(str);
    // });
    //
    // $("#fansuyuangao").on('click',function () {
    // // $("#fansuyuangao").click(function () {
    //     var str =
    //         '<tr>'+
    //         '<td>'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name1" placeholder="被告(反诉原告)" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</td>'+
    //         '<td>'+
    //         '<div class="layui-input-row" >'+
    //         '<div class="layui-col-md9">'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="evid_name2" placeholder="被告(反诉原告)辩论意见" autocomplete="off" class="layui-input"  >'+
    //         '</div>'+
    //         '</div>'+
    //         '<div class="layui-col-md3">'+
    //         '<button id="fansuyuangao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
    //         '<i class="layui-icon">&#xe640;</i>'+
    //         '</button>'+
    //         ' </div>'+
    //         '</div>'+
    //         '</td>'+
    //         '</tr>'
    //     $("#last1").append(str);
    // });

    $("body").on('click', ".removeclass", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().parent().parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent().parent().parent();
        parentEle.remove();
    });

    //删除动态添加的input输入框 法庭询问用的removeclass1
    $("body").on('click', ".removeclass1", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent();
        parentEle.remove();
    });

    // $("input[name='isfansu']").change(function (){
    //     var type =$("input[name='isfansu']:checked").val();
    //     var personType=
    //         '<table class="layui-table" >\n'+
    //         '<tr> <td width="30%">\n'+
    //         '<div class="layui-card-header">\n'+
    //         '<input type="text" name="title" id="text3_yuangaofansu" placeholder="原告(反诉被告)" autoComplete="off" class="layui-input">\n'+
    //         '</div>\n'+
    //         '</td>\n'+
    //         '<td width="70%">\n'+
    //         '<div class="layui-input-row">\n'+
    //         '<div class="layui-col-md9">\n'+
    //         '<div class="layui-card-header">\n'+
    //         '<input type="text" name="title" id="yuangaobianlunyijian" placeholder="原告(反诉被告)辩论意见" autoComplete="off" class="layui-input">\n'+
    //         '</div>\n'+
    //         '</div>\n'+
    //         '<div class="layui-col-md3">\n'+
    //         '<button id="fansubeigao" type="button"\n'+
    //         'class="layui-btn layui-btn-primary layui-btn-sm removeclass" \n'+
    //         'data-type="text">\n'+
    //         '<i class="layui-icon">&#xe654;</i>\n'+
    //         '</button>\n'+
    //         '</div>\n'+
    //         '</div>\n'+
    //         '</td>\n'+
    //         '</tr>\n'+
    //         '<tr><td>'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="input_fansuyuangao" placeholder="被告(反诉原告)"'+
    //         'autoComplete="off" class="layui-input">'+
    //         '</div>'+
    //         '</td>'+
    //         '<td>'+
    //         '<div class="layui-input-row">'+
    //         '<div class="layui-col-md9">'+
    //         '<div class="layui-card-header">'+
    //         '<input type="text" name="title" id="input_fansuyuan" placeholder="被告(反诉原告)辩论意见"'+
    //         'autoComplete="off" class="layui-input">'+
    //         '</div>'+
    //         ' </div>'+
    //         '<div class="layui-col-md3">'+
    //         '<button id="fansuyuangao" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass"  data-type="text">'+
    //         '<i class="layui-icon">&#xe654;</i>'+
    //         '</button>'+
    //         '</div>'+
    //         '</div>'+
    //         '</td>'+
    //         '</tr>'+
    //         '</table>'
    //
    //     var unitType=""
    //     if (type=="1"){
    //         $("#mychange").html(personType);
    //     }
    //     else{
    //         $("#mychange").html(unitType);
    //     }
    // })
    //双方发表陈述意见
    $("#yuangaochenshu").click(function () {
        var str =
            '<tr>'+
            '<td>'+
            '<p>原告</p>'+
            '</td>'+
            '<td>'+
            '<div class="layui-input-row" >'+
            '<div class="layui-col-md9">'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="" placeholder="原告陈述意见" autocomplete="off" class="layui-input"  >'+
            '</div>'+
            '</div>'+
            '<div class="layui-col-md3">'+
            '<button id="yuangaochenshu" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
            '<i class="layui-icon">&#xe640;</i>'+
            '</button>'+
            ' </div>'+
            '</div>'+
            '</td>'+
            '</tr>'
        $("#form_chenshuyijian").append(str);
    });
    $("#beigaochenshu").click(function () {
        var str =
            '<tr>'+
            '<td>'+
            '<p>被告</p>'+
            '</td>'+
            '<td>'+
            '<div class="layui-input-row" >'+
            '<div class="layui-col-md9">'+
            '<div class="layui-card-header">'+
            '<input type="text" name="title" id="" placeholder="原告陈述意见" autocomplete="off" class="layui-input"  >'+
            '</div>'+
            '</div>'+
            '<div class="layui-col-md3">'+
            '<button id="yuangaochenshu" type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass" data-type="text" >'+
            '<i class="layui-icon">&#xe640;</i>'+
            '</button>'+
            ' </div>'+
            '</div>'+
            '</td>'+
            '</tr>'
        $("#form_chenshuyijian2").append(str);
    });
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
    $("#button_beigaodianziwenshusongda").click(function () {
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

        $("#form_beigaodianziwenshusongda").append(str);
    });
})