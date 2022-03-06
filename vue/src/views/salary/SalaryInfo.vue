<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">批量修改</el-button>
    </div>
    <div>
      <el-input v-model="id" placeholder="请输入编号" class="input" style="width: 10%"></el-input>
      <el-input v-model="nickname" placeholder="请输入昵称" class="input" style="width: 10%"></el-input>
      <el-input v-model="salaryScope" placeholder="请输入薪资范围" class="input" style="width: 10%"></el-input>
      <el-select v-model="department" class="m-2" placeholder="选择部门" style="width: 10%">
        <el-option
                v-for="item in deptOptions"
                :key="item.deptno"
                :label="item.dname"
                :value="item.deptno"
        >
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="load(true)">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="id" label="编号" align="center" sortable ></el-table-column>
        <el-table-column prop="nickname" label="昵称" ></el-table-column>
        <el-table-column prop="departmentName" label="部门" ></el-table-column>
        <el-table-column prop="salary" label="薪资(月)" ></el-table-column>
        <el-table-column fixed="right" label="操作" >
          <template #default="scope">
            <el-button type="primary" size="small" @click="update(scope.row)">修改薪资</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog v-model:title="updateTitle" v-model="dialogFormVisible" width="30%">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="编号">
            <el-input disabled v-model="form.id" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input disabled v-model="form.nickname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-select disabled v-model="form.department" class="m-2" placeholder="Select" style="width: 80%">
              <el-option
                      v-for="item in deptOptions"
                      :key="item.deptno"
                      :label="item.dname"
                      :value="item.deptno"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="薪资">
            <el-input v-model="form.salary" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary"  @click="save">确 定</el-button>
        </span>
        </template>
      </el-dialog>

      <el-dialog v-model:title="updateTitle" v-model="dialogFormVisible1" width="30%">
        <el-form-item label="薪资调整(%)" style="margin-left: 80px">
          <el-input-number  v-model="percentum" :step="1" step-strictly :min="-20" :max="20" ></el-input-number>
        </el-form-item>
        <el-form-item label="薪资调整(元)" style="margin-left: 80px">
          <el-input-number  v-model="salaryOfDialogFormVisible1" :step="100" step-strictly :min="-1000" :max="1000" ></el-input-number>
        </el-form-item>
        <template #footer>
        <span class="dialog-footer" >
          <el-button @click="dialogFormVisible1 = false">取 消</el-button>
          <el-button type="primary"  @click="batchUpdate(this.tableData)">确 定</el-button>
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
  mounted() {
    window.Vue = this
  },
  components: {
  },
  created() {
    this.load()
  },
  methods: {
    load(sign){
      request.get("/employee/queryPage",{params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          id: this.id,
          nickname: this.nickname,
          department: this.department,
          salaryScope: this.salaryScope
        }}).then(res => {
        console.log(res)
        if (res.code === "200"){
          this.tableData = res.data.records
          this.total = res.data.total
          //通过查询按钮查询，才跳出查询成功的提示
          if (sign){
            ElMessage.success({
              message: res.code+": "+res.msg,
              type: 'success',
              duration: 3000
            })
          }
        }else {
          ElMessage.error({
            message: res.code+": "+res.msg,
            type: 'error',
            duration: 3000
          })
        }


      })

      request.get("department/queryAll").then(res => {
        this.deptOptions = res.data
      })
    },
    add(){
      this.updateTitle = "统一修改薪资"
      this.dialogFormVisible1 = true
      this.form = {}
    },
    update(row){
      this.updateTitle = "修改员工信息"
      this.dialogFormVisible = true
      this.form = JSON.parse(JSON.stringify(row))

    },
    save(){
      //更新或新增
        console.log(this.form)
        request.post("/employee/addOrUpdate",this.form).then(res => {
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
    batchUpdate(tableData){
      //批量更新薪资
      let employees = [];
      for (let data of tableData){
        employees.push(data)
      }
      request.put("/employee/batchUpdateEmp", {
        employees: employees,
        percentum: this.percentum,
        salaryNum : this.salaryOfDialogFormVisible1
      }).then(res => {
        if (res.code === "200") {
          ElMessage.success({
            message: res.code+": "+res.msg,
            type: 'success',
            duration: 3000
          })
          this.dialogFormVisible1 = false
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
      department: "",
      deptOptions: [],
      id: "",
      nickname: "",
      salaryScope: "",
      form: {},
      total: 10,
      currentPage: 1,
      pageSize: 5,
      checkList: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      percentum: 0,
      salaryOfDialogFormVisible1: 0,
      updateTitle: "",
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