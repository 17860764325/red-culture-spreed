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
  import Detail from './components/ActivityInfoDetail.vue'
  import {initDictOptions} from '/@/utils/dict/JDictSelectUtil';
  import {useModal} from "../../components/Modal";
  import {omit} from "lodash-es";
  const moduleData = ref([{value: 'banbao', lable: '板报'}, {value: 'banbao1', lable: '板报1'}])
  const data = ref([])
  const [pageModal, {openModal:openObjectModal}] = useModal();

  const activityModuleOptions = ref([]);

  // 获取活动模块字典值
  initDictOptions( 'activityModule').then(res => {
    activityModuleOptions.value = res;
    console.log("activityModuleOptions:")
    console.log(activityModuleOptions.value)
  })

  const caseList = ref([
    {
      title: '新疆生产建设兵团第九师白杨市: 红色白杨心向党 戍边堡垒...',
      content: '第九师白杨市始终牢记职责使命，传承兵团精神和胡杨精神、老兵精神，发扬生产队、工作队、战斗队的……'
    },
    {
      title: '广西玉林市: 推行“一网三联两平台”模式 提升城市基层党...',
      content: '随着城市化进程加快，住宅小区不断增多，外来流动人口也不断增多，小区内矛盾纠纷案件频发，同时也……'
    },
    {
      title: '山东青岛市市北区即墨路街道: 以“一核三治九法”铸强多元...',
      content: '近年来，山东省青岛市市北区即墨路街道聚焦“即办先锋·墨蕴初心”党建品牌，锚定“新航运·红里院”……'
    },
    {
      title: '山东安丘市: “鸿雁兴安”工程为流动党员“安家”',
      content: '为了建强流动党员党组织，加强流动党员教育管理，2017年以来，山东省安丘市以流动党员相对集中……'
    },
    {
      title: '江苏常州高新区 (新北区) : “四融”街区治理模式 打造高...',
      content: '近年来，常州高新区（新北区）围绕汉江路街区聚力打造“高品质国际街区”的目标定位，始终坚持党建引领……'
    },
    {
      title: '湖北荆州市沙市区: 探索“三议三联三公开”机制 推动居民...',
      content: '近年来，荆州市沙市区在深化共同缔造、推进党建引领美好小区建设的实践中，充分发挥居民小区党组织战斗堡垒作用……'
    },
    {
      title: '福建厦门市湖里区湖里街道: 党建引领齐发力 谱写城中村现...',
      content: '自后浦社城中村综合治理以来，湖里街道以近邻党建为引领，以“两改造三提升”为抓手，聚焦城中村治理难点……'
    },
    {
      title: '青海西宁市: “每月一题”夯实基层基础',
      content: '青海省西宁市探索建立夯实基层基础“每月一题”工作机制，市委每个月专门召开一次常委会会议，采取“每月一题”的方式……'
    },
    {
      title: '重庆市南岸区住房城乡建委: “334”工作模式推动老旧小...',
      content: '为深入推进辖区老旧小区改造，南岸区住房城乡建委党组坚持党建统领，将基层治理触角向老旧小区改造延伸……'
    }
  ]);

  // 用于记录每个元素的鼠标悬浮状态
  const isHovering = ref(new Array(data.value.length).fill(false));

  const allData = ref([])
  list({pageNo:1,pageSize:1000}).then(res => {
    allData.value = res.records
    data.value = allData.value
    // data.value.push(data.value[0])
    // data.value.push(data.value[0])
    // console.log(data.value)
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
