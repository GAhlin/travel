$(function () {
    /*var search = location.search;
    // 切割字符串，拿到第二个值
    var cid = search.split("=")[1];*/
    //获取cid的参数值
    var cid = getParameter("cid");
    //获取rname的参数值
    var rname = getParameter("rname");
    //判断rname如果不为null或者""
    if (rname) {
        //url解码
        rname = window.decodeURIComponent(rname);
    }

    //当页码加载完成后，调用load方法，发送ajax请求加载数据
    load(cid, null, rname);
});

function load(cid ,currentPage, rname){
    //发送ajax请求，请求route/pageQuery,传递cid
    $.get("route/pageQuery", {cid: cid, currentPage: currentPage, rname: rname}, function (pb) {
        //解析pageBean数据，展示到页面上
        //1.分页工具条数据展示
        //1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);

        /*<li><a href="">首页</a></li>
        <li class="threeword"><a href="#">上一页</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">6</a></li>
        <li><a href="#">7</a></li>
        <li><a href="#">8</a></li>
        <li><a href="#">9</a></li>
        <li><a href="#">10</a></li>
        <li class="threeword"><a href="javascript:;">下一页</a></li>
        <li class="threeword"><a href="javascript:;">末页</a></li>*/

        var lis = "";
        var fristPage = '<li onclick="javascipt:load(' + cid + ', 1, \''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';
        //计算上一页的页码
        var beforeNum = pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }
        var beforePage = '<li onclick="javascipt:load(' + cid + ',' + beforeNum + ', \''+rname+'\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';

        lis += fristPage;
        lis += beforePage;

        //1.2 展示分页页码
        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
        */

        // 定义开始位置begin,结束位置 end
        var begin; // 开始位置
        var end; //  结束位置

        //1.要显示10个页码
        if (pb.totalPage < 10) {
            //总页码不够10页

            begin = 1;
            end = pb.totalPage;
        } else {
            //总页码超过10页

            begin = pb.currentPage - 5;
            end = pb.currentPage + 4;

            //2.如果前边不够5个，后边补齐10个
            if (begin < 1) {
                begin = 1;
                end = begin + 9;
            }

            //3.如果后边不足4个，前边补齐10个
            if (end > pb.totalPage) {
                end = pb.totalPage;
                begin = end - 9;
            }
        }

        for (var i = begin; i <= end; i++) {
            var li;
            //判断当前页码是否等于i
            if (pb.currentPage == i) {

                li = '<li class="curPage" onclick="javascipt:load(' + cid + ',' + i + ', \''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';

            } else {
                //创建页码的li
                li = '<li onclick="javascipt:load(' + cid + ',' + i + ', \''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';
            }
            //拼接字符串
            lis += li;
        }

        //计算下一页的页码
        var nextNum = pb.currentPage + 1;
        if (nextNum >= pb.totalPage) {
            nextNum = pb.totalPage;
        }
        var nextPage = '<li onclick="javascipt:load(' + cid + ',' + nextNum + ', \''+rname+'\')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';
        var lastPage = '<li onclick="javascipt:load(' + cid + ',' + pb.totalPage + ', \''+rname+'\')" class="threeword"><a href="javascript:void(0);">末页</a></li>';
        lis += nextPage;
        lis += lastPage;

        //将lis内容设置到 ul
        $("#pageNum").html(lis);

        //2.列表数据展示
        var route_lis = "";

        for (var i = 0; i < pb.list.length; i++) {
            //获取{rid:1,rname:"xxx"}
            var route = pb.list[i];

            var li = '<li>\n' +
'                        <div class="img"><img src="' + route.rimage + '" style="width: 299px;"></div>\n' +
'                        <div class="text1">\n' +
'                            <p>' + route.rname + '</p>\n' +
'                            <br/>\n' +
'                            <p>' + route.routeIntroduce + '</p>\n' +
'                        </div>\n' +
'                        <div class="price">\n' +
'                            <p class="price_num">\n' +
'                                <span>&yen;</span>\n' +
'                                <span>' + route.price + '</span>\n' +
'                                <span>起</span>\n' +
'                            </p>\n' +
'                            <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>\n' +
'                        </div>\n' +
'                    </li>';
            route_lis += li;
        }
        $("#route").html(route_lis);

        //定位到页面顶部
        window.scrollTo(0, 0);
    });
}