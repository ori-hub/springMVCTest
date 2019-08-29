<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/26
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>表格的增删改查</title>

    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-md-12" style="padding:2em 0;">
                <p>点击 <i class="fa fa-pencil box"></i> 按钮可以对表格进行编辑，点击 <i class="fa fa-trash-o box"></i>按钮可以将该表格行删除。</p>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped" id="mytable">
                        <thead>
                            <tr>
                                <th>id</th>
                                <th>名字</th>
                                <th>年龄</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-12"  style="padding-bottom:2em;">
                <button class="btn btn-info" id="add"><i class="fa fa-plus"></i> 添加新的表格行</button>
            </div>
        </div>
    </div>

    <script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
    <script>window.jQuery || document.write('<script src="js/jquery/jquery-1.11.0.min.js"><\/script>')</script>
    <script type="text/javascript" src="js/bootstrap/bootstable.js"></script>

    <script type="text/javascript">
        $(function () {

            $.ajax({
                url: "account/findAllAccount",
                data: "",
                dataType: "json",
                success: function(data){

                    for (var i = 0; i < data.length; i++) {

                        var trTemp = $("<tr id='" + data[i].id + "'></tr>");
                        trTemp.append("<td>" + data[i].id + "</td>");
                        trTemp.append("<td>"+ data[i].name +"</td>");
                        trTemp.append("<td>"+ data[i].age +"</td>");
                        trTemp.appendTo("#mytable");
                    }
                    $('#mytable').SetEditable({
                        $addButton: $('#add')
                    });
                },
                error: function (err) {

                    alert("加载失败");
                }
            });
        })
    </script>

    <script type="text/javascript">

        var nowTi = "";
        //编辑
        function rowAcep(but) {
            //Acepta los cambios de la edición
            var $row = $(but).parents('tr');  //accede a la fila
            var $cols = $row.find('td');  //lee campos
            if (!ModoEdicion($row)) return;  //Ya está en edición
            //Está en edición. Hay que finalizar la edición
            IterarCamposEdit($cols, function($td) {  //itera por la columnas
                var cont = $td.find('input').val(); //lee contenido del input
                $td.html(cont);  //fija contenido y elimina controles
            });
            FijModoNormal(but);
            params.onEdit($row);

            if(nowTi == "新增"){

                $(function () {

                    $.ajax({
                        url: "account/insertAccount",
                        data: {id: $cols[0].innerText, name: $cols[1].innerText, age: $cols[2].innerText},
                        dataType: "json",
                        success: function(data){

                            nowTi = "";
                            if(data == 1){

                                alert("增加成功");
                            }
                            else{

                                alert("增加失败");
                            }
                        },
                        error: function (err) {

                            alert("增加失败");

                            nowTi = "";
                        }
                    });
                })

            }else{

                $(function () {
                    $.ajax({
                        url: "account/updateAccount",
                        data: {id: $cols[0].innerText, name: $cols[1].innerText, age: $cols[2].innerText},
                        dataType: "json",
                        success: function(data){
                            nowTi = "";
                            if(data == 1){

                                alert("编辑成功");
                            }
                            else{

                                alert("编辑失败");
                            }
                        },
                        error: function (err) {

                            alert("编辑失败");

                            nowTi = "";
                        }
                    });
                })
            }
        }

        //删除
        function rowElim(but) {  //Elimina la fila actual
            var $row = $(but).parents('tr');  //accede a la fila
            var $cols = $row.find('td');  //lee campos
            params.onBeforeDelete($row);
            $row.remove();
            params.onDelete();

            if($cols[0].innerText == ""){
                nowTi = "";
            }else {

                $(function () {

                    $.ajax({
                        url: "account/deleteAccount",
                        data: {id: $cols[0].innerText},
                        dataType: "json",
                        success: function(data){

                            nowTi = "";

                            if(data == 1){

                                alert("删除成功");
                            }
                            else{

                                alert("删除失败");
                            }
                        },
                        error: function (err) {

                            alert("删除失败");

                            nowTi = "";
                        }
                    });
                })
            }
        }

        //新增
        function rowAddNew(tabId) {  //Agrega fila a la tabla indicada.
            var $tab_en_edic = $("#" + tabId);  //Table to edit
            var $filas = $tab_en_edic.find('tbody tr');
            if ($filas.length==0) {
                //No hay filas de datos. Hay que crearlas completas
                var $row = $tab_en_edic.find('thead tr');  //encabezado
                var $cols = $row.find('th');  //lee campos
                //construye html
                var htmlDat = '';
                $cols.each(function() {
                    if ($(this).attr('name')=='buttons') {
                        //Es columna de botones
                        htmlDat = htmlDat + colEdicHtml;  //agrega botones
                    } else {
                        htmlDat = htmlDat + '<td></td>';
                    }
                });
                $tab_en_edic.find('tbody').append('<tr>'+htmlDat+'</tr>');
            } else {
                //Hay otras filas, podemos clonar la última fila, para copiar los botones
                var $ultFila = $tab_en_edic.find('tr:last');
                $ultFila.clone().appendTo($ultFila.parent());
                $ultFila = $tab_en_edic.find('tr:last');
                var $cols = $ultFila.find('td');  //lee campos
                $cols.each(function() {
                    if ($(this).attr('name')=='buttons') {
                        //Es columna de botones
                    } else {
                        $(this).html('');  //limpia contenido
                    }
                });
            }
            nowTi = "新增";

            params.onAdd();
        }

    </script>

</body>
</html>
