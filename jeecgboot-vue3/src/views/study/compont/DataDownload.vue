
<template>
  <BasicDrawer v-bind="$attrs" @register="registerDrawer" title="课程文件资料下载" width="30%" @visible-change="getStart">
    <div>
      <h1>文件下载列表</h1>
      <ul>
        <li v-for="(file, index) in data" :key="index" @click="downloadFile(file)">
          <a :href="filePath" style="color: blue; cursor: pointer;"
             @mouseenter="changeColor(file, true)" @mouseleave="changeColor(file, false)">
            {{ getFileName(file) }}
          </a>
        </li>
      </ul>
    </div>
  </BasicDrawer>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import {BasicDrawer, useDrawerInner} from "@/components/Drawer";
import { selectDataByObjectCode } from '../apiTs/ChapterList.api'
const courseData = ref({})
const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
  courseData.value = data.courseData
});
const data = ref([]);

function getStart() {
// 资料查询
  selectDataByObjectCode({objectCode: courseData.value.objectCode}).then(res =>{
    data.value = []
    for (let i = 0; i < res.length; i++) {
      for (let j = 0; j < res[i].childList.length; j++) {
        if (res[i].childList[j].file !== null && res[i].childList[j].file !== undefined && res[i].childList[j].file !== '') {
          data.value.push(res[i].childList[j].file)
        }
      }
    }
  })
}

// 下载文件的方法
const downloadFile = (url) => {
  const link = document.createElement('a');
  link.href = url;
  const fileName = getFileName(url);
  link.download = fileName;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

// 改变文字颜色的方法
const changeColor = (filePath, isEnter) => {
  const links = document.querySelectorAll('a');
  links.forEach(link => {
    if (link.href === filePath) {
      if (isEnter) {
        link.style.color ='red';
      } else {
        link.style.color = 'blue';
      }
    }
  });
};

// 从文件路径中获取文件名（包括扩展名）
const getFileName = (filePath) => {
  let resFileName = undefined;
  if (filePath !== undefined && filePath !== '' && filePath !== null) {
    const lastIndex = filePath.lastIndexOf('/');
    resFileName = lastIndex!== -1? filePath.substring(lastIndex + 1) : filePath;
  }
  return resFileName;
};
</script>
<style scoped lang="less">

  #app {
    font-family: Arial, sans-serif;
    padding: 20px;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
</style>
