<template>
    <div class="main-content">
        <!-- 替换 el-main 为 div，保持布局与样式 -->
        <div class="main-inner">
            <div class="page-header">
                <div class="title">植物管理</div>
                <el-button type="success" class="btn-new" @click="handleAdd">新增植物</el-button>
            </div>


            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="植物名称">
                        <el-input v-model="filters.name" placeholder="输入名称查询" clearable />
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="applySearch">查询</el-button>
                        <el-button @click="resetSearch">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="table-card" style="margin-top: 16px;">


                <el-table :data="pagedData" stripe border style="width: 100%;" :empty-text="'暂无数据'">
                    <el-table-column prop="code" label="品种编号" width="160" />
                    <el-table-column prop="name" label="品种名" />
                    <el-table-column prop="people" label="负责人" width="120" />
                    <el-table-column prop="time" label="种植时间" />
                    <el-table-column prop="num" label="株数" />
                    <el-table-column prop="qukuaihao" label="区块号" />
                    <el-table-column label="操作" width="160" fixed="right">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" icon="el-icon-edit"
                                @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"
                                style="margin-left:8px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
                        :current-page="currentPage" @current-change="handlePageChange" />
                </div>

                <div v-else class="no-data-tip" style="margin-top:20px; text-align:center; color:#999;">
                    无匹配结果
                </div>
            </div>

            <!-- 新建 / 编辑 弹窗：改为图中表单布局（移除右上返回按钮） -->
            <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="720px" :close-on-click-modal="false">
                <el-form ref="speciesForm" :model="editForm" :rules="rules" label-width="110px" class="dialog-form">
                    <el-form-item label="* 品种号：" prop="code">
                        <el-input v-model="editForm.code" placeholder="请输入品种号" />
                    </el-form-item>

                    <el-form-item label="* 品种名：" prop="name">
                        <el-input v-model="editForm.name" placeholder="请输入品种名" />
                    </el-form-item>

                    <el-form-item label="* 负责人：" prop="people">
                        <el-input v-model="editForm.people" placeholder="请输入负责人" />
                    </el-form-item>

                    <el-form-item label="* 种植时间：" prop="time">
                        <el-date-picker v-model="editForm.time" type="date" placeholder="请选择日期"
                            style="width:100%"></el-date-picker>
                    </el-form-item>

                    <el-form-item label="* 株树：" prop="num">
                        <el-input-number v-model="editForm.num" :min="0" style="width:140px" />
                    </el-form-item>

                    <el-form-item label="* 所在区块号：" prop="qukuaihao">
                        <el-select v-model="editForm.qukuaihao" placeholder="请选择区块号" filterable>
                            <el-option v-for="opt in blockOptions" :key="opt" :label="opt" :value="opt" />
                        </el-select>
                    </el-form-item>

                    <el-form-item label="操作描述：">
                        <el-input type="textarea" v-model="editForm.note" rows="4" placeholder="请输入操作描述"></el-input>
                    </el-form-item>
                </el-form>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmSave">保存</el-button>
                </div>
            </el-dialog>
        </div> <!-- /.main-inner -->
    </div>
</template>

