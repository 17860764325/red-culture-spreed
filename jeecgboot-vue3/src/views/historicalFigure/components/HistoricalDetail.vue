<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">

    <div class="person-container">
      <a-card class="left-column">
        <img :src="personPhoto" alt="人物照片" class="person-image">
      </a-card>
      <a-card class="right-column">
        <h2>{{ personInfo.name }}</h2>
        <p>出生年份：{{ personInfo.birthYear }}</p>
        <p>逝世年份：{{ personInfo.deathYear }}</p>
        <p>主要事迹：</p>
        <div v-html="personInfo.mainAchievement" class="rich-text"></div>
      </a-card>
    </div>
  </BasicModal>
</template>

<script setup>
  import { ref } from 'vue';
  import {useUserStore} from "../../../store/modules/user";
  const userInfo = useUserStore().getUserInfo;
  const personInfo = ref({});
  import {BasicModal, useModalInner} from "@/components/Modal";
  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    personInfo.value = data.persion
    personPhoto.value = "../../../../opt/upFiles/" + personInfo.value.imgUrl;
  });

  const personPhoto = ref();

  // 模拟评论数据
  const comments = ref([]);

  const newComment = ref('');
  const showReply = ref({});
  const replyTexts = ref({});
  const isCommentExpanded = ref({});


  function getStart() {
  }
</script>

<style scoped>
  /* 这里可以添加自定义样式 */
  .person-container {
    display: flex;
  }

  .left-column {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    max-width: 100%;
    height: auto;
  }

  .right-column {
    flex: 2;
    padding: 20px;
    max-width: 100%;
    height: auto;
  }

  .person-image {
    max-width: 100%;
    max-height: 100%;
    border: 4px solid transparent;
    border-image: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet) 1;
    animation: border-marquee 5s linear infinite;
    height: 100vh;
    object-fit: cover;
  }

  @keyframes border-marquee {
    0% {
      border-image: linear-gradient(to right, red, orange, yellow, green, blue, indigo, violet) 1;
    }
    100% {
      border-image: linear-gradient(to left, red, orange, yellow, green, blue, indigo, violet) 1;
    }
  }

  /* 小屏幕设备适配 */
  @media (max-width: 600px) {
    .person-container {
      flex-direction: column;
    }
  }
</style>
