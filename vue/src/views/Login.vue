<template>
    <div class="main">
        <div class="back_img"></div>
        <div class="login">
            <div class="login_title">
                <h3>无纸化企业人力资源管理系统</h3>
            </div>
                <div class="login_input">
                    <el-input
                            :prefix-icon="Avatar"
                            placeholder="请输入账号"
                            v-model="from.username"
                    >
                    </el-input>
                </div>
                <div class="login_input">
                    <el-input
                            :prefix-icon="Lock"
                            placeholder="请输入密码"
                            show-password
                            v-model="from.password">
                    </el-input>
                </div>
            <div class="login_button">
                <p @click="login">登录</p>
            </div>
        </div>
    </div>


    <!--    粒子背景-->
    <!--  粒子形状：shapeType: String类型。默认’circle’。可用的粒子外观类型有：“circle-圆形”,“edge-方形”,“triangle-三角形”, “polygon-五边形”,“star-星形”-->
    <!--  鼠标指向事件：hoverMode: String类型。grab。可用的hover模式有: “grab-神罗天争/吸引”, “repulse-地爆天星/排斥”, + “bubble-放大镜”。-->
    <!--  鼠标点击事件：clickMode: String类型。push。可用的click模式有: “push-生成粒子”, “remove-移除粒子”, “repulse-地爆天星/排斥”, “bubble-放大镜”。-->
    <div style="" id="login-bg1">
        <vue-particles
                class="login-bg"
                color="#39AFFD"
                :particleOpacity="0.7"
                :particlesNumber="150"
                shapeType="star"
                :particleSize="4"
                linesColor="#8DD1FE"
                :linesWidth="1"
                :lineLinked="true"
                :lineOpacity="0.4"
                :linesDistance="150"
                :moveSpeed="3"
                :hoverEffect="true"
                hoverMode="grab"
                :clickEffect="true"
                clickMode="push"
        >
        </vue-particles>
    </div>


<!--    黑客帝国背景-->
<!--    <canvas id="bg" ref="canvas"></canvas>-->


</template>
<script>
    import {Avatar,Lock,UserFilled } from '@element-plus/icons-vue'
    import request from "../util/request";
    import {ElMessage} from "element-plus";

    export default {
        setup(){
            return{
                Avatar,
                Lock
            }
        },
        data() {
            return {
                from: {
                    username: "",
                    password: ""
                },
            };
        },
        methods: {
            login() {
                request.post("/admin/login",this.from).then(res => {
                    if (res.code === "200"){
                        ElMessage.success({
                            message: res.code+": "+res.msg,
                            type: 'success',
                            duration: 3000
                        })
                        sessionStorage.setItem("isLogin",true)
                        sessionStorage.setItem("username",this.from.username)
                        this.$router.push("/welcome")
                    }else {
                        ElMessage.error({
                            message: res.code+": "+res.msg,
                            type: 'error',
                            duration: 3000
                        })
                    }
                })
            },
        },

        // 黑客帝国背景设置
        // mounted() {
        //     // getContext() 方法返回一个用于在画布上绘图的环境。
        //     let canvas = this.$refs.canvas;
        //     let context = canvas.getContext("2d");
        //     window.onload = function () {
        //         //var canvas = document.getElementById("canvas");
        //         let W = window.innerWidth;
        //         let H = window.innerHeight;
        //         canvas.width = W;
        //         canvas.height = H;
        //         let fontSize = 15;
        //         let colunms = Math.floor(W / fontSize);
        //         let drops = [];
        //         for (let i = 0; i < colunms; i++) {
        //             drops.push(0);
        //         }
        //         let str = "01abcdefghijklmnopqurstuvwxyz";
        //
        //         function draw() {
        //             //让背景逐渐由透明到不透明
        //             context.fillStyle = "rgba(0,0,0,0.05)";
        //             context.fillRect(0, 0, W, H);
        //             //给字体设置样式
        //             //context.font = "700 "+fontSize+"px  微软雅黑";
        //             context.font = fontSize + 'px arial';
        //             //给字体添加颜色
        //             context.fillStyle = "rgb(50, 113, 167)";//随意更改字体颜色
        //             //写入图形中
        //             for (let i = 0; i < colunms; i++) {
        //                 let index = Math.floor(Math.random() * str.length);
        //                 let x = i * fontSize;
        //                 let y = drops[i] * fontSize;
        //                 context.fillText(str[index], x, y);
        //                 //如果要改变时间，肯定就是改变每次他的起点
        //                 if (y >= canvas.height && Math.random() > 0.92) {
        //                     drops[i] = 0;
        //                 }
        //                 drops[i]++;
        //             }
        //         }
        //         draw();
        //         setInterval(draw, 33);
        //     };
        // },
    };
</script>

<style scoped>

    .login {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        background: #fff;
        padding: 30px;
        width: 25%;
        border: 1px solid #eee;
        border-radius: 20px;
    }

    .login_title {
        text-align: center;
        padding-bottom: 50px;
    }

    .login_input:nth-child(2) {
        margin-bottom: 20px;
    }

    .login_input:nth-child(3) {
        margin-top: 20px;
    }

    .login_button {
        margin-top: 20px;
        display: flex;
    }

    .login_button p {
        color: #fff;
        font-size: 15px;
        display: block;
        line-height: 40px;
        text-align: center;
        cursor: pointer;
        width: 100%;
        border: 1px solid #ddd;
    }

    .login_button p:nth-child(1) {
        background: #409eff;
        margin-right: 10px;
    }

    .login_button p:nth-child(2) {
        background: #67c23a;
        margin-left: 10px;
    }

    #login-bg1{
        width: 100%;
        height: 100%;
    }
     /*设置背景颜色，如果想做背景图片 可以给标签一个class 直接添加背景图*/
       .login-bg{
           width: 100%;
           height: 100%;
           background: #3E3E3E;
       }

    .el-form-item__content{
        margin-left: 0px;
    }

    .bg {
        width: 100%;
        height: 100%;
        background-color: black;
    }

</style>