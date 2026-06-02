<template>
    <div>
        <div class="main-content">
            <el-main>
                <div class="dashboard">
                    <!-- 原有仪表与列表内容保留 -->
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>区块数</h3>
                                    <p class="stat-number">24</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>植物总数</h3>
                                    <p class="stat-number">156</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>本月养护</h3>
                                    <p class="stat-number">32</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>本月种植</h3>
                                    <p class="stat-number">8</p>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>

                    <el-row :gutter="20" style="margin-top: 20px;">
                        <el-col :span="12">
                            <el-card class="bigbox">
                                <template #header>
                                    <div class="card-header"><span>近日活动列表</span></div>
                                </template>
                                <div>
                                    <div class="card-header11">种植</div>
                                    <div class="green">
                                        <ul>
                                            <li>xx在x区种植了x植物|2025-11-21</li>
                                            <li>xx在x区种植了x植物|2025-11-21</li>
                                            <li>xx在x区种植了x植物|2025-11-21</li>
                                            <li>xx在x区种植了x植物|2025-11-21</li>
                                            <li>xx在x区种植了x植物|2025-11-21</li>
                                        </ul>
                                    </div>
                                </div>
                                <div>
                                    <div class="card-header11">养护</div>
                                    <div class="green">
                                        <ul>
                                            <li>xx在x区为x植物进行了养护|2025-11-21</li>
                                            <li>xx在x区为x植物进行了养护|2025-11-21</li>
                                            <li>xx在x区为x植物进行了养护|2025-11-21</li>
                                            <li>xx在x区为x植物进行了养护|2025-11-21</li>
                                            <li>xx在x区为x植物进行了养护|2025-11-21</li>
                                        </ul>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="12">
                            <el-card class="smellbox">
                                <template #header>
                                    <div class="card-header"><span>种植/养护人</span></div>
                                </template>
                                <div class="chart-container">
                                    <!-- <p style="text-align: center; color: #999;">图表占位符</p> -->
                                    <div class="people-card">
                                        <div class="people-content">
                                            <div class="pie-placeholder">75%</div>
                                            <div>
                                                <div class="card-header1">
                                                    <p><span class="legend-dot plant"></span> 养护人（75%）</p>
                                                </div>
                                                <div class="card-header1">
                                                    <p><span class="legend-dot maintain"></span> 种植人（25%）</p>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </el-card>



                        </el-col>
                    </el-row>


                </div>
            </el-main>
        </div>
    </div>
</template>

<style scoped>
/* 主内容区：右侧滚动 */
.main-content {
    flex: 1;
    padding: 18px;
    height: 100%;
    overflow: auto;
    box-sizing: border-box;
    background-color: #F3F4F6 !important;
}

.dashboard {
    padding: 0;
}

.stat-card {
    text-align: center;
    border-radius: 8px;
    box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06);
    padding: 50px 5px;
}

.card-header {
    color: #7f8b8a;
    font-size: 24px;
    font-weight: 700;
}

.card-header11 {
    color: #006B3E;
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 20px;
}

.card-header1 {
    color: #006B3E;
    font-size: 23px;
    font-weight: 700;
    margin-bottom: 5px;
    
}

/* 修复宽度导致列重叠：使用栅格列宽，不超出容器 */
.bigbox {
    width: 100%;
    max-width: 100%;
    box-sizing: border-box;
}

.smellbox {
    width: 100%;
    max-width: 100%;
    box-sizing: border-box;
    margin-left: 0;
    height: 100%;
}

.green {
    background-color: rgba(240, 250, 244, 0.81);
    padding: 8px;
    /* 增大内边距让内容不贴边 */
    border-radius: 8px;
    margin-bottom: 16px;
    box-sizing: border-box;
}

/* 更高优先级，覆盖 Element Plus 的样式 */
.el-card .green ul,
.green ul {
    margin: 0;
    /* 去掉浏览器默认外边距 */
    padding-left: 20px;
    /* 保留缩进 */
    font-size: 18px;
    color: #3D3D3D;
    line-height: 1.6;
    /* 使用相对行高更稳定 */
}

/* li 样式 */
.el-card .green ul li,
.green ul li {
    margin: 6px;
}

/* 如果仍被覆盖，可临时使用 !important（不推荐长期使用） */
/* .el-card .green ul { font-size:18px !important; } */

.stat-content h3 {
    color: #7f8b8a;
    font-size: 24px;
    margin-bottom: 8px;
    font-weight: 700;
}

.stat-number {
    font-size: 24px;
    font-weight: 700;
    color: #0C642B;
}

.el-card {
    border-radius: 8px;
}

@media (max-width: 900px) {
    .el-aside {
        width: 72px;
        padding: 10px;
    }

    .menu-text {
        display: none;
    }

    .sidebar-top .brand {
        display: none;
    }

    .top-title {
        font-size: 38px;
        font-weight: 700;
    }

    .content-wrapper {
        margin-top: 64px;
    }
}

.people-card {
    border-radius: 8px;
    box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06);
    padding: 16px;
    background-color: #fff;
    margin-bottom: 20px;
}

.people-card .people-content {
    display: flex;
    align-items: center;
    gap: 18px;
    padding: 8px 4px;
}

.people-card .pie-placeholder {
    width: 300px;
    height: 300px;
    border-radius: 50%;
    /* 圆环示意：实际可用图表库替换 */
    background: conic-gradient(#0C642B 0 75%, #e6f0e9 0 100%);
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 700;
    color: #ffffff;
    font-size: 40px;
}

.people-legend {
    color: #3D3D3D;
    font-size: 14px;
}

.people-legend p {
    margin: 6px 0;
    display: flex;
    align-items: center;
    gap: 10px;
}

.legend-dot {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    display: inline-block;
}

.legend-dot.plant {
    background: #0C642B;
}

.legend-dot.maintain {
    background: #8ec18b;
}
</style>