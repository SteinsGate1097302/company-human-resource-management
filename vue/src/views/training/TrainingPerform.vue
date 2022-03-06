<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div>
      <el-input v-model="pno" placeholder="请输入培训编号" class="input" style="width: 10%;margin-right: 10px"></el-input>
      <el-select v-model="success" class="m-2" placeholder="请选择" style="width: 10%;">
        <el-option
                v-for="item in successOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="pno" label="编号" align="center" sortable ></el-table-column>
        <el-table-column prop="title" label="培训标题" ></el-table-column>
        <el-table-column prop="host" label="主持人" ></el-table-column>
        <el-table-column prop="success" label="是否成功召开" >
          <template #default="scope">
            {{scope.row.success === "T" ? "是" : "否"}}
          </template>
        </el-table-column>
        <el-table-column prop="participationNum" label="参与人数" ></el-table-column>
        <el-table-column prop="cost" label="培训花销"></el-table-column>
        <el-table-column width="10"><div style="height: 80px"></div></el-table-column>
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
          <el-form-item label="培训编号">
            <el-input v-model="form.pno" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="主持人">
            <el-input v-model="form.host" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="是否成功召开">
            <el-select v-model="form.success" class="m-2" placeholder="请选择" style="width: 80%;">
              <el-option
                      v-for="item in successOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="参与人数">
            <el-input v-model="form.participationNum" type="number" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="培训花销" >
            <el-input v-model="form.cost" type="number" style="width: 80%"></el-input>
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
  mounted() {
    window.Vue = this;
  },
  methods: {
    load(){
      request.get("/trainingPerform/queryPage",{params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          title: this.title,
          pNo: this.pno,
          success: this.success,
        }}).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    add(){
      this.addOrUpadteTitle = "新增培训实施信息"
      this.dialogFormVisible = true
      this.form = {}
      this.timeline = [{
        title: "",
        time: "",
        content:"",
      }]
    },
    update(row){
      this.addOrUpadteTitle = "修改培训实施信息"
      this.dialogFormVisible = true
      this.form = JSON.parse(JSON.stringify(row))
      this.timeline = JSON.parse(this.form.timeline)
    },
    save(){
      //更新或新增
      this.form.timeline = JSON.stringify(this.timeline)
        console.log(this.form)
        request.post("/trainingPerform/addOrUpdate",this.form).then(res => {
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
      request.delete("/trainingPerform/deleteById?id="+id).then(res => {
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
      title: "",
      pno: "",
      form: {},
      total: 10,
      currentPage: 1,
      pageSize: 5,
      dialogFormVisible: false,
      addOrUpdateTitle: "",
      tableData: [],
      successOptions: [{label: "已召开",value: "T"},{label: "未召开",value: "F"}],
      success: "T",
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

</style>