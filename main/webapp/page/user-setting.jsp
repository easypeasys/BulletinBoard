
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../lib/layui-v2.6.3/css/layui.css" media="all">
    <link rel="stylesheet" href="../css/public.css" media="all">
    <style>
        .layui-form-item .layui-input-company {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>

<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" action="">
            <div class="layui-form-item">
                <label class="layui-form-label required">管理账号</label>
                <div class="layui-input-block">
                    <input type="text" name="username"  readonly placeholder="请输入管理账号"  value="<%=(String)session.getAttribute("username")%>" class="layui-input">
                    <tip>填写自己管理账号的名称。</tip>
                </div>
            </div>
<!--            <div class="layui-form-item">-->
<!--                <label class="layui-form-label required">手机</label>-->
<!--                <div class="layui-input-block">-->
<!--                    <input type="number" name="phone" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"  value="" class="layui-input">-->
<!--                </div>-->
<!--            </div>-->
            <div class="layui-form-item">
                <label class="layui-form-label">昵称</label>
                <div class="layui-input-block">
                    <input type="text" name="nickname"  placeholder="请输入昵称"  value="<%=(String)session.getAttribute("nickname")%>" class="layui-input">
                </div>
            </div>

            <input type="hidden" name="userId"   value="<%=(String)session.getAttribute("userId")%>">

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveBtn">确认保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="../lib/layui-v2.6.3/layui.js" charset="utf-8"></script>
<script src="../js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script src="../lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.use(['form','miniTab'], function () {
        var form = layui.form,
            layer = layui.layer,
            miniTab = layui.miniTab;

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            data = data.field;
            if (data.nickname == '') {
                layer.msg('昵称不能为空');
                return false;
            }
            $.ajax({
                type:"POST",
                url:"../updateAccount",
                data:{
                    nickname:data.nickname,
                    type:'1',
                    userId:data.userId
                },
                dataType:"text",//类型
                success:function (res){
                    layer.msg('修改成功');
                },
                fail:function (err){
                    layer.msg('修改失败');
                }
            })

            return false;
        });

    });
</script>
</body>
</html>