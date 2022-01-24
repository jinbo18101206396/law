layui.use('form', function() {
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function (data) {
        layer.msg(JSON.stringify(data.field));
        return false;
    });

    form.on('button(deleteitem)',function (data) {
        var num=this.id.replace(/[^0-9]/ig,"");
        var myid="item"+num;
        $("#"+myid).remove();
    });

});
var asknum=1;
$("#courtinves_add").click(function () {
    asknum++;
    var askhtml="            <div class=\"layui-form-item\" id=\"item"+asknum+"\" style=\"margin-top: 11px;\">\n" +
        "                <label class=\"layui-form-label\">\n" +
        "                    <input type=\"text\" name=\"title\"  lay-verify=\"required\" placeholder=\"原告简称\"  class=\"layui-input\" style=\"margin-top: -9px;\">\n" +
        "                </label>\n" +
        "                <div class=\"layui-input-inline\" style=\"width: 50%;\">\n" +
        "                    <div style=\"float: left;display: inline;width: 80%\">\n" +
        "                        <input type=\"text\" name=\"title\" lay-verify=\"required\" placeholder=\"诉讼请求项\" autocomplete=\"on\" class=\"layui-input\">\n" +
        "                    </div>\n" +
        "                    <div style=\"float: right;display: inline ;margin-left: 10px;\">\n" +
        "<div class=\"layui-btn-group\">\n" +
        "<button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-sm removeclass\" name=\"deleteitem\" id=\"deleteitem"+asknum+"\" style=\"height: 38px;\" lay-filter=\"deleteitem\">\n" +
        " <i class=\"layui-icon\"></i>\n" +
        "</button>\n" +
        "</div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>";
    $("#requestitem").append(askhtml);
});
