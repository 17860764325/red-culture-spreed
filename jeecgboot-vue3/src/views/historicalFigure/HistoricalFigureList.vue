<template>
  <a-row :gutter="20" justify="center">
    <a-col :span="6" v-for="(person, index) in historicalFigures" @click="handleClick(person)" :key="index">
      <div class="person-card" >
        <img  :src="'../../../../opt/upFiles/'+person.imgUrl" alt="人物图片" />
        <h2>{{ person.name }}</h2>
        <div style="height: 200px">
          <ScrollContainer >
            <p>
              {{ replaceMainAchievement(person.mainAchievement) }}
            </p>
          </ScrollContainer>
        </div>
      </div>
    </a-col>
  </a-row>
  <Detail @register="pageModal"></Detail>
</template>

<script setup>
  import {  ref } from "vue";
  import {list} from './HistoricalFigure.api'
  import {useModal} from "../../components/Modal";
  import Detail from './components/HistoricalDetail.vue'
  const [pageModal, {openModal:detailModal}] = useModal();

  // detailModal(true, {courseData: courseData.value});

  const historicalFigures = ref([]);
  list({pageNo:1,pageSize:1000}).then(res => {
    historicalFigures.value = res.records;
  })

  function replaceMainAchievement(mainAchievement) {
    // 使用正则表达式去除 HTML 标签
    const regex = /<[^>]*>/g;
    return mainAchievement.replace(regex, '');
  }

  function handleClick(persion) {
    detailModal(true, {persion: persion});
  }
</script>

<style scoped>
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 20px;
  }

  .person-card {
    width: 100%;
    padding: 20px;
    position: relative;
    color: #f66e6e;
    text-align: center;
    overflow: hidden;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    transition: transform 0.3s ease;
  }

  .person-card:hover {
    transform: scale(1.05);
  }

  .person-card::before {
    content: "";
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: linear-gradient(45deg, #ffd700, #ff6600, #ffd700, #ff6600);
    background-size: 200% 200%;
    animation: borderAnimation 4s linear infinite;
    z-index: -1;
  }

  .person-card::after {
    content: "";
    position: absolute;
    top: 5px;
    left: 5px;
    right: 5px;
    bottom: 5px;
    background-color: #ff0000;
    z-index: -1;
    border-radius: 8px;
  }

  .person-card img {
    width: 100%;
    height: auto;
    border-radius: 5px;
    margin-bottom: 10px;
    object-fit: cover;
  }

  .person-card h2 {
    margin-bottom: 10px;
    font-size: 1.5em;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  }

  .person-card p {
    font-size: 0.9em;
    line-height: 1.4;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
    color: #0d1117;
  }

  @keyframes borderAnimation {
    0% {
      background-position: 0 0;
    }
    100% {
      background-position: 200% 200%;
    }
  }

  @media (max-width: 1200px) {
    .ant-col {
      width: calc(33.33% - 20px);
    }
  }

  @media (max-width: 900px) {
    .ant-col {
      width: calc(50% - 20px);
    }
  }

  @media (max-width: 600px) {
    .ant-col {
      width: 100%;
    }
  }
</style>
