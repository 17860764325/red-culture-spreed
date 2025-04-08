<template>
  <div class="fixed top-0 left-0 right-0 bottom-0 bg-gray-800 bg-opacity-75 flex justify-center items-center">
    <div class="bg-white p-6 rounded-md w-3/4 md:w-1/2">
      <h2 class="text-xl font-bold mb-4">发布新帖子</h2>
      <form @submit.prevent="submitPost">
        <div class="mb-3">
          <label for="title" class="block text-gray-700 mb-1">标题</label>
          <input type="text" id="title" v-model="moduleForm.title" class="border border-gray-300 p-1 w-full rounded-md">
        </div>
        <div class="mb-3">
          <label for="content" class="block text-gray-700 mb-1">内容</label>
          <textarea id="content" v-model="moduleForm.content" rows="4" class="border border-gray-300 p-1 w-full rounded-md"></textarea>
        </div>
        <div class="mb-3">
<!--          <label for="module" class="block text-gray-700 mb-1">模块</label>-->
<!--          <input type="text" id="module" v-model="module" class="border border-gray-300 p-1 w-full rounded-md">-->
          <a-select placeholder="模块" v-model:value="moduleForm.moduleCode" class="border border-gray-300 p-1 w-full rounded-md">
            <a-select-option v-for="item in postModuleOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </a-select-option>
          </a-select>
        </div>
        <div class="flex justify-between">
          <button type="submit" class="bg-green-600 text-white px-3 py-1 rounded-md hover:bg-green-700">发布</button>
          <button @click="$emit('cancel')" class="bg-gray-300 text-gray-700 px-3 py-1 rounded-md hover:bg-gray-400">取消</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
  import { ref, defineEmits } from 'vue';
  import {initDictOptions} from '/@/utils/dict/JDictSelectUtil';
  import { saveOrUpdate } from '../OnlinePost.api'

  const title = ref('');
  const content = ref('');
  const module = ref('');
  const moduleForm = ref({});

  const emits = defineEmits(['post-created', 'cancel']);
  const postModuleOptions = ref([])
  // 获取字典值
  initDictOptions( 'postModule').then(res => {
    postModuleOptions.value = res;
  })

  const submitPost = () => {
    console.log("moduleForm:")
    console.log(moduleForm)
    console.log(moduleForm.value.title)
    console.log(moduleForm.value.content)
    console.log(moduleForm.value.moduleCode)
    if (moduleForm.value.title && moduleForm.value.content && moduleForm.value.moduleCode) {
      console.log('进入')
      emits('post-created', { title: moduleForm.value.title, content: moduleForm.value.content, module: moduleForm.value.moduleCode });
      // title.value = '';
      // content.value = '';
      // module.value = '';
      console.log('进出')
    }
    saveOrUpdate(moduleForm.value, false);
  };
</script>

<style scoped>
  /* 可添加自定义样式 */
</style>
