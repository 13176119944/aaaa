var vm = new Vue({
    el:"#meunlistdiv",
    data:{
        meunlist:[],
        pid:1,
        enetity:{}
    },
    methods:{
        getMeunListByPid:function (pid) {
            var _this = this;
            _this.pid=pid;
            axios.get("../meun/getMeunListByPid.do?pid="+pid).then(function (response) {
                console.log(response.data)
                _this.meunlist = response.data;
            });
        },
        toAddMeun:function () {
             document.getElementById("meunupdatediv").style.display="block";
        },
        saveMeun:function () {
            var _this = this;
            this.enetity.pid = this.pid;
            axios.post("../meun/saveMeun.do",_this.enetity).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("meunupdatediv").style.display="none";
                    _this.getMeunListByPid(_this.pid);
                }else{
                    alert(response.data.msg);
                }
            });
        },
        deleteMeunByPid:function (id) {
            var _this= this;
            axios.get("../meun/deleteMeunByPid.do?id="+id).then(function (response) {
                if(response.data.flag){
                    alert(response.data.msg);
                    _this.getMeunListByPid(_this.pid);
                }else{
                    alert(response.data.msg);
                }
            });

        }
    }

});
vm.getMeunListByPid(1);