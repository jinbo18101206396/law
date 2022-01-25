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