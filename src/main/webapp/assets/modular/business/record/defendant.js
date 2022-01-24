layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
    //监听 radio
    form.on('radio(defendantType)', function(data){
        var type=data.value;
        var personType = "                <div class=\"layui-form-item\">\n" +
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

        var unitType="<div class=\"layui-form-item\">\n" +
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
            $("#mychange").html(personType);
        }
        else{
            $("#mychange").html(unitType);
        }
    });
});

