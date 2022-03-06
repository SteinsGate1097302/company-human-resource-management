<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div>
      <el-input v-model="dname" placeholder="请输入部门名称" class="input" style="width: 15%"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="deptno" label="编号" align="center" sortable ></el-table-column>
        <el-table-column prop="dname" label="名称" ></el-table-column>
        <el-table-column prop="location" label="地址" ></el-table-column>
        <el-table-column fixed="right" label="操作" >
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
          <el-form-item label="部门编号">
            <el-input v-model="form.deptno" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="部门名称">
            <el-input v-model="form.dname" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="部门地址">
            <el-input v-model="form.location" style="width: 80%"></el-input>
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
      request.get("/department/queryPage",{params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          dname: this.dname,
        }}).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    add(){
      this.addOrUpadteTitle = "新增部门信息"
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
        request.post("/department/insertOrUpdate",this.form).then(res => {
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
      request.delete("/department/delete?id="+id).then(res => {
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
  },
  data() {
    return {
      dname: "",
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