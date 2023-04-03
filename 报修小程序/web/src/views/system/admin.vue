<template>
  <div class="app-container">
    <div class="role-menu-box">
      <el-row :gutter="30">
        <el-col :span="13">
          <el-form :inline="true" class="demo-form-inline">
            <el-row>
              <el-form-item label="姓名">
                <el-input v-model="dataVo.name" placeholder="请输入维修管理员姓名" />
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="dataVo.status" clearable placeholder="请选择账号状态">
                  <el-option value="0" label="禁用"/>
                  <el-option value="1" label="正常"/>
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
            @row-click="rowClick"
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

            <el-table-column prop="name" label="管理员姓名" />
            <el-table-column prop="phone" label="联系电话" />
            <el-table-column prop="username" label="登录账号" />
            <el-table-column prop="status" label="账号状态">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status == '0'" type="success">禁用</el-tag>
                <el-tag v-if="scope.row.status == '1'" type="error">正常</el-tag>
              </template>
            </el-table-column>
            <!--<el-table-column prop="createTime" label="创建时间" />-->
            <el-table-column label="操作" align="center" width="200px">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <el-pagination
            :current-page="page"
            style="padding: 30px 0; text-align: center;"
            :page-size="limit"
            layout="total, prev, pager, next, jumper"
            :total="total"
            @current-change="getList"
          />
        </el-col>
        <el-col :span="11">

          <el-card class="box-card" shadow="never" style="margin-top: 61px">
            <div slot="header">
              <span>分配管理设备</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="saveEquipment">保存权限</el-button>
            </div>
            <el-scrollbar style="width: 300px">
              <el-checkbox-group v-model="checkList">
                <el-row v-for="item in equipmentList">
                  <el-checkbox :label="item.id">{{item.name}}</el-checkbox>
                </el-row>
              </el-checkbox-group>
            </el-scrollbar>
          </el-card>
        </el-col>
      </el-row>
    </div>


    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="管理员姓名">
          <el-input v-model="formData.name" placeholder="请填写维修管理员姓名"/>
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
        <el-form-item label="账号状态">
          <el-select v-model="formData.status" placeholder="请选择账号状态">
            <el-option label="禁用" value="0"></el-option>
            <el-option label="正常" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>
<script>
  import admin from '@/api/admin'
  import equipment from '@/api/equipment'
  export default {
    data() { // 定义变量和初始值
      return {
        checkList: [],
        list: null, // 查询之后接口返回集合
        page: 1, // 当前页
        limit: 9, // 每页记录数
        total: 100, // 总页数
        dataVo: {}, // 条件封装对象
        dialogVisible: false,
        formData: {},
        title: '',
        equipmentList:[], // 存储所有设备
        clickId:'', // 存放某行数据的id
      }
    },
    created() { // 页面渲染之前执行，一般调用method定义方法
      this.getList()

      this.getEquipmentAll();
    },
    methods: { // 创建具体的方法
      // 查询
      getList(page = 1) {
        this.page = page // 获取用户点击的页码赋值
        admin.pageQuery(this.page, this.limit, this.dataVo)
          .then(response => { // 请求成功
            this.list = response.data.rows
            this.total = response.data.total
          })
      },

      // 更新关联设备信息
      saveEquipment(){
        if(this.clickId == null || this.clickId == undefined || this.clickId == ''){
          this.$message({
            type: 'error',
            message: '管理员id不存在'
          });
          return false;
        }
        // 执行更新关联设备
        admin.updateAdminEquipment(this.clickId, this.checkList).then(res=>{
          this.$message({
            type: 'success',
            message: '保存成功！'
          })
        })
      },

      // 获取所有设备信息
      getEquipmentAll(){
        equipment.getAll().then(res=>{
          console.log(res)
          this.equipmentList = res.data.data;
        });

      },

      // 获取点击事件某行数据
      rowClick(row, column, event){
        this.clickId = row.id;
        // 查询当前id的绑定设备
        admin.getEquipmentIdByAdminId(this.clickId).then(res=>{
          this.checkList = res.data.data;
        })
      },

      // 修改和更新功能
      updateAndSave(row) {
        console.log(row)
        if (row == null) {
          this.title = '新增维修管理员'
          this.formData = {}
        } else {
          this.title = '修改维修管理员'
          this.formData = row
        }
        this.dialogVisible = true
      },

      // 确认按钮
      handleBtn() {
        // 关闭弹窗
        this.dialogVisible = false
        if (this.formData.id) {
          admin.update(this.formData)
            .then(response => { // 请求成功
              this.$message({
                type: 'success',
                message: '修改成功！'
              })
              this.getList()
            })
        } else {
          admin.save(this.formData)
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
          admin.deleteById(id)
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

<style lang="scss">
  .avatar {
    width: 110px;
    height: 110px;
    display: block;
  }
  .role-menu-box {
    height: calc(100% - 100px);
  }
  .el-row, .el-col {
    height: 100%;
  }
</style>
