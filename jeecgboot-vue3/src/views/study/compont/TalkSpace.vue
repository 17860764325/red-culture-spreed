<template>
  <BasicDrawer v-bind="$attrs" title="Drawer Title" width="40%">
    <h1>评论展示</h1>
    <a-list item-layout="horizontal" :data-source="data">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta
            :description="item.text"
          >
            <template #title>
              <a href="">{{ item.title }}</a>
            </template>
            <template #avatar>
              <a-avatar src="../../../../assets/images/daiban.png" />
            </template>
          </a-list-item-meta>
        </a-list-item>
      </template>
    </a-list>
    <a-card>
      <a-textarea
        v-model:value="value2"
        placeholder="Autosize height with minimum and maximum number of lines"
        :auto-size="{ minRows: 2, maxRows: 5 }"
      />
      <a-button type="primary" style="margin-top: 10px;margin-left: 700px;" @click="addComent">提交</a-button>
    </a-card>


<!--    <div class="p-4">-->
<!--      <h1 class="text-2xl font-bold mb-4">评论展示</h1>-->
<!--      <div class="flex mb-4">-->
<!--        <input v-model="newComment" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="发表评论">-->
<!--        <button @click="addComment" class="bg-blue-500 text-white p-2 rounded-r">提交</button>-->
<!--      </div>-->
<!--      <div v-for="comment in comments" :key="comment.id" class="mb-4">-->
<!--        <div class="flex justify-between items-center">-->
<!--          <div class="flex items-center">-->
<!--            <img :src="comment.avatar" alt="Avatar" class="w-8 h-8 rounded-full mr-2">-->
<!--            <span class="font-bold">{{ comment.name }}</span>-->
<!--          </div>-->
<!--          <div class="flex items-center space-x-2">-->
<!--          <span @click="toggleComment(comment.id)" class="text-blue-500 cursor-pointer">-->
<!--            {{ isCommentExpanded[comment.id] ? '收起' : '展开' }}-->
<!--          </span>-->
<!--            <span @click="deleteComment(comment.id, comment)" class="text-red-500 cursor-pointer">删除</span>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div v-if="isCommentExpanded[comment.id]">-->
<!--          <div class="flex justify-between items-center">-->
<!--            <p>{{ comment.content }}</p>-->
<!--            <span @click="toggleReply(comment.id)" class="text-blue-500 cursor-pointer">回复</span>-->
<!--          </div>-->
<!--          <div v-if="showReply[comment.id]" class="mt-2">-->
<!--            <div class="flex">-->
<!--              <input v-model="replyTexts[comment.id]" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="输入回复内容">-->
<!--              <button @click="addReply(comment)" class="bg-blue-500 text-white p-2 rounded-r">提交回复</button>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div v-for="reply in comment.replies" :key="reply.id" class="ml-8 mt-4">-->
<!--            <div class="flex items-center mb-2">-->
<!--              <img :src="reply.avatar" alt="Avatar" class="w-8 h-8 rounded-full mr-2">-->
<!--              <span class="font-bold">-->
<!--              {{ reply.name }} 回复 {{ reply.replyToName }}-->
<!--            </span>-->
<!--              <span @click="deleteReply(comment.id, reply.id)" class="text-red-500 cursor-pointer ml-2">删除</span>-->
<!--            </div>-->
<!--            <div class="flex justify-between items-center">-->
<!--              <p>{{ reply.content }}</p>-->
<!--              <span @click="toggleReply(reply.id)" class="text-blue-500 cursor-pointer">回复</span>-->
<!--            </div>-->
<!--            <div v-if="showReply[reply.id]" class="mt-2">-->
<!--              <div class="flex">-->
<!--                <input v-model="replyTexts[reply.id]" type="text" class="border border-gray-300 p-2 rounded-l w-full" placeholder="输入回复内容">-->
<!--                <button @click="addReply(reply, comment)" class="bg-blue-500 text-white p-2 rounded-r">提交回复</button>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
  </BasicDrawer>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { saveOrUpdate, selectByObjectCodeAndChapterCode, deleteByIds, deleteOne } from '../../courseComment/CourseComment.api'
  // import {BasicDrawer} from "@/components/Drawer";
  import {refundTableData} from "@/views/demo/page/desc/basic/data";
  import {useUserStore} from "../../../store/modules/user";
  const userInfo = useUserStore().getUserInfo;
  // 模拟评论数据
  const comments = ref([]);

  const newComment = ref('');
  const showReply = ref({});
  const replyTexts = ref({});
  const isCommentExpanded = ref({});

  const props = defineProps({
    courseData: { type: Object, default: ()=>{}}
  });

  console.log("评论里面的：")
  console.log(props.courseData)
  // 初始化查询
  selectByObjectCodeAndChapterCode({objectCode: undefined, chapterCode: undefined}).then(res => {
    comments.value = res;
  });


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
        replies: []
      }
      console.log(comment)
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








// import { ref } from 'vue';
// import {BasicDrawer} from "@/components/Drawer";
// import {refundTableData} from "@/views/demo/page/desc/basic/data";
//
// // 模拟文件列表数据
// const fileList = ref([
//   { id: 1, name: 'aaa', url: 'https://example.com/file1.pdf' },
//   { id: 2, name: 'xxx', url: 'https://example.com/file2.txt' },
//   { id: 3, name: 'zzz', url: 'https://example.com/file3.zip' }
// ]);
//
// const data = ref([
//   {
//     title: 'xxx',
//     text:"课程质量很好"
//   },
//   {
//     title: 'zzz',
//     text:"课程质量很好"
//   },
//   {
//     title: 'aaa',
//     text:"课程质量很好"
//   },
//   {
//     title: 'vdvvd',
//     text:"课程质量很好"
//   },
// ]);
//
// // 下载文件的方法
// const downloadFile = (url) => {
//   const link = document.createElement('a');
//   link.href = url;
//   link.download = ''; // 可以根据需要设置下载的文件名
//   link.click();
//   link.remove();
// };
//
// const value2 = ref()
//
// function  addComent(){
//   data.value.push({
//     title: 'vdvvd',
//     text:value2.value
//   })
//   value2.value = ''
// }
</script>

<style scoped lang="less">

</style>
