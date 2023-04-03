<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="学生姓名">
        <el-input v-model="dataVo.name" placeholder="请输入学生姓名" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="dataVo.status" clearable placeholder="请选择状态">
          <el-option value="0" label="未审核"/>
          <el-option value="1" label="审核通过"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
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

      <el-table-column prop="name" label="学生姓名" />
      <el-table-column prop="phone" label="联系电话" />
      <el-table-column prop="username" label="登录账号" />
      <el-table-column prop="secondarySchools" label="二级学院" />
      <el-table-column prop="grade" label="年级" />
      <el-table-column prop="status" label="审批状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="success">未审核</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="error">审核通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="200" align="center">
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
        <el-form-item label="学生姓名">
          <el-input v-model="formData.name" placeholder="请填写学生姓名"/>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="formData.phone" placeholder="请填写联系电话"/>
        </el-form-item>
        <el-form-item label="登录账号">
          <el-input v-model="formData.username" placeholder="请填写登录账号"/>
        </el-form-item>
        <el-form-item label="登录密码">
          <el-input v-model="formData.password" type="password" placeholder="请填写登录密码"/>
        </el-form-item>
        <el-form-item label="二级学院">
          <el-input v-model="formData.secondarySchools" placeholder="请填写二级学院"/>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="formData.grade" placeholder="请填写年级"/>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="formData.status" placeholder="请选择审核状态">
            <el-option label="未审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
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
import student from '@/api/student'

export default {
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      dialogVisible: false,
      formData: {},
      title: ''
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 调用teacher.js 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      student.pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          console.log(response)
          this.list = response.data.rows
          this.total = response.data.total
        })
    },

    // 修改和更新功能
    updateAndSave(row) {
      console.log(row)
      if (row == null) {
        this.title = '新增学生信息'
        this.formData = {}
      } else {
        this.title = '修改学生信息'
        this.formData = row
      }
      this.dialogVisible = true
    },

    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        student.update(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.getList()
          })
      } else {
        student.save(this.formData)
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
        student.deleteById(id)
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
