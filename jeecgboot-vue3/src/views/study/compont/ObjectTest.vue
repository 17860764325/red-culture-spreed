<template>
    <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
                @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
        <index v-if="courseData.objectCode" :objectCode="courseData.objectCode" ></index>
    </BasicModal>
</template>

<script setup>
  import { ref } from 'vue';
  import index from "../../test/testOnLine/index.vue"
  import {BasicModal, useModalInner} from "@/components/Modal";

  const courseData = ref({});
  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: false, showOkBtn: false});
    courseData.value = data.courseData
  });


</script>

<style scoped>
  .exam-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }

  .exam-title {
    text-align: center;
    color: #ff0000;
    font-size: 2em;
    margin-bottom: 20px;
  }

  .question-item {
    margin-bottom: 20px;
    border: 1px solid #ccc;
    padding: 10px;
    border-radius: 5px;
  }

  .question-text {
    font-size: 1.2em;
    margin-bottom: 10px;
  }

  .answer-option {
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }

  .answer-option input {
    margin-right: 10px;
  }

  .submit-button {
    display: block;
    margin: 0 auto;
    padding: 10px 20px;
    background-color: #ff0000;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1.2em;
  }

  .submit-button:hover {
    background-color: #cc0000;
  }
</style>
