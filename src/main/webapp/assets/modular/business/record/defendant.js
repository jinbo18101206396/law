$(function (){
    $("input[name='plaintiffType']").change(function (){
        var type =$("input[name='plaintiffType']:checked").val();
        var personType="                            <div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\">被告姓名</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"plaintiffName\" placeholder=\"请输入被告姓名\" type=\"text\" class=\"mylayui-input\" lay-verify=\"required\" required autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "\n" +
            "                            <div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\" >被告住址</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"carColor\" placeholder=\"请输入被告住址\" type=\"text\" class=\"mylayui-input\" autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>";
        var unitType="<div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\">被告全称</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"plaintiffName\" placeholder=\"请输入被告名称\" type=\"text\" class=\"mylayui-input\" lay-verify=\"required\" required autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "\n" +
            "                            <div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\" >被告地址</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"carColor\" placeholder=\"请输入被告地址\" type=\"text\" class=\"mylayui-input\" autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "\n" +
            "                            <div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\">法人代表</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"legalRep\" placeholder=\"请输入法人代表\" type=\"text\" class=\"mylayui-input\" autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                            <div class=\"layui-inline layui-col-md12\">\n" +
            "                                <label class=\"mylayui-form-label layui-form-required\">法人职务</label>\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <input name=\"LegalPos\" placeholder=\"请输入法人职务\" type=\"text\" class=\"mylayui-input\" autocomplete=\"off\"/>\n" +
            "                                </div>\n" +
            "                            </div>";
        if (type=="1"){
            $("#mychange").html(personType);
        }
        else{
            $("#mychange").html(unitType);
        }
    })
})