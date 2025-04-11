<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
    <a-row>
      <div id="app">
        <div class="article-container">
          <h1 class="article-title">{{ activityInfo.activityName }}</h1>
          <span style="font-weight: bold; padding-right: 300px;">{{ '活动开始时间：'}}{{activityInfo.startTime}}</span>
          <span style="font-weight: bold;">{{ '活动结束时间：'}}{{activityInfo.endTime}}</span>
          <p style="font-weight: bold;">{{ '活动地点：'}}{{activityInfo.address}}</p>
          <div v-html="activityInfo.content"></div>
        </div>
        <!--      <div style="align: center">-->
        <!--        <a-button>活动报名</a-button>-->
        <!--      </div>-->
        <!--      <button class="center-button">活动报名</button>-->
      </div>
    </a-row>
    <a-row style="align-items: center;padding-left: 45%;">
      <button v-if="!isRegistered" href="#" class="contact-us-button" @click="handleClick">
        活动报名
      </button>
      <a v-else href="#" class="contact-us-button1">
        已报名
      </a>
    </a-row>

    <a-row style="display:flex;flex-direction: column;width: 100%;align-items: center">
      <a-card style="width: 70%">
        <h1 class="text-2xl font-bold mb-4">{{activityInfo.activityName}}活动评论</h1>
        <div v-for="comment in comments" :key="comment.id" class="mb-4">
          <div class="flex justify-between items-center">
            <div class="flex items-center">
              <img :src="comment.avatar" alt="Avatar" class="w-8 h-8 rounded-full mr-2">
              <span class="font-bold">{{ comment.name }}</span>
            </div>
            <div class="flex items-center space-x-2">
            <span @click="toggleComment(comment.id)" class="text-blue-500 cursor-pointer">
              {{ isCommentExpanded[comment.id] ? '收起' : '展开' }}
            </span>
              <span @click="deleteComment(comment.id, comment)" class="text-red-500 cursor-pointer">删除</span>
            </div>
          </div>
          <div v-if="isCommentExpanded[comment.id]">
            <div class="flex justify-between items-center">
              <p>{{ comment.content }}</p>
              <span @click="toggleReply(comment.id)" class="text-blue-500 cursor-pointer">回复</span>
            </div>
            <div v-if="showReply[comment.id]" class="mt-2">
              <div class="flex">
                <input v-model="replyTexts[comment.id]" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="输入回复内容">
                <button @click="addReply(comment)" class="bg-blue-500 text-white p-2 rounded-r">提交回复</button>
              </div>
            </div>
            <div v-for="reply in comment.replies" :key="reply.id" class="ml-8 mt-4">
              <div class="flex items-center mb-2">
                <img :src="reply.avatar" alt="Avatar" class="w-8 h-8 rounded-full mr-2">
                <span class="font-bold">
                {{ reply.name }} 回复 {{ reply.replyToName }}
              </span>
                <span @click="deleteReply(comment.id, reply.id)" class="text-red-500 cursor-pointer ml-2">删除</span>
              </div>
              <div class="flex justify-between items-center">
                <p>{{ reply.content }}</p>
                <span @click="toggleReply(reply.id)" class="text-blue-500 cursor-pointer">回复</span>
              </div>
              <div v-if="showReply[reply.id]" class="mt-2">
                <div class="flex">
                  <input v-model="replyTexts[reply.id]" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="输入回复内容">
                  <button @click="addReply(reply, comment)" class="bg-blue-500 text-white p-2 rounded-r">提交回复</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </a-card>
      <div style="width:70%" class="flex mb-4">
        <input v-model="newComment" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="发表评论">
        <button @click="addComment" class="bg-blue-500 text-white p-2 rounded-r">提交</button>
      </div>
    </a-row>
  </BasicModal>

</template>

