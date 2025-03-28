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
  </BasicDrawer>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import {BasicDrawer} from "@/components/Drawer";
import {refundTableData} from "@/views/demo/page/desc/basic/data";

// 模拟文件列表数据
const fileList = ref([
  { id: 1, name: 'aaa', url: 'https://example.com/file1.pdf' },
  { id: 2, name: 'xxx', url: 'https://example.com/file2.txt' },
  { id: 3, name: 'zzz', url: 'https://example.com/file3.zip' }
]);

const data = ref([
  {
    title: 'xxx',
    text:"课程质量很好"
  },
  {
    title: 'zzz',
    text:"课程质量很好"
  },
  {
    title: 'aaa',
    text:"课程质量很好"
  },
  {
    title: 'vdvvd',
    text:"课程质量很好"
  },
]);

// 下载文件的方法
const downloadFile = (url) => {
  const link = document.createElement('a');
  link.href = url;
  link.download = ''; // 可以根据需要设置下载的文件名
  link.click();
  link.remove();
};

const value2 = ref()

function  addComent(){
  data.value.push({
    title: 'vdvvd',
    text:value2.value
  })
  value2.value = ''
}
</script>

<style scoped lang="less">

</style>
