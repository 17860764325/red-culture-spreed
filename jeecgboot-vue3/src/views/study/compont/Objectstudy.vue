<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']">
    <a-row style="display: flex">
      <!--    左侧章节树-->
      <ScrollContainer  style="width: 20%;border: black 1px solid;height: 800px;background-color: #cfe0ee">

        <a-card>
          <h1>课程列表</h1>
          <div v-for="item in 3">
            <div style="width: 100%;">
              <a-button type="text" style="" @click="">第一章节</a-button>
            </div>
            <div v-for="index in 4" style="width: 100%;">
              <a-button type="text" style="margin-left: 30px" @click="">唯物主义辩证法</a-button>
            </div>
          </div>

        </a-card>
        <a-card>
          <h1>章节概述：</h1>
          <p>该章节主要讲述，马克思主义的基本原理等相关理论概念</p>
        </a-card>
        <a-card>
          <h1>课程概述：</h1>
          <p>该课程讲述了伟大的马克思主义原理</p>
        </a-card>

      </ScrollContainer>
      <!--    对应具体的视频播放模块-->
      <a-card
        style="border: black 1px solid;width: 70%;margin-left: 5%;height: 800px;background-color: #cfe0ee">
        <video controls style="width: 80%">
          <source src="../../../assets/vedio/test.mp4" type="video/mp4">
          你的浏览器不支持视频播放。
        </video>
      </a-card>
    </a-row>
  </BasicModal>
</template>

<script setup lang="ts">
import {BasicModal, useModalInner} from "@/components/Modal";
import {BasicTree, TreeItem} from "@/components/Tree";
import { ScrollContainer } from '/@/components/Container/index';

const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
});
const videoUrl = ''
const treeData: TreeItem[] = [
  {
    title: 'parent ',
    key: '0-0',
    children: [
      {title: 'leaf', key: '0-0-0'},
      {
        title: 'leaf',
        key: '0-0-1',
        children: [
          {title: 'leaf', key: '0-0-0-0', children: [{title: 'leaf', key: '0-0-0-0-1'}]},
          {title: 'leaf', key: '0-0-0-1'},
        ],
      },
    ],
  },
  {
    title: 'parent 2',
    key: '1-1',
    children: [
      {title: 'leaf1231231231222312312', key: '1-1-0'},
      {title: 'leaf', key: '1-1-1'},
    ],
  },
  {
    title: 'parent 3',
    key: '2-2',
    children: [
      {title: 'leaf', key: '2-2-0'},
      {title: 'leaf', key: '2-2-1'},
    ],
  },
];

</script>


<style scoped lang="less">

</style>
