<template>
  <div class="item">
    <div class="question-title">
      <h2>{{ props.currentItem.remark ? props.currentItem.remark : '' }}</h2>
    </div>
    <div class="answer-options">
      <a-radio-group v-model:value="value">
        <a-radio style="width: 100%;" v-for="item in answers" :key="item.answerCode" :value="item.answerCode" @click="chooseAnswer(item)">
          <h3>{{ item.answerName }}</h3>
        </a-radio>
      </a-radio-group>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { answerList } from '../../title/Title.api';

const emit = defineEmits(['score', 'canNext']);
const props = defineProps({
  currentItem: {
    type: Object,
    required: true,
  }
});

const answers = ref([]);

// 获取所有答案
async function getAllAnswer() {
  if (props.currentItem?.questionId === '999') {
    answers.value = [];
  } else {
    const res = await answerList({ questionId: props.currentItem?.questionId });
    console.log(res, 'answer');
    answers.value = res.records;
  }
}

getAllAnswer();

const currentAnswer = ref({});
function chooseAnswer(data) {
  currentAnswer.value = data;
}

const value = ref();

watch(
  () => props.currentItem,
  () => {
    getAllAnswer();
    const param = { title: props.currentItem, answer: currentAnswer.value };
    emit('score', param);
    value.value = null;
  }
);

watch(
  () => value.value,
  () => {
    console.log(value.value, '当前选项值');
    if (value.value !== null && value.value !== '' && value.value !== undefined) {
      console.log('可以进行下一题了');
      emit('canNext');
    }
  }
);
</script>

<style scoped>
.item {
  padding: 30px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 600px;
  margin: 30px auto;
}

.question-title {
  margin-bottom: 30px;
  text-align: center;
}

.question-title h2 {
  font-size: 28px;
  color: #2c3e50;
}

.answer-options {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.answer-options a-radio {
  margin-bottom: 15px;
  width: 90%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 15px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
  background-color: #f9f9f9;
}

.answer-options a-radio:hover {
  background-color: #e9ecef;
  transform: scale(1.03);
}

.answer-options a-radio h3 {
  font-size: 22px;
  color: #34495e;
  margin: 0;
  padding-left: 15px;
}
</style>
