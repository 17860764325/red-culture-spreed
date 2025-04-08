<template>
  <ul>
    <li v-for="post in posts" :key="post.id" class="mb-1 flex items-center">
      <button @click="$emit('post-clicked', post)" class="text-blue-600 hover:underline">
        {{ post.title }}
      </button>
<!--      <button v-if="post.author === currentUser" @click="$emit('edit-post', post)" class="ml-2 bg-yellow-600 text-white px-2 py-1 rounded-md hover:bg-yellow-700">-->
<!--      <button  @click="$emit('edit-post', post)" class="ml-2 bg-yellow-600 text-white px-2 py-1 rounded-md hover:bg-yellow-700">-->
<!--        编辑-->
<!--      </button>-->
    </li>
  </ul>
</template>

<script setup>
  import { defineProps, defineEmits } from 'vue';

  const props = defineProps({
    posts: {
      type: Array,
      required: true
    },
    currentUser: {
      type: String,
      required: true
    }
  });

  const emits = defineEmits(['post-clicked', 'edit-post']);
</script>

<style scoped>
  /* 可添加自定义样式 */
</style>
