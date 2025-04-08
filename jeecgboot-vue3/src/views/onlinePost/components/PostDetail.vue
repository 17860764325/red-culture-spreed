<template>
    <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
                @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
    <div>
<!--    <div class="fixed top-0 left-0 right-0 bottom-0 bg-gray-800 bg-opacity-75 flex justify-center items-center">-->
      <div class="bg-white p-6 rounded-md w-3/4 md:w-1/2">
        <h2 class="text-xl font-bold mb-2">{{ moduleData.title }}</h2>
        <p class="mb-4">{{ moduleData.content }}</p>
        <p class="text-gray-500">模块: {{ moduleData.moduleCode }}</p>
        <button @click="$emit('close')" class="bg-red-600 text-white px-3 py-1 rounded-md hover:bg-red-700">关闭</button>
      </div>
    </div>
    </BasicModal>
</template>

<script setup>
  import { defineProps, defineEmits, ref } from 'vue';
  import {BasicModal, useModalInner} from "@/components/Modal";


  const moduleData = ref({})
  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    // courseData.value = data.courseData
    moduleData.value = data.moduleForm
  });
  console.log("moduleData:")
  console.log(moduleData.value)

  const props = defineProps({
    post: {
      type: Object,
      required: true
    }
  });
  function getStart() {
//
  }

  const emits = defineEmits(['close']);
</script>

<style scoped>
  /* 可添加自定义样式 */
</style>
