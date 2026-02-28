<template>
  <div class="scenic-card-container" @scroll="handleScroll">
    <div class="page-header">
      <h1 class="page-title">{{ title }}</h1>
    </div>
    <div class="scenic-card-wrapper">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6" v-for="item in scenicInfoList" :key="item.id">
          <el-card class="scenic-card" shadow="hover">
            <div class="card-image">
              <image-preview :src="item.image" :width="'100%'" :height="180"/>
            </div>
            <div class="card-content">
              <h3 class="card-title">{{ item.name }}</h3>
              <div class="card-stats">
                <span class="stat-item">
                  <i class="el-icon-view"></i>
                  {{ item.looksNumber || 0 }}
                </span>
                <span class="stat-item">
                  <i class="el-icon-star-on"></i>
                  {{ item.likesNumber || 0 }}
                </span>
                <span class="stat-item">
                  <i class="el-icon-chat-dot-round"></i>
                  {{ item.commentsNumber || 0 }}
                </span>
              </div>
              <div class="card-footer">
                <span class="create-time">{{ parseTime(item.createTime, '{y}-{m}-{d}') }}</span>
                <el-button type="text" size="small" @click="handleDetail(item)">查看详情</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 加载状态 -->
      <div class="loading-wrapper" v-if="loading">
        <i class="el-icon-loading"></i>
        <span>加载中...</span>
      </div>

      <!-- 没有更多数据 -->
      <div class="no-more" v-if="noMore">
        <span>没有更多了</span>
      </div>

      <!-- 空状态 -->
      <el-empty v-if="!loading && scenicInfoList.length === 0" description="暂无景区数据"></el-empty>
    </div>
  </div>
</template>

<script>
import {listScenicInfo} from "@/api/manage/scenicInfo";

export default {
  name: "Index",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      // 景区列表数据
      scenicInfoList: [],
      // 加载状态
      loading: false,
      // 是否还有更多数据
      noMore: false,
      // 分页参数
      queryParams: {
        pageNum: 1,
        pageSize: 12
      },
      // 总数据条数
      total: 0
    };
  },
  created() {
    this.getList();
    // 监听窗口滚动事件
    window.addEventListener('scroll', this.handleScroll);
  },
  destroyed() {
    // 移除滚动监听
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    /** 查询景区信息列表 */
    getList() {
      this.loading = true;
      listScenicInfo(this.queryParams).then(response => {
        const newRows = response.rows || [];
        // 追加新数据
        this.scenicInfoList = [...this.scenicInfoList, ...newRows];
        this.total = response.total;

        // 判断是否还有更多数据
        if (this.scenicInfoList.length >= this.total) {
          this.noMore = true;
        }

        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 加载更多 */
    loadMore() {
      if (this.loading || this.noMore) return;
      this.queryParams.pageNum++;
      this.getList();
    },
    /** 滚动监听 - 底部自动加载更多 */
    handleScroll() {
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
      const clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
      
      // 当滚动到距离底部 100px 时触发加载
      if (scrollTop + clientHeight >= scrollHeight - 100) {
        this.loadMore();
      }
    },
    /** 查看详情 */
    handleDetail(row) {
      console.log('查看详情:', row);
      // TODO: 跳转详情页或打开详情弹窗
    },
    /** 时间格式化 */
    parseTime(time, pattern) {
      if (!time) return '';
      const date = new Date(time);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');

      if (pattern === '{y}-{m}-{d}') {
        return `${year}-${month}-${day}`;
      }
      return `${year}-${month}-${day}`;
    }
  }
};
</script>

<style scoped lang="scss">
.scenic-card-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.page-header {
  text-align: center;
  margin-bottom: 20px;

  .page-title {
    font-size: 48px;
    font-weight: 600;
    color: #0afd00;
    margin: 0;
  }
}

.scenic-card-wrapper {
  max-width: 1600px;
  margin: 0 auto;
}

.scenic-card {
  margin-bottom: 20px;
  transition: all 0.3s ease;
  border-radius: 8px;
  overflow: hidden;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
  }
}

.card-image {
  position: relative;
  width: 100%;
  height: 180px;
  overflow: hidden;
}

.card-content {
  padding: 15px;
}

.card-title {
  margin: 0 0 10px 0;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.card-stats {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-top: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 10px;

  .stat-item {
    display: flex;
    align-items: center;
    font-size: 13px;
    color: #606266;

    i {
      margin-right: 4px;
      font-size: 14px;
    }

    &:hover {
      color: #409eff;
      cursor: pointer;
    }
  }
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .create-time {
    font-size: 12px;
    color: #c0c4cc;
  }
}

.loading-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  color: #909399;

  i {
    margin-right: 8px;
    font-size: 18px;
  }
}

.no-more {
  text-align: center;
  padding: 20px;
  color: #c0c4cc;
  font-size: 14px;
}

// 响应式调整
@media (max-width: 768px) {
  .scenic-card-container {
    padding: 10px;
  }

  .card-image {
    height: 150px;
  }

  .card-title {
    font-size: 14px;
  }
}
</style>
