layui.use('form', function() {
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function (data) {
        layer.msg(JSON.stringify(data.field));
        return false;
    });
    form.on('radio(stateType)', function(data){
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