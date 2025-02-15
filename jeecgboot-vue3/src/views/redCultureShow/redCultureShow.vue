<template>
  <a-row>
    <!--  图片展示区，首页上面的大图-->
    <img style="width: 100%;" src="../../../static/page.jpg">
  </a-row>
  <a-row>
    <!--    锚点导航栏-->
    <div style="padding: 20px">
      <div id="app">
        <!-- 导航栏，包含锚点链接 -->
        <nav>
          <ul style="display: flex;justify-content: space-evenly;font-size: 20px">
            <li><a @click="scrollToTarget('1')">首页</a></li>
            <li><a @click="scrollToTarget('2')">内涵阐释</a></li>
            <li><a @click="scrollToTarget('3')">心得感悟</a></li>
            <li><a @click="scrollToTarget('4')">传承实践</a></li>
          </ul>
        </nav>
        <!-- 页面内容 -->
        <div id="section2" style="display: flex">
          <a-col :span="8">
            <img src="../../../static/book.jpg">
          </a-col>
          <a-col ref="targetRef">
            <h2 ref="targetRef">《中国共产党人的精神谱系》</h2>
            <p style="line-height: 4;font-size: 16px">
              2021年7月1日，习近平总书记在庆祝中国共产党成立100周年大会上发表重要讲话指出：“一百年前，中国共产党的先驱们创建了中国共产党，形成了坚持真理、坚守理想，践行初心、担当使命，不怕牺牲、英勇斗争，对党忠诚、不负人民的伟大建党精神，这是中国共产党的精神之源。”一百年来，中国共产党弘扬伟大建党精神，在长期奋斗中构建起中国共产党人的精神谱系，锤炼出鲜明的政治品格，为我们立党兴党强党提供了丰厚滋养。经党中央批准，中宣部发布了第一批纳入精神谱系的精神。

              2021年7月19日至12月17日，《人民日报》推出“奋斗百年路
              启航新征程·中国共产党人的精神谱系”专栏，充分展示我们党在革命战争时期、社会主义建设时期、改革开放时期和中国特色社会主义新时代形成的共产党人的精神，回溯这些宝贵精神所产生的历史背景、形成过程，深入挖掘其丰富内涵和时代价值，系统展示其现实意义和重要作用，展现精神发源地今日的经济社会发展成就和人民群众精神风貌。经人民日报社批准，《人民周刊》收录《人民日报》刊发的阐释的49种精神的系列评论和专版报道196篇，结集出版《中国共产党人的精神谱系》增刊，作为各级干部和广大党员开展党史学习教育的重要案头资料、提高政治站位和思想理论水平的必备工具书。

              增刊出版后，《人民周刊》开设《精神谱系大家谈》栏目，邀请专家学者、领导干部、党员群众一起，围绕精神谱系谈认识、说体会、话心得，把精神谱系的宣传和学习向纵深推进。
            </p>
          </a-col>

        </div>
        <div id="section2" ref="targetRef1">
          <a-divider><h2 style="color: darkred">内涵阐释</h2><a>更多>></a></a-divider>

          <a-row style="display: flex;justify-content: center">
            <a-card v-for="item in modalOne" style="background-color: #E9E9E9;width: 35%;margin: 4px" @click="openPageInfo(item.pageNumber,'内涵阐释')">
              <a-typography>
                <a-typography-paragraph>
                  <blockquote><h2>{{item.spiritName}}</h2></blockquote>
                  <p style="font-size: 20px"><span
                    style="color: darkred;font-size: 20px;align-items: center">·  </span>{{ item.overviewOne }}
                  </p>
                  <p style="font-size: 20px"><span
                    style="color: darkred;font-size: 20px;align-items: center">·  </span>{{ item.overviewTwo }}
                  </p>
                  <p style="font-size: 20px"><span
                    style="color: darkred;font-size: 20px;align-items: center">·  </span>{{ item.overviewThree }}
                  </p>
                  <p style="font-size: 20px"><span
                    style="color: darkred;font-size: 20px;align-items: center">·  </span>{{ item.overviewFour }}
                  </p>
                </a-typography-paragraph>
              </a-typography>
            </a-card>
          </a-row>
        </div>
        <div id="section2" ref="targetRef2">
          <a-divider><h2 style="color: darkred">心得感悟</h2><a>更多>></a></a-divider>
          <a-row style="display: flex;justify-content: center">
            <a-card v-for="item in modalTwo" style="background-color: #E9E9E9;width: 35%;margin: 4px" @click="openPageInfo(item.pageNumber,'心得感悟')">
              <a-typography>
                <a-typography-paragraph>
                  <blockquote><h2>{{item.spiritName}}</h2></blockquote>
                  <p style="font-size: 16px">
                   {{item.overviewOne}}
                  </p>
                </a-typography-paragraph>
              </a-typography>
            </a-card>
          </a-row>
        </div>
        <div id="section2" ref="targetRef3">
          <a-divider><h2 style="color: darkred">传承实践</h2><a>更多>></a></a-divider>
          <a-row style="display: flex;justify-content: center">
            <a-card v-for="item in modalThree" style="background-color: #E9E9E9;width: 35%;margin: 4px" @click="openPageInfo(item.pageNumber,'传承实践')">
              <a-typography>
                <a-typography-paragraph>
                  <blockquote><h2>{{item.spiritName}}</h2></blockquote>
                  <p style="font-size: 16px">
                    {{item.overviewOne}}
                  </p>
                </a-typography-paragraph>
              </a-typography>
            </a-card>
          </a-row>
        </div>

      </div>
    </div>
  </a-row>
  <pageContext @register="pageModal" @success="handleSuccess"></pageContext>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import pageContext from './compent/pageContext.vue'
