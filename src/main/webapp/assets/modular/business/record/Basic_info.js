layui.use(['element', 'jquery', 'layer', 'form', 'laydate'], function () {
    var form = layui.form
        , element = layui.element
        // , laydate = layui.laydate
        , $ = layui.$;
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#test1' //指定元素
        ,format: 'yyyy年MM月dd日' //可任意组合
    });
    laydate.render({
        elem: '#test2'
        ,type: 'datetime'
        ,format: 'yyyy年MM月dd日 HH时mm分' //可任意组合
    });




    //动态添加input输入框
    $("#add").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label">审判长*</label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            // '<button id="add" type="button" class="layui-btn layui-btn-primary layui-btn-sm" data-type="text">\n'+
            // '  <i class="layui-icon">&#xe654;</i>\n'+
            // '</button>\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#last").append(str);
        x++;
    });

    //删除动态添加的input输入框
    $("body").on('click', ".removeclass", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent().parent();
        parentEle.remove();
    });

    $("#add_judge").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label">审判员 *</label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="审判长姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            // '<button id="add" type="button" class="layui-btn layui-btn-primary layui-btn-sm" data-type="text">\n'+
            // '  <i class="layui-icon">&#xe654;</i>\n'+
            // '</button>\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#last1").append(str);
        x++;
    });

    //删除动态添加的input输入框
    $("body").on('click', ".removeclass1", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent().parent();
        parentEle.remove();
    });
    $("#add_2").click(function () {
        var str = '<div class="layui-form-item">\n'+
            '<label class="layui-form-label">陪审员</label>\n ' +
            '<div class="layui-input-inline">\n'+
            '<input type="text" name="title" lay-verify="required" placeholder="陪审员姓名" autocomplete="on" class="layui-input"></div>\n'+
            '<div class="layui-input-inline">\n'+
            '<div class="layui-btn-group">\n'+
            // '<button id="add_2" type="button" class="layui-btn layui-btn-primary layui-btn-sm" data-type="text">\n'+
            // '  <i class="layui-icon">&#xe654;</i>\n'+
            // '</button>\n'+
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm removeclass2">\n'+
            ' <i class="layui-icon">&#xe640;</i>\n'+
            '</button>\n'+
            '</div>\n'+
            '</div>\n'+
            '</div>\n';

        $("#last2").append(str);
        x++;
    });

    //删除动态添加的input输入框
    $("body").on('click', ".removeclass2", function () {
        //元素移除前校验是否被引用
        var approvalName = $(this).parent().parent().prev('div.layui-form-item').children().val();
        var parentEle = $(this).parent().parent().parent();
        parentEle.remove();
    });
})