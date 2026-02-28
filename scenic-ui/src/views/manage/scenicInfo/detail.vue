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
          <div class="action-buttons" v-hasPermi="['manage:likesInfo:add']">
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
          <span v-if="scenicInfo.updateTime">更新时间：{{
              parseTime(scenicInfo.updateTime, '{y}-{m}-{d} {h}:{i}:{s}')
            }}</span>
        </div>

        <!-- 评论区域 -->
        <div class="comment-section" v-hasPermi="['manage:evaluateInfo:add']">
          <h3 class="comment-title">评论</h3>

          <!-- 评论列表 -->
          <div class="comment-list" v-loading="commentLoading">
            <div v-if="commentList.length === 0" class="no-comment">
              暂无评论，快来抢沙发吧~
            </div>
            <div v-else class="comment-item" v-for="comment in commentList" :key="comment.id">
              <div class="comment-header">
                <span class="comment-user">{{ comment.userName || '匿名用户' }}</span>
                <span class="comment-time">{{ parseTime(comment.createTime, '{y}-{m}-{d}') }}</span>
              </div>
              <div class="comment-content">{{ comment.content }}</div>
              <div class="comment-score" v-if="comment.score">
                <el-rate v-model="comment.score" disabled :max="5" show-score></el-rate>
              </div>
            </div>
          </div>

          <!-- 分页 -->
          <pagination
            style="padding: 10px"
            v-show="commentTotal > 0"
            :total="commentTotal"
            :page.sync="commentQueryParams.pageNum"
            :limit.sync="commentQueryParams.pageSize"
            @pagination="getCommentList"
          />

          <!-- 新增评论 -->
          <div class="comment-form" v-hasPermi="['manage:evaluateInfo:add']">
            <h4>发表评论</h4>
            <el-form ref="commentForm" :model="commentForm" :rules="commentRules" label-width="80px">
              <el-form-item label="标题" prop="title">
                <el-input v-model="commentForm.title" placeholder="请输入标题"/>
              </el-form-item>
              <el-form-item label="评分" prop="score">
                <el-rate v-model="commentForm.score" show-text></el-rate>
              </el-form-item>
              <el-form-item label="内容" prop="content">
                <el-input v-model="commentForm.content" type="textarea" :rows="3" placeholder="请输入评论内容"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitComment" :loading="commentSubmitLoading">提交评论</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <!-- 空状态 -->
    <el-empty v-if="!loading && !scenicInfo" description="暂无数据"></el-empty>
  </div>
</template>

<script>
import {getScenicInfoDetail} from "@/api/manage/scenicInfo";
import {addLikesInfo} from "@/api/manage/likesInfo";
import {addEvaluateInfo, listEvaluateInfoByScenic} from "@/api/manage/evaluateInfo";
import Pagination from "@/components/Pagination";

export default {
  name: "ScenicDetail",
  components: {Pagination},
  data() {
    return {
      // 详情数据
      scenicInfo: null,
      // 加载状态
      loading: false,
      // 是否点赞
      isLike: false,
      // 评论相关数据
      commentLoading: false,
      commentSubmitLoading: false,
      commentList: [],
      commentTotal: 0,
      commentQueryParams: {
        pageNum: 1,
        pageSize: 10,
        scenicId: null
      },
      commentForm: {
        scenicId: null,
        title: '',
        score: 5,
        content: ''
      },
      commentRules: {
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        score: [
          {required: true, message: "评分不能为空", trigger: "change"}
        ],
        content: [
          {required: true, message: "评论内容不能为空", trigger: "blur"}
        ]
      }
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
        // 获取评论列表
        this.commentQueryParams.scenicId = response.data.id;
        this.commentForm.scenicId = response.data.id;
        this.getCommentList();
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 点赞/取消点赞 */
    handleLike() {
      const that = this;
      that.$modal.confirm('是否确认点赞').then(function () {
        return addLikesInfo({
          scenicId: that.scenicInfo.id,
        });
      }).then(() => {
        that.$modal.msgSuccess("操作成功");
        if (that.scenicInfo) {
          that.scenicInfo.likesNumber = (Number(that.scenicInfo.likesNumber) || 0) + (that.isLike ? -1 : 1);
        }
        that.isLike = !that.isLike;
      }).catch(() => {
      });
    },
    /** 获取评论列表 */
    getCommentList() {
      this.commentLoading = true;
      listEvaluateInfoByScenic(this.commentQueryParams).then(response => {
        this.commentList = response.rows || [];
        this.commentTotal = response.total || 0;
        this.commentLoading = false;
      }).catch(() => {
        this.commentLoading = false;
      });
    },
    /** 提交评论 */
    submitComment() {
      this.$refs["commentForm"].validate(valid => {
        if (valid) {
          this.commentSubmitLoading = true;
          // 设置默认状态为正常（0）
          const data = {
            ...this.commentForm,
            status: '0'
          };
          addEvaluateInfo(data).then(response => {
            this.$modal.msgSuccess("评论成功");
            this.commentSubmitLoading = false;
            // 重置表单
            this.commentForm = {
              scenicId: this.scenicInfo.id,
              title: '',
              score: 5,
              content: ''
            };
            // 刷新评论列表
            this.getCommentList();
            // 更新评论数
            if (this.scenicInfo) {
              this.scenicInfo.commentsNumber = (Number(this.scenicInfo.commentsNumber) || 0) + 1;
            }
          }).catch(() => {
            this.commentSubmitLoading = false;
          });
        }
      });
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

.comment-section {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 2px solid #ebeef5;

  .comment-title {
    margin: 0 0 20px 0;
    font-size: 20px;
    font-weight: 600;
    color: #303133;
  }
}

.comment-list {
  margin-bottom: 20px;
  min-height: 100px;

  .no-comment {
    text-align: center;
    color: #909399;
    padding: 40px 0;
    font-size: 14px;
  }

  .comment-item {
    padding: 15px;
    border-bottom: 1px solid #ebeef5;
    margin-bottom: 10px;
    background-color: #fff;
    border-radius: 4px;

    .comment-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 8px;

      .comment-user {
        font-weight: 600;
        color: #303133;
      }

      .comment-time {
        color: #909399;
        font-size: 12px;
      }
    }

    .comment-content {
      color: #606266;
      line-height: 1.6;
      font-size: 14px;
      margin-bottom: 8px;
    }

    .comment-score {
      ::v-deep .el-rate {
        height: auto;
      }
    }
  }
}

.comment-form {
  margin-top: 30px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 8px;

  h4 {
    margin: 0 0 15px 0;
    font-size: 16px;
    font-weight: 600;
    color: #303133;
  }

  ::v-deep .el-rate {
    height: 32px;
    line-height: 32px;
  }
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
