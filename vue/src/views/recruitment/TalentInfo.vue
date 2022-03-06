<template>
    <div style="padding: 30px">
        <div class="add" >
            <el-button type="primary" @click="add">新增</el-button>
        </div>
        <div>
            <el-input v-model="talentNo" placeholder="请输入编号" class="input" style="width: 15%"></el-input>
            <el-input v-model="nickname" placeholder="请输入姓名" class="input" style="width: 15%"></el-input>
            <el-select v-model="optionsValue" class="m-2" placeholder="选择国籍" style="width: 5%;margin-right: 10px">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                >
                </el-option>
            </el-select>
            <el-select v-model="levelOptionsValue" class="m-2" placeholder="等级" style="width: 5%;margin-right: 10px">
                <el-option
                        v-for="item in levelOptions"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value"
                >
                </el-option>
            </el-select>
            <el-button type="primary" @click="load">查询</el-button>
        </div>
        <div>
            <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
                <el-table-column prop="talentPhoto" label="照片" align="center" >
                    <template #default="scope">
                        <el-avatar shape="square" :size="100" :src="scope.row.talentPhoto"></el-avatar>
                    </template>
                </el-table-column>
                <el-table-column prop="talentNo" label="编号" align="center" sortable ></el-table-column>
                <el-table-column prop="talentName" label="姓名" ></el-table-column>
                <el-table-column  label="性别" >
                    <template #default="scope">
                        {{ scope.row.talentSex === "M" ? "男" : "女"}}
                    </template>
                </el-table-column>
                <el-table-column prop="talentAge" label="年龄" ></el-table-column>
                <el-table-column prop="talentPhone" label="联系方式"></el-table-column>
                <el-table-column prop="talentNationality" label="国籍"></el-table-column>
                <el-table-column prop="talentLevel" label="人才等级"></el-table-column>
                <el-table-column fixed="right" label="操作" width="200">
                    <template #default="scope">
                        <el-button type="primary" size="small" @click="update(scope.row)">编辑</el-button>
                        <el-popconfirm
                                confirmButtonText="确定"
                                cancelButtonText="取消"
                                icon="el-icon-info"
                                iconColor="red"
                                title="确定删除吗？"
                                @confirm="handleDelete(scope.row.id)"
                                @cancel="cancelEvent"
                        >
                            <template #reference>
                                <el-button type="danger" size="small" >删除</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog v-model:title="addOrUpdateTitle" v-model="dialogFormVisible" width="30%">
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item>
                        <div class="file">
                            <input type="file" class="update" accept="image/*" @change="change($event)" ref="updata">
                            <img :src="imageUrl ? imageUrl : baseImg" alt="" class="img">
                        </div>
                    </el-form-item>
                    <el-form-item label="编号">
                        <el-input v-model="form.talentNo" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名">
                        <el-input v-model="form.talentName" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄">
                        <el-input v-model="form.talentAge" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-radio-group v-model="form.talentSex">
                            <el-radio label="M" value="M">男</el-radio>
                            <el-radio label="F" value="F">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="联系方式">
                        <el-input v-model="form.talentPhone" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="国籍">
                        <el-select v-model="form.talentNationality" class="m-2" placeholder="请选择" style="width: 25%;margin-right: 10px">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="人才等级">
                        <el-select v-model="form.talentLevel" class="m-2" placeholder="请选择" style="width: 25%;margin-right: 10px">
                            <el-option
                                    v-for="item in levelOptions"
                                    :key="item.value"
                                    :label="item.value"
                                    :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>

                </el-form>
                <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary"  @click="save">确 定</el-button>
        </span>
                </template>
            </el-dialog>
        </div>

        <!--    分页条，total总条数，@size-change设置每页多少条，@current-change设置当前页-->
        <div class="block" >
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    v-model:currentPage="currentPage"
                    :page-size="pageSize"
                    layout="prev, pager, next, jumper"
                    :total="total"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script >
    import { } from '@element-plus/icons-vue'
    import request from "../../util/request";
    import {ElMessage} from "element-plus";
    export default {
        name: 'TalentInfo',
        mounted() {
            window.Vue = this;
        },
        components: {
        },
        created() {
            this.load()
        },
        methods: {
            load(){
                request.get("/talentPool/queryPage",{params: {
                        currentPage: this.currentPage,
                        pageSize: this.pageSize,
                        talentNo: this.talentNo,
                        nickname: this.nickname,
                        nationality: this.optionsValue,
                        level: this.levelOptionsValue
                    }}).then(res => {
                    console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
            add(){
                this.addOrUpadteTitle = "新增员工"
                this.dialogFormVisible = true
                this.form = {}
                this.imageUrl = ""
            },
            update(row){
                this.addOrUpadteTitle = "修改员工信息"
                this.dialogFormVisible = true
                this.form = JSON.parse(JSON.stringify(row))
                this.imageUrl = this.form.talentPhoto
            },
            save(){
                //更新或新增
                console.log(this.form)
                request.post("/talentPool/addOrUpdate",this.form).then(res => {
                    console.log(res)
                    if (res.code === "200"){
                        ElMessage.success({
                            message: res.code+": "+res.msg,
                            type: 'success',
                            duration: 3000
                        })
                        this.dialogFormVisible = false
                    }else {
                        ElMessage.error({
                            message: res.code+": "+res.msg,
                            type: 'error',
                            duration: 3000
                        })
                    }
                    this.load()
                })
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`)
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`)
                this.load()
            },
            handleDelete(id){
                request.delete("/talentPool/delete?id="+id).then(res => {
                    if (res.code === "200"){
                        ElMessage.success({
                            message: res.code+": "+res.msg,
                            type: 'success',
                            duration: 3000
                        })
                        this.dialogFormVisible = false
                    }else {
                        ElMessage.error({
                            message: res.code+": "+res.msg,
                            type: 'error',
                            duration: 3000
                        })
                    }
                    this.load()
                })
            },
            cancelEvent(){},
            change(e) {
                console.log(e.target.files[0].name);
                // 判断是不是规定格式
                // let name  =  e.target.files[0].name

                // 获取到第一张图片
                let file = e.target.files[0]

                // 创建文件读取对象
                var reader = new FileReader()
                var that = this

                //  将文件读取为DataURL
                reader.readAsDataURL(file)

                // 读取成功调用方法
                reader.onload = e => {
                    console.log('读取成功');

                    // e.target.result 获取 读取成功后的  文件DataURL
                    that.imageUrl = e.target.result
                    that.form.talentPhoto = that.imageUrl
                    // 如果要将图片上传服务器，就在这里调用后台方法
                }

            }
        },
        data() {
            return {
                optionsValue: "Foreign",
                levelOptionsValue: "",
                options: [{label: "请选择"},{value: "China",label: "中国"},{value: "Foreign",label: "外国"}],
                sexOptions: [{value: "M",label: "男"},{value: "F",label: "女"}],
                levelOptions: [{value: "",label: "请选择"},{value: "一"},{value: "二"},{value: "三"},{value: "四"},{value: "五"},{value: "六"},{value: "七"},{value: "八"},{value: "九"},{value: "十"}],
                deptOptions: [],
                talentNo: "",
                nickname: "",
                form: {},
                total: 10,
                currentPage: 1,
                pageSize: 5,
                checkList: [],
                dialogFormVisible: false,
                addOrUpdateTitle: "",
                tableData: [],
                imageUrl: "",
                baseImg: "/head.png"
            }
        },
    }

</script>

<style>
    .block{
        text-align: center;
        margin-top: 20px;
    }
    .input{
        margin-top: 10px;
        margin-right: 5px;
    }
    .add{
        float: right;
        margin-right: 10%;
    }
    .table{
        padding-left: 20px;
    }
    .file {
        position: relative;
        margin-left: 80px;
        width: 100px;
        height: 100px;
        background-color: #ccc;
    }
    .update {
        display: block;
        height: 100%;
        width: 100%;
        opacity: 0;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 10;
    }
    .img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100px;
        height: 100px;
    }
</style>