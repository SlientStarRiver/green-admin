<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">用户信息管理</div>
            </div>

            <div class="table-card">
                <el-table :data="pagedData" stripe border style="width:100%" :empty-text="'暂无数据'">
                    <!-- 移除固定宽度，让列平分 -->
                    <el-table-column prop="id" label="用户ID" />
                    <el-table-column prop="name" label="用户名" />
                    <el-table-column prop="phone" label="手机号" />
                    <el-table-column prop="created" label="注册时间" />
                    <el-table-column prop="maintainCount" label="养护次数" />
                    <el-table-column prop="plantCount" label="种植次数" />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
                        :current-page.sync="currentPage" @current-change="handlePageChange" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'UserBox',
    data() {
        return {
            users: [
                { id: '040646', name: '王明', phone: '193****0816', created: '2025-08-03', maintainCount: 5, plantCount: 0 },
                { id: '040647', name: '李梅', phone: '188****1111', created: '2025-07-20', maintainCount: 3, plantCount: 2 },
                { id: '040648', name: '张强', phone: '136****2222', created: '2025-06-11', maintainCount: 4, plantCount: 1 },
                { id: '040649', name: '赵丽', phone: '139****3333', created: '2025-05-30', maintainCount: 2, plantCount: 0 },
                { id: '040650', name: '孙鹏', phone: '150****4444', created: '2025-05-20', maintainCount: 6, plantCount: 2 },
                { id: '040651', name: '周健', phone: '131****5555', created: '2025-04-12', maintainCount: 1, plantCount: 0 },
                { id: '040652', name: '吴敏', phone: '135****6666', created: '2025-03-08', maintainCount: 5, plantCount: 1 }
            ],
            currentPage: 1,
            pageSize: 6
        }
    },
    computed: {
        total() {
            return this.users.length
        },
        pagedData() {
            const start = (this.currentPage - 1) * this.pageSize
            return this.users.slice(start, start + this.pageSize)
        }
    },
    methods: {
        handlePageChange(page) {
            this.currentPage = page
        }
    }
}
</script>

<style scoped>
.main-content {
    flex: 1;
    padding: 18px;
    height: 100%;
    overflow: auto;
    box-sizing: border-box;
    background-color: #F3F4F6 !important;
}

.main-inner {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.page-header {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 8px;
}

.title {
    font-size: 22px;
    font-weight: 700;
    color: #2b6b48;
}

/* 表格卡片样式与 homebox 保持一致 */
.table-card {
    margin-top: 8px;
    padding: 12px;
    background: #fff;
    border-radius: 8px;
    box-sizing: border-box;
    width: 100%;
}

/* 让表格撑满容器并均分列宽 */
.main-content ::v-deep .el-table {
    width: 100% !important;
    table-layout: fixed !important; /* 平分可用列宽 */
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

/* 去掉表格右侧空白（element-ui 有时在固定列下产生滚动空白） */
.main-content ::v-deep .el-table__body-wrapper {
    overflow: auto !important;
}

/* 分页对齐 */
.table-footer {
    display: flex;
    justify-content: center;
    padding-top: 12px;
}

/* 响应式 */
@media (max-width: 900px) {
    .table-card {
        padding: 10px;
    }

    .title {
        font-size: 18px;
    }
}
</style>