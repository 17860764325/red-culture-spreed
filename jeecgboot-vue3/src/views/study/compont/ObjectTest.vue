<template>
    <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
                @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
  <div class="exam-container">
    <h1 class="exam-title">红色文化考试</h1>
    <div v-for="(question, index) in questions" :key="index" class="question-item">
      <p class="question-text">{{ index + 1 }}. {{ question.question }} ({{ question.score }} 分)</p>
      <div v-for="(option, optionIndex) in question.options" :key="optionIndex" class="answer-option">
        <input
          type="radio"
          :id="`question-${index}-option-${optionIndex}`"
          :name="`question-${index}`"
          v-model="selectedAnswers[index]"
          :value="optionIndex"
          @change="onOptionChange(index, optionIndex)"
          :style="{ accentColor: selectedAnswers[index] === optionIndex? 'red' : '' }"
        />
        <label
          :for="`question-${index}-option-${optionIndex}`"
          :style="{ color: selectedAnswers[index] === optionIndex? 'red' : '' }"
        >
          {{ String.fromCharCode(65 + optionIndex) }}. {{ option }}
        </label>
      </div>
    </div>
    <button class="submit-button" @click="submitExam">提交试卷</button>
  </div>
    </BasicModal>
</template>

<script setup>
  import { ref } from 'vue';
  import { randomQuery } from '../../courseExam/CourseExam.api'
  import {BasicModal, useModalInner} from "@/components/Modal";
  import { saveOrUpdate } from '../../examResult/ExamResult.api'
  const courseData = ref({});
  const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    courseData.value = data.courseData
  });
  const questions = ref([])

  const selectedAnswers = ref(new Array(questions.value.length).fill(null));

  const onOptionChange = (questionIndex, optionIndex) => {
    selectedAnswers.value[questionIndex] = optionIndex;
  };

  const submitExam = async () => {
    let totalScore = 0;
    questions.value.forEach((question, index) => {
      if (selectedAnswers.value[index] === question.answer) {
        totalScore += question.score;
      }
    });
    const saveForm = {}
    saveForm.score = totalScore
    saveForm.objectCode = courseData.value.objectCode
    await saveOrUpdate(saveForm, false);
    alert(`你的考试成绩是：${totalScore} 分`);
  };

  function getStart() {
    // 数据查询
    randomQuery({objectCode: courseData.value.objectCode}).then(res => {
      questions.value = res
    })
  }
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
