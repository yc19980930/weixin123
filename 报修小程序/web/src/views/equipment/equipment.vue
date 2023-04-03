<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="设备名称">
        <el-input v-model="dataVo.name" placeholder="请输入设备名称"/>
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
      <el-table-column prop="name" label="设备名称"/>
      <el-table-column prop="equipmentDesc" label="设备描述"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column label="操作" align="center">
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
        <el-form-item label="设备名称">
          <el-input v-model="formData.name" placeholder="请填写设备名称"/>
        </el-form-item>
        <el-form-item label="设备描述">
          <el-input v-model="formData.equipmentDesc" placeholder="请填写设备描述"/>
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
      equipment.pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
        this.list = response.data.rows
      this.total = response.data.total
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
        equipment.update(this.formData)
          .then(response => { // 请求成功
          this.$message({
          type: 'success',
          message: '修改成功！'
        })
        this.getList()
      })
      } else {
        equipment.save(this.formData)
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
        equipment.deleteById(id)
        .then(response => { // 删除成功执行的方法
        if(response.success
    )
      {
        this.$message({
          type: 'success',
          message: '删除成功！'
        })
        // 刷新表格
        this.getList()
      }
    else
      {
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