import {useModal} from '/src/components/Modal';
import {list} from '/src/views/redSpirit/RedSpirit.api'

const [pageModal, {openModal:openPageModal}] = useModal();

// 创建一个 ref 来引用目标元素
const targetRef = ref(null);
const targetRef1 = ref(null);
const targetRef2 = ref(null);
const targetRef3 = ref(null);

// 定义点击事件处理函数
function scrollToTarget(flag) {
  console.log(flag)
  switch (flag) {
    case '1':
      console.log("1")
      if (targetRef.value) {
        // 调用 scrollIntoView 方法将目标元素滚动到可见区域
        targetRef.value.scrollIntoView({
          behavior: 'smooth', // 平滑滚动
          block: 'center' // 滚动到元素的顶部
        });
      }
      break
    case '2':
      console.log("2")
      if (targetRef1.value) {
        // 调用 scrollIntoView 方法将目标元素滚动到可见区域
        targetRef1.value.scrollIntoView({
          behavior: 'smooth', // 平滑滚动
          block: 'start' // 滚动到元素的顶部
        });
      }
      break
    case '3':
      console.log("3")
      if (targetRef2.value) {
        // 调用 scrollIntoView 方法将目标元素滚动到可见区域
        targetRef2.value.scrollIntoView({
          behavior: 'smooth', // 平滑滚动
          block: 'start' // 滚动到元素的顶部
        });
      }
      break
    case '4':
      console.log("4")
      if (targetRef3.value) {
        // 调用 scrollIntoView 方法将目标元素滚动到可见区域
        targetRef3.value.scrollIntoView({
          behavior: 'smooth', // 平滑滚动
          block: 'start' // 滚动到元素的顶部
        });
      }
      break
    default:
      break
  }
};
// 打开具体文章的方法，打开弹窗展示文章界面
function openPageInfo(pageNumber,flagName){
  // 根据文章的编码获文章的内容并跳转到相关页面
  console.log(pageNumber)
  // 打开页面并传输文章编码
  openPageModal(true, {pageNumber: pageNumber,flagName:flagName});
}


const listData = ref<Array>([])
const modalOne = ref<Array>([])
const modalTwo = ref<Array>([])
const modalThree = ref<Array>([])

// 获取数据
list().then(res =>{
  // 获取所有文章的列表，并将获取的数据进行存储
  listData.value = res.records
  // 将数据分为三个板块，内涵阐释，心得感悟，传承实践
  modalOne.value = listData.value.filter(item =>item.pageType === '1')
  modalTwo.value = listData.value.filter(item =>item.pageType === '2')
  modalThree.value = listData.value.filter(item =>item.pageType === '3')
  console.log(res.records)
})

</script>

<style scoped lang="less">
nav ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: flex;
  background-color: #c62828;
}

nav ul li {
  margin: 10px;
}

nav ul li a {
  color: white;
  text-decoration: none;
}

div[id^="section"] {
  padding: 20px;
  margin: 20px 0;
  //border: 1px solid #ccc;
}

button {
  margin: 10px;
  padding: 10px 20px;
  background-color: #c62828;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
