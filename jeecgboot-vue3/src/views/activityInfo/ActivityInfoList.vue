<template>
  <div id="app">
    <!-- 页面头部，包含活动标题 -->
    <header class="header">
      <h1 class="activity-title" style="text-align: center;">红色主题活动报名</h1>
    </header>
    <!-- 活动详细内容区域 -->
    <main class="main-content" style="text-align: center;">
      <p>活动目的：</p>
      <p class="content" >
        本次红色主题活动旨在传承红色基因，弘扬革命精神。我们将通过参观红色纪念馆、邀请老革命家讲述历史故事等丰富多样的形式，让大家深入了解革命先辈们的英勇事迹和伟大精神。活动时间为[具体时间]，地点在[详细地点]。欢迎各位积极参与，一同感受红色文化的魅力！
      </p>
      <!-- 这里可以继续添加更多详细内容，如活动流程、注意事项等 -->
    </main>

<!-- 活动模块   -->
    <a-card style="margin-top: 30px;">
      <div class="container">
        <div class="left-part">
          <h2>活动模块</h2>
        </div>
        <div class="right-part">
          <div v-for="(item, index) in activityModuleOptions" :key="index"
               class="item-box"
               :class="[{active: currentIndex === index}, {highlight: isHovering[index]}]"
               @click="selectItem(item.value)"
               @mouseenter="handleMouseEnter(index)"
               @mouseleave="handleMouseLeave(index)">
            {{ item.label }}
          </div>
        </div>
      </div>
    </a-card>

<!--  具体的活动  -->
    <div class="container1">
      <div v-for="(item, index) in data" :key="index" class="case-item">
        <a-card>
          <div class="case-header">
            <h3>{{ item.activityName }}</h3>
          </div>
          <div class="case-content">
            <p class="truncated-text">{{ replaceContent(item.content) }}</p>
            <a href="#" class="more-link" @click="detailClick(item)">[详情]</a>
          </div>
        </a-card>
      </div>
    </div>
  </div>
  <Detail @register="pageModal"></Detail>
</template>

<script setup>
  // 这里目前没有复杂的逻辑，后续可根据需求添加
  import {ref} from 'vue';
  import {list} from './ActivityInfo.api'
  import {initDictOptions} from '/@/utils/dict/JDictSelectUtil';
  import {useModal} from "../../components/Modal";
  const data = ref([])
  const [pageModal, {openModal:openObjectModal}] = useModal();

  const activityModuleOptions = ref([]);

  // 获取活动模块字典值
  initDictOptions( 'activityModule').then(res => {
    activityModuleOptions.value = res;
  })

  // 用于记录每个元素的鼠标悬浮状态
  const isHovering = ref(new Array(data.value.length).fill(false));

  const allData = ref([])
  list({pageNo:1,pageSize:1000}).then(res => {
    allData.value = res.records
    data.value = allData.value
  })

  function selectItem(moduleCode) {
    data.value = allData.value.filter(item => item.activityModule === moduleCode);
  }

  // 鼠标进入事件处理函数
  const handleMouseEnter = (index) => {
    isHovering.value[index] = true;
  };

  // 鼠标离开事件处理函数
  const handleMouseLeave = (index) => {
    isHovering.value[index] = false;
  };

  function replaceContent(content) {
    if (content !== undefined) {
      // 使用正则表达式去除 HTML 标签
      const regex = /<[^>]*>/g;
      return content.replace(regex, '');
    } else {
      return content;
    }

  }

  function detailClick(item) {
// 详情点击触发事件
    openObjectModal(true, {activityInfo: item});
  }

</script>

<style scoped>
  /*#app {*/
  /*  font-family: Arial, sans-serif;*/
  /*  text-align: center;*/
  /*  background-color: #f0f0f0;*/
  /*  padding: 20px;*/
  /*}*/

  .header {
    background-color: #ff0000;
    color: white;
    padding: 20px;
  }

  .activity-title {
    margin: 0;
  }

  .main-content {
    background-color: white;
    padding: 20px;
    margin-top: 20px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  }

  .content {
    text-align: justify;
  }

  .signup-form label {
    display: block;
    margin-bottom: 5px;
  }

  .signup-form input {
    width: 300px;
    padding: 10px;
    margin-bottom: 15px;
  }

  .signup-form button {
    padding: 10px 20px;
    background-color: #ff0000;
    color: white;
    border: none;
    cursor: pointer;
  }

  .container {
    display: flex;
    background-color: #f0f0f0;
  }

  .left-part {
            width: 150px;
            background-color: #d60000;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            text-align: center;
          }

  .right-part {
             flex: 1;
             display: flex;
             flex-wrap: wrap;
             padding: 10px;
           }

  .item-box {
            color: #d60000;
            margin: 10px;
            padding: 5px;
            cursor: pointer;
          }


  .container1 {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 20px;
  }

  .case-item {
    background-color: #f0f0f0;
    padding: 10px;
    border-radius: 5px;
  }

  .case-header {
    margin-bottom: 10px;
  }

  .case-content {
    margin-bottom: 10px;
  }

  .more-link {
    color: #007bff;
    text-decoration: none;
  }

  .share-icon {
    text-align: right;
  }

  .truncated-text {
    margin: 0;
    text-overflow: ellipsis; /* 超出部分显示省略号 */
    white-space: nowrap; /* 不换行 */
    overflow: hidden;
    width: 300px; /* 可以根据需要调整宽度 */
    height: 80px; /* 可以根据需要调整高度 */
  }

</style>
