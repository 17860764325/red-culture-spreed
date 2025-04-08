<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold mb-4">视频评论</h1>
    <div class="flex mb-4">
      <input v-model="newComment" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="发表评论">
      <button @click="addComment" class="bg-blue-500 text-white p-2 rounded-r">提交</button>
    </div>
    <div v-for="comment in comments" :key="comment.id" class="mb-4">
      <div class="flex justify-between items-center">
        <div class="flex items-center">
          <img :src="comment.avatar" alt="Avatar" class="w-8 h-8 rounded-full mr-2">
          <span class="font-bold">{{ comment.name }}</span>
        </div>
        <span @click="toggleComment(comment.id)" class="text-blue-500 cursor-pointer">
          {{ isCommentExpanded[comment.id] ? '收起' : '展开' }}
        </span>
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
  </div>
</template>

<script setup>
  import { ref } from 'vue';

  // 模拟评论数据
  const comments = ref([
    {
      id: 1,
      avatar: 'https://picsum.photos/30/30?random=1',
      name: '用户 1',
      content: '这个视频真不错！',
      replies: []
    }
  ]);

  const newComment = ref('');
  const showReply = ref({});
  const replyTexts = ref({});
  const isCommentExpanded = ref({});

  const addComment = () => {
    if (newComment.value.trim()) {
      const comment = {
        id: Date.now(),
        avatar: 'https://picsum.photos/30/30?random=' + Date.now(),
        name: '新用户',
        content: newComment.value,
        replies: []
      }
      comments.value.push(comment);
      newComment.value = '';
      isCommentExpanded.value[comment.id] = true
    }
  };

  const toggleReply = (id) => {
    showReply.value[id] = !showReply.value[id];
  };

  const addReply = (parent, grandParent = null) => {
    const replyText = replyTexts.value[parent.id];
    if (replyText.trim()) {
      // const replyToName = grandParent ? grandParent.name : parent.name;
      const replyToName = parent.name;
      const newReply = {
        id: Date.now(),
        avatar: 'https://picsum.photos/30/30?random=' + Date.now(),
        name: '新回复者',
        content: replyText,
        replyToName: replyToName,
        replies: []
      };
      if (grandParent) {
        grandParent.replies.push(newReply);
      } else {
        parent.replies.push(newReply);
      }
      replyTexts.value[parent.id] = '';
      showReply.value[parent.id] = false;
    }
  };

  const toggleComment = (id) => {
    isCommentExpanded.value[id] = !isCommentExpanded.value[id];
  };
</script>

<style scoped>
  /* 这里可以添加自定义样式 */
</style>
