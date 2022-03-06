<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div>
      <el-input v-model="title" placeholder="请输入培训标题" class="input" style="width: 10%"></el-input>
      <el-input v-model="location" placeholder="请输入培训地点" class="input" style="width: 10%;margin-right: 10px"></el-input>
      <el-date-picker v-model="startTime" type="datetime" placeholder="选择开始时间" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
      <el-date-picker v-model="endTime" type="datetime" placeholder="选择结束时间" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
      <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="pno" label="编号" align="center" sortable ></el-table-column>
        <el-table-column prop="title" label="培训标题" ></el-table-column>
        <el-table-column prop="startTime" label="开始时间" ></el-table-column>
        <el-table-column prop="endTime" label="结束时间" ></el-table-column>
        <el-table-column prop="location" label="培训地址" ></el-table-column>
        <el-table-column label="培训事件" type="expand" width="200">
          <template #default="scope">
            <el-timeline >
              <el-timeline-item
                      v-for="(activity, index) in JSON.parse(scope.row.timeline)"
                      :key="index"
                      :timestamp="activity.time"
                      placement="top"
                      type="primary"
                      style="margin-left: 20%"
              >
                <el-card style="width: 60%;">
                  <h3>{{activity.title}}</h3><br>
                  <p>&nbsp;&nbsp;&nbsp;&nbsp;{{activity.content}}</p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </template>
        </el-table-column>
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
          <el-form-item label="培训标题">
            <el-input v-model="form.title" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="培训地点">
            <el-input v-model="form.location" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="培训开始时间">
            <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间" value-format="YYYY-MM-DD HH:mm:ss" style="width: 80%"></el-date-picker>
          </el-form-item>
          <el-form-item label="培训结束时间" >
            <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间" value-format="YYYY-MM-DD HH:mm:ss" style="width: 80%"></el-date-picker>
          </el-form-item>

<!--          form表单动态添加item-->
          <el-form-item label="培训事件"></el-form-item>
          <div v-for="(item, index) in this.timeline" :key="index">
            <el-form-item label="培训标题" >
              <el-input v-model="item.title" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="培训时间">
              <el-date-picker style="width: 80%" v-model="item.time" type="datetime" placeholder="选择培训时间" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="培训内容" >
              <el-input type="textarea" rows="3" v-model="item.content" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button v-if="index+1 === timeline.length" @click="addItem" type="primary">增加</el-button>
              <el-button v-if="index !== 0" @click="deleteItem(item, index)" type="danger">删除</el-button>
            </el-form-item>
          </div>

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
      request.get("/trainingPlan/queryPage",{params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          title: this.title,
          location: this.location,
          startTime: this.startTime,
          endTime: this.endTime
        }}).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    add(){
      this.addOrUpadteTitle = "新增应聘者信息"
      this.dialogFormVisible = true
      this.form = {}
      this.timeline = [{
        title: "",
        time: "",
        content:"",
      }]
    },
    update(row){
      this.addOrUpadteTitle = "修改应聘者信息"
      this.dialogFormVisible = true
      this.form = JSON.parse(JSON.stringify(row))
      this.timeline = JSON.parse(this.form.timeline)
    },
    save(){
      //更新或新增
      this.form.timeline = JSON.stringify(this.timeline)
        console.log(this.form)
        request.post("/trainingPlan/addOrUpdate",this.form).then(res => {
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
      request.delete("/trainingPlan/"+id).then(res => {
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
    addItem() {
      this.timeline.push({
        title: "",
        time: "",
        content:""
      });
    },
    deleteItem(item, index) {
      this.timeline.splice(index, 1);
      console.log(this.form.dynamicItem, "删除");
    },
  },
  data() {
    return {
      optionsValue: "wait",
      options: [{value: "",label: "请选择"},{value: "wait",label: "审核中"},{value: "success",label: "已通过"},{value: "error",label: "未通过"}],
      sexOptions: [{value: "M",label: "男"},{value: "F",label: "女"}],
      deptOptions: [],
      progressOptions: [{value: 0,label: "简历审核"},{value: 1,label: "笔试"},{value: 2,label: "面试"},{value: 3,label: "入职"},{value: 4,label: "完成入职"}],
      statusOptions: [{value: "wait",label: "审核中"},{value: "error",label: "未通过"}],
      educationOptions: [{value: "本科"},{value: "硕士"},{value: "博士"}],
      title: "",
      location: "",
      startTime: "",
      endTime: "",
      form: {},
      total: 10,
      currentPage: 1,
      pageSize: 5,
      dialogFormVisible: false,
      addOrUpdateTitle: "",
      tableData: [],
      timeline: [{
        title: "",
        time: "",
        content:"",
      }],
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