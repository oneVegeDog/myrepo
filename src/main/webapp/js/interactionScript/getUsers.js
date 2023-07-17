
var usersvue = new Vue({
    el:"#usersBox",
    data(){
        return{
            usersArray:[],
            totalPageNum:0
        }
    },
    methods:{
        loadUsers(pageNum){
            axios.get("../../manager/getUsers?pageNum="+pageNum).then(resp=>{
                let message = resp.data;
                // alert(JSON.stringify(message));
                usersvue.usersArray = JSON.parse(message.jsonstr);
            });
        },
        changeStatue(user){
            if(confirm("确认改变用户状态？")){
                axios.get("http://localhost:8080/FileManageSystem/manager/changeUserStatue?statue="+user.userStatue+"&userId="+user.userId).then(resp=>{
                    let message=resp.data;
                    if(message.statue=200){
                        alert("修改成功");
                    }else{
                        alert("服务器出错，稍后再试");
                        if(user.userStatue==1){
                            user.userStatue = 2;
                        }else{
                            user.userStatue = 1;
                        }
                    }
                });
            }
        },
        toUserFileTree(userId){
            location.href="http://localhost:8080/FileManageSystem/html/manager/lookUserFileTree.html?userId="+userId;
        }
    },
    mounted(){
        this.loadUsers(1);
        axios.get("http://localhost:8080/FileManageSystem/getTotalPageNum").then(resp=>{
            // alert(resp.data);
            usersvue.totalPageNum = resp.data;
        });
    }

});

