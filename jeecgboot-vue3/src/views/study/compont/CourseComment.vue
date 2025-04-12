<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
    <div >
      <a-row style="display:flex;flex-direction: column;width: 100%;align-items: center">
        <a-row style="width: 70%">
        <img style="width: 20%;height: 300px;padding: 10px" :src="'../../../../opt/upFiles/'+courseData.objectImg"/>
        <a-card  style="height: 300px;width: 80%">
          <div style="display: flex;justify-content: space-between;text-align: center;">
            <div style="width: 50px;height: 20px;background-color: #0a8fe9"><p style="color: white">课程</p></div>
            <p>指导教师：{{courseData.objectTeacher}}</p>
          </div>
          <p style="font-size: 30px;height: 30px">{{ courseData.objectName }}</p>
          <a-divider style=" background-color: #b70b0b;margin-bottom: 20px"/>
          <p>课程概述：{{ courseData.objectRemark}}</p>
          <a-row>
            <p>发帖：0/5条｜</p>
            <!--          <p>课件学习：0/5次｜</p>-->
            <p>课件学习：{{courseData.coursewareLearning}}｜</p>
            <p>总时长：0/5分钟</p>
          </a-row>
        </a-card>
        </a-row>
      </a-row>
      <a-row style="display:flex;flex-direction: column;width: 100%;align-items: center">
      <a-card style="width: 70%">
        <h1 class="text-2xl font-bold mb-4">{{courseData.objectName}}课程评论</h1>
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
    </div>
  </BasicModal>
</template>

<script setup>
  import { ref } from 'vue';
  import { saveOrUpdate, selectByObjectCodeAndChapterCode, deleteByIds, deleteOne } from '../../courseComment/CourseComment.api'
  import {useUserStore} from "../../../store/modules/user";
  const userInfo = useUserStore().getUserInfo;
  const courseData = ref({});
  import {BasicModal, useModalInner} from "@/components/Modal";
  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    courseData.value = data.courseData
    console.log("courseData.value:")
    console.log(courseData.value)
  });

  // 模拟评论数据
  const comments = ref([]);

  const newComment = ref('');
  const showReply = ref({});
  const replyTexts = ref({});
  const isCommentExpanded = ref({});


  function getStart() {
    // 初始化查询
    selectByObjectCodeAndChapterCode({objectCode: courseData.value.objectCode, chapterCode: courseData.value.chapterCode}).then(res => {
      comments.value = res;
    });
  }

  function replaceObjectRemark(objectRemark) {
    if (objectRemark !== undefined) {
      // 使用正则表达式去除 HTML 标签
      const regex = /<[^>]*>/g;
      return objectRemark.replace(regex, '');
    } else {
      return objectRemark;
    }

  }

  function addComment() {
    if (newComment.value.trim()) {
      const comment = {
        id: Date.now(),
        commentId: userInfo.id,
        // avatar: 'https://picsum.photos/30/30?random=' + Date.now(),
        avatar: userInfo.avatar,
        name: userInfo.realname,
        content: newComment.value,
        replyToId: 0,
        objectCode: courseData.value.objectCode,
        chapterCode: courseData.value.chapterCode,
        replies: []
      }
      comments.value.push(comment);
      newComment.value = '';
      isCommentExpanded.value[comment.id] = true
      saveOrUpdate(comment, false);
    }
  }

  function addReply(parent, grandParent) {
    const replyText = replyTexts.value[parent.id];
    if (replyText.trim()) {
      const replyToName = parent.name;
      const newReply = {
        id: Date.now(),
        commentId: userInfo.id,
        // avatar: 'https://picsum.photos/30/30?random=' + Date.now(),
        avatar: userInfo.avatar,
        name: userInfo.realname,
        content: replyText,
        replyToName: replyToName,
        objectCode: courseData.value.objectCode,
        chapterCode: courseData.value.chapterCode,
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
      saveOrUpdate(newReply, false);
    }
  }

  const toggleReply = (id) => {
    showReply.value[id] = !showReply.value[id];
  };

  const toggleComment = (id) => {
    isCommentExpanded.value[id] = !isCommentExpanded.value[id];
  };

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

  const deleteReply = (commentId, replyId) => {
    const comment = comments.value.find(comment => comment.id === commentId);
    if (comment) {
      comment.replies = comment.replies.filter(reply => reply.id !== replyId);
    }
    deleteOne({id: replyId});
  };
</script>

<style scoped>
  /* 这里可以添加自定义样式 */
</style>
