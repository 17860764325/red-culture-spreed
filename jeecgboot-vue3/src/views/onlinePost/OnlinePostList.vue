<template>
  <div class="bg-gray-100 min-h-screen">
    <!-- 头部 -->
    <header class="bg-red-600 text-white p-4 flex justify-between items-center">
      <h1 class="text-2xl font-bold">红色文化发帖网站</h1>
      <button @click="showCreatePost = true" class="bg-white text-red-600 px-3 py-1 rounded-md hover:bg-gray-200">发布帖子</button>
    </header>
    <!-- 模块区域 -->
    <main class="container mx-auto p-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div v-for="module in modules" :key="module.id" class="border border-gray-300 rounded-md p-4 bg-white">
        <h2 class="text-xl font-bold mb-2">{{ module.name }}</h2>
        <PostList :posts="module.posts" @post-clicked="showPost(module, $event)" @edit-post="editPost(module, $event)" />
      </div>
    </main>
    <!-- 帖子详情模态框 -->
<!--    <PostDetail @register="editModal" v-if="selectedPost" :post="selectedPost" @close="selectedPost = null" @edit="showEditPost = true; editPost = selectedPost" />-->
    <PostDetail @register="editModal"  />
    <!-- 发帖表单模态框 -->
    <CreatePost v-if="showCreatePost" @post-created="addPost" @cancel="showCreatePost = false" />
    <!-- 编辑帖子模态框 -->
<!--    <EditPost v-if="showEditPost" :post="editPost" @post-updated="updatePost" @cancel="showEditPost = false" />-->
    <EditPost v-if="showEditPost" :post="editPost" @post-updated="updatePost" @cancel="showEditPost = false" />
<!--    <EditPost v-if="showEditPost" @register="editModal" :post="editPost" @post-updated="updatePost" @cancel="showEditPost = false" />-->
  </div>
</template>

<script setup>
  import { ref } from 'vue';
  import {useModal} from "@/components/Modal";
  import PostList from '../onlinePost/components/PostList.vue';
  import PostDetail from '../onlinePost/components/PostDetail.vue';
  import CreatePost from '../onlinePost/components/CreatePost.vue';
  import EditPost from '../onlinePost/components/EditPost.vue';
  const [editModal, {openModal:openObjectEditModal}] = useModal();

  // 模拟模块和帖子数据
  const modules = ref([
    {
      id: 1,
      name: '红色历史',
      posts: [
        { id: 1, title: '南昌起义的意义', content: '南昌起义打响了武装反抗国民党反动派的第一枪。', author: 'user1' },
        { id: 2, title: '遵义会议的影响', content: '遵义会议是中国共产党历史上一个重要的转折点。', author: 'user1' }
      ]
    },
    {
      id: 2,
      name: '红色人物',
      posts: [
        { id: 3, title: '毛泽东的贡献', content: '毛泽东带领中国人民经过了长期的革命斗争，赢得民族独立和人民解放。', author: 'user2' },
        { id: 4, title: '周恩来的事迹', content: '周恩来为国家和人民无私奉献，深受人民爱戴。', author: 'user2' }
      ]
    }
  ]);

  const selectedPost = ref(null);
  const showCreatePost = ref(false);
  const showEditPost = ref(false);
  const currentUser = 'admin';
  const moduleForm = ref({})

  const editPost = (module, post) => {
    console.log(module)
    moduleForm.value = { ...moduleForm, moduleForm: module };
    showEditPost.value = true;
    // openObjectEditModal(true, {moduleForm: module});
  };

  const showPost = (module, post) => {
    openObjectEditModal(true, {moduleForm: post});
    selectedPost.value = { ...post, module: module.name };
  };

  const addPost = (newPost) => {
    const targetModule = modules.value.find((module) => module.name === newPost.module);
    if (targetModule) {
      const newId = targetModule.posts.length > 0 ? Math.max(...targetModule.posts.map((p) => p.id)) + 1 : 1;
      targetModule.posts.push({ ...newPost, id: newId, author: currentUser });
    }
    showCreatePost.value = false;
  };

  const updatePost = (updatedPost) => {
    const targetModule = modules.value.find((module) => module.name === updatedPost.module);
    if (targetModule) {
      const postIndex = targetModule.posts.findIndex((p) => p.id === updatedPost.id);
      if (postIndex!== -1) {
        targetModule.posts[postIndex] = updatedPost;
      }
    }
    showEditPost.value = false;
    selectedPost.value = null;
  };
</script>

<style scoped>
  /* 可添加自定义样式 */
</style>
