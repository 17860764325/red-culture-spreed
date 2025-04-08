<template>
  <a-row>
    <a-col :span="24">
      <img class="imageBack" src="@/assets/images/shqudingbu.png"/>
    </a-col>
  </a-row>
    <a-card :title="props.item.text" style="width: 100%;margin: 10px">
      <template #extra><a-button @click="backIndex()">返回</a-button></template>
      <!--        专栏文章列表-->
<!--      <div v-for="no in pages">-->
<!--        <a @click="pageDetailOpen(no)">{{no.title}}</a>-->
<!--        <br/>-->
<!--      </div>-->
      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="pages">
        <template #footer>
          <a-divider style="height: 2px; background-color: #ff0303" />
        </template>
        <template #renderItem="{ item }">
          <a-list-item key="item.title">
            <template #actions>
          <span v-for="{ icon, text } in actions" :key="icon">
            <component :is="icon" style="margin-right: 8px" />
            {{ text }}
          </span>
            </template>

            <a-list-item-meta :description="item.description">
              <template #title>
                <h3>{{item.createPeople}}</h3>
                <a  @click="pageDetailOpen(item)">{{ item.title }}</a>
              </template>
              <template #avatar><a-avatar :src="item.avatar" /></template>
            </a-list-item-meta>
            {{ item.content }}
          </a-list-item>
        </template>
      </a-list>
    </a-card>
<!--  <pageDetail @register="registerModal"></pageDetail>-->
</template>
<script lang="ts" setup>
import {getAllPageQuery,list} from  "../../page/PageInfo.api"
import back from "@/views/demo/permission/back/index.vue";
import {ref} from "vue";
import {useModal} from "@/components/Modal";
import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
const listData: Record<string, string>[] = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://joeschmoe.io/api/v1/random',
    description:
      'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
      'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}

const pagination = {
  onChange: (page: number) => {
    console.log(page);
  },
  pageSize: 3,
};
const actions: Record<string, any>[] = [
  { icon: StarOutlined, text: '156' },
  { icon: LikeOutlined, text: '156' },
  { icon: MessageOutlined, text: '2' },
];
// const [registerModal, {openModal,setModalProps}] = useModal();

const emit =  defineEmits(["cancel","view"])

const props = defineProps({
  item: {
    type: Object
  }
})

// 文章详情的弹窗

// 所有文章 无限
const pages = ref([]);
async function  getAllPages(){
  await getAllPageQuery(props.item?.value).then((res) => {
    pages.value= res
  })
}
getAllPages()


function backIndex(){
  emit("cancel")
}

// 文章详情打开
function pageDetailOpen(data){
  // setModalProps({defaultFullscreen:true})
  // openModal(true ,data)
  emit("view",data)
}

</script>
<style>

</style>
