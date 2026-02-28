<template>
  <div class="detail-container" v-loading="loading">
    <!-- 详情内容 -->
      <div class="detail-content" v-if="scenicInfo">
        <!-- 头部图片 -->
        <div class="detail-header">
          <image-preview :src="scenicInfo.image" :width="'100%'" :height="800"/>
        </div>

        <!-- 基本信息 -->
        <div class="detail-info">
          <div class="info-header">
            <h1 class="scenic-name">{{ scenicInfo.name }}</h1>
            <div class="action-buttons">
              <el-button
                :type="isLike ? 'danger' : 'default'"
                :icon="isLike ? 'el-icon-star-on' : 'el-icon-star-off'"
                round
                @click="handleLike"
              >
                {{ isLike ? '已点赞' : '点赞' }}
              </el-button>
            </div>
          </div>

          <!-- 统计数据 -->
          <div class="stats-row">
            <div class="stat-item">
              <i class="el-icon-view"></i>
              <span>浏览 {{ scenicInfo.looksNumber || 0 }}</span>
            </div>
            <div class="stat-item">
              <i class="el-icon-star-on"></i>
              <span>点赞 {{ scenicInfo.likesNumber || 0 }}</span>
            </div>
            <div class="stat-item">
              <i class="el-icon-chat-dot-round"></i>
              <span>评论 {{ scenicInfo.commentsNumber || 0 }}</span>
            </div>
          </div>

          <!-- 描述内容 -->
          <div class="describe-section">
            <h3>景区介绍</h3>
            <div class="describe-content" v-html="scenicInfo.describe"></div>
          </div>

          <!-- 底部信息 -->
          <div class="footer-info">
            <span>创建时间：{{ parseTime(scenicInfo.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            <span v-if="scenicInfo.updateTime">更新时间：{{ parseTime(scenicInfo.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <el-empty v-if="!loading && !scenicInfo" description="暂无数据"></el-empty>
  </div>
</template>

<script>
import {getScenicInfoDetail} from "@/api/manage/scenicInfo";

export default {
  name: "ScenicDetail",
  data() {
    return {
      // 详情数据
      scenicInfo: null,
      // 加载状态
      loading: false,
      // 是否点赞
      isLike: false
    };
  },
  created() {
    this.getDetail();
  },
  methods: {
    /** 获取详情 */
    getDetail() {
      this.loading = true;
      const id = this.$route.query.id;
      if (!id) {
        this.loading = false;
        return;
      }
      getScenicInfoDetail(id).then(response => {
        this.scenicInfo = response.data;
        // 后台返回的 isLike 字段
        this.isLike = response.data.isLike || false;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 点赞/取消点赞 */
    handleLike() {
      this.isLike = !this.isLike;
      if (this.scenicInfo) {
        this.scenicInfo.likesNumber = (this.scenicInfo.likesNumber || 0) + (this.isLike ? 1 : -1);
      }
      console.log('点赞状态:', this.isLike);
    },
    /** 时间格式化 */
    parseTime(time, pattern) {
      if (!time) return '';
      const date = new Date(time);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>

<style scoped lang="scss">
.detail-container {
  padding: 20px;
  width: 90%;
  margin: 0 auto;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.detail-header {
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
}

.detail-info {
  padding: 0 10px;
}

.info-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  .scenic-name {
    margin: 0;
    font-size: 24px;
    font-weight: 600;
    color: #303133;
  }
}

.stats-row {
  display: flex;
  gap: 30px;
  padding: 15px 0;
  border-top: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 20px;

  .stat-item {
    display: flex;
    align-items: center;
    color: #606266;
    font-size: 14px;

    i {
      margin-right: 6px;
      font-size: 18px;
    }
  }
}

.describe-section {
  margin-bottom: 20px;

  h3 {
    margin: 0 0 15px 0;
    font-size: 18px;
    font-weight: 600;
    color: #303133;
  }

  .describe-content {
    ::v-deep img {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
      margin: 20px 0;
    }
    color: #606266;
    line-height: 1.8;
    font-size: 14px;
  }
}

.footer-info {
  display: flex;
  justify-content: space-between;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
  color: #909399;
  font-size: 13px;
}

// 响应式
@media (max-width: 768px) {
  .info-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;

    .scenic-name {
      font-size: 20px;
    }
  }

  .stats-row {
    flex-wrap: wrap;
    gap: 15px;
  }

  .footer-info {
    flex-direction: column;
    gap: 8px;
  }
}
</style>
