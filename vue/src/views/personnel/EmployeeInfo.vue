<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div>
      <el-input v-model="username" placeholder="请输入用户名" class="input" style="width: 15%"></el-input>
      <el-input v-model="nickname" placeholder="请输入昵称" class="input" style="width: 15%"></el-input>
      <el-select v-model="optionsValue" class="m-2" placeholder="Select" style="width: 5%;margin-right: 10px">
        <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button type="primary" @click="load">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table"  stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="id" label="编号" align="center" sortable width="200"></el-table-column>
        <el-table-column prop="username" label="用户名" width="200"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="200"></el-table-column>
        <el-table-column  label="性别" width="180">
          <template #default="scope">
            {{ scope.row.sex === "M" ? "男" : "女"}}
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="180"></el-table-column>
        <el-table-column prop="departmentName" label="部门" width="240"></el-table-column>
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
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.sex">
              <el-radio label="M" value="M">男</el-radio>
              <el-radio label="F" value="F">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-select v-model="form.department" class="m-2" placeholder="Select" style="width: 80%">
              <el-option
                      v-for="item in deptOptions"
                      :key="item.deptno"
                      :label="item.dname"
                      :value="item.deptno"
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
  name: 'EmployeeInfo',
  components: {
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      request.get("/employee/queryPage",{params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          workerStatus: this.optionsValue
        }}).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

      request.get("department/queryAll").then(res => {
        this.deptOptions = res.data
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
        request.post("/employee/addOrUpdate",this.form).then(res => {
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
      request.delete("/employee/deleteById?id="+id).then(res => {
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
      optionsValue: "onLine",
      options: [{value: "onLine",label: "在职"},{value: "departure",label: "离职"}],
      sexOptions: [{value: "M",label: "男"},{value: "F",label: "女"}],
      deptOptions: [],
      username: "",
      nickname: "",
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
</style>