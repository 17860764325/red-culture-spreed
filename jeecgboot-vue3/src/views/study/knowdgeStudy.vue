<template>
  <div style="padding-left: 15%;padding-right: 15%;">
    <p style="font-size: 50px;color: darkred">红色文化课程学习</p>
    <a-divider style="height: 4px; background-color: #b70b0b"/>
    <a-row v-for="item in objectInfoList" style="margin-top: 20px;border: black 2px solid;">
      <img style="width: 200px;height: 300px;padding: 10px" :src="'../../../../opt/upFiles/'+item.objectImg"/>
      <a-card  style="height: 300px;width: 79%">
        <div style="display: flex;justify-content: space-between;text-align: center;">
          <div style="width: 50px;height: 20px;background-color: #0a8fe9"><p style="color: white">课程</p></div>
          <p>指导教师：{{item.objectTeacher}}</p>
        </div>
        <p style="font-size: 30px;height: 30px">{{ item.objectName }}</p>
        <a-divider style=" background-color: #b70b0b;margin-bottom: 20px"/>
        <p>课程概述：{{ item.objectRemark}}</p>
        <a-row>
          <p>发帖：0/5条｜</p>
<!--          <p>课件学习：0/5次｜</p>-->
          <p>课件学习：{{item.coursewareLearning}}｜</p>
          <p>总时长：0/5分钟</p>
        </a-row>
        <a-row style="">
<!--          打开弹窗进行学习-->
          <a-button type="primary" style="" @click="handleClick('1', item)">课程学习</a-button>
<!--          打开抽栏进行选择资源下载-->
          <a-button type="primary" style="margin-left: 30px" @click="handleClick('2', item)">资料下载</a-button>
<!--          点击弹窗，进入试卷选择页面，点击进行考试-->
          <a-button type="primary" style="margin-left: 30px" @click="handleClick('3', item)">课程考试</a-button>
<!--          点击展示抽栏论坛-->
          <a-button type="primary" style="margin-left: 30px" @click="handleClick('4', item)">课程讨论</a-button>
        </a-row>
      </a-card>
    </a-row>
  </div>
  <Objectstudy @register="pageModal"></Objectstudy>
  <DataDownload @register="register1" />
  <ObjectTest @register="testModal"></ObjectTest>
  <Comment @register="register2"></Comment>
</template>
<script setup lang="ts">
import {ref, reactive, computed, unref,provide} from 'vue';
import {list} from '../objectList/ObjectList.api'
import Objectstudy  from "@/views/study/compont/Objectstudy.vue";
import {useModal} from "@/components/Modal";
import Drawer1 from "@/views/demo/comp/drawer/Drawer1.vue";
import DataDownload from "@/views/study/compont/DataDownload.vue";
import {useDrawer} from "@/components/Drawer";
import ObjectTest from "@/views/study/compont/ObjectTest.vue";
import TalkSpace from "@/views/study/compont/TalkSpace.vue";
import Comment  from '../study/compont/CourseComment.vue';

const objectInfoList = ref<Array>([])
const [pageModal, {openModal:openObjectStudyModal}] = useModal();
const [testModal, {openModal:openObjectTestModal}] = useModal();
const [register1, { openDrawer: openDrawer1, setDrawerProps1 }] = useDrawer();
// const [register2, { openDrawer: openDrawer2, setDrawerProps2 }] = useDrawer();
const [register2, {openModal:openDrawer2}] = useModal();

const courseData = ref({});

// 获取基本的课程信息
list().then(res=>{
  objectInfoList.value = res.records
  console.log(res.records)
})

function handleClick(type, item){
  courseData.value = item;
  switch (type){
    case '1':
      openObjectStudyModal(true, {courseData: courseData.value});
      break;
    case '2':
      openDrawer1(true, {courseData: courseData.value})
      break;
    case '3':
      openObjectTestModal(true,{courseData: courseData.value})
      break;
    case '4':
      openDrawer2(true, {courseData: courseData.value})
      break;
    default:
      break;

  }
}

</script>
<style scoped lang="less">

</style>
