<template>
  <div style="padding: 30px">
    <div class="add" >
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div>
      <el-input v-model="pno" placeholder="请输入培训编号" class="input" style="width: 10%;margin-right: 10px"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
    </div>
    <div>
      <el-table :data="tableData" class="table" stripe style="width: calc(100% - 30px);border: solid 1px #cccccc;margin-top: 20px">
        <el-table-column prop="pno" label="编号" align="center" sortable ></el-table-column>
        <el-table-column prop="title" label="培训标题" ></el-table-column>
        <el-table-column label="培训文件" >
          <template #default="scope" >
              <table v-for="(item,index) in JSON.parse(scope.row.files)">
                <tr>
                  <td width="200px">{{item.name}}</td>
                  <td><el-button type="primary" size="small" @click="download(item.url)">下载</el-button></td>
                </tr>
              </table>
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

      <el-dialog v-model:title="addOrUpdateTitle" v-model="dialogFormVisible" width="30%" :show-close=false :close-on-click-modal=false>
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="培训编号">
            <el-input v-model="form.pno" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="培训文件">
            <el-upload
                    action="http://localhost:9090/trainingFiles/upload"
                    :file-list="files"
                    :on-success="uploadSuccessFunction"
                    :on-error="uploadErrorFunction"
                    :on-remove="removeFileFunction"
                    :limit="fileNum"
                    :on-exceed="fileNumOutFunction"
            >
              <el-button size="small" type="primary">点击上传</el-button>
              <template #tip>
                <div class="el-upload__tip"></div>
              </template>
            </el-upload>
          </el-form-item>

        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
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
        request.get("/trainingFiles/queryPage",{params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            pNo: this.pno,
          }}).then(res => {
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })

      },
      add(){
        this.addOrUpadteTitle = "新增培训文件信息"
        this.dialogFormVisible = true
        this.form = {}
        this.files = []
        this.deleteFiles = []
        this.tempFiles = []
      },
      update(row){
        this.addOrUpadteTitle = "修改培训文件信息"
        this.dialogFormVisible = true
        this.form = JSON.parse(JSON.stringify(row))
        this.files = JSON.parse(this.form.files)
        this.deleteFiles = []
        this.tempFiles = []
      },

      //更新或新增
      save(){
        this.form.files = JSON.stringify(this.files)
        console.log(this.form)
        request.post("/trainingFiles/addOrUpdate", this.form).then(res => {
          console.log(res)
          if (res.code === "200") {
            ElMessage.success({
              message: res.code + ": " + res.msg,
              type: 'success',
              duration: 3000
            })
            this.dialogFormVisible = false
          } else {
            ElMessage.error({
              message: res.code + ": " + res.msg,
              type: 'error',
              duration: 3000
            })
          }
          this.load()
        })

        //根据deleteFiles中的文件名，删除服务器中对应的资源文件
        request.post("/trainingFiles/deleteFile",this.deleteFiles).then(res => {
          console.log("deleteSuccess...")
        })
      },

      //取消新增或更新，删除临时上传的文件
      cancel(){
        this.dialogFormVisible = false
        //根据tempFiles中的文件名，删除服务器中对应的资源文件
        request.post("/trainingFiles/deleteFile",this.tempFiles).then(res => {
          console.log("deleteSuccess...")
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
        request.delete("/trainingFiles/deleteById?id="+id).then(res => {
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

      //上传成功的钩子，同时向files添加一个对象
      uploadSuccessFunction(response,file,fileList){
        this.files.push({
          name: file.name,
          url: response.msg
        })
        //把文件名拼接出来放入临时文件数组中，如果点击了取消按钮，则删除服务器中对应的资源
        this.tempFiles.push(response.msg.substring(response.msg.lastIndexOf("/") + 1) + "_" + file.name)
      },

      //上传失败的钩子
      uploadErrorFunction(){
        ElMessage.error({
          message: "文件上传失败...",
          type: 'error',
          duration: 3000
        })
      },

      //移除文件时的钩子，同时删除files列表对应的对象
      removeFileFunction(file,fileList){
        //删除files列表对应的对象
        this.files.splice(this.files.findIndex(i => i.url === file.url),1)

        //将移除的文件 拼接源文件名称， 放入deleteFiles中，确认更新后在save方法中执行删除资源文件
        this.deleteFiles.push(file.url.substring(file.url.lastIndexOf("/") + 1) + "_" + file.name)
      },

      //下载文件方法
      download(url){
        console.log(url)
        window.location.href = url
      },

      //文件数量超出限制的钩子
      fileNumOutFunction(){
        ElMessage.error({
          message: "最多可上传文件数量: "+this.fileNum,
          type: 'error',
          duration: 3000
        })
      }


    },
    data() {
      return {
        pno: "",
        form: {},
        total: 10,
        currentPage: 1,
        pageSize: 5,
        dialogFormVisible: false,
        addOrUpdateTitle: "",
        tableData: [],
        fileNum: 5,
        files: [],
        tempFiles: [],
        deleteFiles: [],
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