<template>
<!--  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"-->
<!--              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">-->
  <div class="fixed top-0 left-0 right-0 bottom-0 bg-gray-800 bg-opacity-75 flex justify-center items-center">
    <div class="bg-white p-6 rounded-md w-3/4 md:w-1/2">
      <h2 class="text-xl font-bold mb-4">编辑帖子</h2>
      <form @submit.prevent="submitEdit">
        <div class="mb-3">
          <label for="title" class="block text-gray-700 mb-1">标题</label>
          <input type="text" id="title" v-model="moduleForm.title" class="border border-gray-300 p-1 w-full rounded-md">
        </div>
        <div class="mb-3">
          <label for="content" class="block text-gray-700 mb-1">内容</label>
          <textarea id="content" v-model="moduleForm.content" rows="4" class="border border-gray-300 p-1 w-full rounded-md"></textarea>
        </div>
        <div class="mb-3">
          <a-select placeholder="模块" v-model:value="moduleForm.moduleCode" class="border border-gray-300 p-1 w-full rounded-md">
            <a-select-option v-for="item in postModuleOptions" :key="item.value" :value="item.value">
              {{ item.label }}
            </a-select-option>
          </a-select>
        </div>
        <div class="flex justify-between">
          <button type="submit" class="bg-blue-600 text-white px-3 py-1 rounded-md hover:bg-blue-700">保存修改</button>
          <button @click="$emit('cancel')" class="bg-gray-300 text-gray-700 px-3 py-1 rounded-md hover:bg-gray-400">取消</button>
        </div>
      </form>
    </div>
  </div>
<!--  </BasicModal>-->
</template>

<script setup>
  import { ref, defineProps, defineEmits } from 'vue';
  import {initDictOptions} from '/@/utils/dict/JDictSelectUtil';

  import {BasicModal, useModalInner} from "@/components/Modal";
  import {useModal} from "@/components/Modal";
  const moduleForm = ref({});

  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: false, showCancelBtn: true, showOkBtn: true});
    console.log("data.moduleForm:")
    console.log(data.moduleForm)
    moduleForm.value = data.moduleForm
  });

  const props = defineProps({
    moduleForm: {
      type: Object,
      required: true
    }
  });
  function getStart() {

  }

  console.log('测试：')
  console.log(props)
  console.log(props.moduleForm)
  console.log(moduleForm.value)
  const postModuleOptions = ref([])
  // 获取字典值
  initDictOptions( 'postModule').then(res => {
    postModuleOptions.value = res;
  })

  const emits = defineEmits(['post-updated', 'cancel']);

  // const editedPost = ref({...props.post });

  const submitEdit = () => {
    emits('post-updated', moduleForm.value);
  };
</script>

<style scoped>
  /* 可添加自定义样式 */
</style>
