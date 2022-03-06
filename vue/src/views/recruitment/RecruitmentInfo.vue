<template>
    <div style="padding: 30px">
        <div class="add" >
            <el-button type="primary" @click="add">新增</el-button>
        </div>
        <div>
            <el-select v-model="this.department" class="m-2" placeholder="选择部门" style="width: 10%;">
                <el-option
                        v-for="item in deptOptions"
                        :key="item.deptno"
                        :label="item.dname"
                        :value="item.dname"
                >
                </el-option>
            </el-select>
            <el-select v-model="this.position" class="m-2" placeholder="选择职位" style="width: 10%;margin-left: 10px">
                <el-option
                        v-for="item in positionOptions"
                        :key="item.value"
                        :label="item.value"
                        :value="item.value"
                >
                </el-option>
            </el-select>
            <el-button type="primary" @click="load" style="margin-left: 10px">查询</el-button>
        </div>
        <div>
            <el-table :data="tableData" class="table"  stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
                <el-table-column prop="id" label="编号" width="100" align="center" sortable></el-table-column>
                <el-table-column prop="rdeptName" width="100" label="部门"></el-table-column>
                <el-table-column prop="rposition" label="职位"></el-table-column>
                <el-table-column prop="rnum" width="100" label="招聘人数"></el-table-column>
                <el-table-column prop="salaryScope" width="100" label="薪资范围"></el-table-column>
                <el-table-column prop="ageScope" width="100" label="年龄要求"></el-table-column>
                <el-table-column prop="education" label="学历要求"></el-table-column>
                <el-table-column prop="workingYears" width="100" label="工作经验"></el-table-column>
                <el-table-column width="250" label="技能要求" style="margin-right: 20px">
                    <template #default="scope" >
                        <el-input type="textarea" disabled rows="6" v-model="scope.row.workingSkill"></el-input>
                    </template>
                </el-table-column>
                <el-table-column width="50"></el-table-column>
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

            <el-dialog v-model:title="addOrUpadteTitle" v-model="dialogFormVisible" width="30%">
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="部门">
                        <el-select v-model="form.rdeptName" class="m-2" placeholder="选择部门" style="width: 80%">
                            <el-option
                                    v-for="item in deptOptions"
                                    :key="item.deptno"
                                    :label="item.dname"
                                    :value="item.dname"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="职位">
                        <el-select v-model="form.rposition" class="m-2" placeholder="选择职位" style="width: 80%">
                            <el-option
                                    v-for="item in positionOptions"
                                    :key="item.value"
                                    :label="item.value"
                                    :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="招聘人数">
                        <el-input v-model="form.rnum" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="薪资范围">
                        <el-input v-model="form.salaryScope" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄要求">
                        <el-input v-model="form.ageScope" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="学历要求">
                        <el-input v-model="form.education" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="工作经验">
                        <el-input v-model="form.workingYears" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="技能要求">
                        <el-input type="textarea" rows="10" v-model="form.workingSkill" style="width: 80%"></el-input>
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
        name: 'RecruitmentInfo',
        components: {
        },
        mounted() {
            window.Vue = this;
        },
        created() {
            this.load()
        },
        methods: {
            load(){
                request.get("/recruitment/queryPage",{params: {
                        currentPage: this.currentPage,
                        pageSize: this.pageSize,
                        department: this.department,
                        position: this.position
                    }}).then(res => {
                    console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })

                request.get("department/queryAll").then(res => {
                    this.deptOptions = res.data
                    this.deptOptions.unshift({})
                    console.log(this.deptOptions)
                })
            },
            add(){
                this.addOrUpadteTitle = "新增员工"
                this.dialogFormVisible = true
                this.form = {}
            },
            update(row){
                this.addOrUpadteTitle = "修改员工信息"
                this.dialogFormVisible = true
                this.form = JSON.parse(JSON.stringify(row))

            },
            save(){
                //更新或新增
                console.log(this.form)
                request.post("/recruitment/addOrUpdate",this.form).then(res => {
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
                request.delete("/recruitment/delete?id="+id).then(res => {
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
            cancelEvent(){}
        },
        data() {
            return {
                positionOptions: [{},{value: "初级工程师"},{value: "中级工程师"},{value: "高级工程师"},{value: "项目经理"}],
                deptOptions: [],
                department: "",
                position: "",
                form: {},
                total: 10,
                currentPage: 1,
                pageSize: 5,
                checkList: [],
                dialogFormVisible: false,
                addOrUpadteTitle: "",
                tableData: [],
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
    .el-table .cell {
    white-space: pre-line;
    }
</style>