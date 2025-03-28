

<template>
  <BasicDrawer v-bind="$attrs" title="Drawer Title" width="30%">
    <div>
      <h1>文件下载列表</h1>
      <a-list item-layout="horizontal" :data-source="data">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta
              description="马克思主义原理知识点相关，试题总结"
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
    </div>
  </BasicDrawer>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import {BasicDrawer} from "@/components/Drawer";
// 模拟文件列表数据
const fileList = ref([
  { id: 1, name: '文件1.pdf', url: 'https://example.com/file1.pdf' },
  { id: 2, name: '文件2.txt', url: 'https://example.com/file2.txt' },
  { id: 3, name: '文件3.zip', url: 'https://example.com/file3.zip' }
]);

const data: DataItem[] = [
  {
    title: '马克思主义原理章节试题.pdf',
  },
  {
    title: '马克思主义原理笔记.pdf',
  },
  {
    title: '马克思主义原理小结测试.pdf',
  },
  {
    title: '马克思主义原理知识点总结.pdf',
  },
];

// 下载文件的方法
const downloadFile = (url) => {
  const link = document.createElement('a');
  link.href = url;
  link.download = ''; // 可以根据需要设置下载的文件名
  link.click();
  link.remove();
};
</script>
<style scoped lang="less">
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}

button {
  margin-left: 10px;
}
</style>
