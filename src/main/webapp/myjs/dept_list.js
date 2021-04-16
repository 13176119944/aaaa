var vm = new Vue({
    el:"#peptlistdiv",
    data:{
        deptlist:[],
        pageNum:1,
        pageSize:3,
        page:{},
        searchEntity:{},
        postids:[],
        plist:[],
        entity:{}
    },
    methods:{
        getDeptListConn:function () {
            var _this=this;
            axios.post("../dept/getDeptListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.deptlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        toAddpost:function (id) {
            var _this=this;
            document.getElementById("addPostdiv").style.display="block";
            axios.get("../dept/toAddpost.do?id="+id).then(function (response) {
                _this.entity=response.data;
                _this.postids=response.data.postids;
                _this.plist = response.data.postBeans;
            });
        },
        savePost:function () {
            var _this = this;
            axios.post("../dept/savePost.do?id="+_this.entity.id,_this.postids).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("addPostdiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getDeptListConn();