<script setup>
  import { ref } from 'vue';
  const { createMessage, createWarningModal } = useMessage();
  import {BasicModal, useModalInner} from "@/components/Modal";
  import {useUserStore} from "../../../store/modules/user";
  import { saveOrUpdate, list } from '../../registeredPerson/RegisteredPerson.api'
  import { saveOrUpdateComment, selectByActivityId, deleteByIds, deleteOne } from '../../activityComment/ActivityComment.api'
  import { useUserStoreWithOut } from "/@/store/modules/user";
  import {useMessage} from "../../../hooks/web/useMessage";
  import {selectByObjectCodeAndChapterCode} from "../../courseComment/CourseComment.api";
  const userStore = useUserStoreWithOut();
  const activityInfo = ref({});
  // 当前登录人信息
  let userInfo = userStore.getUserInfo;
  // 判断当亲登录人是否为活动报名人
  const isRegistered = ref(false);

  // 评论
  const comments = ref([]);
  const newComment = ref('');
  const showReply = ref({});
  const replyTexts = ref({});
  const isCommentExpanded = ref({});

  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    activityInfo.value = data.activityInfo
    // 查询活动报名人数
    list({activityId: activityInfo.value.id, pageNo:1,pageSize:1000}).then(res => {
      const filterList = res.records.filter(item => {
        return (item.activityId === activityInfo.value.id && item.createBy === userInfo.username)
      })
      if (filterList.length > 0) {
        isRegistered.value = true;
      }
    })

    // 查询评论
    // 初始化查询
    selectByActivityId({activityId: activityInfo.value.id}).then(res => {
      comments.value = res;
    });
  });

  function getStart() {
    console.log("activityInfo:")
    console.log(activityInfo.value)

  }

  /**
   * 报名按钮点击事件
   */
  function handleClick() {
    // isRegistered.value = true
    // 点击事件
    const saveForm = {};
    saveForm.activityId = activityInfo.value.id
    saveOrUpdate(saveForm, false).then(res => {
      isRegistered.value = res.isRegistered;
      if (res.isRegistered) {
        createMessage.success(res.message);
      }
      if (!res.isRegistered) {
        createMessage.error(res.message);
      }
    });
  }

  // 新增评论
  function addComment() {
    if (newComment.value.trim()) {
      const comment = {
        id: Date.now(),
        commentId: userInfo.id,
        avatar: userInfo.avatar,
        name: userInfo.realname,
        content: newComment.value,
        replyToId: 0,
        activityId: activityInfo.value.id,
        replies: []
      }
      comments.value.push(comment);
      newComment.value = '';
      isCommentExpanded.value[comment.id] = true
      saveOrUpdateComment(comment, false);
    }
  }

  // 回复评论
  function addReply(parent, grandParent) {
    const replyText = replyTexts.value[parent.id];
    if (replyText.trim()) {
      const replyToName = parent.name;
      const newReply = {
        id: Date.now(),
        commentId: userInfo.id,
        avatar: userInfo.avatar,
        name: userInfo.realname,
        content: replyText,
        replyToName: replyToName,
        activityId: activityInfo.value.id,
        replies: []
      };
      if (grandParent) {
        newReply.topToId = grandParent.id;
        grandParent.replies.push(newReply);
      } else {
        newReply.topToId = parent.id;
        parent.replies.push(newReply);
      }
      replyTexts.value[parent.id] = '';
      showReply.value[parent.id] = false;

      newReply.replyToId = parent.id;
      saveOrUpdateComment(newReply, false);
    }
  }

  const toggleReply = (id) => {
    showReply.value[id] = !showReply.value[id];
  };

  const toggleComment = (id) => {
    isCommentExpanded.value[id] = !isCommentExpanded.value[id];
  };

  // 删除顶部评论
  const deleteComment = (commentId, comment) => {
    comments.value = comments.value.filter(comment => comment.id !== commentId);
    // 获取comment下面的所有ID
    let ids = '';
    ids = ids + commentId;
    for (let i = 0; i < comment.replies.length; i++) {
      ids = ids + ',' + comment.replies[i].id;
    }
    deleteByIds({ids: ids});
  };

  // 删除非顶部评论
  const deleteReply = (commentId, replyId) => {
    const comment = comments.value.find(comment => comment.id === commentId);
    if (comment) {
      comment.replies = comment.replies.filter(reply => reply.id !== replyId);
    }
    deleteOne({id: replyId});
  };

</script>

<style scoped>
  #app {
    font-family: Arial, sans-serif;
    padding: 20px;
  }

  .article-container {
    max-width: 800px;
    margin: 0 auto;
  }

  .article-title {
    text-align: center;
    margin-bottom: 20px;
  }

  .article-meta {
    text-align: center;
    margin-bottom: 30px;
    font-size: 14px;
  }

  .article-content p {
    text-align: justify;
    line-height: 1.6;
  }

  .center-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }


  .contact-us-button {
    padding: 10px 20px;
    background-color: red;
    color: white;
    font-size: 20px;
  }
  .contact-us-button1 {
    padding: 10px 20px;
    background-color: grey;
    color: white;
    font-size: 20px;
  }
</style>