<script>
export default {
    name: 'PlantsBox',
    data() {
        return {
            filters: {
                name: ''
            },
            // 示意数据，实际应从 API 加载
            blocks: [
                { id: 1, code: 'qk01', name: '黄杨', people: '白白', time: '2004',num: '200', qukuaihao: 'A01' },
                { id: 2, code: 'qk02', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' },
                { id: 3, code: 'qk03', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' },
                { id: 4, code: 'qk04', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' },
                { id: 5, code: 'qk05', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' },
                { id: 6, code: 'qk06', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' },
                { id: 7, code: 'qk07', name: '黄杨', people: '白白', time: '2004' ,num: '200', qukuaihao: 'A01' }
                
                
            ],
            // 分页
            currentPage: 1,
            pageSize: 5,
            // 编辑弹窗
            dialogVisible: false,
            editForm: { id: null, code: '', name: '', area: null, location: '' }
        }
    },
    computed: {
        filtered() {
            const name = (this.filters.name || '').trim().toLowerCase()
            if (!name) return this.blocks
            return this.blocks.filter(b => (b.name || '').toLowerCase().includes(name) || (b.code || '').toLowerCase().includes(name))
        },
        total() {
            return this.filtered.length
        },
        pagedData() {
            const start = (this.currentPage - 1) * this.pageSize
            return this.filtered.slice(start, start + this.pageSize)
        },
        dialogTitle() {
            return this.editForm && this.editForm.id ? '植物管理|编辑植物' : '植物管理|新增植物'
        }
    },
    methods: {
        applySearch() {
            this.currentPage = 1
        },
        resetSearch() {
            this.filters.name = ''
            this.currentPage = 1
        },
        handlePageChange(page) {
            this.currentPage = page
        },
        handleAdd() {
            this.editForm = { id: null, code: '', name: '', area: null, location: '' }
            this.dialogVisible = true
        },
        handleEdit(row) {
            this.editForm = Object.assign({}, row)
            this.dialogVisible = true
        },
        handleDelete(row) {
            this.$confirm(`确定删除区块 “${row.name}” 吗？`, '删除确认', {
                confirmButtonText: '删除',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.blocks = this.blocks.filter(b => b.id !== row.id)
                this.$message({ type: 'success', message: '已删除' })
                if ((this.currentPage - 1) * this.pageSize >= this.total && this.currentPage > 1) {
                    this.currentPage--
                }
            }).catch(() => { })
        },
        saveItem() {
            if (this.editForm.id) {
                const idx = this.blocks.findIndex(b => b.id === this.editForm.id)
                if (idx !== -1) this.blocks.splice(idx, 1, Object.assign({}, this.editForm))
                this.$message.success('更新成功')
            } else {
                const nextId = this.blocks.length ? Math.max(...this.blocks.map(b => b.id)) + 1 : 1
                const newItem = Object.assign({ id: nextId }, this.editForm)
                this.blocks.unshift(newItem)
                this.$message.success('创建成功')
            }
            this.dialogVisible = false
        }
    }
}
</script>

<style scoped>
/* 在 style 标签中添加（如果用了 el-container，也要给容器加高度） */
html,
body {
    height: 100%;
    margin: 0;
    /* 清除默认边距 */
}

/* 如果外层有 el-container，补充： */
.el-container {
    height: 100%;
}



.main-content {
    flex: 1;
    padding: 18px;
    height: 100%;
    overflow: auto;
    box-sizing: border-box;
    /* background-color: #F3F4F6 !important; */
}


.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
}

.title {
    font-size: 22px;
    font-weight: 700;
    color: #2b6b48;
}

.btn-new {
    border-radius: 6px;
    background-color: #0C642B;
}

.search-card {
    padding: 14px;
    background: #fff;
    border-radius: 8px;
}

.search-form .el-form-item {
    margin-right: 12px;
}

/* 修复表格 body 被压扁为 0 的问题 */
.table-card {
    /* 恢复内边距，防止 el-table 距离边缘为 0 导致高度计算异常 */
    padding: 12px !important;
    display: block;
}

/* 穿透 element-ui 组件，保证表格 body 可见并有合理高度 */
.main-content ::v-deep .el-table {
    width: 100%;
    table-layout: auto;
    /* 或 fixed，按需选择 */
}

/* 关键：确保 body-wrapper 不为 0，高度自适应并可滚动 */
.main-content ::v-deep .el-table__body-wrapper {
    max-height: none !important;
    min-height: 180px !important;
    /* 根据需要调整最小高度 */
    overflow: auto !important;
}

/* 恢复每行可见性与行高 */
.main-content ::v-deep .el-table__row {
    display: table-row !important;
    height: 48px !important;
    visibility: visible !important;
}

/* 单元格文字与内边距保证显示 */
.main-content ::v-deep .el-table td,
.main-content ::v-deep .el-table th,
.main-content ::v-deep .el-table .cell {
    display: table-cell !important;
    color: #333 !important;
    font-size: 14px !important;
    line-height: 1.6 !important;
    opacity: 1 !important;
    visibility: visible !important;
    transform: none !important;
    white-space: nowrap !important;
    text-overflow: ellipsis !important;
    overflow: hidden !important;
}

/* 单元格样式 */
.main-content ::v-deep .el-table th,
.main-content ::v-deep .el-table td,
.main-content ::v-deep .el-table .cell {
    padding: 12px 16px !important;
    color: #333 !important;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    height: 48px;
    line-height: 24px;

    /* 横向居中 */
    text-align: center !important;
}

/* 使用 flex 保证单元格内部内容（如按钮、图标等）也居中 */
.main-content ::v-deep .el-table th .cell,
.main-content ::v-deep .el-table td .cell {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}

/* 额外保障：为表格行恢复显示（避免被其他样式隐藏） */
.main-content ::v-deep .el-table__row {
    display: table-row !important;
    height: auto !important;
    visibility: visible !important;
}

/* 若使用固定列（fixed="right"），确保固定列区域不遮挡主体 */
.main-content ::v-deep .el-table__fixed,
.main-content ::v-deep .el-table__fixed-right {
    background: transparent !important;
}

.table-tittle {
    width: 100%;
}

.table-tit {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-around;
    margin-right: 20%;
    font-size: 24px;
    font-weight: 700;
    color: #6B7280;
}

.table-footer {
    display: flex;
    justify-content: center;
    padding-top: 14px;
}

.no-data-tip {
    padding: 30px 0;
}

.dialog-footer {
    text-align: right;
}

/* 强制让 el-table 内部可见（scoped 时穿透） */
.main-content ::v-deep .el-table {
    color: #333 !important;
}

.main-content ::v-deep .el-table td,
.main-content ::v-deep .el-table th {
    color: #333 !important;
    line-height: 1.6 !important;
    height: auto !important;
}

.main-content ::v-deep .el-table .el-table__row {
    display: table-row !important;
}
</style>