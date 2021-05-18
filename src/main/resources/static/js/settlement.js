$(function(){
    //计算总价
    let array = $(".qprice");
    let totalCost = 0;
    for(let i = 0;i < array.length;i++){
        let val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥"+totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});

//商品数量++
function addQuantity(obj){
    let index = $(".car_btn_2").index(obj);
    let stock = parseInt($(".productStock").eq(index).val());
    let id = parseInt($(".id").eq(index).val());
    let quantity = parseInt($(".car_ipt").eq(index).val());;

    if(quantity == stock){
        alert("库存不足！");
        return false;
    }
    ++quantity;
    let price = parseInt($(".productPrice").eq(index).val());
    let cost = quantity*price;

    $.ajax({
        url:"/cart/update/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data){
            if(data=="success"){
                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
                //settlement2使用
                //$("#settlement2_totalCost").val(totalCost);
            }
        }

    });

    $(".qprice").eq(index).text('￥'+cost);
    $(".car_ipt").eq(index).val(quantity);

}

//商品数量--
function subQuantity(obj){
    let index = $(".car_btn_1").index(obj);
    let price = parseInt($(".productPrice").eq(index).val());
    let quantity = parseInt($(".car_ipt").eq(index).val());;
    let id = parseInt($(".id").eq(index).val());
    if(quantity == 1){
        alert("至少选择一件");
        return false;
    }
    --quantity;
    let cost = quantity*price;
    $(".qprice").eq(index).text('￥'+cost);
    $(".car_ipt").eq(index).val(quantity);

    $.ajax({
        url:"/cart/update/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data){
            if(data=="success"){
                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
                //settlement2使用
                //$("#settlement2_totalCost").val(totalCost);
            }
        }

    });

}

//移出购物车
function removeCart(obj){
    let index = $(".delete").index(obj);
    let id = parseInt($(".id").eq(index).val());
    if(confirm("是否确定要删除？")){
        window.location.href = "/cart/deleteById/"+id;
    }

    let array = $(".qprice");
    let totalCost = 0;
    for(let i = 0;i < array.length;i++){
        let val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥"+totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
}

function settlement2(){
    let totalcost = $("#totalprice").text();
    if(totalcost =="￥0"){
        alert("购物车为空，无法结算");
        return false;
    }
    window.location.href = "/cart/settlement2"
}