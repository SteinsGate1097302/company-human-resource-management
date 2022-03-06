<template >
    <div >
        <el-menu
                active-text-color="#ffd04b"
                background-color="#545c64"
                text-color="#fff"
                default-active="2"
                class="el-menu-vertical-demo"
                :collapse="isCollapse"
                @open="handleOpen"
                @close="handleClose"
                style="min-height: calc(100vh - 50px)"
                router
                :default-active="$route.path"
        >
            <div @click="toggleCollapse" class="aside01">|||</div>
            <!-- 带子菜单的菜单栏-->
            <el-sub-menu index="1" :disabled="personnel">
                <!-- 标题-->
                <template #title>
                    <el-icon> <avatar /> </el-icon>
                    <span>人事管理</span>
                </template>
                <!-- 子菜单1 -->
                <el-menu-item index="/welcome">
                    <el-icon><location /></el-icon>
                    <template #title >基本信息</template>
                </el-menu-item>
                <!-- 子菜单2-->
                <el-menu-item index="/emp">
                    <el-icon><location /></el-icon>
                    <template #title>员工管理</template>
                </el-menu-item>
                <!-- 子菜单3-->
                <el-menu-item index="/dept">
                    <el-icon><location /></el-icon>
                    <template #title>部门管理</template>
                </el-menu-item>
            </el-sub-menu>

            <!-- 带子菜单的菜单栏-->
            <el-sub-menu index="2" :disabled="recruitment">
                <!-- 标题-->
                <template #title>
                    <el-icon><flag /></el-icon>
                    <span>招聘管理</span>
                </template>
                <!-- 子菜单1 -->
                <el-menu-item index="/recruitment">
                    <el-icon><location /></el-icon>
                    <template #title>招聘信息</template>
                </el-menu-item>
                <!-- 子菜单2-->
                <el-menu-item index="/apply">
                    <el-icon><location /></el-icon>
                    <template #title>应聘信息</template>
                </el-menu-item>
                <el-menu-item index="/talent">
                    <el-icon><location /></el-icon>
                    <template #title>人才库</template>
                </el-menu-item>
            </el-sub-menu>

            <!-- 带子菜单的菜单栏-->
            <el-sub-menu index="3" :disabled="training">
                <!-- 标题-->
                <template #title>
                    <el-icon><opportunity /></el-icon>
                    <span>培训管理</span>
                </template>
                <!-- 子菜单1 -->
                <el-menu-item index="/trainingPlan">
                    <el-icon><location /></el-icon>
                    <template #title>培训计划</template>
                </el-menu-item>
                <!-- 子菜单2-->
                <el-menu-item index="/trainingPerform">
                    <el-icon><location /></el-icon>
                    <template #title>培训实施</template>
                </el-menu-item>
                <el-menu-item index="TrainingFiles">
                    <el-icon><location /></el-icon>
                    <template #title>培训材料</template>
                </el-menu-item>
            </el-sub-menu>

            <!-- 不带子菜单的菜单栏-->
            <el-menu-item index="/salary" :disabled="salary">
                <el-icon><WalletFilled /></el-icon>
                <template #title>薪酬管理</template>
            </el-menu-item>

            <!-- 不带子菜单的菜单栏-->
            <el-menu-item index="/home" :disabled="salary">
                <el-icon><help-filled /></el-icon>
                <template #title>系统管理</template>
            </el-menu-item>

<!--            &lt;!&ndash; 带子菜单的菜单栏&ndash;&gt;-->
<!--            <el-sub-menu index="5" :disabled="system">-->
<!--                &lt;!&ndash; 标题&ndash;&gt;-->
<!--                <template #title>-->
<!--                    <el-icon><location /></el-icon>-->
<!--                    <span>系统管理</span>-->
<!--                </template>-->
<!--                &lt;!&ndash; 子菜单1 &ndash;&gt;-->
<!--                <el-menu-item index="5-1">-->
<!--                    <el-icon><location /></el-icon>-->
<!--                    <template #title>管理员信息</template>-->
<!--                </el-menu-item>-->
<!--                &lt;!&ndash; 子菜单2&ndash;&gt;-->
<!--                <el-menu-item index="5-2">-->
<!--                    <el-icon><location /></el-icon>-->
<!--                    <template #title>管理员权限</template>-->
<!--                </el-menu-item>-->
<!--            </el-sub-menu>-->

        </el-menu>
    </div>

</template>
<style>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 220px;
        min-height: calc(100vh - 50px);
    }
    .aside01{
        text-align: center;
        color: #cccccc;
    }
</style>
<script>
    import { Location,IconMenu,Document,Setting,Avatar,Opportunity,Flag,WalletFilled,HelpFilled } from '@element-plus/icons-vue'
    import request from "../util/request";
    export default {
        components: {
            Location,
            IconMenu,
            Document,
            Setting,
            Avatar,
            Opportunity,
            Flag,
            WalletFilled,
            HelpFilled
        },
        created() {
          this.load()
        },
        data() {
            return {
                isCollapse: false,
                personnel: true,
                recruitment: true,
                training: true,
                salary: true,
                system: true
            };
        },
        methods: {
            //菜单的点击事件
            // handleOpen(key, keyPath) {
            //     console.log(key, keyPath);
            // },
            // handleClose(key, keyPath) {
            //     console.log(key, keyPath);
            // },

            // 点击按钮，切换菜单的折叠与展开
            toggleCollapse () {
                this.isCollapse = !this.isCollapse
            },

            // 根据权限设置功能可用
            load() {
                request.get("/admin/queryByUsername?username="+sessionStorage.getItem("username")).then(res => {
                    if (res.data.power.includes("personnel")){
                        this.personnel = false
                    }
                    if (res.data.power.includes("recruitment")){
                        this.recruitment = false
                    }
                    if (res.data.power.includes("training")){
                        this.training = false
                    }
                    if (res.data.power.includes("salary")){
                        this.salary = false
                    }
                    if (sessionStorage.getItem("username") === "SuperAdmin"){
                        this.system = false
                    }
                })
            }

        }
    }
</script>