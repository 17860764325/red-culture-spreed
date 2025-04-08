<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose title="文章查看" :width="800"
              @ok="closeMMM" @cancel="closeMMM">
    <!--    文章展示-->
    <div class="mt-2">
      <a-card id="" style="" :title="pageInfo.title">
        <a-row style="display: flex;justify-content: center;align-items: center;height: 200px;flex-direction: column">
          <h1>{{pageInfo.title}}</h1>
          <h3>{{pageInfo.createPeople}}</h3>
        </a-row>
<!--        <JEditor :value="pageInfo.about" />-->
        <a-row style="display: flex;justify-content: center;">
          <div style="width: 70%" v-html="pageInfo.about"></div>
        </a-row>
      </a-card>
    </div>
    <!-- 评论功能-->
    <a-row style="display: flex;flex-direction: column;align-items: center">
    <h1>相关评论：</h1>
    <!--    自己添加评论-->
    <div style="width: 80%">
      <a-car>
        <a-form-item>
          <a-textarea placeholder="请在这里输入你的评论！" style="s" v-model:value="value"></a-textarea>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="submitCommant">提交评论</a-button>
        </a-form-item>
      </a-car>
    </div>
    <!--    评论展示-->
    <div style="width: 80%;">
      <commant v-if="isCommant" :pageinfo="pageInfo"></commant>
    </div>
    </a-row>
  </BasicModal>
</template>
<script setup lang="ts">
import {nextTick, ref} from 'vue'
import {MarkdownViewer} from "@/components/Markdown";
import {BasicModal, useModalInner} from "@/components/Modal";
import commant from "./commant.vue"
import {saveOrUpdate} from "../../commant/CommantInfo.api"
import {useMessage} from "@/hooks/web/useMessage";
import JEditor from "@/components/Form/src/jeecg/components/JEditor.vue";
// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();
const isCommant = ref(false)
const value = ref()
const pageInfo = ref({})

const emit = defineEmits(["cancel"])

const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  pageInfo.value = data
  isCommant.value = true
});

const list = ref([])


async function submitCommant() {
  isCommant.value = false
  const data = {
    about: value.value,
    pageId: pageInfo.value.id,
  }
  await saveOrUpdate(data, false).then(res => {
    createMessage.info(res)
    value.value = []
    isCommant.value = true
  })
}

function closeMMM(){
  emit("cancel")
}

</script>
<style>

</style>
