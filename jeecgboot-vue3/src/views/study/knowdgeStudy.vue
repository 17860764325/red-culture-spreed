<template>
  <div style="padding-left: 15%;padding-right: 15%;">
    <p style="font-size: 50px;color: darkred">红色文化课程学习</p>
    <a-divider style="height: 4px; background-color: #b70b0b"/>
    <a-row v-for="item in objectInfoList" style="margin-top: 20px;border: black 2px solid;">
      <img style="width: 200px;height: 300px;padding: 10px" :src="item.objectImg"/>
      <a-card  style="height: 300px;width: 86%">
        <div style="display: flex;justify-content: space-between;text-align: center;">
          <div style="width: 50px;height: 20px;background-color: #0a8fe9"><p style="color: white">课程</p></div>
          <p>指导教师：{{item.objectTeacher}}</p>
        </div>
        <p style="font-size: 30px;height: 30px">{{ item.objectName }}</p>
        <a-divider style=" background-color: #b70b0b;margin-bottom: 20px"/>
        <p>课程概述：{{ item.objectRemark}}</p>
        <a-row>
          <p>发帖：0/5条｜</p>
          <p>课件学习：0/5次｜</p>
          <p>总时长：0/5分钟</p>
        </a-row>
        <a-row style="">
<!--          打开弹窗进行学习-->
          <a-button type="primary" style="" @click="">课程学习</a-button>
<!--          打开抽栏进行选择资源下载-->
          <a-button type="primary" style="margin-left: 30px">资料下载</a-button>
<!--          点击弹窗，进入试卷选择页面，点击进行考试-->
          <a-button type="primary" style="margin-left: 30px">课程考试</a-button>
<!--          点击展示抽栏论坛-->
          <a-button type="primary" style="margin-left: 30px">论坛分享</a-button>
        </a-row>
      </a-card>
    </a-row>

  </div>
</template>
<script setup lang="ts">
import {ref, reactive, computed, unref,provide} from 'vue';
import {list} from '../objectList/ObjectList.api'

const objectInfoList = ref<Array>([])

// 获取基本的课程信息
list().then(res=>{
  objectInfoList.value = res.records
  console.log(res.records)
})

</script>
<style scoped lang="less">

</style>
