<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-row>
        <el-form-item label="联系人">
          <el-input v-model="dataVo.phone" placeholder="请输入联系人手机" />
        </el-form-item>
        <el-form-item label="报修设备">
          <el-select v-model="dataVo.equipmentId" clearable placeholder="请选择报修设备" >
            <el-option v-for="item in equipmentList" :value="item.id" :label="item.name"/>
          </el-select>
        </el-form-item>
        <el-form-item label="维修状态">
          <el-select v-model="dataVo.status" clearable placeholder="请选择维修状态">
            <el-option value="0" label="未处理"/>
            <el-option value="1" label="已处理"/>
          </el-select>
        </el-form-item>
        <el-form-item label="紧急状态">
          <el-select v-model="dataVo.level" clearable placeholder="请选择紧急状态">
            <el-option value="0" label="普通维修"/>
            <el-option value="1" label="紧急维修"/>
          </el-select>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">重置</el-button>
        <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
      </el-row>
    </el-form>
    <!-- 表格 -->
    <el-table

      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column prop="equipmentName" label="维修设备" />
      <el-table-column prop="name" label="联系人姓名" />
      <el-table-column prop="phone" label="联系人手机号" />
      <el-table-column prop="recordDesc" label="维修描述" />
      <el-table-column prop="address" label="维修地址" />
      <el-table-column prop="studentUsername" label="申请学生账号" />
      <el-table-column prop="orderType" label="申报级别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level == '0'" type="success">普通维修</el-tag>
          <el-tag v-if="scope.row.level == '1'" type="error">紧急维修</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="处理状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="success">未处理</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="error">已处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="申请时间" width="200px" />
      <el-table-column label="操作" align="center" width="180px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="联系人姓名">
          <el-input v-model="formData.name" placeholder="请填写联系人姓名"/>
        </el-form-item>
        <el-form-item label="联系人手机号">
          <el-input v-model="formData.phone" placeholder="请填写联系人手机号"/>
        </el-form-item>
        <el-form-item label="维修描述">
          <el-input  type="textarea" v-model="formData.recordDesc" placeholder="维修描述"/>
        </el-form-item>
        <el-form-item label="维修地址">
          <el-input v-model="formData.address" placeholder="请填写维修地址"/>
        </el-form-item>
        <el-form-item label="报修设备">
          <el-select v-model="formData.equipmentId" clearable placeholder="请选择报修设备" >
            <el-option v-for="item in equipmentList" :value="item.id" :label="item.name"/>
          </el-select>
        </el-form-item>
        <el-form-item label="报修账号">
          <el-select v-model="formData.studentId" clearable placeholder="请选择申请报修账号" >
            <el-option v-for="item in studentList" :value="item.id" :label="item.username"/>
          </el-select>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-select v-model="formData.status" placeholder="请选择审核状态">
            <el-option label="未处理" value="0"></el-option>
            <el-option label="已处理" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申报级别">
          <el-select v-model="formData.level" placeholder="请选择申报级别">
            <el-option label="普通维修" value="0"></el-option>
            <el-option label="紧急维修" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import repair from '@/api/repair'
import student from '@/api/student'
import equipment from '@/api/equipment'

export default {
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      formData: {},
      dialogVisible: false,
      name: '',
      title: '',
      studentList:[], // 存储学生列表
      equipmentList:[], // 存储设备列表
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getStudentAndEquipmentAll();
    this.getList()
  },
  methods: { // 创建具体的方法, 调用teacher.js 定义的方法

    /**
     * 查询学生列表 和 设备列表用于查询下拉框
     */
    getStudentAndEquipmentAll(){
      student.getAll().then(res=>{
        this.studentList = res.data.data;
      })

      equipment.getAll().then(res=>{
        this.equipmentList = res.data.data;
    })
    },
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      repair.pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records;
          this.total = response.data.rows.total
        })
    },

    // 修改和更新功能
    updateAndSave(row) {
      console.log(row)
      if (row == null) {
        this.title = '新增'
        this.formData = {}
      } else {
        this.title = '修改'
        this.formData = row
      }
      this.dialogVisible = true
    },

    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        repair.update(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.getList()
          })
      } else {
        repair.save(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '新增成功！'
            })
            this.getList()
          })
      }
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        repair.deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败！'
              })
            }
          })
      })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {}
      this.getList()
    }
  }
}
</script>
