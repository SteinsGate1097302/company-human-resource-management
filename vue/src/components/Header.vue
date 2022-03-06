<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
        <div style="width: 250px; padding-left: 30px; font-weight: bold; color: dodgerblue" @click="toWelcome">无纸化企业人力资源管理系统</div>
        <div style="flex: 1"></div>
        <div style="width: 120px">
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                  {{nickname}}
                  <el-icon><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>个人信息</el-dropdown-item>
                        <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import { Edit,FolderOpened,ArrowDown } from '@element-plus/icons-vue'
    import {ElMessage, ElMessageBox} from "element-plus";
    import request from "../util/request";
    export default {
        name: "Header",
        components: {
            ArrowDown
        },
        data(){
            return{
                nickname: ""
            }
        },
        created() {
            request.get("/admin/queryByUsername?username="+sessionStorage.getItem("username")).then(res => {
                this.nickname = res.data.nickname
            })
        },
        methods: {
            exit() {
                ElMessageBox.confirm(
                    '确认退出登陆?',
                    '警告',
                    {
                        confirmButtonText: '确认',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }
                )
                    .then(() => {
                        ElMessage({
                            type: 'success',
                            message: '退出登陆成功!',
                        })
                        sessionStorage.setItem("isLogin",null)
                        sessionStorage.setItem("username",null)
                        this.$router.push("/")
                    })
                    .catch(() => {

                    })
            },
            toWelcome(){
                this.$router.push("/welcome")
            }
        },

    }
</script>

<style scoped>

</style>