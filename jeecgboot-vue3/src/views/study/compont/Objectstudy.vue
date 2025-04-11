<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
    <a-row style="display: flex">
      <!--    左侧章节树-->
      <ScrollContainer  style="width: 20%;border: black 1px solid;height: 800px;background-color: #cfe0ee">

        <a-card>
          <h1>课程列表</h1>
          <div v-for="item in dataList" :key="item.id" class="parent-item">
            <span style="font-size: 1.2em;">{{item.chapterName}}</span>
            <div v-for="itemChild in item.childList" :key="itemChild.id" class="child-item"
                 @click="handleChildClick(itemChild)"
                 @mouseenter="handleMouseEnter(itemChild)"
                 @mouseleave="handleMouseLeave(itemChild)"
                 :style="{ color: itemChild.isHovered ? 'red' : '' }">
              <span style="font-size: 1em;">{{itemChild.chapterName}}</span>
<!--              <a-button type="text" style="margin-left: 30px" @click="">唯物主义辩证法</a-button>-->
            </div>
          </div>
        </a-card>

        <a-card>
          <h1>章节概述：</h1>
<!--          <p>{{specificContent}}</p>-->
          <div v-html="specificContent"></div>
        </a-card>
        <a-card>
          <h1>课程概述：</h1>
          <p>该课程讲述了伟大的马克思主义原理</p>
        </a-card>

      </ScrollContainer>
      <!--    对应具体的视频播放模块-->
      <a-card
        style="border: black 1px solid;width: 70%;margin-left: 5%;height: 800px;background-color: #cfe0ee">
        <video controls style="width: 80%" v-if="shouldShowVideo">
          <source :src=vedioPath type="video/mp4">
<!--          <source src="../../../../opt/upFiles/temp/测试视频_1744097293823.mp4" type="video/mp4">-->
          你的浏览器不支持视频播放。
        </video>
      </a-card>
    </a-row>
  </BasicModal>
</template>

<script setup lang="ts">
  import { ref, computed } from 'vue';
import {BasicModal, useModalInner} from "@/components/Modal";
import {BasicTree, TreeItem} from "@/components/Tree";
import { ScrollContainer } from '/@/components/Container/index';
import { selectDataByObjectCode, updateWatch } from '../apiTs/ChapterList.api'
  import {useModal} from "@/components/Modal";

const courseData = ref({});

const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
  courseData.value = data.courseData
});
const videoUrl = ''

const dataList = ref([]);

const specificContent = ref();
const vedioPath = ref();
const isComment = ref();
const shouldShowVideo = ref(true);
function getStart() {
  selectDataByObjectCode({objectCode: courseData.value.objectCode}).then(res => {
    dataList.value = res;
  })
}

  function handleChildClick(child) {
    // 先设为false销毁video元素
    shouldShowVideo.value = false;
    // 使用正则表达式去除 HTML 标签
    const regex = /<[^>]*>/g;
    specificContent.value = child.context.replace(regex, '');
    vedioPath.value = "../../../../opt/upFiles/" + child.vedio;
    isComment.value = true;
    courseData.value.chapterCode = child.chapterCode

    // 再设为true重建元素，浏览器会重新获取新地址内容
    setTimeout(() => {
      shouldShowVideo.value = true;
    }, 100);

    if (child.watch !== 'true') {
      // 更新watch字段
      child.watch = 'true'
      updateWatch(child);
    }
  }

  function handleMouseEnter(child) {
    child.isHovered = true;
  }

  function handleMouseLeave(child) {
    child.isHovered = false;
  }

</script>


<style scoped lang="less">
  .parent-item {
    margin-bottom: 10px;
  }

  .child-item {
    margin-left: 20px;
    margin-bottom: 5px;
  }
</style>
