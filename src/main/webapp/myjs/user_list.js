var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        page:{},
        pageNum:1,
        pageSize:3,
        searchEntity:{},
        entity:{},
        deptids:[],
        dlist:[{postids:[]}],
        deptid:0

    },
    methods:{
        getUserListConn:function () {
            var _this = this;
            axios.post("../user/getUserListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.userlist =response.data.list;
                _this.page=response.data;
                _this.pageNum=response.data.currentPage;
                _this.pageSize = response.data.pageSize;
            });
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getUserListConn();
        },
        toUserDept:function (id) {
            var _this = this;
            axios.get("../user/getUserVoById.do?id="+id).then(function (response) {
                document.getElementById("userdeptdiv").style.display="block";
                _this.dlist=response.data.dlist;
                _this.entity = response.data;
                _this.deptids = response.data.deptids;
            });
        },
        saveUserDept:function () {
            var _this=this;
            axios.post("../user/saveUserDept.do?id="+_this.entity.id,_this.deptids).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    _this.getUserListConn();
                    document.getElementById("userdeptdiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            });
        },
        toUserPost:function (id) {
            var _this = this;
            document.getElementById("userpostdiv").style.display="block";
            axios.get("../user/getUserInfo.do?id="+id).then(function (response) {
                _this.entity = response.data;
                _this.dlist = response.data.dlist;
                document.getElementById("userpostdiv").style.display="block";
            });
        },
        saveUserPost:function () {
            this.entity.dlist=this.dlist;
            var _this = this;
            console.log(_this.entity)
            axios.post("../user/saveUserPost.do",_this.entity).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("userpostdiv").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            });
        }
    }
});
vm.getUserListConn();
